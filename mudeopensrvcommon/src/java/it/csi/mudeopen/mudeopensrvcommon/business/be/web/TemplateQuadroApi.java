
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/template-quadri")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

public interface TemplateQuadroApi {
    @GET
	@Path("/tipo-istanza/{tipoIstanza}")
	Response loadTemplateQuadriByTipoIstanza(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("tipoIstanza") String tipoIstanza,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
	@Path("/code-template/{idTipoIstanza}")
	Response loadTemplateQuadriByCodeTemplate(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("idTipoIstanza") String idTipoIstanza,
			@QueryParam("boTemplateOverride") String boTemplateOverride, 
			@QueryParam("idIstanza") Long idIstanza, 
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
	@Path("/id-template-quadro/{idTemplateQuadro}")
	Response loadTemplateQuadriByIdTemplateQuadro(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("idTemplateQuadro") Long idTemplateQuadro,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);
    @GET
	@Path("/id-quadro/{idQuadro}")
	Response loadQuadroById(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest,
			@PathParam("idQuadro") Long idQuadro,
			@QueryParam("idIstanza") Long idIstanza,
			@QueryParam("idFascicolo") Long idFascicolo);

	@GET
	@Path("/istanza/{idIstanza}")
	public Response loadJDataIstanza(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idIstanza") Long idIstanza,
			@QueryParam("idQuadro") Long idQuadro,
			@QueryParam("codTipoQuadro") String codTipoQuadro,
			@QueryParam("isObbligatoriaNominaPM") Boolean isObbligatoriaNominaPM,
			@QueryParam("requestType") String requestType);

    @GET
	@Path("/quadri/template/{idTemplate}")
	public Response retrieveAllQuadriInTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate,
			@QueryParam("idIstanza") Long idIstanza,
			@QueryParam("idUser") Long idUser,
			@QueryParam("includeJson") Boolean includeJson);
	
}