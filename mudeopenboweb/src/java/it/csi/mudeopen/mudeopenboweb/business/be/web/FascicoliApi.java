
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
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

/**
 * The interface Fascicoli api.
 */
@Path("/fascicoli")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

public interface FascicoliApi {

    /**
     * Recupera fascicoli response.
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
    Response recuperaFascicoli(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@QueryParam("filter") String filter, 
    		@QueryParam("sort") String sort,
    		@NotNull @QueryParam("page") int page,
    		@NotNull @QueryParam("size") int size,
    		@QueryParam("scope") String scope);

    /**
     * Recupera fascicolo response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param idFascicolo     the id fascicolo
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
	@Path("/{idFascicolo}")
	@Produces({ "application/json" })
	Response recuperaFascicolo(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("idFascicolo") Long idFascicolo,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	
	/**
     * Esporta fascicoli response.
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
    @Path("/exportExcel")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response exportExcelFascicoli(@HeaderParam("user-cf") String userCf, 
    		@HeaderParam("X-Request-ID") String XRequestId, 
    		@HeaderParam("X-Forwarded-For") String XForwardedFor, 
    		@Context SecurityContext securityContext, 
    		@Context HttpHeaders httpHeaders, 
    		@Context HttpServletRequest httpRequest,
    		@QueryParam("filter") String filter, 
    		@QueryParam("sort") String sort,
    		@QueryParam("scope") String scope);

	
}