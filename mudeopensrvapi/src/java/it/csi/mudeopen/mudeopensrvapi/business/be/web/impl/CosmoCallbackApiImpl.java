/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.InviaSegnaleFruitoreResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.cosmo.AssegnazioneRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.StatiProcessoCosmoEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvapi.business.be.web.CosmoCallbackApi;
import it.csi.mudeopen.mudeopensrvapi.vo.DatiModificaStatoIstanza;
import it.csi.mudeopen.mudeopensrvapi.vo.cosmo.AggiornaStatoPraticaRequest;
import it.csi.mudeopen.mudeopensrvapi.vo.cosmo.DocumentoFruitore;
import it.csi.mudeopen.mudeopensrvapi.vo.cosmo.Esito;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDWfStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaCosmo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.MudeCosmoManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDocpaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDWfStatiRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeREnteTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaCosmoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.TipoDocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaSlimVO;

@Component
public class CosmoCallbackApiImpl extends StatiIstanzaApiImpl implements CosmoCallbackApi {
	 
	private static final String DESCRIPTION_NOT_FOUND_LA_PRATICA_SPECIFICATA_NON_E_STATA_TROVATA = "description: Not found - la pratica specificata non e' stata trovata";
	private static final String PRATICA_COSMO_NOT_FOUND_LA_PRATICA_SPECIFICATA_NON_E_STATA_TROVATA = "description: Pratica cosmo non trovata - la pratica specificata non e' stata trovata";

	private static Logger logger = Logger.getLogger(CosmoCallbackApiImpl.class.getCanonicalName());
	
	@Autowired
	private MudeTIstanzaRepository mudeTIstanzaRepository;
	
	@Autowired
	private IstanzaEntityMapper istanzaEntityMapper;
	
	@Autowired
	private MudeREnteTipoIstanzaRepository mudeREnteTipoIstanzaRepository;
	
	@Autowired
	private MudeDWfStatiRepository mudeDWfStatiRepository;
	
    @Autowired
    private MudeCosmoManager mudeCosmoManager;
    
    @Autowired
    private MudeDTipoDocpaRepository mudeDTipoDocpaRepository;
    
    @Autowired
    private MudeTPraticaRepository mudeTPraticaRepository;
    
    @Autowired
    private MudeTPraticaCosmoRepository mudeTPraticaCosmoRepository;
    
	@Override
	public Response callbackPutAssegnaPratica(SecurityContext securityContext, 
												String codIstanzaPraticaCosmo,
												AssegnazioneRequest assegnazioneRequest) {
		String errMsg;
		MudeTPraticaCosmo mudeTPraticaCosmo = null;
		
		try {
	        String input = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(assegnazioneRequest);
	        LoggerUtil.info(logger, "[CosmoCallbackApi::callbackPutAssegnaPratica] input: " + input);
	        
			MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findByCodIstanza(codIstanzaPraticaCosmo.replace("-CC", ""));
			if(mudeTIstanza == null)
				return stringToResponse(DESCRIPTION_NOT_FOUND_LA_PRATICA_SPECIFICATA_NON_E_STATA_TROVATA, 404);
			
			MudeTIstanza mudeTIstanzaDS = mudeTIstanzaRepository.retrieveMainDenunciaSismica(mudeTIstanza.getId());
			if(mudeTIstanzaDS == null)
				return stringToResponse(DESCRIPTION_NOT_FOUND_LA_PRATICA_SPECIFICATA_NON_E_STATA_TROVATA, 404);

			if((mudeTPraticaCosmo = mudeTPraticaCosmoRepository.findByIdIstanza(mudeTIstanzaDS.getId())) == null)
				return stringToResponse(PRATICA_COSMO_NOT_FOUND_LA_PRATICA_SPECIFICATA_NON_E_STATA_TROVATA, 404);
				
			mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + "callbackPutAssegnaPratica input: " + input + "\r\n\r\n"); 
			mudeTPraticaCosmo.setJsonAssegnatari(input);
			
			Esito esito = new Esito();
			esito.setCode("OK");
			esito.setStatus(200);
			esito.setTitle("La richiesta di assegnazione è stata elaborata correttamente");
			
			mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + "callbackPutAssegnaPratica RESULT: OK\r\n\r\n");
			mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
			
