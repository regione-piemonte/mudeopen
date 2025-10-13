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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;

/**
 * The interface Contatti api.
 */
@Path("/contatti")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface ContattiApi {
    /**
     * Save contatto response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param contatto        the contatto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Path("")
	public Response saveContatto(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@QueryParam("idIstanza") Long idIstanza,
			ContattoVO contatto);

    /**
     * Ricerca persona fisica response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param filter          the filter
     * @param sort            the sort
     * @param page            the page
     * @param size            the size
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("")
    public Response ricercaContatto(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
    		@QueryParam("filter") String filter, 
    		@QueryParam("sort") String sort, 
    		@NotNull @QueryParam("page") int page, 
    		@NotNull @QueryParam("size") int size);

    /**
     * Modifica persona fisica response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param contatto        the contatto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @PUT
    @Path("")
    public Response modificaContatto(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, ContattoVO contatto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    /**
     * Elimina persona fisica response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param codiceFiscale   the codice fiscale
     * @param piva            the piva
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("")
    public Response eliminaContatto(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @QueryParam("codiceFiscale") String codiceFiscale, @QueryParam("partitaIva") String piva, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    //
    //    /**
    //     * Insert persona fisica response.
    //     *
    //     * @param userCf          the user cf
    //     * @param XRequestId      the x request id
    //     * @param XForwardedFor   the x forwarded for
    //     * @param contatto        the contatto
    //     * @param securityContext the security context
    //     * @param httpHeaders     the http headers
    //     * @param httpRequest     the http request
    //     * @return the response
    //     */
    //    @POST
    //    @Path("/persone-fisiche")
    //    @Produces({"application/json"})
    //    public Response insertPersonaFisica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, ContattoVO contatto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    //
    //    /**
    //     * Ricerca persona fisica response.
    //     *
    //     * @param userCf          the user cf
    //     * @param XRequestId      the x request id
    //     * @param XForwardedFor   the x forwarded for
    //     * @param filter          the filter
    //     * @param sort            the sort
    //     * @param page            the page
    //     * @param size            the size
    //     * @param securityContext the security context
    //     * @param httpHeaders     the http headers
    //     * @param httpRequest     the http request
    //     * @return the response
    //     */
    //    @GET
    //    @Path("/persone-fisiche")
    //    @Produces({"application/json"})
    //    public Response ricercaPersonaFisica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @QueryParam("filter") String filter, @QueryParam("sort") String sort, @NotNull @QueryParam("page") int page, @NotNull @QueryParam("size") int size, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    //
    //    /**
    //     * Elimina persona fisica response.
    //     *
    //     * @param userCf          the user cf
    //     * @param XRequestId      the x request id
    //     * @param XForwardedFor   the x forwarded for
    //     * @param codiceFiscale   the codice fiscale
    //     * @param securityContext the security context
    //     * @param httpHeaders     the http headers
    //     * @param httpRequest     the http request
    //     * @return the response
    //     */
    //    @DELETE
    //    @Path("/persone-fisiche/{codiceFiscale}")
    //    @Produces({"application/json"})
    //    public Response eliminaPersonaFisica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @PathParam("codiceFiscale") String codiceFiscale, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    //
    //    /**
    //     * Modifica persona fisica response.
    //     *
    //     * @param userCf          the user cf
    //     * @param XRequestId      the x request id
    //     * @param XForwardedFor   the x forwarded for
    //     * @param pesona          the pesona
    //     * @param securityContext the security context
    //     * @param httpHeaders     the http headers
    //     * @param httpRequest     the http request
    //     * @return the response
    //     */
    //    @PUT
    //    @Path("/persone-fisiche")
    //    @Produces({"application/json"})
    //    public Response modificaPersonaFisica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, ContattoVO pesona, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    //
    //    /**
    //     * Insert persona giuridica response.
    //     *
    //     * @param userCf          the user cf
    //     * @param XRequestId      the x request id
    //     * @param XForwardedFor   the x forwarded for
    //     * @param contatto        the contatto
    //     * @param securityContext the security context
    //     * @param httpHeaders     the http headers
    //     * @param httpRequest     the http request
    //     * @return the response
    //     */
    //    @POST
    //    @Path("/persone-giuridiche")
    //    @Produces({"application/json"})
    //    public Response insertPersonaGiuridica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, ContattoVO contatto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    //
    //    /**
    //     * Ricerca persona giuridica response.
    //     *
    //     * @param userCf          the user cf
    //     * @param XRequestId      the x request id
    //     * @param XForwardedFor   the x forwarded for
    //     * @param filter          the filter
    //     * @param sort            the sort
    //     * @param page            the page
    //     * @param size            the size
    //     * @param securityContext the security context
    //     * @param httpHeaders     the http headers
    //     * @param httpRequest     the http request
    //     * @return the response
    //     */
    //    @GET
    //    @Path("/persone-giuridiche")
    //    @Produces({"application/json"})
    //    public Response ricercaPersonaGiuridica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @QueryParam("filter") String filter, @QueryParam("sort") String sort, @NotNull @QueryParam("page") int page, @NotNull @QueryParam("size") int size, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    //
    //    /**
    //     * Elimina persona giuridica response.
    //     *
    //     * @param userCf          the user cf
    //     * @param XRequestId      the x request id
    //     * @param XForwardedFor   the x forwarded for
    //     * @param piva            the piva
    //     * @param securityContext the security context
    //     * @param httpHeaders     the http headers
    //     * @param httpRequest     the http request
    //     * @return the response
    //     */
    //    @DELETE
    //    @Path("/persone-giuridiche/{partitaIva}")
    //    @Produces({"application/json"})
    //    public Response eliminaPersonaGiuridica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @PathParam("partitaIva") String piva, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    //
    //    /**
    //     * Modifica persona giuridica response.
    //     *
    //     * @param userCf          the user cf
    //     * @param XRequestId      the x request id
    //     * @param XForwardedFor   the x forwarded for
    //     * @param pesona          the pesona
    //     * @param securityContext the security context
    //     * @param httpHeaders     the http headers
    //     * @param httpRequest     the http request
    //     * @return the response
    //     */
    //    @PUT
    //    @Path("/persone-giuridiche")
    //    @Produces({"application/json"})
    //    public Response modificaPersonaGiuridica(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, ContattoVO pesona, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    //
    //    //	@GET
    //    //	@Path("/objectPersonaFisica")
    //    //	@Produces({ "application/json" })
    //    //	public Response object(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
    //    //			@Context HttpServletRequest httpRequest);
    //    //
    //    //	@GET
    //    //	@Path("/objectPersonaGiuridica")
    //    //	@Produces({ "application/json" })
    //    //	public Response objectPG(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
    //    //			@Context HttpServletRequest httpRequest);

}