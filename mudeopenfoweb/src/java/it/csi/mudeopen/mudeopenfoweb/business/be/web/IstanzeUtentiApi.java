/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web;

import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;

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

/**
 * The interface Istanze utenti api.
 */
@Path("/istanze-utenti")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface IstanzeUtentiApi {

    /**
     * Save istanza utente response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param istanzaUtenteVO the istanza utente vo
     * @return the response
     */
    @POST
    @Path("")
    Response saveIstanzaUtente(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, IstanzaUtenteVO istanzaUtenteVO);

    /**
     * Save abilitazioni funzioni per istanza utente response.
     *
     * @param userCf                       the user cf
     * @param XRequestId                   the x request id
     * @param XForwardedFor                the x forwarded for
     * @param securityContext              the security context
     * @param httpHeaders                  the http headers
     * @param httpRequest                  the http request
     * @param idIstanza                    the id istanza
     * @param abilitazioneFunzioneCustomVO the abilitazione funzione custom vo
     * @return the response
     */
    @POST
    @Path("/id-istanza/{idIstanza}")
    Response saveAbilitazioniFunzioniPerIstanzaUtente(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @PathParam("idIstanza") Long idIstanza, AbilitazioneFunzioneCustomVO abilitazioneFunzioneCustomVO);

    /**
     * Delete istanza utente response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param idIstanzaUtente the id istanza utente
     * @return the response
     */
    @DELETE
    @Path("/{idIstanzaUtente}")
    Response deleteIstanzaUtente(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @PathParam("idIstanzaUtente") Long idIstanzaUtente);

}