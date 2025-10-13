/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web;

import javax.servlet.http.HttpServletRequest;
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

@Path("/tipi-allegato")
//@Consumes({ MediaType.APPLICATION_JSON })
@Produces({MediaType.APPLICATION_JSON})
public interface TipiAllegatoApi {

    @GET
    Response loadTipiAllegato(@HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @GET
    @Path("/codice-tipo-allegato/{codTipoAllegato}")
    Response loadTipoAllegatoByCode(@PathParam("codTipoAllegato") String codTipoAllegato, @HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @GET
    @Path("/id-categoria-allegato/{codCategoriaAllegato}")
    Response loadTipiAllegatoByCategoriaAllegato(@PathParam("codCategoriaAllegato") String codiceCategoriaAllegato, @HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @GET
    @Path("/codice-tipo-allegato/{codTipoAllegato}/sub-codice-tipo-allegato/{subCodTipoAllegato}")
    Response loadByCodTipoAllegatoAndSubCodeTipoAllegato(@PathParam("codTipoAllegato") String codTipoAllegato, @PathParam("subCodTipoAllegato") String subCodTipoAllegato, @HeaderParam("user-cf") String userCf, @HeaderParam("X-Request-ID") String XRequestId, @HeaderParam("X-Forwarded-For") String XForwardedFor, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    @GET
    @Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
	public Response loadTipiAllegatoByTemplateQuadro(@HeaderParam ("user-cf") String userCf, @HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
			@PathParam("idIstanza") Long idIstanza,
			@PathParam("idTemplateQuadro") Long idTemplateQuadro, 
			@QueryParam("tipo_allegato") String tipo_allegato);
}