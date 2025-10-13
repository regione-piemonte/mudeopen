/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.mudeopen.mudeopenboweb.business.be.web;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
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

/**
 * The interface Allegati api.
 */
@Path("/allegati")
@Produces({ MediaType.APPLICATION_JSON })
public interface AllegatiApi {

    /**
     * Download allegato response.
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
    @Path("/download/{uuid}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadAllegato(@PathParam("uuid") String uuid, @HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    			@QueryParam("scope") String scope, @QueryParam("con_firma") Boolean con_firma);

    @GET
    @Path("/download-test/index")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadAllegatoTest(@HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@QueryParam("scope") String scope, @QueryParam("con_firma") Boolean con_firma);

    /**
     * Load allegati istanza response.
     *
     * @param idIstanza       the id istanza
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/id-istanza/{idIstanza}")
    Response loadAllegatiIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			 @PathParam("idIstanza") Long idIstanza,
			 @QueryParam("tipo_allegato") String tipo_allegato,
			 @QueryParam("scope") String scope);
    /**
     * Download excel lista allegati istanza response.
     *
     * @param idIstanza       the id istanza
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/exportExcel/id-istanza/{idIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response exportExcelAllegatiIstanza(@PathParam("idIstanza") Long idIstanza, @HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}