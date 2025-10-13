/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.web.impl;
import javax.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.codehaus.jettison.json.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.IstanzeApi;
import it.csi.mudeopen.mudeopensrvapi.vo.invio.istanza.IstanzaExtVO;
import it.csi.mudeopen.mudeopensrvsoap.business.be.exception.MudeWSException;
import it.csi.mudeopen.mudeopensrvsoap.business.be.web.Acquisizione;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.CognomeIntestatarioPersoneFisicheException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.DataNonValidaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.DocumentoPraticaInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FruitoreNoModelliAssociatiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FruitoreNonAbilitatoModelloException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FruitoreNonAbilitatoOperazioneException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FruitoreNonAbilitatoTipologiaIstanzaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FunzioneNonAbilitataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.GeneraNumeroMUDEType;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IdentificativoIstanzaExt;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IntestatarioNonPresenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IstanzaExt;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IstanzaInviataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IstanzaNonRegistrataPAException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NomeIntestatarioPersoneFisicheException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroFascicoloComuneNonCoerentiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroFascicoloNonCorrettoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroIstanzaComuneNonCoerentiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroIstanzaNonCorrettoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroIstanzaObbligatorioException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroIstanzaRiferimentoInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.PraticaNonConsultabileException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.ProtocollazioneIstanzaExt;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.RagioneSocialeIntestatarioPersoneGiuridicheException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.RuoloUtenteNonValidoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.SpeciePraticaNonAbilitataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.SpeciePraticaNonRiferitaModelloException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TipoTracciatoNonAbilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TipoTracciatoNonPrevistoTipoIstanzaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TipologiaIstanzaInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TipologiaIstanzaNonAbilitataFruitoreException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TokenInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TracciatoVersioneNonCoerentiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.VersioneTracciatoInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.VersioneTracciatoNonAttivaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione.FruitoreNoComuniAssociatiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.CampoObbligatorioException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.ComuneInsesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.ComuneNonAbilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.FruitoreNonAbilitatoComuneException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.IstanzaNonConsultabileException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.IstanzaNonTrovataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.NumeroTicketInesistente_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.PraticaAssociataAdAltraIstanzaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.SystemException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.UnrecoverableException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.ValoreNonAmmessoException_Exception;
/**
 * This class was generated by Apache CXF 3.2.2
 * 2023-01-25T18:38:23.951+01:00
 * Generated source version: 3.2.2
 *
 */
@javax.jws.WebService(
                      serviceName = "AcquisizioneSrvService",
                      portName = "AcquisizioneSrvPort",
                      targetNamespace = "http://acquisizione.business.mudesrvextsic.mude.csi.it/",
                      endpointInterface = "it.csi.mudeopen.mudeopensrvsoap.business.be.web.Acquisizione"
                      )
