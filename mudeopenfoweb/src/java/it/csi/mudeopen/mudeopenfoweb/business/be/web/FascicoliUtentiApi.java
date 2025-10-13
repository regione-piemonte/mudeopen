/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FascicoloUtenteVO;

/**
 * The interface Fascicoli utenti api.
 */
@Path("/fascicoli-utenti")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface FascicoliUtentiApi {

    /**
     * Save fascicolo utente response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param fascicoloUtenteVO the fascicolo utente vo
     * @return the response
     */
    @POST
    @Path("")
    Response saveFascicoloUtente(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		FascicoloUtenteVO fascicoloUtenteVO);

    /**
     * Save abilitazioni funzioni per fascicolo utente response.
     *
     * @param userCf                       the user cf
     * @param XRequestId                   the x request id
     * @param XForwardedFor                the x forwarded for
     * @param securityContext              the security context
     * @param httpHeaders                  the http headers
     * @param httpRequest                  the http request
     * @param idFascicolo                    the id fascicolo
     * @param abilitazioneFunzioneCustomVO the abilitazione funzione custom vo
     * @return the response
     */
    @POST
    @Path("/id-fascicolo/{idFascicolo}")
    Response saveAbilitazioniFunzioniPerFascicoloUtente(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idFascicolo") Long idFascicolo, AbilitazioneFunzioneCustomVO abilitazioneFunzioneCustomVO);

    /**
     * Delete fascicolo utente response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param idFascicoloUtente the id fascicolo utente
     * @return the response
     */
    @DELETE
    @Path("/{idFascicoloUtente}")
    Response deleteFascicoloUtente(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idFascicoloUtente") Long idFascicoloUtente);

}