/**********************************************
 *  
 **********************************************/
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
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

import it.csi.mudeopen.mudeopensrvcommon.vo.response.DefaultResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ProfiloResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

@Path("/utenti")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

public interface UtentiApi {

    @GET
	@Path("/infoProfilo")
	@Produces({ "application/json" })
	public Response getInfoProfilo(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, 
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest,
			@QueryParam("scope") String scope);

    @POST
	@Path("/infoProfilo")
	@Produces({ "application/json" })
	public DefaultResponse salvaInfoProfilo(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor, 
			UtenteVO utente,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

    @PUT
	@Path("/infoProfilo")
	@Produces({ "application/json" })
	public DefaultResponse modificaInfoProfilo(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,
			UtenteVO utente,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
    @GET
	@Path("/accreditamenti")
	public Response recuperaAccreditamenti(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@QueryParam("filter") String filter, 
			@QueryParam("sort") String sort, 
			@NotNull @QueryParam("page")  int page, 
			@NotNull @QueryParam("size") int size);

	
}