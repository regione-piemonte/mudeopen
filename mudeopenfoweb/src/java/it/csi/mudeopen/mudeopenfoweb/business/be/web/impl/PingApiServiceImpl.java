/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenfoweb.business.be.web.PingApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.PingApiServiceHelper;

/**
 * The type Ping api service.
 */
@Component
public class PingApiServiceImpl implements PingApi{
	
	@Autowired
	private PingApiServiceHelper pingApiServiceHelper;
	
	@Override
	public Response ping(String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return Response.ok(pingApiServiceHelper.ping()).build();
	}
	

}