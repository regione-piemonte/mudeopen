/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.BaseLoggerEntity;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.EntityLoggingFilter;
import it.csi.mudeopen.mudeopensrvcommon.util.oauth2.OauthHelperGeneric;

public abstract class AbstractServiceHelper {
    protected final Logger logger = Logger.getLogger(getClass().getCanonicalName());
	
    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;
    
	private boolean isApiManagerTokenChacheEnabled() {return "abilitato".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("API_MANAGER_TOKEN_CACHE", "abilitato")) ;}
	private int getMaxTimeout() { return Integer.parseInt(mudeCProprietaRepository.getValueByName(getScope() + "_MAX_TOKEN_LEASE_MS", "-1")); } // -1 disabled by default, it takes the expires from token
	
	private String getURLToken() { return mudeCProprietaRepository.getValueByName(getScope() + "_URL_TOKEN", ""); }
	private String getConsumerKey() { return mudeCProprietaRepository.getValueByName(getScope() + "_CUSTOMER_KEY", ""); }
	private String getConsumerSecret() { return mudeCProprietaRepository.getValueByName(getScope() + "_CONSUMER_SECRET", ""); }

    private String apiEndpoint() { return mudeCProprietaRepository.getValueByName(getScope() + "_SERVICES_ENDPOINT", ""); }
	
    protected final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();

    protected abstract String getScope();
    
    protected Invocation.Builder getBuilder(String url) {
        Client client = ClientBuilder.newBuilder()
        		.connectTimeout(Integer.parseInt(mudeCProprietaRepository.getValueByName(getScope() + "_TIMEOUT_CONNECTION_SEC", "30")), TimeUnit.SECONDS)
        		.readTimeout(Integer.parseInt(mudeCProprietaRepository.getValueByName(getScope() + "_TIMEOUT_READ_SEC", "30")), TimeUnit.SECONDS)
        		.build();
        if(Constants._environment.equals("local") || 
        		"enabled".equalsIgnoreCase(mudeCProprietaRepository.getValueByName(getScope() + "_CALL_TRACE", "disabled")))
	        client.register(new EntityLoggingFilter(getScope() + " CALL TRACE ["+url+"]"));

    	if(mudeCProprietaRepository.getValueByName(getScope() + "_AUTH_METHOD", "BASIC").trim().equalsIgnoreCase("BASIC")) {
	        Builder builder = client.target(mudeCProprietaRepository.getValueByName(getScope() + "_SERVICES_ENDPOINT_BASIC", "")+url).request();
	        
    		Stream.of(mudeCProprietaRepository.getValueByName(getScope() + "_BASIC_AUTH_HEADERS", "").split("~")).forEach(header -> {
    			builder.header(header.substring(0, header.indexOf(":")), header.substring(header.indexOf(":")+1).trim());
    		});
    		
    		return builder;
    	}
    	
        String oauth = new OauthHelperGeneric(getURLToken(), getConsumerKey(), getConsumerSecret(), getMaxTimeout(), isApiManagerTokenChacheEnabled()).getToken();
        return client.target(apiEndpoint()+url).request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + oauth);
    }

	protected String handleError(Response response, Exception t) throws Exception {
		String msg = "error reading response from " + getScope() + "[" + (response == null?"BEFORE CALL":response.getStatus()) + "]";
			
		if(t != null) {
			msg += ": " + t.getMessage();
			
			try {
				StringWriter st = new StringWriter();
				t.printStackTrace(new PrintWriter(st));
				msg += "\n" + st.toString();				
			} catch (Exception skip) {}
		}
		
		try {
			msg += " - RESPONSE: " + response.readEntity(String.class);
	        JSONObject js = new JSONObject(msg);
            if(js.has("code")) msg = js.getString("status") + "-" + js.getString("code") + ": " + js.getString("title");
		} catch (Exception noJsonEx) {}
		
		String method = Thread.currentThread().getStackTrace()[2].getMethodName();
		logger.error(String.format("[%s::%s] ECXEPTION:" + msg, getClass().getSimpleName(), method), t);
		
		throw new Exception(msg + (t == null? "" : t.getMessage()));
	}
	
	protected void saveLogResponse(final BaseLoggerEntity mudeTPraticaLogger, Object res,
									String label) throws JsonProcessingException {
		logger.debug(getScope() + " saveLog: " + label + ": " + new ObjectMapper().writeValueAsString(res));
		
		try {
			mudeTPraticaLogger.setJsonResponse(StringUtils.defaultString(mudeTPraticaLogger.getJsonResponse(), "") + 
				"["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] " + label + ": " + new ObjectMapper().writeValueAsString(res) + "\r\n\r\n");
		} catch (Exception e) {
			mudeTPraticaLogger.setJsonResponse(""+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+label + " exception: " + e.getMessage() + "\r\n\r\n"); 
		}
	}

 
	protected void doLog(boolean in, BaseLoggerEntity mudeTPraticaLogger, String msg) {
		logger.debug(getScope() + " doLog: " + msg);
		
		if(in)
			mudeTPraticaLogger.setJsonMetadata(StringUtils.defaultString(mudeTPraticaLogger.getJsonMetadata(), "") + "["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] " 
					+ msg +"\r\n\r\n");
		else
			mudeTPraticaLogger.setJsonResponse(StringUtils.defaultString(mudeTPraticaLogger.getJsonResponse(), "") + "["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] " 
					+ msg + "\r\n\r\n");
	}
    

}