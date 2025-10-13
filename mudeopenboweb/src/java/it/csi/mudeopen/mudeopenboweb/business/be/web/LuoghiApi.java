/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
/**
 * The interface Luoghi api.
 */
@Path("luoghi")
public interface LuoghiApi {

    /**
     * Gets nazioni.
     *
     * @param userCf        the user cf
     * @param XRequestId    the x request id
     * @param XForwardedFor the x forwarded for
     * @param httpRequest TODO
     * @return the nazioni
     */
    @GET
    @Path("/nazioni")
    public Response getNazioni(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets stati membri ue.
     *
     * @param userCf        the user cf
     * @param XRequestId    the x request id
     * @param XForwardedFor the x forwarded for
     * @return the stati membri ue
     */
    @GET
    @Path("/nazioni/stati-membri-ue")
    public Response getStatiMembriUE(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets regioni.
     *
     * @param filter        the filter
     * @param userCf        the user cf
     * @param XRequestId    the x request id
     * @param XForwardedFor the x forwarded for
     * @return the regioni
     */
    @GET
    @Path("/regioni")
    public Response getRegioni(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@QueryParam("filter") String filter);
    /**
     * Gets province.
     *
     * @param filter        the filter
     * @param userCf        the user cf
     * @param XRequestId    the x request id
     * @param XForwardedFor the x forwarded for
     * @return the province
     */
    @GET
    @Path("/province")
    public Response getProvince(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@QueryParam("filter") String filter);

    /**
     * Gets comuni.
     *
     * @param filter        the filter
     * @param userCf        the user cf
     * @param XRequestId    the x request id
     * @param XForwardedFor the x forwarded for
     * @return the comuni
     */
    @GET
    @Path("/comuni")
    public Response getComuni(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    			@QueryParam("filter") String filter);

    @GET
    @Path("/province/registered")
    public Response getProvinceForComuniRegistered(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @GET
    @Path("/comuni/registered/{idProvincia}")
    public Response findComuniRegisteredForProvincia(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idProvincia") Long idProvincia);

}