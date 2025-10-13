/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon;
import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.oauth2.OauthHelperLoccsi;
@Component
public class LoccsiServiceHelper {
	private static Logger logger = Logger.getLogger(LoccsiServiceHelper.class.getCanonicalName());
	
    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;
	
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();
	private String getURLToken() { return mudeCProprietaRepository.getValueByName("LOCCSI_URL_TOKEN", "http://api-piemonte.csi.it/token"); }

	//private String getUrlConfigruration() {return mudeCProprietaRepository.getValueByName("LOCCSI_URL_API", "http://api-piemonte.csi.it/gis/loccsiapi/v2/catalogs/civici_full/suggest/buffer"); }
	private String getConsumerKey() { return mudeCProprietaRepository.getValueByName("LOCCSI_CUSTOMER_KEY", "Ai1w8Gnt_FLP97rvhcKMGMf2ObUa"); }

	private String getConsumerSecret() { return mudeCProprietaRepository.getValueByName("LOCCSI_CONSUMER_SECRET", "pyG1MG6wogJ8cfOZfxXfqLNxyE4a"); }
	private boolean isApiManagerTokenChacheEnabled() {return "abilitato".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("API_MANAGER_TOKEN_CACHE", "abilitato")) ;}
	private int getMaxTimeout() { return Integer.parseInt(mudeCProprietaRepository.getValueByName("LOCCSI_MAX_TOKEN_LEASE_MS", "-1")); } // -1 disabled by default, it takes the expires from token
	
	private Invocation.Builder getBuilder(String url) {

        Client client = ClientBuilder.newBuilder().build();
        String token = getURLToken();
        String uid = getConsumerKey();
        String pwd = getConsumerSecret();
        OauthHelperLoccsi oauthHelper = new OauthHelperLoccsi(token, uid, pwd, getMaxTimeout(), isApiManagerTokenChacheEnabled());
        String oauth = oauthHelper.getToken();
    	client.register(new ClientRequestFilter() {
			@Override
			public void filter(ClientRequestContext requestContext) throws IOException {
				LoggerUtil.debug(logger, "LOCCSI-rest CALL ["+url+" - "+token+" - "+uid+" - "+pwd+" - "+oauth+"]: " + requestContext.getHeaders().toString());
			}
		});
		return client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + oauth);
    }

    public String getToponomastica(String urlService) {
    	
    	String result=null;
        Response res = getBuilder(urlService)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)               
                .get();
    	if(res.getStatus() != 200) {
			LoggerUtil.error(logger, "LOCCSI-rest ERROR [" + res.getStatus() + "]: " + res.readEntity(String.class));
			return null;
    	}
    	
    	return res.readEntity(String.class);
    }
}
