/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.cts.IllegalCoordinateException;
import org.cts.op.CoordinateOperationException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;

@Component
public class LoocsiHelper {

    private static final String CLIENT_CREDENTIALS = "client_credentials";

	private static Logger logger = Logger.getLogger(LoocsiHelper.class.getCanonicalName());

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;

	public String getURLToken() { return mudeCProprietaRepository.getValueByName("LOCCSI_URL_TOKEN", "http://api-piemonte.csi.it/token"); }
	public String getUrlConfigruration() {return mudeCProprietaRepository.getValueByName("LOCCSI_URL_API", "http://api-piemonte.csi.it/gis/loccsiapi/v2/catalogs/civici_full/suggest/buffer"); }
	public String getConsumerKey() { return mudeCProprietaRepository.getValueByName("LOCCSI_CUSTOMER_KEY", "Ai1w8Gnt_FLP97rvhcKMGMf2ObUa"); }
	public String getConsumerSecret() { return mudeCProprietaRepository.getValueByName("LOCCSI_CONSUMER_SECRET", "pyG1MG6wogJ8cfOZfxXfqLNxyE4a"); }

    @Autowired
    CoordinateTrasformationHelper coordinateTrasformationHelper;

    public String getAuthToken(){

        logger.info(getConsumerKey());
        logger.info(getConsumerSecret());

        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(new ClientRequestFilter() {
            @Override
            public void filter(ClientRequestContext requestContext) throws IOException {
                String token = getConsumerKey() + ":" + getConsumerSecret();
                String base64Token = Base64.encodeBytes(token.getBytes());
                requestContext.getHeaders().add("Authorization", "Basic " + base64Token);
            }
        });
        //  http://10.138.172.5/api/1.0.0/catalogs/toponomastica/suggest/buffer?x=8.465911164658435&y=44.67289875241866&dist=0.2&limit=30&offset=0
        String uri = getURLToken();
        ResteasyWebTarget target = client.target(uri);
        Form form = new Form();
        form.param("grant_type", CLIENT_CREDENTIALS);
        Entity<Form> entity = Entity.form(form);
        Response res = target.request().post(entity);
        logger.info(uri);
        logger.info(res.getStatus());

        if(res.getStatus() != 200) {
            return null;
        }

        logger.info("Server response : \n");
        String jsonResponse = res.readEntity(String.class);
        logger.info(jsonResponse);

        try {
            JsonNode node = mapper.readTree(jsonResponse);
            return node.get("access_token").textValue();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonNode locationsFromCoordinates(double[] coordsFromGeeco, boolean isTorino) throws IllegalCoordinateException, CoordinateOperationException {

        String authToken = getAuthToken();
        double[] wgs84Coords = isTorino ? coordinateTrasformationHelper.fromEpsg3003ToWgs84(coordsFromGeeco) : coordinateTrasformationHelper.fromUtm32ToWgs84(coordsFromGeeco);
        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(new ClientRequestFilter() {
            @Override
            public void filter(ClientRequestContext requestContext) throws IOException {
                logger.info("authToken = " + authToken);
                requestContext.getHeaders().add("Authorization", "Bearer " + authToken);
            }
        });
        //  http://10.138.172.5/api/1.0.0/catalogs/toponomastica/suggest/buffer?x=8.465911164658435&y=44.67289875241866&dist=0.2&limit=30&offset=0
        String uri = getUrlConfigruration() + "?x=" + wgs84Coords[0] + "&y=" + wgs84Coords[1] + "&dist=" + 0.250 + "&limit=" + 10;
        ResteasyWebTarget target = client.target(uri);
        Response res = target.request().get();

        String jsonResponse = res.readEntity(String.class);
        logger.info("[LoocsiHelper::locationsFromCoordinates] "+coordsFromGeeco[0] + "," + coordsFromGeeco[1] + +res.getStatus()+", " + uri + ": " + jsonResponse);

        try {
            JsonNode node = mapper.readTree(jsonResponse);
            return node;
        } catch (JsonProcessingException e) {
            logger.error("[LoocsiHelper::locationsFromCoordinates] JsonProcessingException", e);
            return null;
        }
    }
}
