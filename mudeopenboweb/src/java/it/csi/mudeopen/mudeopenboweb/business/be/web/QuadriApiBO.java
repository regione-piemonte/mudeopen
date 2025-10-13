/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TipoQuadroVO;

/**
 * The interface gestione quadri api BO.
 */
@Path("/bo-quadri")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface QuadriApiBO {

    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE TIPI QUADRI   -------------------- */
    /* ----------------------------------------------------------------------- */
	
	
	/**
	 * Ricerca persona fisica response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param filter          the filter
	 * @param sort            the sort
	 * @param page            the page
	 * @param size            the size
	 * @return the response
	 */
	@GET
	@Path("/tipo-quadri")
	public Response listaTipoQuadri(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@QueryParam("filter") String filter, 
			@QueryParam("sort") String sort, 
			@NotNull @QueryParam("page")  int page, 
			@NotNull @QueryParam("size") int size);
	
	/**
	 * Save tipo quadro response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param contatto        the contatto
	 * @return the response
	 */
	@POST
	@Path("/tipo-quadri")
	public Response salvaTipoQuadro(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			TipoQuadroVO tipoQuadroVO);

	/**
	 * Elimina tipo quadri response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param tipoQuadro	  tipo quadro
	 * @return the response
	 */
	@DELETE
	@Path("/tipo-quadri/{idTipoQuadro}")
	public Response eliminaTipoQuadro(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTipoQuadro") Long idTipoQuadro);

	/**
	 * Elimina tipo quadri response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param idTipoQuadro	  id tipo quadro
	 * @return the response
	 */
    @GET
	@Path("/quadri-per-tipo/{idTipoQuadro}")
	public Response listaQuadriPerTipo(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTipoQuadro") Long idTipoQuadro);
	
    @GET
	@Path("/categoria-tipo-quadri")
	public Response listaCategoriaTipoQuadri(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE QUADRI   ------------------------- */
    /* ----------------------------------------------------------------------- */
	

	
	/**
	 * Ricerca persona fisica response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param filter          the filter
	 * @param sort            the sort
	 * @param page            the page
	 * @param size            the size
	 * @return the response
	 */
	@GET
	@Path("/quadri")
	public Response listaQuadri(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@QueryParam("filter") String filter, 
			@QueryParam("sort") String sort, 
			@QueryParam("page")  int page, 
			@QueryParam("size") int size,
			@QueryParam("func")  String func);
	
	/**
	 * Save quadro response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param contatto        the contatto
	 * @return the response
	 */
    @POST
	@Path("/quadri")
    @Consumes({ "multipart/form-data" })
    Response salvaQuadro(@HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@MultipartForm MultipartFormDataInput input);

	/**
	 * Elimina  quadri response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param Quadro	   quadro
	 * @return the response
	 */
	@DELETE
	@Path("/quadri/{idQuadro}")
	public Response eliminaQuadro(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idQuadro") Long idQuadro);

	@GET
	@Path("/quadri/templates/{idTipoQuadro}")
	public Response recuperaTemplateAssociaQuadro(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTipoQuadro") Long idTipoQuadro);
	
	@GET
	@Path("/quadri/pubblica/{idTipoQuadro}")
	public Response pubblicaQuadro(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTipoQuadro") Long idTipoQuadro);
	

    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE TEMPLATE   ------------------------- */
    /* ----------------------------------------------------------------------- */
	
	@GET
	@Path("/template")
	public Response listaTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@QueryParam("filter") String filter, 
			@QueryParam("sort") String sort, 
			@NotNull @QueryParam("page")  int page, 
			@NotNull @QueryParam("size") int size);
	
    @POST
	@Path("/template")
	public Response salvaTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			TemplateVO templateVO);

	@DELETE
	@Path("/template/{idTemplate}")
	public Response eliminaTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate);

	@GET
	@Path("/template/quadri/{idTemplate}")
	public Response listaQuadriTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate);
	
    @POST
	@Path("/template/quadri/{idTemplate}")
	public Response salvaQuadroTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate, TemplateQuadroVO templateVO);

	@DELETE
	@Path("/template/quadri/{idTemplateQuadro}")
	public Response eliminaQuadroTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplateQuadro") Long idTemplateQuadro);

	@GET
	@Path("/template/allegati/{idTemplate}")
	public Response listaAllegatiTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate);

    @POST
	@Path("/template/allegati/{idTemplate}")
    @Consumes({ "multipart/form-data" })
	public Response salvaAllegatoTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate, @MultipartForm MultipartFormDataInput input);

    @POST
	@Path("/template/intestazione/{idTemplate}")
    @Consumes({ "multipart/form-data" })
	public Response salvaIntestazioneTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate, 
			@QueryParam("type") String type,
			@MultipartForm MultipartFormDataInput input);

	@DELETE
	@Path("/template/allegati/{id}")
	public Response eliminaAllegatoTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("id") Long id);

	@GET
	@Path("/template/versiona/{idTemplate}")
	public Response nuovaVersioneTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate);

	@GET
	@Path("/template/duplica/{idTemplate}")
	public Response duplicaTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate, @QueryParam("idTipoIstanza")  String idTipoIstanza);

	@GET
	@Path("/template/pubblica/{idTemplate}")
	public Response pubblicaTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate,
			@QueryParam("publishingMode") String publishingMode);

	@GET
	@Path("/template/modello/{idTemplate}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM })
	public Response downloadModelloDocxTemplate(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idTemplate") Long idTemplate);
		
	@GET
	@Path("/lista-enti")
	public Response listaEnti(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
		
}