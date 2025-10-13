/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenfoweb.business.be.web.HomeAPI;

/**
 * The type Ping api service.
 */
@Component
public class HomeApiServiceImpl implements HomeAPI {

	public Response goHome(String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return Response.seeOther(URI.create("index.html")).build();
	}
}