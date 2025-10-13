/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util.oauth2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.util.EntityLoggingFilter;

public class OauthHelperGeeco {

	static String classTag = "GEECO";
	static Logger logger = Logger.getLogger(OauthHelperGeeco.class.getCanonicalName());

    public static final String ACCESS_TOKEN = "access_token";
    public static final String OAUTH2_GRANT_TYPE = "grantType";
    public static final String OAUTH2_GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

	private String oauthURL;
	private String consumerKey;
	private String consumerSecret;
	private int timeout;
	private boolean isTokenCacheEnabled = true;

	private static volatile String token;

	private static volatile Long expires;

	private static volatile long hashCount; // # accessi token in cache
	private static volatile long counter;   // # richieste token

	private static volatile long expirationTime;

	public OauthHelperGeeco(String oauthURL, String consumerKey, String consumerSecret, int timeout, boolean isTokenCacheEnabled) {
		this.oauthURL = (oauthURL != null) ? oauthURL.trim() : oauthURL;
		this.consumerKey = (consumerKey != null) ? consumerKey.trim() : consumerKey;
		this.consumerSecret = (consumerSecret != null) ? consumerSecret.trim() : consumerSecret;
		this.timeout = timeout;
		this.isTokenCacheEnabled = isTokenCacheEnabled;
	}

    public String getToken() {
		if(token != null && isTokenCacheEnabled && System.currentTimeMillis() < expirationTime) {
			hashCount++;
			
			logger.info("CACHED Token "+classTag+" di accesso apiman [" + counter + "," + hashCount + "] " + Util.maskForLog(token) +
					" remaining " + ((expirationTime - System.currentTimeMillis()) / 1000/60) + " of " + (expires/60) + "m - at " + expirationTime + " ("+System.currentTimeMillis()+")");
			
			return token; // use cache if exist
		}

		return getTokenInternal();
    }

    private String getTokenInternal() {
    	
		logger.info("[OauthHelperGeeco.getTokenInternal]\n" + 
			        "oauthURL ........: " + oauthURL + "\n" +
			        "consumerKey .....: " + consumerKey + "\n" +
			        "consumerSecret ..: " + Util.maskForLog(consumerSecret) + "\n" +
			        "max token timeout: " + timeout);

		ResteasyClient client = new ResteasyClientBuilder()
				//.connectTimeout(timeout, TimeUnit.MILLISECONDS)
				//.readTimeout(timeout, TimeUnit.MILLISECONDS)
				.build();
		
        client.register(new EntityLoggingFilter("API MANAGER CALL TRACE ["+this.oauthURL+"]"));
		
        ResteasyWebTarget target = client.target(this.oauthURL);
        Form tokenForm = new Form();
        tokenForm
        .param("grant_type", "client_credentials")
        .param("client_id", consumerKey)
        .param("client_secret", consumerSecret);
        
        Response tokenResponse = target.request()
        	.header("Accept","text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2")
        	.header("Content-type","application/x-www-form-urlencoded")
        	.post(Entity.form(tokenForm));
		int statusCode = tokenResponse.getStatus();
        if (statusCode != HttpStatus.SC_OK) {
        	logger.error("wrong HTTP status code: " + statusCode + "\nStatusLine:" + tokenResponse.getStatusInfo());
        	token = null;
        	expires = null;
        	logger.warn("OAUT2 token set to null");
        	return token;
        }

        // Read the response body.
       GetTokenResponse responseBody = tokenResponse.readEntity(GetTokenResponse.class);

       token = responseBody.getAccess_token();
       expires = responseBody.getExpires_in();
       
		if (token == null || expires == null) {
        	logger.error("unexpected reply: ");
        	token = null;
        	expires = null;
        	logger.info("OAUT2 token set to null");
        	return token;				
		}
		if(this.timeout != -1) {
			logger.info("token timeout override: " + timeout + " <---- " + expires);
			expires = (long)timeout;
		}

		expirationTime = System.currentTimeMillis() + (expires*1000);

		String jsonResponse = null;
		try {
			jsonResponse = new ObjectMapper().writeValueAsString(responseBody);
			logger.info("API MANAGER CALL RESPONSE "+classTag+" " + jsonResponse);
		} catch (Exception e) { }
    	String out = "NEW Token "+classTag+" di accesso apiman[" + counter + "," + hashCount +"] " + Util.maskForLog(token) + " expires in " +   + (expires/60) + "m - at " + expirationTime + " ("+System.currentTimeMillis()+")";

		logger.info(out);
	    return token;
    }


}