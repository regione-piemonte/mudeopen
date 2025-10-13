/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
/**
 * The interface Ping api.
 */
@Path("/")
public interface HomeAPI  {
    @GET
    public Response goHome(@HeaderParam ("X-Request-ID") String XRequestId, @HeaderParam ("X-Forwarded-For") String XForwardedFor,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );
}