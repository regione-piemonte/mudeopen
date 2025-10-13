/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino.Civico;
import it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino.CivicoVO;
import it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino.Via;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.EntityLoggingFilter;
import it.csi.mudeopen.mudeopensrvcommon.util.oauth2.OauthHelperLoccsi;

@Component
public class ToponomasticaHelper {
    public static final Logger logger = Logger.getLogger(ToponomasticaHelper.class.getCanonicalName());
	
    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;
    
	private String getURLToken() { return mudeCProprietaRepository.getValueByName("TOPONOMASTICA_URL_TOKEN", "http://tst-api-piemonte.ecosis.csi.it/token"); }
	private String getConsumerKey() { return mudeCProprietaRepository.getValueByName("TOPONOMASTICA_CUSTOMER_KEY", "Ai1w8Gnt_FLP97rvhcKMGMf2ObUa"); }
	private String getConsumerSecret() { return mudeCProprietaRepository.getValueByName("TOPONOMASTICA_CONSUMER_SECRET", "pyG1MG6wogJ8cfOZfxXfqLNxyE4a"); }

    private String apiEndpoint() { return mudeCProprietaRepository.getValueByName("TOPONOMASTICA_SERVICES_ENDPOINT", "http://api-piemonte.csi.it/territorio/toponomastica/ctsapicoto/v1"); }

	private int getMaxTimeout() { return Integer.parseInt(mudeCProprietaRepository.getValueByName("TOPONOMASTICA_MAX_TOKEN_LEASE_MS", "-1")); } // -1 disabled by default, it takes the expires from token
	private boolean isApiManagerTokenChacheEnabled() {return "abilitato".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("API_MANAGER_TOKEN_CACHE", "abilitato")) ;}
	
    private Invocation.Builder getBuilder(String url) {

        Client client = ClientBuilder.newBuilder().build();
        String oauth = new OauthHelperLoccsi(getURLToken(), getConsumerKey(), getConsumerSecret(), getMaxTimeout(), isApiManagerTokenChacheEnabled()).getToken();
        if(Constants._environment.equals("local") || 
        		"enabled".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("TOPONOMASTICA_CALL_TRACE", "disabled")))
	        client.register(new EntityLoggingFilter("TOPONOMASTICA CALL TRACE ["+url+"]"));
        return client.target(apiEndpoint()+url).request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + oauth);
    }

	protected String handleError(Response response, Exception t) throws Exception {
		String msg = "internal error reading response";
		try {
			msg = response.readEntity(String.class);
	        JSONObject js = new JSONObject(msg);
            if(js.has("code")) msg = js.getString("status") + "-" + js.getString("code") + ": " + js.getString("title");
		} catch (Exception skip) {}
		
		logger.error(String.format("[%s::%s] ECXEPTION:" + msg, getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName()), t);
		
		throw new Exception(msg + (t == null? "" : t.getMessage()));
	}
	
    public CivicoVO cercaCivicoPerId(Integer idCivico) throws Exception {
    	Response response = null;
    	
    	if(idCivico != null)
			try {
				response = getBuilder("/civici/" + idCivico)
						            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
						            .get();
		        if (response.getStatus() == 200) {
		        	String json = response.readEntity(new GenericType<String>() {});
		        	logger.info(String.format("[%s::%s("+idCivico+")] response: " + json, getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName()));
		        	
		        	List<CivicoVO> civici = new ObjectMapper().readValue(json , new TypeReference<List<CivicoVO>>() {}); 
		        	if(civici.size() > 0) {
		        		CivicoVO civico = civici.get(0);
		        		civico.setVia(cercaViaPerId(civico.getIdVia()));
		        		
		        		return civico;
		        	}
		        	
					return null;
		        }
	        
				handleError(response, null);
			} catch (Exception e) { 
				handleError(response, e);
			}
		
		return null;
    }
    
    public Via cercaViaPerId(Integer idVia) throws Exception {
    	Response response = null;

    	if(idVia != null)
			try {
				response = getBuilder("/vie/" + idVia)
						            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
						            .get();
		        if (response.getStatus() == 200) {
		        	String json = response.readEntity(new GenericType<String>() {});
		        	logger.info(String.format("[%s::%s("+idVia+")] response: " + json, getClass().getSimpleName(), Thread.currentThread().getStackTrace()[2].getMethodName()));

		        	List<Via> vie = new ObjectMapper().readValue(json , new TypeReference<List<Via>>() {});
		        	if(vie.size() > 0)
		        		return vie.get(0);

					return null;
		        }
	        
				handleError(response, null);
			} catch (Exception e) { 
				handleError(response, e);
			}
		
		return null;
    }
    
}
