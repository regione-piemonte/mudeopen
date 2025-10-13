/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.web.impl;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenTSessioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenDFruitoreRepository;
import it.csi.mudeopen.mudeopensrvsoap.business.be.exception.MudeWSException;
import it.csi.mudeopen.mudeopensrvsoap.business.be.web.Autenticazione;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione.CodiceFiscaleObbligatorioException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione.FruitoreDisabilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione.FruitoreNoComuniAssociatiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione.SystemException_Exception;

import org.codehaus.jettison.json.JSONObject;
@WebService(
        serviceName = "AutenticazioneSrvService",
        portName = "AutenticazioneSrvPort",
        targetNamespace = "http://autenticazione.business.mudesrvextsic.mude.csi.it/",
        endpointInterface = "it.csi.mudeopen.mudeopensrvsoap.business.be.web.Autenticazione")
//      wsdlLocation = "http://serviziweb.csi.it/mudesrvextsicApplAutenticazioneWs/mudesrvextsicAutenticazione?wsdl",
//      wsdlLocation = "file:templates/wsdl/Autenticazione_MUDE.wsdl",
@Component
public class AutenticazioneImpl extends GenericApi implements Autenticazione {
	
    @Resource
    private WebServiceContext context;

	@Autowired
	protected MudeopenDFruitoreRepository mudeopenDFruitoreRepository;
	
	//autenticazione
	@Override
	public String autenticazioneMUDE(String codiceFiscale,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) 
					throws SystemException_Exception, 
					FruitoreNoComuniAssociatiException_Exception, 
					FruitoreDisabilitatoException_Exception, 
					CodiceFiscaleObbligatorioException_Exception {
        try {
			MessageContext messageContext = context.getMessageContext();
	        HttpServletRequest request = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);
	        
	        String authUser = decodeBasicAuth(request)[0];
			if(StringUtils.isBlank(codiceFiscale))
				throw new CodiceFiscaleObbligatorioException_Exception(authUser);
			
			logStart(" - basica auth username: " + authUser + " - codice fiscale: " + codiceFiscale);
			String token = getToken(authUser, codiceFiscale, request.getRemoteAddr());
			
	        logEnd(" - basica auth username: " + authUser + " - codice fiscale: " + codiceFiscale);
	        return token;
        }
        catch(/*SystemException_Exception | */CodiceFiscaleObbligatorioException_Exception | FruitoreDisabilitatoException_Exception | FruitoreNoComuniAssociatiException_Exception ws) {
            throw ws;
        }
        catch(Throwable t) {
            throw new SystemException_Exception("Errore generico: " + t.getMessage(), t);
        }
	}
	
	private String getToken(String userName, String codiceFiscale, String remoteAdrr) throws 
												SystemException_Exception, 
												CodiceFiscaleObbligatorioException_Exception, 
												FruitoreDisabilitatoException_Exception, 
												FruitoreNoComuniAssociatiException_Exception {
    	if(codiceFiscale == null)
    		throw new CodiceFiscaleObbligatorioException_Exception("CodiceFiscale obbligatorio");
		
    	MudeDFruitore mudeopenDFruitore = mudeopenDFruitoreRepository.findByCodiceFruitore(userName);
    	if(mudeopenDFruitore == null) {
    		if(codiceFiscale != null
    				 && ("local".equals(_ENVIRONMENT)
    						 || mudeCProprietaRepository.getValueByName("RESOLVE_FRUITORE_PARAM_AUTH", "disabilitato").equals("abilitato"))) // resolve fruitoreId in case there is no
    			mudeopenDFruitore = mudeopenDFruitoreRepository.findByCodiceFruitore(codiceFiscale);
    		
        	if(mudeopenDFruitore == null)
        		throw new CodiceFiscaleObbligatorioException_Exception("Fruitore non trovato: " + userName);
    	}
    	
    	if(mudeopenDFruitore.getDataFine() != null && mudeopenDFruitore.getDataFine().getTime() > System.currentTimeMillis())
			throw new FruitoreDisabilitatoException_Exception(userName);
    	if(!mudeopenDFruitoreRepository.existsAtLeastOneComune(mudeopenDFruitore.getIdFruitore()))
			throw new FruitoreNoComuniAssociatiException_Exception("Fruitore non abilitato" + userName);
    	
    	MudeopenTSessione sessione = saveSessione(mudeopenDFruitore.getIdFruitore(), userName, codiceFiscale, remoteAdrr);
    	UUID token = sessione.getIdSessione();
    	
    	return token.toString();
	}

	private MudeopenTSessione saveSessione(Long idFruitore, String username, String codiceFiscale, String ip) {
		MudeopenTSessione mudeopenTSessione = new MudeopenTSessione();
    	mudeopenTSessione.setFruitore(username);
    	mudeopenTSessione.setDataCreazione(new Date());
    	mudeopenTSessione.setIdSessione(UUID.randomUUID());
    	
    	mudeopenTSessione.setParametroInput(
    			  "<it.csi.mude.mudesrvextsic.business.FruitoreSessione>\n"
    			+ "  <idFruitore>"+idFruitore+"</idFruitore>\n"
    			+ "  <codiceFruitore>"+username+"</codiceFruitore>\n"
    			+ "  <codiceFiscale>"+codiceFiscale+"</codiceFiscale>\n"
    			+ "  <ipAddress>"+ip+"</ipAddress>\n"
    			+ "</it.csi.mude.mudesrvextsic.business.FruitoreSessione>");

    	MudeopenTSessione sessione = mudeopenTSessioneRepository.saveDAO(mudeopenTSessione);
    	return sessione;
	}
	
	 private String[] decodeBasicAuth(HttpServletRequest request){
		    String upd=request.getHeader("Authorization");
		    if(upd == null)
				throw new IllegalArgumentException("header Authorization non trovato");
		    	
		    String pair=new String(Base64.getDecoder().decode(upd.substring(6)));
		    return pair.split(":");
	 }
	 

		public void handleException(String msg) throws MudeWSException {
			if(msg == null) 
				throw new SystemException_Exception("Errore generico");
			
			try {
	            JSONObject js = new JSONObject(msg);
	            if(js.has("message")) msg = js.getString("message");
			} catch (Exception noJSON) {}

			if(msg.indexOf("CodiceFiscaleObbligatorioException") > -1)
				throw new CodiceFiscaleObbligatorioException_Exception(msg);
			if(msg.indexOf("FruitoreDisabilitatoException") > -1)
				throw new FruitoreDisabilitatoException_Exception(msg);
			if(msg.indexOf("FruitoreNoComuniAssociatiException") > -1)
				throw new FruitoreNoComuniAssociatiException_Exception(msg);
			if(msg.indexOf("SystemException") > -1)
				throw new SystemException_Exception(msg);
			
	    	throw new SystemException_Exception(msg);
		}
	 
}
