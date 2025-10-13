/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web;
import javax.servlet.http.HttpServletRequest;
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
 * The interface gestione quadri api BO.
 */
@Path("/ppay")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface PiemontePayApi {

	/**
	 * Elimina tipo quadri response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param PiemontePayApi  id istanza
	 * @return the response
	 */
    @GET
	@Path("/istanza/{idIstanza}/importi")
	public Response recuperaDettagliPagamento(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idIstanza") Long idIstanza);
	
    @GET
    @Path("avvia-pagamento/id-istanza/{idIstanza}")
    Response avviaPagamento(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
							@PathParam("idIstanza") Long idIstanza,
							@QueryParam("extraParams") String extraParams);

    @GET
    @Path("callback")
    Response ppayCallback(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, 
			    		  @QueryParam("idPagamento") String idPagamento,
			    		  @QueryParam("descEsito") String descEsito,
			    		  @QueryParam("codEsito") String codEsito,
			    		  @QueryParam("source") String source);
		
}