public class AcquisizioneImpl extends GenericApi implements Acquisizione {
	private static final String ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA = "Errore generico durante l'elaborazione della richiesta";
	private static final Class<IstanzeApi> ISTANZE_API = it.csi.mudeopen.mudeopensrvapi.business.be.web.IstanzeApi.class;
    /* (non-Javadoc)
     * @see Acquisizione#generaNumeroMUDE(String token, GeneraNumeroMUDEType generaNumeroMUDE)*
     */
    public String generaNumeroMUDE(String token, GeneraNumeroMUDEType generaNumeroMUDE) throws 
    						FruitoreNonAbilitatoTipologiaIstanzaException_Exception,  
    						FruitoreNonAbilitatoComuneException_Exception,  
    						ComuneInsesistenteException_Exception,  
    						FruitoreNonAbilitatoOperazioneException_Exception,  
    						FruitoreNoModelliAssociatiException_Exception,  
    						ComuneNonAbilitatoException_Exception,  
    						NumeroTicketInesistente_Exception,  
    						FruitoreNoComuniAssociatiException_Exception,  
    						UnrecoverableException_Exception,  
    						FunzioneNonAbilitataException_Exception,  
    						ValoreNonAmmessoException_Exception,  
    						FruitoreNonAbilitatoModelloException_Exception,  
    						CampoObbligatorioException_Exception,  
    						TokenInesistenteException_Exception   {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
			Response res = callAPI(mudeTSessione, ISTANZE_API, getSafeStringPathPar(generaNumeroMUDE.getCodiceIstatComune()), getSafeStringPathPar(generaNumeroMUDE.getNuovoIntervento()));
			String resStr= res.readEntity(String.class);
			if(HttpStatus.SC_OK == res.getStatus()) {
				logEnd(token);
				return resStr.replace("\"", "");
			}
			
			handleException(resStr);
		} catch (FruitoreNonAbilitatoTipologiaIstanzaException_Exception | 
				FruitoreNonAbilitatoComuneException_Exception | 
				ComuneInsesistenteException_Exception | 
				FruitoreNonAbilitatoOperazioneException_Exception | 
				FruitoreNoModelliAssociatiException_Exception | 
				ComuneNonAbilitatoException_Exception | 
				NumeroTicketInesistente_Exception | 
				FruitoreNoComuniAssociatiException_Exception | 
				UnrecoverableException_Exception | 
				FunzioneNonAbilitataException_Exception | 
				ValoreNonAmmessoException_Exception | 
				FruitoreNonAbilitatoModelloException_Exception | 
				CampoObbligatorioException_Exception | 
				TokenInesistenteException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new UnrecoverableException_Exception(t.getMessage());
		}
		
