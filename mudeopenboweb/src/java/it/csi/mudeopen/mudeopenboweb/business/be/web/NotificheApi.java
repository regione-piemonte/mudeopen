/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/**********************************************
 * CSI PIEMONTE 
 **********************************************/
package it.csi.mudeopen.mudeopenboweb.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

@Path("/notifiche")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface NotificheApi {
    /**
     * Load Notifiche istanza response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param idIstanza       the id istanza
     * @param page            the page
     * @param size            the size
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/id-istanza/{idIstanza}")
    @Produces({"application/json"})
    Response loadNotificheIstanza(
    	@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, 
    	@HeaderParam("X-Forwarded-For") String XForwardedFor,     	
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, 
    	@Context HttpServletRequest httpRequest,
    	@PathParam("idIstanza") Long idIstanza, 
    	@QueryParam("sort") int sort, @NotNull @QueryParam("page") int page, @NotNull @QueryParam("size") int size);

    /**
     * Inserisci Notifiche istanza response.
     *
     */
    @GET
    @Path("/{idIstanza}/template-nuova-notifica/{idTipoNotifica}")
    public Response reuperoTemplateFormIoNotifica(
    	@HeaderParam("user-cf") String userCf, 
    	@HeaderParam("X-Request-ID") String XRequestId, 
    	@HeaderParam("X-Forwarded-For") String XForwardedFor, 
    	@Context SecurityContext securityContext, 
    	@Context HttpHeaders httpHeaders, 
    	@Context HttpServletRequest httpRequest, 
    	@PathParam("idIstanza") Long idIstanza,
    	@PathParam("idTipoNotifica") Long idTipoNotifica);
    @PUT
    @Path("/{idIstanza}/nuova-notifica/{idTipoNotifica}")
    public Response nuovaNotifica(@HeaderParam("user-cf") String userCf, 
    	@HeaderParam("X-Request-ID") String XRequestId, 
    	@HeaderParam("X-Forwarded-For") String XForwardedFor, 
    	@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    	@PathParam("idIstanza") Long idIstanza,
    	IstanzaVO istanza,
    	@PathParam("idTipoNotifica") Long idTipoNotifica,
    	@QueryParam("scope") String scope);

    @GET
    @Path("/tipi-notifica")
    public Response loadTipiNotifica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest 
    		);
}