/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco.GeecoParticella;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco.GeecoParticellaExtraTorino;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco.GeecoParticellaTorinoCatastoFabbricati;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco.GeecoParticellaTorinoCatastoTerreni;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.generali.SigalfsrvGenerali;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.generali.SigalfsrvGeneraliService;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni.DettaglioDatiTerreno;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni.IdentificativoCatastaleTerreno;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni.SigalfsrvTerreni;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni.SigalfsrvTerreniService;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu.DettaglioDatiFabbricato;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu.IdentificativoCatastaleFabbricato;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu.SigalfsrvUiu;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu.SigalfsrvUiuService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoSessionCllbkRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.oauth2.OauthHelperSigmater;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;

import javax.ws.rs.core.HttpHeaders;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

@Component
public class SigmaterFactoryServiceHelper {
	private static Logger logger = Logger.getLogger(SigmaterFactoryServiceHelper.class.getCanonicalName());

	@Autowired
	MudeTGeecoSessionCllbkRepository mudeTGeecoSessionCllbkRepository;

	@Autowired
	MudeCProprietaRepository mudeCProprietaRepository;

	public String getSigmaterUser() { return mudeCProprietaRepository.getValueByName("SIGMATER_USER", "USER_REG_029"); }
	public String getSigmaterPassword() { return mudeCProprietaRepository.getValueByName("SIGMATER_PASSWORD", "tst_029"); }

	public Boolean getMockSigmater () {
		try{
			String valueByName = mudeCProprietaRepository.getValueByName("SIGMATER_MOCK", "false");
			logger.info(">>>> SIGMATER_MOCK ="+ valueByName);

			Boolean mockSigmater = Boolean.parseBoolean(valueByName);
			return mockSigmater;
		} catch (Exception e){
			return false;
		}

	}

	/**
	 *
	 * @param particella
	 * @param idEditingSession
	 
	 */
	public boolean isAbilitatoSigmater(GeecoParticella particella, String idEditingSession, String codiceFiscale) {
		String belfiore=particella.getBelfiore();
		if(belfiore ==null) {
			belfiore = mudeTGeecoSessionCllbkRepository.findBelfioreByGeecoSessinId(idEditingSession);
		}
		boolean isComuneEnabled=false;
		if(belfiore==null)
			return isComuneEnabled;
		try {
			isComuneEnabled=isComuneAventeCodiceBelfioreAbilitatoSigmater(belfiore, codiceFiscale);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isComuneEnabled;
	}
	
	private Map<String, Object> setAuhentication(BindingProvider bindingProvider, String endpoint, String endpointOauth) {
        Map<String, List<String>> requestHeaders = new HashMap<>();
		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		
        List<String> auths = new ArrayList<>();
        List<String> xauths = new ArrayList<>();
		try {
			String basicAuth = Base64.getEncoder().encodeToString((getSigmaterUser() + ":" + getSigmaterPassword()).getBytes("ISO-8859-1"));
			auths.add("Basic " + basicAuth);
		}
		catch(Exception ex) {
			logger.error("Impossible set TOKEN API MANAGER for sigalfsrvGeneraliPort", ex);
		}

    	String tokenEndpoint = mudeCProprietaRepository.getValueByName("SIGMATER_URLTOKEN", "");
    	if(!"".equals(tokenEndpoint)) {
	        OauthHelperSigmater oauthHelper = new OauthHelperSigmater(tokenEndpoint, 
	        								mudeCProprietaRepository.getValueByName("SIGMATER_CONSUMER_KEY", ""), 
	        								mudeCProprietaRepository.getValueByName("SIGMATER_CONSUMER_SECRET", ""), 
	        								Integer.parseInt(mudeCProprietaRepository.getValueByName("SIGMATER_MAX_TOKEN_LEASE_MS", "-1")), 
	        								"abilitato".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("API_MANAGER_TOKEN_CACHE", "abilitato")));
	        xauths.add("Bearer " + oauthHelper.getToken());
	        requestHeaders.put(HttpHeaders.AUTHORIZATION, xauths);
	        requestHeaders.put(TokenRetryManager.X_AUTH, auths);
	        endpoint = endpointOauth; // override basic auth endpoint
    	}
    	else
    		requestHeaders.put(HttpHeaders.AUTHORIZATION, auths);
        
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, mudeCProprietaRepository.getValueByName(endpoint, ""));
    	
        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
		
