/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.mudeopen.mudeopensrvapi.business.be.web;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("")
@Produces({ MediaType.APPLICATION_JSON })
public interface AllegatiApi {
    /**
     * Load allegati istanza response.
     *
     * @param numeroIstanza   the numeroIstanza
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
      the response
     */
    @GET
    @Path("/elenco-allegati/{numeroIstanza}")
    Response ricercaElencoAllegati(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("numeroIstanza") String numeroIstanza) throws Exception;

    @GET
    @Path("/get-elenco-allegati/{numeroIstanza}")
    Response getElencoAllegatiByNumeroIstanza(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("numeroIstanza") String numeroIstanza) throws Exception;
}