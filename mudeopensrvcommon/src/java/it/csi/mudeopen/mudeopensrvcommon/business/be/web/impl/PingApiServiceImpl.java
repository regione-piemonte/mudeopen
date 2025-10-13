/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.PingApiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.PingApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
@Component
public class PingApiServiceImpl extends AbstractApi implements PingApi {
	@Autowired
	private PingApiServiceHelper pingApiServiceHelper;
	
	@Override
	public Response ping(String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return Response.ok(pingApiServiceHelper.ping()).build();
	}
}