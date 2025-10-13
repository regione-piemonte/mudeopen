
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/pratiche")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})

public interface PraticheApi {
    @GET
    @Path("/")
    @Produces({"application/json"})
    Response recuperaPratiche(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor,
    		@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
            @Context HttpServletRequest httpRequest, @QueryParam("filter") String filter,
            @QueryParam("sort") int sort, @NotNull @QueryParam("page") int page, 
            @NotNull @QueryParam("size") int size,
            @QueryParam("scope") String scope);

    @GET
    @Path("/documenti/id-pratica/{idPratica}")
    @Produces({"application/json"})
    Response recuperaDocumentiPraticaById(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, 
    		@HeaderParam("X-Forwarded-For") String XForwardedFor,
    		@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
            @Context HttpServletRequest httpRequest, @PathParam("idPratica") Long idPratica,
            @QueryParam("sort") int sort, @NotNull @QueryParam("page") int page, 
            @NotNull @QueryParam("size") int size);

    @DELETE
    @Path("/documenti/{idDocumento}")
    Response deleteDocumento(@HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
								@PathParam("idDocumento") Long idDocumento,
								@QueryParam("scope") String scope);

    @POST
    @Consumes({ "multipart/form-data" })
    @Path("/documenti/{idPratica}")
    Response uploadDocumento(@MultipartForm MultipartFormDataInput input, @PathParam("idPratica") Long idPratica, @QueryParam("scope") String scope, @HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    @GET
    @Path("/documenti/download/{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadDocumento(@PathParam("uuid") String uuid, @HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /* 
     * BO
     * 
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
   @GET
   @Path("/tipo-doc")
   public Response recuperoListaTipoDoc(
       @HeaderParam("user-cf") String userCf, 
       @HeaderParam("X-Request-ID") String XRequestId, 
       @HeaderParam("X-Forwarded-For") String XForwardedFor, 
       @Context SecurityContext securityContext, 
       @Context HttpHeaders httpHeaders, 
       @Context HttpServletRequest httpRequest);
	*/
}