			return voToResponse(esito, HttpStatus.SC_OK);
		} catch (BusinessException be) {
	        LoggerUtil.info(logger, "[CosmoCallbackApi::callbackPutAssegnaPratica] EXCEPTION: " + ExceptionUtils.getStackTrace(be));
	        errMsg = be.getMessage();

		} catch (Exception e) {
			LoggerUtil.info(logger, "[CosmoCallbackApi::callbackPutAssegnaPratica] EXCEPTION: bad request " + ExceptionUtils.getStackTrace(e));
			errMsg = "Bad Request - i dati inviati non sono formalmente corretti: " + ExceptionUtils.getStackTrace(e);
		}
		
		if(mudeTPraticaCosmo != null) {
			mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + "["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] callbackPutStatoPratica input: " + errMsg + "\r\n\r\n");
			mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		}
		
		return stringToResponse(errMsg, 400);
	}
	
	@Override
	public Response callbackPutStatoPratica(SecurityContext securityContext, 
												String codIstanzaPraticaCosmo,
												AggiornaStatoPraticaRequest aggiornaStatoPraticaRequest) {
		String errMsg;
		MudeTPraticaCosmo mudeTPraticaCosmo = null;
		
		try {
	        String input = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(aggiornaStatoPraticaRequest);
	        LoggerUtil.info(logger, "[CosmoCallbackApi::callbackPutStatoPratica] input: " + input);
	        
			MudeTIstanza mudeTIstanzaDS = mudeTIstanzaRepository.findByCodIstanza(aggiornaStatoPraticaRequest.getIdRiferimento().replace("-CC", ""));
			if(mudeTIstanzaDS == null)
				return stringToResponse(DESCRIPTION_NOT_FOUND_LA_PRATICA_SPECIFICATA_NON_E_STATA_TROVATA, 404);

			if((mudeTPraticaCosmo = mudeTPraticaCosmoRepository.findByIdIstanza(mudeTIstanzaDS.getId())) == null)
				return stringToResponse(PRATICA_COSMO_NOT_FOUND_LA_PRATICA_SPECIFICATA_NON_E_STATA_TROVATA, 404);

			mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + "callbackPutStatoPratica input: " + input + "\r\n\r\n"); 
			
			String codEventSimplified = aggiornaStatoPraticaRequest.getCodice() == null? "" : aggiornaStatoPraticaRequest.getCodice().replaceAll("[ _-]", " ").trim();

			IstanzaVO istanzaFigliaVO = null;
			MudeTIstanza mudeTIstanzaFiglia = null;
			if("FIL-DS".equals(mudeTIstanzaDS.getTipoIstanza().getCodice()) && !"".equals(codEventSimplified)) {
				mudeTIstanzaFiglia = mudeTIstanzaDS;
				mudeTIstanzaDS = mudeTIstanzaRepository.findOne(mudeTIstanzaDS.getIdIstanzaRiferimento());

				istanzaFigliaVO = istanzaEntityMapper.mapEntityToVO(mudeTIstanzaFiglia, null);
			}
			else if(aggiornaStatoPraticaRequest.getIdFiglia() != null) {
				if((mudeTIstanzaFiglia = mudeTIstanzaRepository.findByCodIstanza(aggiornaStatoPraticaRequest.getIdFiglia())) == null)
					return stringToResponse(DESCRIPTION_NOT_FOUND_LA_PRATICA_SPECIFICATA_NON_E_STATA_TROVATA, 404);

				istanzaFigliaVO = istanzaEntityMapper.mapEntityToVO(mudeTIstanzaFiglia, null);
			}
				
			
			IstanzaVO istanzaVO = istanzaEntityMapper.mapEntityToVO(mudeTIstanzaDS, null);
			
			
			/*
				{
				    "annoPratica": "2024",
				    "idRiferimento": "01-001272-8000009077-2023",
				    "numeroPratica": "DB2000/00000053/2024",
				    "numeroProtocollo": "DB2000/00000053/2024"
				    
				    "codice": "registrata-da-apa",
				    "codiceFiscaleUtente": "AAAAAA00A11B000J",
				    "dataCambioStato": 1705936746000,
				    "dataProtocollo": 1705881600000,
				    "documenti": [
				    ],
				    "idFiglia": null,
				}
				
		         <datiModifica>
		            <numeroIstanza>0100101100000048492023</numeroIstanza>
		            <dataProtocollo>2023-01-12</dataProtocollo>
		            <annoPratica>2023</annoPratica>
		            <numeroPraticaComunale>2023011</numeroPraticaComunale>
		            <numeroProtocollo>0000112</numeroProtocollo>
		            <nuovoStato>APA</nuovoStato>
		            
		            <oggetto>Oggetto Notifica da WS</oggetto>
		            <testo>Testo Notifica da WS</testo>
		            <responsabileProcedimento>DEMO24</responsabileProcedimento>
		         </datiModifica>
			 */
			
			if("registrata da apa".equalsIgnoreCase(codEventSimplified) || "registrata da pa".equalsIgnoreCase(codEventSimplified))
				changeState("APA", codIstanzaPraticaCosmo, aggiornaStatoPraticaRequest, mudeTIstanzaDS, istanzaVO, null);
			else {
				String newStateDS = null;
				String newStateFiglia = null;
				String propagetEventSignal = null;
				
				String docTypeCodeDS = "NEWONE";
				String docTypeCodeFiglia = "NEWONE";
				
				if("in attesa integrazione".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE: 
						•	Modificare lo stato dell’istanza di DS in ATI (stato in sola visualizzazione nel BO di MUDEOPEN)
						•	Inserire il documento ricevuto in ingresso nella tabella mudeopen_t_documento e associarlo alla pratica relativa alla DS.
						•	Il tipo documento PA da attribuire sarà INTDOC.
						•	Inviare la notifica di cambio stato
					 */
					newStateDS = "ATI"; // ATTESA INTEGRAZIONE --- DOCPA: 
					docTypeCodeDS = "INTDOR";
				}
				if("scadenza attesa integrazione".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza di DS padre in SIA (stato in sola visualizzazione nel BO di MUDEOPEN)
						•	Inviare notifica di cambio stato da MUDEOPEN
					 */
					newStateDS = "SIA"; // INTEGRAZIONE DOCUMENTALE AVVENUTA
				}
				if("arrivo integrazione".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza di DS padre in INT (stato in sola visualizzazione nel BO di MUDEOPEN)
						•	Inviare la notifica di cambio stato in APA della INT-DOC e della DS padre in INT
						•	Modificare lo stato dell’istanza INT-DOC in APA, associare la INT-DOC alla pratica della DS padre e impostare come numero protocollo il valore contenuto nel campo NumeroProtocollo della callback. 
					 */
					newStateDS = "INT"; // INTEGRAZIONE DOCUMENTALE AVVENUTA
					docTypeCodeDS = "INTDOC";
					
					newStateFiglia = "APA";
					docTypeCodeFiglia = "INTDOC";
				}
				if("rifiutata".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE: 
						•	Inviare la notifica di cambio stato
						•	Modificare lo stato dell’istanza DS padre in RES (stato in sola visualizzazione nel BO di MUDEOPEN)
						•	Inserire il documento ricevuto in ingresso nella tabella mudeopen_t_documento e associarlo alla pratica relativa alla DS.
					 * TBD: 
						•	Il tipo documento PA da attribuire sarà.Da cendsire nuova tipologia?
					 */
					 newStateDS = "RES"; // RESPINTA
				}
				if("accettata".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE: 
						•	Modificare lo stato dell’istanza di DS in ACC “Accettata” 
						•	Inserire il documento ricevuto in ingresso nella tabella mudeopen_t_documento e associarlo alla pratica relativa alla DS.
					 * TODO: 
						•	In questo caso NON inviare la notifica di cambio in quanto l’istanza era già stata accettata.
					 */
					MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findByCodIstanza(aggiornaStatoPraticaRequest.getIdRiferimento().replace("-CC", ""));
					if(mudeTIstanza != null 
							&& "DENUNCIA-SISMICA".equals(mudeTIstanza.getTipoIstanza().getCodice()) 
							&& mudeTPraticaCosmoRepository.findByIdIstanza(mudeTIstanza.getId()).getCcSelezionato())
				    	newStateDS = "ACO"; // ACCETTATA
				    else
						propagetEventSignal = newStateDS = "ACC"; // ACCETTATA
				}
				if("in attesa di fine lavori".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * TBD: 
						•	Il tipo documento PA da attribuire sarà.Da cendsire nuova tipologia?
					 * DONE: 
						•	Modificare lo stato dell’istanza di DS in AFL (stato in sola visualizzazione nel BO di MUDEOPEN)
						•	Inserire il documento ricevuto in ingresso nella tabella mudeopen_t_documento e associarlo alla pratica relativa alla DS.
					 */
					
					//					newStateDS = "AFL"; // ATTESA FINE LAVORI ------ DISMISSED!!!
					propagetEventSignal = newStateDS = "ACC"; // ACCETTATA
				}
				if("in controllo a campione".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza di DS in ICO
						•	Inviare la notifica di cambio stato
					 */
					newStateDS = "ICO"; // INTEGRAZIONE DOCUMENTALE AVVENUTA
				}
				if("istruttoria controllo a campione".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza di DS in ICI “Istruttoria Controllo a Campione” 
						•	Inviare la notifica di cambio stato
						•	Inserire il documento ricevuto in ingresso nella tabella mudeopen_t_documento e associarlo alla pratica relativa alla DS.
					 * TBD: 
						•	Il tipo documento PA da attribuire sarà (TBD...)  
					 */
					newStateDS = "ICI"; // “Istruttoria Controllo a Campione”
				}
				if("in attesa integrazione controllo a campione".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza di DS in AIC (stato in sola visualizzazione nel BO di MUDEOPEN)
						•	Inviare la notifica di cambio stato
						•	Inserire il documento ricevuto in ingresso nella tabella mudeopen_t_documento e associarlo alla pratica relativa alla DS.
						•	Il tipo documento PA da attribuire sarà INTDOC.
					 */
					newStateDS = "AIC"; // “Istruttoria Controllo a Campione”
					docTypeCodeDS = "INTDOR";
					
				}
				if("scadenza attesa integrazione controllo campione".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza di DS in SIC
						•	Inviare la notifica di cambio stato
					 */
					newStateDS = "SIC"; // “Scadenza attesa integrazione controllo a campione”
				}
				if("ricezione integrazione controllo a campione".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza di DS padre in INC (stato in sola visualizzazione nel BO di MUDEOPEN)
						•	Inviare la notifica di cambio stato in APA della INT-DOC .
						•	Modificare lo stato dell’istanza INT-DOC in APA, associare la INT-DOC alla pratica della DS padre e impostare come numero protocollo il valore contenuto nel campo NumeroProtocollo della callback.
					 */
					newStateDS = "INC"; // INC “Ricezione integrazione controllo a campione”
					docTypeCodeDS = "INTDOC";
					
					newStateFiglia = "APA";
					docTypeCodeFiglia = "INTDOC";
				}
				if("in vigilanza".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza di DS in VIG“In vigilanza” 
						•	Inviare la notifica di cambio stato
						•	Inserire il documento ricevuto in ingresso nella tabella mudeopen_t_documento e associarlo alla pratica relativa alla DS.
						•	Il tipo documento PA da attribuire sarà  TBD…
					 */
					newStateDS = "VIG"; // VIG “In vigilanza”
				}
				if("in attesa adempimenti successivi".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza figlia della DS (la Fine Lavori) che ha creato il processo  in APA
						•	Modificare lo stato della Denuncia Sismica in ITS (in attesa di adempimenti successivi)
						•	Se ci sono Denunce sismiche legate alla prima Denuncia come varianti sostaziali modificare anche lo stato delle Varianti in ITS (DA VERIFICARE)
						•	Inserire i dati di protocollazione per l’istanza figlia in tabella mudeopen_t_istanza 
						•	Inviare la notifica di cambio stato sull’istanza figlia
						•	Legare  l’istanza alla pratica della DS Padre (numeroPratica e annoPratica passato nella callbck).
					 */
					newStateDS = "ITS";
					// TODO: in case the DS has a varian ('id rif != null) set to "ITS" that instnce too!

					newStateFiglia = "APA";
				}
				if("archiviata".equalsIgnoreCase(codEventSimplified)) {
					/*
					 * DONE:
						•	Modificare lo stato della Denuncia Sismica in ARC  (Archiviata)
						•	Se ci sono Denunce sismiche legate alla prima Denuncia come varianti sostaziali modificare anche lo stato delle Varianti in ARC  (Archiviata) (DA VERIFICARE).
						•	Inviare la notifica di cambio stato della denuncia sismica
					 */

					propagetEventSignal = newStateDS = "ARC";
				}
				
				if(StringUtils.isNotBlank(aggiornaStatoPraticaRequest.getTipoIstruttoria())) {
				    MudeTPraticaCosmo mudeTPraticaCosmoDS = mudeTPraticaCosmoRepository.findByIdIstanza(mudeTIstanzaDS.getId());
					if(mudeTPraticaCosmoDS != null) {
						mudeTPraticaCosmoDS.setTipoControllo("2".equals(aggiornaStatoPraticaRequest.getTipoIstruttoria()) ? "PA" : "CF" );
						
						mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmoDS);
					}
				}
				else if("".equals(codEventSimplified)) {
					// 4.12.9.3	CallBack informazioni di protocollo documenti in ingresso
					
					/*
					 * DONE:
						•	Modificare lo stato dell’istanza figlia della DS in APA (codice istanza presente nel campo idFiglia della request 
						•	Inserire i dati di protocollazione per l’istanza figlia in tabella mudeopen_t_istanza 
						•	Inviare la notifica di cambio stato sull’istanza figlia
						•	Legare  l’istanza alla pratica della DS Padre 
					 */
					newStateFiglia = "APA"; 
				}

				
				
				// tutti gli altri stati sono di altri processi che i cosmici stanno disegnando
				// e non ho info fino a quando non consegnano i disegni 
				
				// COC “Controllo a campione” 
				// ICO “in controllo” 
				// DRE “in attesa di DRE” 
				// RSU “in attesa di relazione a struttura ultimata RSU” 
				// COL “Attesa di Collaudo” 

				if(newStateDS != null)
					changeInstanceStatus(codIstanzaPraticaCosmo, aggiornaStatoPraticaRequest, mudeTIstanzaDS, istanzaVO, newStateDS, docTypeCodeDS, null);
					//MudeTIstanza mudeTIstanzaRif = mudeTIstanzaRepository.findMainCosmoInstance(codIstanzaPraticaCosmo);
					//justChangeState(mudeTIstanzaRif, newStateDS);
				
				if(newStateFiglia != null && mudeTIstanzaFiglia != null)
					changeInstanceStatus(codIstanzaPraticaCosmo, aggiornaStatoPraticaRequest, mudeTIstanzaFiglia, istanzaFigliaVO, newStateFiglia, docTypeCodeFiglia, istanzaVO);
					
		        LoggerUtil.info(logger, "[CosmoCallbackApi::callbackPutStatoPratica] CAMBIO STATO ["+codEventSimplified+"]: " + newStateFiglia);

				if(propagetEventSignal != null) {
					// set PROPAGA_SEGNALE to be processed asychronously from the COMMON job
					mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + "impostato '"+propagetEventSignal+"' PROPAGA SEGNALE\r\n\r\n");
					mudeTPraticaCosmo.setRetries(0);
					mudeTPraticaCosmo.setCodiceStatoCosmo(StatiProcessoCosmoEnum.PROPAGA_SEGNALE.toString());
				}
			}
			
			Esito esito = new Esito();
			esito.setCode("OK");
			esito.setStatus(200);
			esito.setTitle("La richiesta di notifica è stata elaborata correttamente");

			if(mudeTPraticaCosmo != null) {
				mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + "callbackPutStatoPratica RESULT: OK\r\n\r\n");
				mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
			}
			
			return voToResponse(esito, HttpStatus.SC_OK);
		} catch (BusinessException be) {
	        LoggerUtil.info(logger, "[CosmoCallbackApi::callbackPutStatoPratica] EXCEPTION: " + ExceptionUtils.getStackTrace(be));
	        errMsg = be.getMessage();

		} catch (Exception e) {
			LoggerUtil.info(logger, "[CosmoCallbackApi::callbackPutStatoPratica] EXCEPTION: bad request " + ExceptionUtils.getStackTrace(e));
			errMsg = "Bad Request - i dati inviati non sono formalmente corretti: " + ExceptionUtils.getStackTrace(e);
		}
		
		if(mudeTPraticaCosmo != null) {
			mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + "["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] callbackPutStatoPratica input: " + errMsg + "\r\n\r\n");
			mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		}
		
		return stringToResponse(errMsg, 400);
		
	}

	private void changeInstanceStatus(String codIstanzaPraticaCosmo, AggiornaStatoPraticaRequest aggiornaStatoPraticaRequest,
										MudeTIstanza mudeTIstanza, IstanzaVO istanzaVO, String newState, String docTypeCode, IstanzaVO istanzaDSVO)
										throws Exception, JsonProcessingException {
		if(newState == null) return;
		
		changeState(newState, codIstanzaPraticaCosmo, aggiornaStatoPraticaRequest, mudeTIstanza, istanzaVO, istanzaDSVO);
		
		/*
				{
				    "annoPratica": null,
				    "codice": "in-attesa-di-fine-lavori",
				    "codiceFiscaleUtente": "AAAAAA00A11B000J",
				    "dataCambioStato": 1706718434000,
				    "dataProtocollo": null,
				    "documenti": [
				        {
				            "archiviazione": {
				                "classificazione": "C.arc, RP201209.e, Regione Piemonte - Agg. 09/2012.ra, Tit01RPGiunta.t, 14.v, Modulistica personale/DB2000.sfa, DB2000.arm, 5/2024C/DB2000.frc, DB2000.arm, 64.nd",
				                "protocollo": {
				                    "data": "31/01/2024",
				                    "numero": "DB2000/00000073/2024"
				                }
				            },
				            "autore": "DEMO 21 CSI PIEMONTE",
				            "descrizione": "Comunicazione di avvenuto deposito (Presa d'atto)",
				            "id": "ac51e7be-5626-4d9f-8719-70fe382d5cc7",
				            "idPadre": null,
				            "mimeType": "application/pdf",
				            "refURL": "/documenti/ac51e7be-5626-4d9f-8719-70fe382d5cc7/contenuto",
				            "tipo": {
				                "codice": "comunicazione-di-avvenuto-deposito-presa-d-atto",
				                "descrizione": "Comunicazione di avvenuto deposito (Presa d’atto)"
				            },
				            "titolo": "Comunicazione di avvenuto deposito (Presa d'atto)"
				        }
				    ],
				    "idFiglia": null,
				    "idRiferimento": "01-001272-0000009099-2024",
				    "numeroPratica": null,
				    "numeroProtocollo": null
				}
		 */
		if(docTypeCode != null && aggiornaStatoPraticaRequest.getDocumenti() != null)
			for(DocumentoFruitore documentoFruitore : aggiornaStatoPraticaRequest.getDocumenti()) {
				if(documentoFruitore.getTipo() == null) continue; // required!

				MudeDTipoDocPA mudeDTipoDocPA = mudeDTipoDocpaRepository.findByCodice(docTypeCode);

				// IF IT DOES NOT EXIST, INVENT ONE FOR NOW!
				if(mudeDTipoDocPA == null) {
					docTypeCode = "COSMO-" + documentoFruitore.getTipo().getCodice().replaceAll("([a-z])[a-z]*[^a-z]*", "$1").toUpperCase();
					if((mudeDTipoDocPA = mudeDTipoDocpaRepository.findByCodice(docTypeCode)) == null) {
						String docTypeDescr = documentoFruitore.getTipo().getDescrizione();
						
						mudeDTipoDocPA = mudeDTipoDocpaRepository.findByCodice(docTypeCode);
						if(mudeDTipoDocPA == null) {
							// add it, if it doesn't exist
							mudeDTipoDocPA = new MudeDTipoDocPA();
							
							mudeDTipoDocPA.setCodeTipoDocpa(docTypeCode);
							mudeDTipoDocPA.setDescTipoDocpa(docTypeDescr);
							mudeDTipoDocPA.setDataInizio(new Date());
							
							mudeDTipoDocpaRepository.save(mudeDTipoDocPA);
						}
					}
				}
				
				PraticaSlimVO mudeTPratica = mudeTPraticaRepository.findByIdIstanzaSlim(mudeTIstanza.getId());
				if(mudeTPratica == null)
					throw new BusinessException("Impossibile trovare la pratica associata all'istanza: " + mudeTIstanza.getId());

				File fileFromCosmo = mudeCosmoManager.getDocumento(documentoFruitore.getRefURL());
				if(fileFromCosmo == null) 
					throw new BusinessException("Impossibile recuperare il documento da COSMO: " + documentoFruitore.getRefURL());
				
				try {
					String filename = fileFromCosmo.getName();
	
		            // fine controlli
					MultipartFormDataOutput output = new MultipartFormDataOutput();
					
		            DocumentoVO documentoPratica = new DocumentoVO(); 
		            TipoDocumentoVO tipoDocumento2 = new TipoDocumentoVO();
		            tipoDocumento2.setCodice(mudeDTipoDocPA.getCodeTipoDocpa());
		            tipoDocumento2.setDescrizione(mudeDTipoDocPA.getDescTipoDocpa());
					documentoPratica.setTipoDocumento(tipoDocumento2);
					
					if(documentoFruitore.getArchiviazione() != null && documentoFruitore.getArchiviazione().getProtocollo() != null) {
						documentoPratica.setNumeroProtocollo(documentoFruitore.getArchiviazione().getProtocollo().getNumero());
						documentoPratica.setDataProtocollo(documentoFruitore.getArchiviazione().getProtocollo().getData());
					}
	
					ObjectMapper mapper = new ObjectMapper();
		            documentoPratica.setNomeFileDocumento(mudeDTipoDocPA.getCodeTipoDocpa() + "_" + filename);
					
		            output.addFormData("documentoPratica", mapper.writeValueAsString(documentoPratica), MediaType.TEXT_PLAIN_TYPE);
					
					OutputPart objPart = output.addFormData("file", fileFromCosmo, MediaType.APPLICATION_OCTET_STREAM_TYPE);
		        	objPart.getHeaders().putSingle("Content-Disposition", "form-data; name=file; filename=" + filename);
	
					GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(output) {};
					
					Response response = postResponse("/pratiche/documenti/"+mudeTPratica.getId()+"?scope=ws", entity, aggiornaStatoPraticaRequest.getCodiceFiscaleUtente());
					if(response.getStatus() >= 201)
						throw new BusinessException("Errore dal COMMON durante il caricamento del documento: " + response.readEntity(String.class));
				} finally {
					if(!fileFromCosmo.delete())
						LoggerUtil.info(logger, "[CosmoCallbackApi::changeInstanceStatus] file not deleted" + fileFromCosmo.getAbsolutePath());
				}
			}
	}

	/*
	private void justChangeState(MudeTIstanza mudeTIstanza, String newState) {
		MudeRIstanzaStato istanzaStato = mudeRIstanzaStatoRepository.findStatoByIstanza(mudeTIstanza.getId());
		istanzaStato.setDataFine(new Date());
		mudeRIstanzaStatoRepository.save(istanzaStato);

		istanzaStato = new MudeRIstanzaStato();
		istanzaStato.setIstanza(mudeTIstanza);
		istanzaStato.setStatoIstanza(mudeDStatoIstanzaRepository.findOne(newState));
		mudeRIstanzaStatoRepository.save(istanzaStato);
	}
	*/

	private void changeState(String newState
								, String codIstanzaPraticaCosmo
								, AggiornaStatoPraticaRequest aggiornaStatoPraticaRequest
								, MudeTIstanza mudeTIstanza
								, IstanzaVO istanzaVO
								, IstanzaVO istanzaDSVO) throws Exception {
		DatiModificaStatoIstanza datiModificaStatoIstanza = new DatiModificaStatoIstanza();
		try {
			if(newState.equals(istanzaVO.getStatoIstanza().getCodice()))
				return; // state alredy set
					
			datiModificaStatoIstanza.setNuovoStato(newState);
			
			datiModificaStatoIstanza.setNumeroIstanza(codIstanzaPraticaCosmo);
			
			if(aggiornaStatoPraticaRequest.getAnnoPratica() == null && istanzaDSVO != null && istanzaDSVO.getAnno() != null) {
				datiModificaStatoIstanza.setAnnoPratica(istanzaDSVO.getAnno().intValue());
				datiModificaStatoIstanza.setNumeroPraticaComunale(istanzaDSVO.getNumeroPratica());
			}
			else if(!"ICI".equals(newState)) {
				datiModificaStatoIstanza.setNumeroPraticaComunale(mudeTPraticaCosmoRepository.findByIdIstanza(mudeTIstanza.getId()).getNumeroPratica());

				String annoPraticaIstRef;
				if(StringUtils.isNotBlank(annoPraticaIstRef = mudeTPraticaRepository.getAnnoPraticaByIdIstanza(mudeTIstanza.getId()))
						|| (mudeTIstanza.getIdIstanzaRiferimento() != null && StringUtils.isNotBlank(annoPraticaIstRef = mudeTPraticaRepository.getAnnoPraticaByIdIstanza(mudeTIstanza.getIdIstanzaRiferimento()))))
					datiModificaStatoIstanza.setAnnoPratica(Integer.parseInt(annoPraticaIstRef));
				else
					datiModificaStatoIstanza.setAnnoPratica(Integer.parseInt(aggiornaStatoPraticaRequest.getAnnoPratica()));
			}

			// if protocol already set, get the old one 
			if(istanzaVO.getNumeroProtocollo() != null && istanzaVO.getDataProtocollo() != null) {
				datiModificaStatoIstanza.setDataProtocollo(new SimpleDateFormat("dd/MM/yyyy").format(Date.from(istanzaVO.getDataProtocollo().atZone(ZoneId.systemDefault()).toInstant()) ));
				datiModificaStatoIstanza.setNumeroProtocollo(istanzaVO.getNumeroProtocollo());
			}
			else if(aggiornaStatoPraticaRequest.getDataProtocollo() != null) {
				datiModificaStatoIstanza.setDataProtocollo(new SimpleDateFormat("dd/MM/yyyy").format(new Date(aggiornaStatoPraticaRequest.getDataProtocollo().getTime())));
				datiModificaStatoIstanza.setNumeroProtocollo(aggiornaStatoPraticaRequest.getNumeroProtocollo());
			}

			String respProcedimento = mudeREnteTipoIstanzaRepository.findRespProcedimentoForCambioStato(mudeTIstanza.getId());
			datiModificaStatoIstanza.setResponsabileProcedimento(respProcedimento);
			
		    MudeDWfStato wfStato = mudeDWfStatiRepository.findAllByIdIstanzaAndStatoEnd(mudeTIstanza.getId(), datiModificaStatoIstanza.getNuovoStato());
			if(wfStato == null)
				throw new BusinessException("Passaggio di stato non consentito: " + mudeTIstanza.getId() + " -> " +datiModificaStatoIstanza.getNuovoStato(), "400", Constants.ERRORE_INTERNO, null);

			datiModificaStatoIstanza.setOggetto(wfStato.getOggettoNotifica());
			datiModificaStatoIstanza.setTesto(wfStato.getTestoNotifica());

			String dataCambioStato = null;
			if(aggiornaStatoPraticaRequest.getDataCambioStato() != null)
				dataCambioStato = new SimpleDateFormat("dd/MM/yyyy").format(new Date(aggiornaStatoPraticaRequest.getDataCambioStato().getTime()));
			
			//TODO: find a way to get "mittente" param
			String error = modificaStatoIstanza(istanzaVO, null, datiModificaStatoIstanza, "mittente", dataCambioStato);
			if(error != null) 
				throw new BusinessException("Errore avvenuto durante il cambio stato: " + error, "400", Constants.ERRORE_INTERNO, null);
				
		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			throw new BusinessException("Errore interno: " + e.getMessage() + "\r\n" + ExceptionUtils.getStackTrace(e), e);
		}
	}

	
}
