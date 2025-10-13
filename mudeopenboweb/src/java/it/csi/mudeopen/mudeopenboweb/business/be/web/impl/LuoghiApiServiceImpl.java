/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenboweb.business.be.web.LuoghiApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;

/**
 * The type Luoghi api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class LuoghiApiServiceImpl extends BaseAPI<LuoghiApi> implements LuoghiApi {

    /**
     * The Configuration helper.
     */
	@Override
	public Response getNazioni(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null);
	}

	@Override
	public Response getStatiMembriUE(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null);
	}

	@Override
	public Response getRegioni(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String filter) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null,
				filter);
	}

	@Override
	public Response getProvince(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String filter) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, 
				filter);
	}

	@Override
	public Response getComuni(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String filter) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null,
				filter);
	}

	@Override
	public Response getProvinceForComuniRegistered(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null);
	}

	@Override
	public Response findComuniRegisteredForProvincia(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idProvincia) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, 
				idProvincia);
	}
}