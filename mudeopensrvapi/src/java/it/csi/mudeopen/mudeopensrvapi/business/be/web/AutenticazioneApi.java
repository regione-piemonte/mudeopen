/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.mudeopen.mudeopensrvapi.business.be.web;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The interface Autenticazione api.
 */
@Path("/token")
@Produces({ MediaType.APPLICATION_JSON })
public interface AutenticazioneApi {

    /**
     * @param codiceFruitore
     * @param userCf
     * @param XRequestId
     * @param XForwardedFor
     * @param securityContext
     * @param httpHeaders
     * @param httpRequest

     */
    @GET
    @Path("/{codiceFruitore}")
    Response autenticazioneMUDE(@PathParam("codiceFruitore") String codiceFruitore);
}