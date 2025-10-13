/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopenboweb.business.be.helper.UtenteApiServiceHelper;
import it.csi.mudeopen.mudeopenboweb.business.be.web.UtentiApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ProfiloResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

/**
 * The type Utenti api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class UtentiApiServiceImpl extends BaseAPI<UtentiApi> implements UtentiApi {

	@Autowired
	private UtenteApiServiceHelper utenteApiServiceHelper;
	
	@Override
	public Response getInfoProfilo(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest,
			@QueryParam("scope") String scope) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, "backoffice");
	}

	@Override
	public Response localLogout(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {

		HttpServletRequest hreq = (HttpServletRequest) httpRequest;
		hreq.getSession().invalidate();

		return Response.ok().build();
	}

	@Override
	public Response salvaInfoProfilo(String userCf, String XRequestId, String XForwardedFor, UtenteVO utente, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
		return utenteApiServiceHelper.salvaInfoProfilo(userInfo.getCodFisc(), XRequestId, XForwardedFor, utente, securityContext, httpHeaders, httpRequest);
	}

	@Override
	public Response modificaInfoProfilo(String userCf, String XRequestId, String XForwardedFor, UtenteVO utente, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response recuperaAccreditamenti(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									String filter, 
									String sort, 
									int page, 
									int size) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, filter, sort, 
				page, 
				size);
	}
	
}