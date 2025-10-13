/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web;

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

@Path("/istanze-utenti")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface IstanzeUtentiApi {

    @POST
    @Path("")
    Response saveIstanzaUtente(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, IstanzaUtenteVO istanzaUtenteVO);

    @POST
    @Path("/id-istanza/{idIstanza}")
    Response saveAbilitazioniFunzioniPerIstanzaUtente(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @PathParam("idIstanza") Long idIstanza, AbilitazioneFunzioneCustomVO abilitazioneFunzioneCustomVO);

    @DELETE
    @Path("/{idIstanzaUtente}")
    Response deleteIstanzaUtente(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @PathParam("idIstanzaUtente") Long idIstanzaUtente);

}