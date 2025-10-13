
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web;
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
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.SoggettoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaIstanzaRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaTitoloSoggettoAbilitatoRequest;

/**
 * The interface Istanze api.
 */
@Path("/istanze")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface IstanzeApi {

    /**
     * Salva istanza response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param request         the request
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Path("")
    public Response salvaIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, SalvaIstanzaRequest request, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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
    public Response recuperaIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Elimina istanza response.
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
    @DELETE
    @Path("/{idIstanza}")
    public Response eliminaIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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
     * Salva titolo soggetto abilitato response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param idIstanza       the id istanza
     * @param request         the request
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Path("/{idIstanza}/titoloSoggettoAbilitato")
    public Response salvaTitoloSoggettoAbilitato(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @PathParam("idIstanza") Long idIstanza, SalvaTitoloSoggettoAbilitatoRequest request, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Aggiungi soggetto coinvolto response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param idIstanza       the id istanza
     * @param soggetto        the soggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Path("/{idIstanza}/soggetti")
    @Produces({"application/json"})
    Response aggiungiSoggettoCoinvolto(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@PathParam("idIstanza") Long idIstanza,
    		@QueryParam("replaceIdSoggetto") Long replaceIdSoggetto,
    		SoggettoIstanzaVO soggetto);

    /**
     * Elimina soggetto coinvolto response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param idIstanza       the id istanza
     * @param idSoggetto      the id soggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("/{idIstanza}/soggetti/{idSoggetto}")
    public Response eliminaSoggettoCoinvolto(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @PathParam("idIstanza") Long idIstanza, @PathParam("idSoggetto") Long idSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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

    @GET
    @Path("/istanza/{idIstanza}/quadro/{idQuadro}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadQuadroPDFIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
						    		  @PathParam("idIstanza") Long idIstanza,
						    		  @PathParam("idQuadro") Long idQuadro);

    @POST
    @Path("/quadro/istanza/{idIstanza}")
    @Consumes({ "multipart/form-data" })
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadDocxPDF(@HeaderParam("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
							 @PathParam("idIstanza") Long idIstanza,
							 @QueryParam("options") String options,
				    		 @MultipartForm MultipartFormDataInput input);

    /**
     * Upload istanza firmata response.
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
    @Path("/upload")
    @Consumes({"multipart/form-data"})
    Response uploadIstanzaFirmata(@MultipartForm MultipartFormDataInput input, @HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete file istanza firmata response.
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
    @DELETE
    @Path("/signed-file/{idIstanza}")
    Response deleteFileIstanzaFirmata(@PathParam("idIstanza") Long idIstanza, @HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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
     * Recupera abilitazioni istanza response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param idIstanza       the id istanza
     * @return the response
     */
    @GET
    @Path("/abilitazioni-istanza/{idIstanza}")
    public Response recuperaAbilitazioniIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idIstanza") Long idIstanza);

    @GET
    @Path("/possibili/{idFascicolo}")
    Response listaPossibiliIstanze(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@PathParam("idFascicolo") Long idFascicolo, 
    		@QueryParam("idIstanza") Long idIstanza, 
    		@QueryParam("idTipoIstanza") String idTipoIstanza, 
    		@NotNull @QueryParam("page") int page, 
    		@NotNull @QueryParam("size") int size);

    @PUT
    @Path("/{idIstanza}/cambia-stato/{codiceStato}")
    public Response cambiaStatoIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idIstanza") Long idIstanza,
    		@PathParam("codiceStato") String codStatus,
    		IstanzaVO istanza,
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
    public Response downloadIstanza(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idIstanza") Long idIstanza,
    		@QueryParam("con_firma") Boolean con_firma);

    @GET
    @Path("/ricevuta-pdf/{idIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadRicevutaPdf( @HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@PathParam("idIstanza") Long idIstanza);
}