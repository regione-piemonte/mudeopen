
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.AmbitoVO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/ambiti")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })

public interface AmbitiApi {
    @GET
	@Consumes("application/json")
	Response loadAmbiti(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders ,
			@Context HttpServletRequest httpRequest);
    @GET
	@Path("/{idAmbito}")
	Response loadAmbito(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("idAmbito") Long idAmbito,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);
    @GET
	@Path("/codice/{codAmbito}")
	Response loadAmbitoByCodice(
			@HeaderParam ("user-cf") String userCf,
			@HeaderParam ("X-Request-ID") String XRequestId,
			@HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@PathParam("codAmbito") String codAmbito,
			@Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @POST
	@Consumes({ "application/json" })
	Response saveAmbito(
			@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@RequestBody AmbitoVO ambito,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
    @PUT
	@Consumes({ "application/json" })
	Response updateAmbito(
			@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@RequestBody AmbitoVO ambito,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest httpRequest);
}