        if("abilitato".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("SIGMATER_TRACE", ""))) {
			Binding binding = bindingProvider.getBinding();
			List<Handler> handlerChain = binding.getHandlerChain();
			handlerChain.add(new SOAPHandler<SOAPMessageContext>() {
			    public Set<QName> getHeaders() { return null; }

			    public boolean handleMessage(SOAPMessageContext messageContext) {
			        Boolean isRequest = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			        if (isRequest)
			            System.out.println("SOAP Request["+tokenEndpoint+" / "+StringUtils.join(auths, ",")+" / "+StringUtils.join(xauths, ",")+"]: ");
			        else
			            System.out.println("SOAP Response: ");
			        
			        SOAPMessage message = messageContext.getMessage();
			        try {
			            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
			            SOAPHeader header = envelope.getHeader();
			            message.writeTo(System.out);
			        } catch (Exception skip) { }

			        return true;
			    }
			
			    public boolean handleFault(SOAPMessageContext smc) { return true; }
			    public void close(MessageContext messageContext) { }
			
			});
			binding.setHandlerChain(handlerChain);
        }
        
        return requestContext;
	}
	
	private SigalfsrvGenerali getGeneraliWS() {
		SigalfsrvGenerali sigalfsrvGeneraliPort = new SigalfsrvGeneraliService().getSigalfsrvGenerali();
		setAuhentication(((BindingProvider)sigalfsrvGeneraliPort), "SIGMATER_GENERALI_URL", "SIGMATER_APIMANAGER_ENDPOINT_SigalfsrvGenerali");
		
		return sigalfsrvGeneraliPort;
	}
	
	private SigalfsrvUiu getUiuWS() {
		SigalfsrvUiu sigalfsrvUiuPort = new SigalfsrvUiuService().getSigalfsrvUiu();
		setAuhentication(((BindingProvider)sigalfsrvUiuPort), "SIGMATER_UIU_URL", "SIGMATER_APIMANAGER_ENDPOINT_SigalfsrvUiu");
		
		return sigalfsrvUiuPort;
	}
	
	private SigalfsrvTerreni getTerreniWS() {
		SigalfsrvTerreni sigalfsrvTerreniPort = new SigalfsrvTerreniService().getSigalfsrvTerreni();
		setAuhentication(((BindingProvider)sigalfsrvTerreniPort), "SIGMATER_TERRENI_URL", "SIGMATER_APIMANAGER_ENDPOINT_SigalfsrvTerreni");
		
		return sigalfsrvTerreniPort;
	}

	public boolean isComuneAventeCodiceBelfioreAbilitatoSigmater(String belfiore, String codiceFiscale) throws Exception {
		SigalfsrvGenerali sigalfsrvGenerali = getGeneraliWS();

		return sigalfsrvGenerali.isComuneAventeCodiceBelfioreAbilitatoSigmater(belfiore, codiceFiscale);
	}
	
	public List<ElementoCatasto> getAllElementiCatastoFromGeecoParticellaTorinoCatastoFabbricati(GeecoParticellaTorinoCatastoFabbricati geecoParticella, String codiceFiscale) throws Exception {
		List<ElementoCatasto> elementi = new ArrayList<>();

		SigalfsrvUiu sigalfsrvUiu = getUiuWS();

		if(geecoParticella.getParticellaCu()!=null && geecoParticella.getFoglioCu()!=null) {
			if(getMockSigmater()){

				logger.info("MOCKKKKKKKKK >>>>>>>>>>> QUERY SIGMATER FOR subalterni Fabbricati");

				ElementoCatasto el1 = new ElementoCatasto();
				el1.setBelfiore("L219");
				el1.setFoglio(geecoParticella.getFoglioCu());
				el1.setMappale(geecoParticella.getParticellaCu());
				el1.setSezione("_");
				el1.setSezioneUrbana("");
				el1.setSubalterno("1");
				el1.setCensito_al_catasto("catasto_fabbricati");
				elementi.add(el1);

				ElementoCatasto el2 = new ElementoCatasto();
				el2.setBelfiore("L219");
				el2.setFoglio(geecoParticella.getFoglioCu());
				el2.setMappale(geecoParticella.getParticellaCu());
				el2.setSezione("_");
				el2.setSezioneUrbana("");
				el2.setSubalterno("2");
				el2.setCensito_al_catasto("catasto_fabbricati");
				elementi.add(el2);
			}
			else {
				logger.info("QUERY SIGMATER FOR subalterni Fabbricati CU");

				List<IdentificativoCatastaleFabbricato> subalterni = new ArrayList<>();
				try {
					subalterni = sigalfsrvUiu.cercaElencoSubalterniConBelfioreESezione(
							geecoParticella.getBelfiore(),
							"_",
							geecoParticella.getFoglioCu(),
							geecoParticella.getParticellaCu(),
							codiceFiscale
					);
				} catch (Exception e) {
					logger.error("[SigmaterFactoryServiceHelper::getAllElementiCatastoFromGeecoParticellaTorinoCatastoFabbricati] Sigmater EXCEPTION", e);
				}

				logger.info("FOUND " + subalterni.size() + " subalterni Fabbricati");
				for (IdentificativoCatastaleFabbricato fabbricato : subalterni) {
					DettaglioDatiFabbricato dettagli = sigalfsrvUiu.cercaDettaglioDatiFabbricato(fabbricato,codiceFiscale);
					if(dettagli.getPartita()!=null && dettagli.getPartita().trim().equalsIgnoreCase("C")){
						logger.info("PROCESSING CESSATO subalterno Fabbricato " + fabbricato.getSubalterno()+" ");
					}
					else {
						logger.info("PROCESSING subalterno Fabbricato " + fabbricato.getSubalterno());

						ElementoCatasto el = new ElementoCatasto();
						el.setBelfiore(fabbricato.getCodComuneBelfiore());
						el.setFoglio(fabbricato.getFoglio());
						el.setMappale(fabbricato.getMappale());
						el.setSezione(fabbricato.getSezione());
						el.setSezioneUrbana(fabbricato.getSezioneUrbana());
						el.setSubalterno(fabbricato.getSubalterno());
						el.setCensito_al_catasto("catasto_fabbricati");

						elementi.add(el);
					}

				}
			}
		}
		if(geecoParticella.getParticellaCt()!=null && geecoParticella.getFoglioCt()!=null) {
			if(getMockSigmater()){

				logger.info("MOCKKKKKKKKK >>>>>>>>>>> QUERY SIGMATER FOR subalterni Fabbricati");

				ElementoCatasto el1 = new ElementoCatasto();
				el1.setBelfiore("L219");
				el1.setFoglio(geecoParticella.getFoglioCt());
				el1.setMappale(geecoParticella.getParticellaCt());
				el1.setSezione("_");
				el1.setSezioneUrbana("");
				el1.setSubalterno("1");
				el1.setCensito_al_catasto("catasto_fabbricati");
				elementi.add(el1);

				ElementoCatasto el2 = new ElementoCatasto();
				el2.setBelfiore("L219");
				el2.setFoglio(geecoParticella.getFoglioCt());
				el2.setMappale(geecoParticella.getParticellaCt());
				el2.setSezione("_");
				el2.setSezioneUrbana("");
				el2.setSubalterno("2");
				el2.setCensito_al_catasto("catasto_fabbricati");
				elementi.add(el2);
			}
			else {
				logger.info("QUERY SIGMATER FOR subalterni Fabbricati CT");

				List<IdentificativoCatastaleFabbricato> subalterni = new ArrayList<>();
				try {
					subalterni = sigalfsrvUiu.cercaElencoSubalterniConBelfioreESezione(
							geecoParticella.getBelfiore(),
							"_",
							geecoParticella.getFoglioCt(),
							geecoParticella.getParticellaCt(),
							codiceFiscale
					);
				} catch (Exception e) {
					logger.error("[SigmaterFactoryServiceHelper::getAllElementiCatastoFromGeecoParticellaTorinoCatastoFabbricati] Sigmater EXCEPTION", e);
				}

				logger.info("FOUND " + subalterni.size() + " subalterni Fabbricati");
				for (IdentificativoCatastaleFabbricato fabbricato : subalterni) {
					DettaglioDatiFabbricato dettagli = sigalfsrvUiu.cercaDettaglioDatiFabbricato(fabbricato,codiceFiscale);
					if(dettagli.getPartita()!=null && dettagli.getPartita().trim().equalsIgnoreCase("C")){
						logger.info("PROCESSING CESSATO subalterno Fabbricato " + fabbricato.getSubalterno()+" ");
					}
					else {
						logger.info("PROCESSING subalterno Fabbricato " + fabbricato.getSubalterno());

						ElementoCatasto el = new ElementoCatasto();
						el.setBelfiore(fabbricato.getCodComuneBelfiore());
						el.setFoglio(fabbricato.getFoglio());
						el.setMappale(fabbricato.getMappale());
						el.setSezione(fabbricato.getSezione());
						el.setSezioneUrbana(fabbricato.getSezioneUrbana());
						el.setSubalterno(fabbricato.getSubalterno());
						el.setCensito_al_catasto("catasto_fabbricati");

						elementi.add(el);
					}

				}
			}
		}

		logger.info("FOUND "+elementi.size()+" elementi catasto");

		return elementi;
	}
	
	public List<ElementoCatasto> getAllElementiCatastoFromParticellaTorinoCatastoTerreni(GeecoParticellaTorinoCatastoTerreni geecoParticella, String codiceFiscale) throws Exception {
		List<ElementoCatasto> elementi = new ArrayList<>();

		SigalfsrvTerreni sigalfsrvTerreni = getTerreniWS();

		if(getMockSigmater()){

			logger.info("MOCKKKKKKKKK >>>>>>>>>>> QUERY SIGMATER FOR subalterni Terreni");

			ElementoCatasto el1 = new ElementoCatasto();
			el1.setBelfiore("L219");
			el1.setFoglio(geecoParticella.getFoglio());
			el1.setMappale(geecoParticella.getnPart());
			el1.setSezione("_");
			el1.setSezioneUrbana("");
			el1.setSubalterno("1");
			el1.setCensito_al_catasto("catasto_terreni");
			elementi.add(el1);

			ElementoCatasto el2 = new ElementoCatasto();
			el2.setBelfiore("L219");
			el2.setFoglio(geecoParticella.getFoglio());
			el2.setMappale(geecoParticella.getnPart());
			el2.setSezione("_");
			el2.setSezioneUrbana("");
			el2.setSubalterno("2");
			el2.setCensito_al_catasto("catasto_terreni");
			elementi.add(el2);
		}
		else {

			logger.info("QUERY SIGMATER FOR subalterni Terreni");

			List<IdentificativoCatastaleTerreno> subalterniTerreni = new ArrayList<>();
			try {
				subalterniTerreni = sigalfsrvTerreni.cercaElencoSubalterniTerreniConBelfioreESezione(
						geecoParticella.getBelfiore(),
						"_",
						Integer.parseInt(geecoParticella.getFoglio()),
						geecoParticella.getnPart(),
						codiceFiscale
				);
			} catch (Exception e) {
			}

			logger.info("FOUND " + subalterniTerreni.size() + " subalterni Terreni");
			for (IdentificativoCatastaleTerreno terreno : subalterniTerreni) {
				DettaglioDatiTerreno dettagli = sigalfsrvTerreni.cercaDettaglioDatiTerreno(terreno,codiceFiscale);
				if(dettagli.getPartita()!=null && dettagli.getPartita().trim().equalsIgnoreCase("C")) {
					logger.info("PROCESSING CESSATO subalterno Terreno " + terreno.getSubalterno()+" ");
				}
				else {
					logger.info("PROCESSING subalterno Terreno " + terreno.getSubalterno());

					ElementoCatasto el = new ElementoCatasto();
					el.setBelfiore(terreno.getCodComuneBelfiore());
					el.setFoglio("" + terreno.getFoglio());
					el.setMappale(terreno.getMappale());
					el.setSezione(terreno.getSezione());
					el.setSubalterno(terreno.getSubalterno());

					el.setCensito_al_catasto("catasto_terreni");
					elementi.add(el);
				}
			}
		}

		logger.info("FOUND "+elementi.size()+" elementi catasto");

		return elementi;
	}
	
	public List<ElementoCatasto> getAllElementiCatastoFromParticellaExtraTorino(GeecoParticellaExtraTorino geecoParticella, String codiceFiscale) throws Exception {
		List<ElementoCatasto> elementi = new ArrayList<>();
		
		SigalfsrvTerreni sigalfsrvTerreni = getTerreniWS();

		if(getMockSigmater()){

			logger.info("MOCKKKKKKKKK >>>>>>>>>>> QUERY SIGMATER FOR subalterni Terreni");

			ElementoCatasto el1 = new ElementoCatasto();
			el1.setBelfiore(geecoParticella.getBelfiore());
			el1.setFoglio(geecoParticella.getFoglio());
			el1.setMappale(geecoParticella.getMappale());
			el1.setSezione("_");
			el1.setSezioneUrbana("");
			el1.setSubalterno("1");
			el1.setCensito_al_catasto("catasto_terreni");
			elementi.add(el1);

			ElementoCatasto el2 = new ElementoCatasto();
			el2.setBelfiore("L219");
			el2.setFoglio(geecoParticella.getFoglio());
			el2.setMappale(geecoParticella.getMappale());
			el2.setSezione("_");
			el2.setSezioneUrbana("");
			el2.setSubalterno("2");
			el2.setCensito_al_catasto("catasto_terreni");
			elementi.add(el2);
		}
		else {

			logger.info("QUERY SIGMATER FOR subalterni Terreni");

			List<IdentificativoCatastaleTerreno> subalterniTerreni = new ArrayList<>();
			try {
				subalterniTerreni = sigalfsrvTerreni.cercaElencoSubalterniTerreniConBelfioreESezione(
						geecoParticella.getBelfiore(),
						geecoParticella.getSezione(),
						Integer.parseInt(geecoParticella.getFoglio()),
						geecoParticella.getMappale(),
						codiceFiscale
				);
			} catch (Exception e) {
				logger.error("[SigmaterFactoryServiceHelper::getAllElementiCatastoFromParticellaExtraTorino] Sigmater EXCEPTION", e);
			}

			logger.info("FOUND " + subalterniTerreni.size() + " subalterni Terreni");
			for (IdentificativoCatastaleTerreno terreno : subalterniTerreni) {
				DettaglioDatiTerreno dettagli = sigalfsrvTerreni.cercaDettaglioDatiTerreno(terreno,codiceFiscale);
				if(dettagli.getPartita()!=null && dettagli.getPartita().trim().equalsIgnoreCase("C")) {
					logger.info("PROCESSING CESSATO subalterno Terreno " + terreno.getSubalterno()+" ");
				}
				else {
					logger.info("PROCESSING subalterno Terreno " + terreno.getSubalterno());

					ElementoCatasto el = new ElementoCatasto();
					el.setBelfiore(terreno.getCodComuneBelfiore());
					el.setFoglio("" + terreno.getFoglio());
					el.setMappale(terreno.getMappale());
					el.setSezione(terreno.getSezione());
					el.setSubalterno(terreno.getSubalterno());

					el.setCensito_al_catasto("catasto_terreni");

					elementi.add(el);
				}
			}
		}

		logger.info("FOUND "+elementi.size()+" elementi catasto");
		return elementi;

	}
	
	/*
	private it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari.IdentificativoCatastaleFabbricato convert(IdentificativoCatastaleFabbricato fabbricato){
		it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari.IdentificativoCatastaleFabbricato fabbricatoTitolari = new it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari.IdentificativoCatastaleFabbricato();
		fabbricatoTitolari.setCodComuneBelfiore(fabbricato.getCodComuneBelfiore());
		fabbricatoTitolari.setFoglio(fabbricato.getFoglio());
		fabbricatoTitolari.setMappale(fabbricato.getMappale());
		fabbricatoTitolari.setSezione(fabbricato.getSezione());
		fabbricatoTitolari.setSezioneUrbana(fabbricato.getSezioneUrbana());
		fabbricatoTitolari.setSubalterno(fabbricato.getSubalterno());
		return fabbricatoTitolari;
	}

	private it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari.IdentificativoCatastaleTerreno convert(IdentificativoCatastaleTerreno terreno){
		it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari.IdentificativoCatastaleTerreno terrenoTitolari = new it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari.IdentificativoCatastaleTerreno();
		terrenoTitolari.setCodComuneBelfiore(terreno.getCodComuneBelfiore());
		terrenoTitolari.setFoglio(terreno.getFoglio());
		terrenoTitolari.setMappale(terreno.getMappale());
		terrenoTitolari.setSezione(terreno.getSezione());
		terrenoTitolari.setSubalterno(terreno.getSubalterno());
		return terrenoTitolari;
	}
	*/
}
