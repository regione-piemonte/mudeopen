
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * The interface Template quadro api.
 */
@Path("/template-quadri")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

public interface TemplateQuadroApi {
    /**
     * Load template quadri by tipo istanza response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param tipoIstanza     the tipo istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
	@Path("/tipo-istanza/{tipoIstanza}")
	Response loadTemplateQuadriByTipoIstanza(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("tipoIstanza") String tipoIstanza,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * Load template quadri by code template response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param codeTemplate    the code template
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
	@Path("/code-template/{idTipoIstanza}")
	Response loadTemplateQuadriByCodeTemplate(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("idTipoIstanza") String idTipoIstanza,
			@QueryParam("boTemplateOverride") String boTemplateOverride,
			@QueryParam("idIstanza") Long Istanza, 
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);
    /**
     * Load template quadri by id template quadro response.
     *
     * @param userCf           the user cf
     * @param XRequestId       the x request id
     * @param XForwardedFor    the x forwarded for
     * @param idTemplateQuadro the id template quadro
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
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