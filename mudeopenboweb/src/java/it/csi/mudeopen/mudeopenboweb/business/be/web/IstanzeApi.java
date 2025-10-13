
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaIstanzaRequest;

/**
 * The interface Istanze api.
 */
@Path("/istanze")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface IstanzeApi {

    /**
     * Recupera istanze response.
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
    @Path("/")
    public Response recuperaIstanze(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @NotNull @QueryParam("page") int page, @NotNull @QueryParam("size") int size, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Recupera istanza response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/{idIstanza}")
    public Response recuperaIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @QueryParam("scope") String scope);

    /**
     * Cerca istanze response.
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
    @Path("/search")
    Response cercaIstanze(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@QueryParam("filter") String filter, 
    		@QueryParam("sort") String sort,
    		@NotNull @QueryParam("page") int page,
    		@NotNull @QueryParam("size") int size,
    		@QueryParam("scope") String scope);

    /**
     * Download template response.
     *
     * @param idIstanza       the id istanza
     * @param idTemplate      the id template
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/{idIstanza}/templates/{idTemplate}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadTemplate(@PathParam("idIstanza") Long idIstanza, @PathParam("idTemplate") Long idTemplate, @HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Download template pdf istanza response.
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
    @Path("/{idIstanza}/templates/istanza")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadTemplatePDFIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@PathParam("idIstanza") Long idIstanza,
			@QueryParam("scope") String scope,
			@QueryParam("saveAllFilesToDisk") String saveAllFilesToDisk);

    @PUT
    @Path("/{idIstanza}/cambia-stato/{codiceStato}")
    public Response cambiaStatoIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idIstanza") Long idIstanza,
    		@PathParam("codiceStato") String codStatus,
    		IstanzaVO istanza,
    		@QueryParam("scope") String scope);
    /**
     * Esporta istanze response.
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
    public Response exportExcelIstanze(@HeaderParam("user-cf") String userCf, 
    		@HeaderParam("X-Request-ID") String XRequestId, 
    		@HeaderParam("X-Forwarded-For") String XForwardedFor, 
    		@Context SecurityContext securityContext, 
    		@Context HttpHeaders httpHeaders, 
    		@Context HttpServletRequest httpRequest,
    		@QueryParam("filter") String filter, 
    		@QueryParam("sort") String sort,
    		@QueryParam("scope") String scope);

    /**
     * Download istanza response.
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
    @Path("/download/{idIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadIstanza( 
    		@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, 
    		@HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, 
    		@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@PathParam("idIstanza") Long idIstanza,
    		@QueryParam("con_firma") Boolean con_firma,
    		@QueryParam("scope") String scope);

    @GET
    @Path("/{idIstanza}/possibile-stato")
    public Response possibileStatoIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idIstanza") Long idIstanza
    		);

    @GET
    @Path("/tipi-stato-filtro-veloce")
    public Response loadTipiStatoIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest 
    		);
    @GET
    @Path("/{idIstanza}/template-cambia-stato/{codiceStatoEnd}")
    public Response reuperoTemplateFormIo(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idIstanza") Long idIstanza,
    		@PathParam("codiceStatoEnd") String codStatusEnd);
    /**
     * Download ricevutaPDF response.
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
    @Path("/ricevuta-pdf/{idIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadRicevutaPdf( 
    		@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, 
    		@HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, 
    		@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@PathParam("idIstanza") Long idIstanza);

    /**
     * Istanze pratica response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param idPratica     the id pratica
     * @param page            the page
     * @param size            the size
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/pratica/{idPratica}")
    Response istanzePratica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @PathParam("idPratica") Long idPratica, @NotNull @QueryParam("page") int page, @NotNull @QueryParam("size") int size, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    @GET
    @Path("/scrivania")
    Response cercaIstanzeScrivania(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@QueryParam("filter") String filter, 
    		@QueryParam("sort") String sort,
    		@NotNull @QueryParam("page") int page,
    		@NotNull @QueryParam("size") int size,
    		@QueryParam("scope") String scope);

    @GET
    @Path("/istanze-tracciato/{idIstanza}")
    Response rigeneraTracciati(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@PathParam("idIstanza") Long idIstanza,
    		@QueryParam("debug") String debug);
    /**
     * Gets lista tipo presentatore.
     *
     * @param userCf             the user cf
     * @param XRequestId         the x request id
     * @param XForwardedFor      the x forwarded for
     * @param idTipoPresentatore the id tipo presentatore
     * @param securityContext    the security context
     * @param httpHeaders        the http headers
     * @param httpRequest        the http request
     * @return the lista tipo presentatore
     */
    @GET
    @Path("/titoloPresentatore/{idTipoPresentatore}")
    public Response getListaTipoPresentatore(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
			@PathParam("idTipoPresentatore") Long idTipoPresentatore);

    /**
     * Gets ruoli istanza.
     *
     * @param userCf           the user cf
     * @param XRequestId       the x request id
     * @param XForwardedFor    the x forwarded for
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param idIstanza        the id istanza
     * @param excl_user_ids    the excl user ids
     * @param loadAbilitazioni the load abilitazioni
     * @return the ruoli istanza
     */
    @GET
    @Path("/{idIstanza}/ruoli")
    public Response getRuoliIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    			@PathParam("idIstanza") Long idIstanza, @QueryParam("excl_user_ids") String excl_user_ids, @QueryParam("load_abilitazioni") Boolean loadAbilitazioni);

    @GET
    @Path("/{idIstanza}/stati")
    public Response getStatiIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    			@PathParam("idIstanza") Long idIstanza,
				@QueryParam("scope") String scope);

    /**
     * Istanze fascicolo response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param idFascicolo     the id fascicolo
     * @param page            the page
     * @param size            the size
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/fascicolo/{idFascicolo}")
    Response istanzeFascicolo(@HeaderParam("user-cf") String userCf
    					, @HeaderParam("X-Request-ID") String XRequestId
    					, @HeaderParam("X-Forwarded-For") String XForwardedFor
    					, @PathParam("idFascicolo") Long idFascicolo
			    		, @QueryParam("filter") String filter
			    		, @QueryParam("sort") String sort
			    		, @NotNull @QueryParam("page") int page
			    		, @NotNull @QueryParam("size") int size
    					, @Context SecurityContext securityContext
    					, @Context HttpHeaders httpHeaders
    					, @Context HttpServletRequest httpRequest);

    
}