		throw new UnrecoverableException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
    }
    /* (non-Javadoc)
     * @see Acquisizione#visualizzaDatiProtocollazioneIstanza(String token, IdentificativoIstanzaExt identificativoIstanzaExt)*
     */
    public ProtocollazioneIstanzaExt visualizzaDatiProtocollazioneIstanza(String token, IdentificativoIstanzaExt identificativoIstanzaExt) throws SystemException_Exception,  
																							    					IstanzaNonRegistrataPAException_Exception,  
																							    					FruitoreNonAbilitatoTipologiaIstanzaException_Exception,  
																							    					FruitoreNonAbilitatoComuneException_Exception,  
																							    					FruitoreNonAbilitatoOperazioneException_Exception,  
																							    					FruitoreNoModelliAssociatiException_Exception,  
																							    					IstanzaNonConsultabileException_Exception,  
																							    					ComuneNonAbilitatoException_Exception,  
																							    					NumeroTicketInesistente_Exception,  
																							    					FruitoreNoComuniAssociatiException_Exception,  
																							    					UnrecoverableException_Exception,  
																							    					NumeroIstanzaObbligatorioException_Exception,  
																							    					ValoreNonAmmessoException_Exception,  
																							    					FruitoreNonAbilitatoModelloException_Exception,  
																							    					CampoObbligatorioException_Exception,  
																							    					TokenInesistenteException_Exception   {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
    		Response res = callAPI(mudeTSessione, ISTANZE_API, getSafeStringPathPar(identificativoIstanzaExt.getNumeroIstanza()));
			String json = res.readEntity(String.class);
			if(HttpStatus.SC_OK == res.getStatus()) {
				ProtocollazioneIstanzaExt responseList = new ObjectMapper().readValue(json , new TypeReference<ProtocollazioneIstanzaExt>() {}); 
				
				logEnd(token);
				return responseList;
			}
			
			handleException(json);
		} catch (SystemException_Exception | 
				IstanzaNonRegistrataPAException_Exception | 
				FruitoreNonAbilitatoTipologiaIstanzaException_Exception | 
				FruitoreNonAbilitatoComuneException_Exception | 
				FruitoreNonAbilitatoOperazioneException_Exception | 
				FruitoreNoModelliAssociatiException_Exception | 
				IstanzaNonConsultabileException_Exception | 
				ComuneNonAbilitatoException_Exception | 
				NumeroTicketInesistente_Exception | 
				FruitoreNoComuniAssociatiException_Exception | 
				UnrecoverableException_Exception | 
				NumeroIstanzaObbligatorioException_Exception | 
				ValoreNonAmmessoException_Exception | 
				FruitoreNonAbilitatoModelloException_Exception | 
				CampoObbligatorioException_Exception | 
				TokenInesistenteException_Exception me) {
			logError(me);
			throw me;
		} catch (Throwable t) {
			logError(t);
			throw new SystemException_Exception(t.getMessage());
		}
		
		throw new SystemException_Exception(ERRORE_GENERICO_DURANTE_L_ELABORAZIONE_DELLA_RICHIESTA);
    }
    /* (non-Javadoc)
     * @see Acquisizione#invioIstanza(String token, IstanzaExt istanzaExt)*
     */
    public String invioIstanza(String token, IstanzaExt istanzaExt) throws NomeIntestatarioPersoneFisicheException_Exception,  RuoloUtenteNonValidoException_Exception,  DataNonValidaException_Exception,  IstanzaNonTrovataException_Exception,  TipologiaIstanzaInesistenteException_Exception,  NumeroIstanzaComuneNonCoerentiException_Exception,  FruitoreNonAbilitatoTipologiaIstanzaException_Exception,  TipoTracciatoNonPrevistoTipoIstanzaException_Exception,  PraticaAssociataAdAltraIstanzaException_Exception,  FruitoreNoModelliAssociatiException_Exception,  NumeroIstanzaRiferimentoInesistenteException_Exception,  ComuneNonAbilitatoException_Exception,  IstanzaNonConsultabileException_Exception,  VersioneTracciatoInesistenteException_Exception,  NumeroTicketInesistente_Exception,  NumeroFascicoloNonCorrettoException_Exception,  FunzioneNonAbilitataException_Exception,  ValoreNonAmmessoException_Exception,  FruitoreNonAbilitatoModelloException_Exception,  TokenInesistenteException_Exception,  TipoTracciatoNonAbilitatoException_Exception,  SystemException_Exception,  VersioneTracciatoNonAttivaException_Exception,  TipologiaIstanzaNonAbilitataFruitoreException_Exception,  FruitoreNonAbilitatoComuneException_Exception,  SpeciePraticaNonRiferitaModelloException_Exception,  FruitoreNonAbilitatoOperazioneException_Exception,  ComuneInsesistenteException_Exception,  IntestatarioNonPresenteException_Exception,  NumeroIstanzaNonCorrettoException_Exception,  NumeroFascicoloComuneNonCoerentiException_Exception,  IstanzaInviataException_Exception,  PraticaNonConsultabileException_Exception,  FruitoreNoComuniAssociatiException_Exception,  CognomeIntestatarioPersoneFisicheException_Exception,  UnrecoverableException_Exception,  NumeroIstanzaObbligatorioException_Exception,  TracciatoVersioneNonCoerentiException_Exception,  CampoObbligatorioException_Exception,  DocumentoPraticaInesistenteException_Exception,  SpeciePraticaNonAbilitataException_Exception,  RagioneSocialeIntestatarioPersoneGiuridicheException_Exception   {
		try {
			logStart(token);
			MudeopenTSessione mudeTSessione = getMudeTSessionByToken(token);
			
        	String jsonIn = new ObjectMapper().writeValueAsString(istanzaExt);
			Response res = callAPI(mudeTSessione, ISTANZE_API, new ObjectMapper().readValue(jsonIn, new TypeReference<IstanzaExtVO>() {}));
            res.bufferEntity();
			String json = res.readEntity(String.class);
			if(HttpStatus.SC_OK == res.getStatus()) {
				logEnd(token);
				return "true";
			}
			
			handleException(json);
		} catch (
				NomeIntestatarioPersoneFisicheException_Exception | 
				RuoloUtenteNonValidoException_Exception | 
				DataNonValidaException_Exception | 
				IstanzaNonTrovataException_Exception | 
				TipologiaIstanzaInesistenteException_Exception | 
				NumeroIstanzaComuneNonCoerentiException_Exception | 
				FruitoreNonAbilitatoTipologiaIstanzaException_Exception | 
				TipoTracciatoNonPrevistoTipoIstanzaException_Exception | 
				PraticaAssociataAdAltraIstanzaException_Exception | 
				FruitoreNoModelliAssociatiException_Exception | 
				NumeroIstanzaRiferimentoInesistenteException_Exception | 
				ComuneNonAbilitatoException_Exception | 
				IstanzaNonConsultabileException_Exception | 
				VersioneTracciatoInesistenteException_Exception | 
				NumeroTicketInesistente_Exception | 
				NumeroFascicoloNonCorrettoException_Exception | 
				FunzioneNonAbilitataException_Exception | 
				ValoreNonAmmessoException_Exception | 
				FruitoreNonAbilitatoModelloException_Exception | 
				TokenInesistenteException_Exception | 
				TipoTracciatoNonAbilitatoException_Exception | 
				SystemException_Exception | 
				VersioneTracciatoNonAttivaException_Exception | 
				TipologiaIstanzaNonAbilitataFruitoreException_Exception | 
				FruitoreNonAbilitatoComuneException_Exception | 
				SpeciePraticaNonRiferitaModelloException_Exception | 
				FruitoreNonAbilitatoOperazioneException_Exception | 
				ComuneInsesistenteException_Exception | 
				IntestatarioNonPresenteException_Exception | 
				NumeroIstanzaNonCorrettoException_Exception | 
				NumeroFascicoloComuneNonCoerentiException_Exception | 
				IstanzaInviataException_Exception | 
				PraticaNonConsultabileException_Exception | 
				FruitoreNoComuniAssociatiException_Exception | 
				CognomeIntestatarioPersoneFisicheException_Exception | 
				UnrecoverableException_Exception | 
				NumeroIstanzaObbligatorioException_Exception | 
				TracciatoVersioneNonCoerentiException_Exception | 
				CampoObbligatorioException_Exception | 
				DocumentoPraticaInesistenteException_Exception | 
				SpeciePraticaNonAbilitataException_Exception | 
				RagioneSocialeIntestatarioPersoneGiuridicheException_Exception me) {
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
		if(msg.indexOf("ValoreNonAmmessoException") > -1)
			throw new ValoreNonAmmessoException_Exception(msg);
		if(msg.indexOf("IstanzaNonTrovataException") > -1)
			throw new IstanzaNonTrovataException_Exception(msg);
		if(msg.indexOf("UnrecoverableException") > -1)
			throw new UnrecoverableException_Exception(msg);
		if(msg.indexOf("ComuneInsesistenteException") > -1)
			throw new ComuneInsesistenteException_Exception(msg);
		if(msg.indexOf("FruitoreNoComuniAssociatiException") > -1)
			throw new FruitoreNoComuniAssociatiException_Exception(msg);
		if(msg.indexOf("FruitoreNonAbilitatoComuneException") > -1)
			throw new FruitoreNonAbilitatoComuneException_Exception(msg);
		if(msg.indexOf("FruitoreNonAbilitatoTipologiaIstanzaException") > -1)
			throw new FruitoreNonAbilitatoTipologiaIstanzaException_Exception(msg);
		if(msg.indexOf("FruitoreNonAbilitatoOperazioneException") > -1)
			throw new FruitoreNonAbilitatoOperazioneException_Exception(msg);
		if(msg.indexOf("FruitoreNoModelliAssociatiException") > -1)
			throw new FruitoreNoModelliAssociatiException_Exception(msg);
		if(msg.indexOf("FunzioneNonAbilitataException") > -1)
			throw new FunzioneNonAbilitataException_Exception(msg);
		if(msg.indexOf("FruitoreNonAbilitatoModelloException") > -1)
			throw new FruitoreNonAbilitatoModelloException_Exception(msg);
		if(msg.indexOf("TokenInesistenteException") > -1)
			throw new TokenInesistenteException_Exception(msg);
		if(msg.indexOf("NomeIntestatarioPersoneFisicheException") > -1)
			throw new NomeIntestatarioPersoneFisicheException_Exception(msg);
		if(msg.indexOf("RuoloUtenteNonValidoException") > -1)
			throw new RuoloUtenteNonValidoException_Exception(msg);
		if(msg.indexOf("DataNonValidaException") > -1)
			throw new DataNonValidaException_Exception(msg);
		if(msg.indexOf("TipologiaIstanzaInesistenteException") > -1)
			throw new TipologiaIstanzaInesistenteException_Exception(msg);
		if(msg.indexOf("NumeroIstanzaComuneNonCoerentiException") > -1)
			throw new NumeroIstanzaComuneNonCoerentiException_Exception(msg);
		if(msg.indexOf("TipoTracciatoNonPrevistoTipoIstanzaException") > -1)
			throw new TipoTracciatoNonPrevistoTipoIstanzaException_Exception(msg);
		if(msg.indexOf("NumeroIstanzaRiferimentoInesistenteException") > -1)
			throw new NumeroIstanzaRiferimentoInesistenteException_Exception(msg);
		if(msg.indexOf("VersioneTracciatoInesistenteException") > -1)
			throw new VersioneTracciatoInesistenteException_Exception(msg);
		if(msg.indexOf("NumeroFascicoloNonCorrettoException") > -1)
			throw new NumeroFascicoloNonCorrettoException_Exception(msg);
		if(msg.indexOf("TipoTracciatoNonAbilitatoException") > -1)
			throw new TipoTracciatoNonAbilitatoException_Exception(msg);
		if(msg.indexOf("VersioneTracciatoNonAttivaException") > -1)
			throw new VersioneTracciatoNonAttivaException_Exception(msg);
		if(msg.indexOf("TipologiaIstanzaNonAbilitataFruitoreException") > -1)
			throw new TipologiaIstanzaNonAbilitataFruitoreException_Exception(msg);
		if(msg.indexOf("SpeciePraticaNonRiferitaModelloException") > -1)
			throw new SpeciePraticaNonRiferitaModelloException_Exception(msg);
		if(msg.indexOf("IntestatarioNonPresenteException") > -1)
			throw new IntestatarioNonPresenteException_Exception(msg);
		if(msg.indexOf("NumeroIstanzaNonCorrettoException") > -1)
			throw new NumeroIstanzaNonCorrettoException_Exception(msg);
		if(msg.indexOf("NumeroFascicoloComuneNonCoerentiException") > -1)
			throw new NumeroFascicoloComuneNonCoerentiException_Exception(msg);
		if(msg.indexOf("IstanzaInviataException") > -1)
			throw new IstanzaInviataException_Exception(msg);
		if(msg.indexOf("PraticaNonConsultabileException") > -1)
			throw new PraticaNonConsultabileException_Exception(msg);
		if(msg.indexOf("CognomeIntestatarioPersoneFisicheException") > -1)
			throw new CognomeIntestatarioPersoneFisicheException_Exception(msg);
		if(msg.indexOf("TracciatoVersioneNonCoerentiException") > -1)
			throw new TracciatoVersioneNonCoerentiException_Exception(msg);
		if(msg.indexOf("DocumentoPraticaInesistenteException") > -1)
			throw new DocumentoPraticaInesistenteException_Exception(msg);
		if(msg.indexOf("SpeciePraticaNonAbilitataException") > -1)
			throw new SpeciePraticaNonAbilitataException_Exception(msg);
		if(msg.indexOf("RagioneSocialeIntestatarioPersoneGiuridicheException") > -1)
			throw new RagioneSocialeIntestatarioPersoneGiuridicheException_Exception(msg);
		if(msg.indexOf("SystemException") > -1)
			throw new SystemException_Exception(msg);
		if(msg.indexOf("IstanzaNonRegistrataPAException") > -1)
			throw new IstanzaNonRegistrataPAException_Exception(msg);
		if(msg.indexOf("NumeroIstanzaObbligatorioException") > -1)
			throw new NumeroIstanzaObbligatorioException_Exception(msg);
		
    	throw new SystemException_Exception(msg);
	}
}
