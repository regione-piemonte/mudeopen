
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

/**
 * The interface Pratiche api.
 */
@Path("/pratiche")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})

public interface PraticheApi {
    /**
     * Recupera pratiche response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param filter          the filter
     * @param page            the page
     * @param size            the size
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/")
    @Produces({"application/json"})
    Response recuperaPratiche(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@QueryParam("filter") String filter, 
    		@QueryParam("sort") String sort,
    		@NotNull @QueryParam("page") int page,
    		@NotNull @QueryParam("size") int size,
    		@QueryParam("scope") String scope);
    /**
     * Esporta pratiche response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param filter          the filter
     * @param page            the page
     * @param size            the size
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/export-excel")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response exportExcelPratiche(@HeaderParam("user-cf") String userCf, 
    		@HeaderParam("X-Request-ID") String XRequestId, 
    		@HeaderParam("X-Forwarded-For") String XForwardedFor, 
    		@Context SecurityContext securityContext, 
    		@Context HttpHeaders httpHeaders, 
    		@Context HttpServletRequest httpRequest,
    		@QueryParam("filter") String filter, 
    		@QueryParam("sort") String sort,
    		@QueryParam("scope") String scope);

    /**
     * Download documento response.
     *
     * @param uuid            the uuid
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/documenti/download/{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadDocumento(@PathParam("uuid") String uuid, @HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete documento response.
     *
     * @param idDocumento      the id documento
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("/documenti/{idDocumento}")
    Response deleteDocumento(@HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
								@PathParam("idDocumento") Long idDocumento,
								@QueryParam("scope") String scope);

    /**
     * Upload documento response.
     *
     * @param input           the input
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Consumes({ "multipart/form-data" })
    @Path("/documenti/{idPratica}")
    Response uploadDocumento(@MultipartForm MultipartFormDataInput input, @PathParam("idPratica") Long idPratica, @QueryParam("scope") String scope, @HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    /* Recupera tipo documento per Upload documenti.
    *
    * @param userCf          the user cf
    * @param XRequestId      the x request id
    * @param XForwardedFor   the x forwarded for
    * @param securityContext the security context
    * @param httpHeaders     the http headers
    * @param httpRequest     the http request
    * @return the response
    */
   @GET
   @Path("/tipo-doc")
   public Response recuperoListaTipoDoc(
       @HeaderParam("user-cf") String userCf, 
       @HeaderParam("X-Request-ID") String XRequestId, 
       @HeaderParam("X-Forwarded-For") String XForwardedFor, 
       @Context SecurityContext securityContext, 
       @Context HttpHeaders httpHeaders, 
       @Context HttpServletRequest httpRequest);
   /**
    * Upload documenti.
    *
    * @param userCf          the user cf
    * @param idIstanza       the idIstanza 
    * @param XRequestId      the x request id
    * @param XForwardedFor   the x forwarded for
    * @param securityContext the security context
    * @param httpHeaders     the http headers
    * @param httpRequest     the http request
    * @return the response
    */
   @GET
   @Path("/{idPratica}/template-upload-doc")
   public Response recuperoTemplateUplDocFormIo(
       @HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, 
       @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, 
       @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
       @PathParam("idPratica") Long idPratica,
       @QueryParam("nomeFile") String nomeFile,
       @QueryParam("tipoFile") String tipoFile);
   /**
    * Recupera documenti pratica response.
    *
    * @param userCf          the user cf
    * @param XRequestId      the x request id
    * @param XForwardedFor   the x forwarded for
    * @param page            the page
    * @param size            the size
    * @param securityContext the security context
    * @param httpHeaders     the http headers
    * @param httpRequest     the http request
    * @return the response
    */
   @GET
   @Path("/documenti/id-pratica/{idPratica}")
   @Produces({"application/json"})
   Response recuperaDocumentiPraticaById(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, 
   		@HeaderParam("X-Forwarded-For") String XForwardedFor,
   		@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
           @Context HttpServletRequest httpRequest, @PathParam("idPratica") Long idPratica,
           @QueryParam("sort") int sort, @NotNull @QueryParam("page") int page, 
           @NotNull @QueryParam("size") int size);
}