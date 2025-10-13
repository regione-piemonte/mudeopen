/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.web.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.AllegatiApi;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.IstanzeApi;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.NotificheApi;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.PraticheApi;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.StatiIstanzaApi;
import it.csi.mudeopen.mudeopensrvapi.vo.InserimentoNotificaVO;
import it.csi.mudeopen.mudeopensrvsoap.business.be.exception.MudeWSException;
import it.csi.mudeopen.mudeopensrvsoap.business.be.web.Gestionale;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.Allegato;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.CampoObbligatorioException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.ComuneInsesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.ComuneNonAbilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.DatiModificaStatoIstanza;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.DatiSintesiIstanza;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.DatiXML;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.DocumentoNonValidoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.DocumentoPA;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.EsitoRicercaPaginata03;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.FiltroRicercaPaginata;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.FruitoreNonAbilitatoComuneException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.IdentificativoIstanza;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.InserimentoNotifica;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.IstanzaEstesa03;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.IstanzaNonConsultabileException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.IstanzaNonTrovataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.MittenteNonAbilitatoFunzioneException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.NotAuthorizedException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.Notifica;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.NotificaElenco;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.NumeroTicketInesistente_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.PassaggioStatoImpossibileException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.Pratica;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.PraticaAssociataAdAltraIstanzaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.PraticaDiAltroFascicoloException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.PraticaEsistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.RicercaDocumentiPraticaFiltro;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.RicercaPraticheFiltro;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.RicercaStatiAmmessi;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.Ruolo;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.StatoInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.StatoIstanza;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.StatoIstanzaConsultazioneException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.StatoIstanzaDesc;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.StatoIstanzaNonValidoModificaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.SystemException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.TipoDocumento;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.TipoNotifica;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.TipoNotificaInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.TipoNotificaUgualeACambiaStatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.UnrecoverableException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.ValoreNonAmmessoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.VisualizzazioneNotificaCerca;
@WebService(
        serviceName = "GestionaleSrvService",
        portName = "GestionaleSrvPort",
        targetNamespace = "http://gestionale.business.mudesrvextsic.mude.csi.it/",
        endpointInterface = "it.csi.mudeopen.mudeopensrvsoap.business.be.web.Gestionale")
//        wsdlLocation = "http://serviziweb.csi.it/mudesrvextsicApplGestionaleWs/mudesrvextsicGestionale?wsdl",
public class GestionaleImpl extends GenericApi implements Gestionale {

	private static final String ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA = "Errore generico durante l'elaborazione della richiesta";
	private static final Class<NotificheApi> NOTIFICHE_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.NotificheApi.class;

	private static final Class<StatiIstanzaApi> STATI_ISTANZA_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.StatiIstanzaApi.class;

	private static final Class<IstanzeApi> ISTANZE_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.IstanzeApi.class;

	private static final Class<AllegatiApi> ALLEGATI_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.AllegatiApi.class;
	
	private static final Class<PraticheApi> PRATICHE_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.PraticheApi.class;

