/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.mudeopen.mudeopensrvapi.business.be.web;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.web.bind.annotation.RequestBody;

import it.csi.mudeopen.mudeopensrvapi.vo.DatiModificaStatoIstanza;
@Path("")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({MediaType.APPLICATION_JSON})
public interface StatiIstanzaApi {

	
	@PUT
	@Path("/stato-istanza")
	public Response modificaStatoIstanza(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
			@RequestBody DatiModificaStatoIstanza datiModificaStatoIstanza ) throws Exception;
	
    /**
     * Stati istanza response.
     *
     * @param token       	  the token
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
      the response
     */
    @GET
    @Path("/stati-istanza")
    Response ricercaStatiIstanza(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID) throws Exception;
    @GET
    @Path("/stati-istanza-ammessi/{statoIniziale}")
    Response ricercaStatiIstanzaAmmessi(@HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @HeaderParam("fruitore") String fruitoreID,
    		@PathParam("statoIniziale") String statoIniziale) throws Exception;
}