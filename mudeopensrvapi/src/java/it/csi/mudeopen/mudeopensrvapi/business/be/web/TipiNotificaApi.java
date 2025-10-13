/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/tipi-notifica")
public interface TipiNotificaApi {

	
    /**
     * ottieniTipiNotifica response.
     *
     * @param token       	  the token
     * @param userCf          the user cf
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
      the response
     */
    @GET
    Response ricercaTipoNotifica(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID) throws Exception;

}