	@Override
	public EsitoRicercaPaginata03 ricercaPaginataIstanze03(String token,
														FiltroRicercaPaginata filtro)
			throws SystemException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception,
			ValoreNonAmmessoException_Exception, StatoInesistenteException_Exception,
			FruitoreNonAbilitatoComuneException_Exception, CampoObbligatorioException_Exception,
			NotAuthorizedException_Exception, StatoIstanzaConsultazioneException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);

    		Integer numPagina = filtro.getNumPagina() != null ?filtro.getNumPagina() : 1 ;
    		Integer maxNumIstanzeDaRestituire = filtro.getMaxNumIstanzeDaRestituire() != null ? filtro.getMaxNumIstanzeDaRestituire() : 10;
    		
			Response response = callAPI(mudeTSessione, ISTANZE_API, new ObjectMapper().writeValueAsString(filtro), numPagina, maxNumIstanzeDaRestituire);
        	
			String json = response.readEntity(String.class);
			if(HttpStatus.SC_OK == response.getStatus()) {
				
		        // remap IstanzaEstesa --> IstanzaEstesa03
				List<IstanzaEstesa03> responseList = new ObjectMapper().readValue(json , new TypeReference<List<IstanzaEstesa03>>() {}); 

	        	EsitoRicercaPaginata03 esitoRicercaPaginata03 = new EsitoRicercaPaginata03();

	        	MultivaluedMap<String, Object> headers = response.getHeaders();
	        	esitoRicercaPaginata03.setNumIstanzeRestituite(Integer.parseInt((String)headers.getFirst("NumIstanzeRestituite")));
	        	esitoRicercaPaginata03.setNumIstanzeTotali(Integer.parseInt((String)headers.getFirst("NumIstanzeTotali")));
	        	esitoRicercaPaginata03.setNumPagina(Integer.parseInt((String)headers.getFirst("NumPagina")));
	        	esitoRicercaPaginata03.setNumPagineTotali(Integer.parseInt((String)headers.getFirst("NumPagineTotali")));
	        	esitoRicercaPaginata03.setListaCompleta(Boolean.TRUE.equals(Boolean.valueOf((String)headers.getFirst("ListaCompleta")) ) ? "S" :"N");
	        	esitoRicercaPaginata03.getElencoIstanze().addAll(responseList);
	        	
	    		logEnd(token);
	        	return esitoRicercaPaginata03;
			}
				
			handleException(json);
		} catch (SystemException_Exception | ComuneNonAbilitatoException_Exception | NumeroTicketInesistente_Exception |
				ValoreNonAmmessoException_Exception | StatoInesistenteException_Exception |
				FruitoreNonAbilitatoComuneException_Exception | CampoObbligatorioException_Exception |
				NotAuthorizedException_Exception | StatoIstanzaConsultazioneException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}

	@Override
	public boolean modificaStatoIstanza(String token,
										final DatiModificaStatoIstanza datiModifica)
			throws SystemException_Exception, PassaggioStatoImpossibileException_Exception,
			PraticaEsistenteException_Exception, IstanzaNonTrovataException_Exception,
			FruitoreNonAbilitatoComuneException_Exception, PraticaAssociataAdAltraIstanzaException_Exception,
			PraticaDiAltroFascicoloException_Exception, ComuneNonAbilitatoException_Exception,
			NumeroTicketInesistente_Exception, UnrecoverableException_Exception, ValoreNonAmmessoException_Exception,
			StatoInesistenteException_Exception, StatoIstanzaNonValidoModificaException_Exception,
			CampoObbligatorioException_Exception, NotAuthorizedException_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
			it.csi.mudeopen.mudeopensrvapi.vo.DatiModificaStatoIstanza datiModificaStatoIstanza = new it.csi.mudeopen.mudeopensrvapi.vo.DatiModificaStatoIstanza();
			datiModificaStatoIstanza.setNumeroIstanza(datiModifica.getNumeroIstanza());
			datiModificaStatoIstanza.setNuovoStato(datiModifica.getNuovoStato());
			datiModificaStatoIstanza.setOggetto(datiModifica.getOggetto());
			datiModificaStatoIstanza.setTesto(datiModifica.getTesto());
			datiModificaStatoIstanza.setNumeroPraticaComunale(datiModifica.getNumeroPraticaComunale());
			datiModificaStatoIstanza.setAnnoPratica(datiModifica.getAnnoPratica());
			datiModificaStatoIstanza.setNumeroProtocollo(datiModifica.getNumeroProtocollo());
			datiModificaStatoIstanza.setDataProtocollo(datiModifica.getDataProtocollo() == null? null : new SimpleDateFormat("dd-MM-yyyy").format(datiModifica.getDataProtocollo().toGregorianCalendar().getTime()));
			datiModificaStatoIstanza.setResponsabileProcedimento(datiModifica.getResponsabileProcedimento());
			Response res = callAPI(mudeTSessione, STATI_ISTANZA_API, datiModificaStatoIstanza);
	
			if(HttpStatus.SC_OK == res.getStatus()) {
				logEnd(token);
				return true;
			}		
		
			String json = res.readEntity(String.class);
			handleException(json);
		} catch (SystemException_Exception | PassaggioStatoImpossibileException_Exception |
				PraticaEsistenteException_Exception | IstanzaNonTrovataException_Exception |
				FruitoreNonAbilitatoComuneException_Exception | PraticaAssociataAdAltraIstanzaException_Exception |
				PraticaDiAltroFascicoloException_Exception | ComuneNonAbilitatoException_Exception |
				NumeroTicketInesistente_Exception | UnrecoverableException_Exception | ValoreNonAmmessoException_Exception |
				StatoInesistenteException_Exception | StatoIstanzaNonValidoModificaException_Exception |
				CampoObbligatorioException_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
		
	}

	@Override
	public List<Allegato> ricercaElencoAllegati(String token,
												IdentificativoIstanza ricercaElencoAllegati)
			throws SystemException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception,
			FruitoreNonAbilitatoComuneException_Exception, CampoObbligatorioException_Exception,
			NotAuthorizedException_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
            Response res = callAPI(mudeTSessione, ALLEGATI_API, getSafeStringPathPar(ricercaElencoAllegati.getNumeroIstanza()));
            res.bufferEntity();
			String json = res.readEntity(String.class);
			if(HttpStatus.SC_OK == res.getStatus()) {
				List<Allegato> responseList = new ObjectMapper().readValue(json , new TypeReference<List<Allegato>>() {}); 
				
				logEnd(token);
				return responseList;
			}
			
			handleException(json);
		} catch (SystemException_Exception | 
				ComuneNonAbilitatoException_Exception | NumeroTicketInesistente_Exception |
				FruitoreNonAbilitatoComuneException_Exception | CampoObbligatorioException_Exception |
				NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}

	@Override
	public DatiSintesiIstanza ricercaDatiSintesiIstanza(String token, 
														IdentificativoIstanza identificativoIstanza)
			throws SystemException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception,
			FruitoreNonAbilitatoComuneException_Exception, CampoObbligatorioException_Exception,
			NotAuthorizedException_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
			Response res = callAPI(mudeTSessione, ISTANZE_API, getSafeStringPathPar(identificativoIstanza.getNumeroIstanza()));
			String json = res.readEntity(String.class);
			if(HttpStatus.SC_OK == res.getStatus()) {
		        // remap NotificaElencoVO --> NotificaElenco
	        	DatiSintesiIstanza responseList = new ObjectMapper().readValue(json , new TypeReference<DatiSintesiIstanza>() {});
	        	
				logEnd(token);
				return responseList;
			}
			
			handleException(json);
		} catch (SystemException_Exception | ComuneNonAbilitatoException_Exception | NumeroTicketInesistente_Exception |
				FruitoreNonAbilitatoComuneException_Exception | CampoObbligatorioException_Exception |
				NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}

	@Override
	public List<NotificaElenco> elencoNotificheInviate(String token, 
														IdentificativoIstanza identificativoIstanza)
			throws SystemException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception,
			FruitoreNonAbilitatoComuneException_Exception, CampoObbligatorioException_Exception,
			NotAuthorizedException_Exception {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
			Response res = callAPI(mudeTSessione, NOTIFICHE_API, getSafeStringPathPar(identificativoIstanza.getNumeroIstanza()));
			String json = res.readEntity(String.class);
			if(HttpStatus.SC_OK == res.getStatus()) {
		        // remap NotificaElencoVO --> NotificaElenco
	        	List<NotificaElenco> responseList = new ObjectMapper().readValue(json , new TypeReference<List<NotificaElenco>>() {}); 
				
				logEnd(token);
				return responseList;
			}
			
			handleException(json);
		} catch (SystemException_Exception | ComuneNonAbilitatoException_Exception | NumeroTicketInesistente_Exception |
				FruitoreNonAbilitatoComuneException_Exception | CampoObbligatorioException_Exception |
				NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}

	@Override
	public List<DocumentoPA> ricercaElencoDocumentiPratica(String token,
														   RicercaDocumentiPraticaFiltro filtro)
			throws SystemException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception,
			FruitoreNonAbilitatoComuneException_Exception, CampoObbligatorioException_Exception,
			ComuneInsesistenteException_Exception, NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
			String istatComune = filtro != null ? getSafeStringPathPar(filtro.getCodIstat()) : null;
			String numeroPratica = filtro != null ? filtro.getNumeroPratica() : null;	
			String annoPratica = filtro != null && filtro.getAnnoPratica() != null && filtro.getAnnoPratica() > 0? ""+filtro.getAnnoPratica() : null;
			Response response = callAPI(mudeTSessione, PRATICHE_API, istatComune, numeroPratica, annoPratica);
			
			String json = response.readEntity(String.class);
			if(HttpStatus.SC_OK == response.getStatus()) {
	        	List<DocumentoPA> responseList = new ObjectMapper().readValue(json , new TypeReference<List<DocumentoPA>>() {}); 
	        	
	    		logEnd(token);
	        	return responseList;
			}
			
			handleException(json);
		} catch (SystemException_Exception | ComuneNonAbilitatoException_Exception | NumeroTicketInesistente_Exception |
				FruitoreNonAbilitatoComuneException_Exception | CampoObbligatorioException_Exception |
				ComuneInsesistenteException_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public Notifica visualizzazioneNotifica(String token, 
											VisualizzazioneNotificaCerca filtro)
			throws SystemException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception,
			FruitoreNonAbilitatoComuneException_Exception, CampoObbligatorioException_Exception,
			NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
			Response response = callAPI(mudeTSessione, NOTIFICHE_API, getSafeStringPathPar(filtro.getNumeroIstanza()), filtro.getIdentificativoNotifica());
			
			String json = response.readEntity(String.class);
			if(HttpStatus.SC_OK == response.getStatus()) {
		        // remap DocumentoPAVO --> DocumentoPA
	        	Notifica responseList = new ObjectMapper().readValue(json , new TypeReference<Notifica>() {}); 
				
	    		logEnd(token);
	        	return responseList;
			}
		
			handleException(json);
		} catch (SystemException_Exception | ComuneNonAbilitatoException_Exception | NumeroTicketInesistente_Exception |
				FruitoreNonAbilitatoComuneException_Exception | CampoObbligatorioException_Exception |
				NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	

	@Override
	public List<DocumentoPA> elencoAllegatiNotifica(String token, 
													VisualizzazioneNotificaCerca identificativoIstanza)
			throws SystemException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception,
			FruitoreNonAbilitatoComuneException_Exception, CampoObbligatorioException_Exception,
			NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
			Response response = callAPI(mudeTSessione, NOTIFICHE_API, getSafeStringPathPar(identificativoIstanza.getNumeroIstanza()), identificativoIstanza.getIdentificativoNotifica());
			
			String json = response.readEntity(String.class);
			if(HttpStatus.SC_OK == response.getStatus()) {
				List<DocumentoPA> responseList = new ObjectMapper().readValue(json , new TypeReference<List<DocumentoPA>>() {}); 
				
	    		logEnd(token);
	        	return responseList;
			}
		
			handleException(json);
		} catch (SystemException_Exception | ComuneNonAbilitatoException_Exception | NumeroTicketInesistente_Exception |
				FruitoreNonAbilitatoComuneException_Exception | CampoObbligatorioException_Exception |
				NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public List<StatoIstanza> ricercaStatiIstanzaAmmessi(String token, 
														RicercaStatiAmmessi identificativo)
			throws SystemException_Exception, NumeroTicketInesistente_Exception,
			PassaggioStatoImpossibileException_Exception, StatoInesistenteException_Exception,
			CampoObbligatorioException_Exception, NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
			Response response = callAPI(mudeTSessione, STATI_ISTANZA_API, getSafeStringPathPar(identificativo.getStatoIniziale()));
			String json = response.readEntity(String.class);
			
			if(HttpStatus.SC_OK == response.getStatus()) {
				List<StatoIstanza> responseList = new ObjectMapper().readValue(json , new TypeReference<List<StatoIstanza>>() {}); 
				
	    		logEnd(token);
	        	return responseList;
			}
		
			handleException(json);
		} catch (SystemException_Exception | NumeroTicketInesistente_Exception |
				PassaggioStatoImpossibileException_Exception | StatoInesistenteException_Exception |
				CampoObbligatorioException_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}

	@Override
	public DatiXML scaricoXML(String token, 
							IdentificativoIstanza identificativoIstanza, 
							String codiceTipoTracciato)
			throws SystemException_Exception, ComuneNonAbilitatoException_Exception,
			IstanzaNonConsultabileException_Exception, NumeroTicketInesistente_Exception,
			IstanzaNonTrovataException_Exception, FruitoreNonAbilitatoComuneException_Exception,
			CampoObbligatorioException_Exception, NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
    		if(StringUtils.isBlank(codiceTipoTracciato))
    			throw new CampoObbligatorioException_Exception("Codice tipo tracciato");
    		
			Response response = callAPI(mudeTSessione, it.csi.mudeopen.mudeopensrvapi.business.be.web.ScaricoXmlApi.class, getSafeStringPathPar(identificativoIstanza.getNumeroIstanza()), getSafeStringPathPar(codiceTipoTracciato));
			
			String json = response.readEntity(String.class);
			if(HttpStatus.SC_OK == response.getStatus()) {
				DatiXML responseList = new ObjectMapper().readValue(json , new TypeReference<DatiXML>() {}); 
				
	    		logEnd(token);
	        	return responseList;
			}
			
			handleException(json);
		} catch (SystemException_Exception | ComuneNonAbilitatoException_Exception |
				IstanzaNonConsultabileException_Exception | NumeroTicketInesistente_Exception |
				IstanzaNonTrovataException_Exception | FruitoreNonAbilitatoComuneException_Exception |
				CampoObbligatorioException_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public List<StatoIstanzaDesc> ricercaStatiIstanza(String token)
			throws SystemException_Exception, NumeroTicketInesistente_Exception, NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
			Response response = callAPI(mudeTSessione, STATI_ISTANZA_API);
			String json = response.readEntity(String.class);
			
			if(HttpStatus.SC_OK == response.getStatus()) {
		        // remap StatoIstanzaDescVO --> StatoIstanzaDesc
				List<StatoIstanzaDesc> responseList = new ObjectMapper().readValue(json , new TypeReference<List<StatoIstanzaDesc>>() {}); 
				
	    		logEnd(token);
	        	return responseList;
			}
		
			handleException(json);
		} catch (SystemException_Exception | NumeroTicketInesistente_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public List<TipoNotifica> ricercaTipoNotifica(String token)
			throws SystemException_Exception, NumeroTicketInesistente_Exception, NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
    		Response response = callAPI(mudeTSessione, it.csi.mudeopen.mudeopensrvapi.business.be.web.TipiNotificaApi.class);

			String json = response.readEntity(String.class);
			if(HttpStatus.SC_OK == response.getStatus()) {
				List<TipoNotifica> responseList = new ObjectMapper().readValue(json , new TypeReference<List<TipoNotifica>>() {}); 
				
	    		logEnd(token);
	        	return responseList;
			}
		
			handleException(json);
		} catch (SystemException_Exception | NumeroTicketInesistente_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}

	@Override
	public List<TipoDocumento> ricercaTipoDocumento(String token)
			throws SystemException_Exception, NumeroTicketInesistente_Exception, NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
    		Response response = callAPI(mudeTSessione, it.csi.mudeopen.mudeopensrvapi.business.be.web.TipiDocpaApi.class);

			String json = response.readEntity(String.class);
			if(HttpStatus.SC_OK == response.getStatus()) {
		        // remap TipoDocpaVo --> TipoDocumento
				List<TipoDocumento> responseList = new ObjectMapper().readValue(json , new TypeReference<List<TipoDocumento>>() {}); 
				
	    		logEnd(token);
	        	return responseList;
			}
		
			handleException(json);
		} catch (SystemException_Exception | NumeroTicketInesistente_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public List<Ruolo> ricercaRuoli(String token)
			throws SystemException_Exception, NumeroTicketInesistente_Exception, NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
    		Response response = callAPI(mudeTSessione, it.csi.mudeopen.mudeopensrvapi.business.be.web.RuoliUtenteApi.class);
    		
			String json = response.readEntity(String.class);
			if(HttpStatus.SC_OK == response.getStatus()) {
				
		        // remap RuoliUtenteVO --> Ruolo
				List<Ruolo> responseList = new ObjectMapper().readValue(json , new TypeReference<List<Ruolo>>() {}); 
				
	    		logEnd(token);
	        	return responseList;
			}
		
			handleException(json);
		} catch (SystemException_Exception | NumeroTicketInesistente_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	
	@Override
	public List<Pratica> ricercaPratiche(String token, RicercaPraticheFiltro filtro)
			throws SystemException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception,
			FruitoreNonAbilitatoComuneException_Exception, CampoObbligatorioException_Exception,
			ComuneInsesistenteException_Exception, NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
    		String istatComune = filtro != null ? getSafeStringPathPar(filtro.getCodIstat()) : null;
    		String numeroPratica = filtro != null ? filtro.getNumeroPratica() : null;
			String annoPratica = filtro != null && filtro.getAnnoPratica() != null && filtro.getAnnoPratica() > 0? ""+filtro.getAnnoPratica() : null;
    		Response response = callAPI(mudeTSessione, PRATICHE_API, istatComune, numeroPratica, annoPratica);

			String json = response.readEntity(String.class);
			if(HttpStatus.SC_OK == response.getStatus()) {
				List<Pratica> responseList = new ObjectMapper().readValue(json , new TypeReference<List<Pratica>>() {}); 
				
	    		logEnd(token);
	        	return responseList;
			}
		
			handleException(json);
		} catch (SystemException_Exception | ComuneNonAbilitatoException_Exception | NumeroTicketInesistente_Exception |
				FruitoreNonAbilitatoComuneException_Exception | CampoObbligatorioException_Exception |
				ComuneInsesistenteException_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}

	@Override
	public long inserisciNotifica(String token, InserimentoNotifica notificaDaInserire)
			throws SystemException_Exception, DocumentoNonValidoException_Exception,
			IstanzaNonTrovataException_Exception, FruitoreNonAbilitatoComuneException_Exception,
			ComuneNonAbilitatoException_Exception, IstanzaNonConsultabileException_Exception,
			NumeroTicketInesistente_Exception, TipoNotificaUgualeACambiaStatoException_Exception,
			CampoObbligatorioException_Exception, MittenteNonAbilitatoFunzioneException_Exception,
			TipoNotificaInesistenteException_Exception, NotAuthorizedException_Exception {
        try {
    		logStart(token);
    		MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
    		
        	String jsonIn = new ObjectMapper().writeValueAsString(notificaDaInserire);
    		Response response = callAPI(mudeTSessione, NOTIFICHE_API, new ObjectMapper().readValue(jsonIn, new TypeReference<InserimentoNotificaVO>() {})
    				);
			if(HttpStatus.SC_OK == response.getStatus()) {
	    		logEnd(token);
	        	return response.readEntity(Long.class);
			}
		
			String json = response.readEntity(String.class);
			handleException(json);
		} catch (SystemException_Exception | DocumentoNonValidoException_Exception |
				IstanzaNonTrovataException_Exception | FruitoreNonAbilitatoComuneException_Exception |
				ComuneNonAbilitatoException_Exception | IstanzaNonConsultabileException_Exception |
				NumeroTicketInesistente_Exception | TipoNotificaUgualeACambiaStatoException_Exception |
				CampoObbligatorioException_Exception | MittenteNonAbilitatoFunzioneException_Exception |
				TipoNotificaInesistenteException_Exception | NotAuthorizedException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
	}
	public void handleException(String msg) throws MudeWSException {
		if(msg == null) 
			throw new SystemException_Exception("Errore generico");
		
		try {
            JSONObject js = new JSONObject(msg);
            if(js.has("message")) msg = js.getString("message");
		} catch (Exception noJSON) {}

		if(msg.indexOf("NotAuthorizedException") > -1)
			throw new NotAuthorizedException_Exception(msg);
		if(msg.indexOf("CampoObbligatorioException") > -1)
			throw new CampoObbligatorioException_Exception(msg);
		if(msg.indexOf("NumeroTicketInesistente") > -1)
			throw new NumeroTicketInesistente_Exception(msg);
		if(msg.indexOf("IstanzaNonConsultabileException") > -1)
			throw new IstanzaNonConsultabileException_Exception(msg);
		if(msg.indexOf("ComuneNonAbilitatoException") > -1)
			throw new ComuneNonAbilitatoException_Exception(msg);
		if(msg.indexOf("PraticaAssociataAdAltraIstanzaException") > -1)
			throw new PraticaAssociataAdAltraIstanzaException_Exception(msg);
		if(msg.indexOf("StatoInesistenteException") > -1)
			throw new StatoInesistenteException_Exception(msg);
		if(msg.indexOf("StatoIstanzaNonValidoModificaException") > -1)
			throw new StatoIstanzaNonValidoModificaException_Exception(msg);
		if(msg.indexOf("ValoreNonAmmessoException") > -1)
			throw new ValoreNonAmmessoException_Exception(msg);
		if(msg.indexOf("PraticaEsistenteException") > -1)
			throw new PraticaEsistenteException_Exception(msg);
		if(msg.indexOf("PraticaDiAltroFascicoloException") > -1)
			throw new PraticaDiAltroFascicoloException_Exception(msg);
		if(msg.indexOf("IstanzaNonTrovataException") > -1)
			throw new IstanzaNonTrovataException_Exception(msg);
		if(msg.indexOf("UnrecoverableException") > -1)
			throw new UnrecoverableException_Exception(msg);
		if(msg.indexOf("PassaggioStatoImpossibileException") > -1)
			throw new PassaggioStatoImpossibileException_Exception(msg);
		if(msg.indexOf("StatoIstanzaConsultazioneException") > -1)
			throw new StatoIstanzaConsultazioneException_Exception(msg);
		if(msg.indexOf("DocumentoNonValidoException") > -1)
			throw new DocumentoNonValidoException_Exception(msg);
		if(msg.indexOf("TipoNotificaUgualeACambiaStatoException") > -1)
			throw new TipoNotificaUgualeACambiaStatoException_Exception(msg);
		if(msg.indexOf("TipoNotificaInesistenteException") > -1)
			throw new TipoNotificaInesistenteException_Exception(msg);
		if(msg.indexOf("MittenteNonAbilitatoFunzioneException") > -1)
			throw new MittenteNonAbilitatoFunzioneException_Exception(msg);
		if(msg.indexOf("ComuneInsesistenteException") > -1)
			throw new ComuneInsesistenteException_Exception(msg);
		if(msg.indexOf("FruitoreNonAbilitatoComuneException") > -1)
			throw new FruitoreNonAbilitatoComuneException_Exception(msg);
		if(msg.indexOf("SystemException") > -1)
			throw new SystemException_Exception(msg);
		
    	throw new SystemException_Exception(msg);
	}
	
}
