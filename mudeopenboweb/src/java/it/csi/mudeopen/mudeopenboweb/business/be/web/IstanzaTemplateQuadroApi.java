
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.mudeopen.mudeopensrvcommon.vo.request.IstanzaTemplateQuadroRequest;

@Path("/istanza-template-quadri")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

public interface IstanzaTemplateQuadroApi {

    @GET
	@Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
	Response getIstanzaTemplateQuadro(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idIstanza") Long idIstanza,
			@PathParam("idTemplateQuadro") Long idTemplateQuadro);

    @POST
	@Consumes({ "application/json" })
	Response saveIstanzaTempleteQuadro(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@RequestBody IstanzaTemplateQuadroRequest istanzaTemplateQuadroVO,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
}