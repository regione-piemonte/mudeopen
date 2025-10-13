
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.IstanzaTemplateQuadroRequest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/istanza-template-quadri")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

public interface IstanzaTemplateQuadroApi {

    @GET
	@Path("/id-istanza/{idIstanza}")
	Response loadIstanzaTemplateQuadroByIdistanza(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("idIstanza") Long idIstanza,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
	@Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
	Response getIstanzaTemplateQuadro(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idIstanza") Long idIstanza,
			@PathParam("idTemplateQuadro") Long idTemplateQuadro);

    @GET
	@Path("/code-template/{codeTemplate}/id-istanza/{idIstanza}")
	@Produces({ "application/json" })
	Response getTemplateQuadriByCodeTemplateAndIdIstanza(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("codeTemplate") String codeTemplate,
			@PathParam("idIstanza") Long idIstanza,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);
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