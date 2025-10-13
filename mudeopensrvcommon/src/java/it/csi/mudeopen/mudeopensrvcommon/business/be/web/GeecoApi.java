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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.geeco.GeecoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
@Path("/geeco")
public interface GeecoApi {

	@GET
	@Path("/getgeecoconfiguration/{idIstanza}")
    public Response getGeecoConfiguration(
			@HeaderParam("user-cf") String userCf, 
			@HeaderParam ("X-Request-ID") String XRequestId, 
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,    		
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest, 
			@PathParam("idIstanza") Long idIstanza
    );	
	
	@GET
	@Path("/getselecteddata/{idIstanza}")
    public Response getSelectedData(
			@HeaderParam("user-cf") String userCf, 
			@HeaderParam ("X-Request-ID") String XRequestId, 
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,    		    		
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest,
			@PathParam("idIstanza") Long idIstanza
    );	
	
	@GET
	@Path("/toponomastiche")
	public Response listaToponomastica(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@QueryParam("query") String query, 
			@QueryParam("siglaComune") String siglaComune, 
			@QueryParam("nomeComune") String nomeComune, 
			@NotNull @QueryParam("page")  int page, @NotNull @QueryParam("size") int size);
	
    @GET
    @Path("/dati/istanza/{idIstanza}")
    Response recuperaDatiGeeco(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idIstanza") Long idIstanza);

    @POST
    @Path("/invia-dati/istanza/{idIstanza}")
    @Consumes({MediaType.TEXT_PLAIN})
    Response saveSelectedJson(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
    		@PathParam("idIstanza") Long idIstanza, String selectedJson);
	
	/*
	 * TO BE DELETED???
	
    @POST
	@Path("/configuration/{idIstanza}/{idLocalizzazione}")
    Response getConfigurationUrl(
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest , 
			@PathParam("idIstanza") Long idIstanza,
			@PathParam("idLocalizzazione") Long idLocalizzazione
    );

	
	@GET
	@Path("/getgeecoconfiguration/{idIstanza}/{idLocalizzazione}")
    public Response getGeecoConfiguration(
			@HeaderParam("user-cf") String userCf, 
			@HeaderParam ("X-Request-ID") String XRequestId, 
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,    		
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest , 
			@PathParam("idIstanza") Long idIstanza,
			@PathParam("idLocalizzazione") Long idLocalizzazione
    );

	@GET
	@Path("/lastselected/{geecoSessionId}/feature")
    public Response getLastFeatures(
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest , 
			@PathParam("geecoSessionId") String geecoSessionId
    );	

	@GET
	@Path("/quitcallback/{idIstanza}/{idLocalizzazione}")
    public Response quitcallback(
			@HeaderParam("user-cf") String userCf, 
			@HeaderParam ("X-Request-ID") String XRequestId, 
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,    		    		
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest  , 
			@PathParam("idIstanza") Long idIstanza,
			@PathParam("idLocalizzazione") Long idLocalizzazione
    );	
	
	@POST
	@Path("/callback")
	public Response callback(
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);

	
	@GET
	@Path("/callback2")
	public Response callback2(
			@Context SecurityContext securityContext, 
			@Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
	

	 */
	
}
