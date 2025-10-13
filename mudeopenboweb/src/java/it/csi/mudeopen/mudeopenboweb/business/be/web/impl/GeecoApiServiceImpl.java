/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenboweb.business.be.web.GeecoApi;
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class GeecoApiServiceImpl extends BaseAPI<GeecoApi> implements GeecoApi {

	@Override
	public Response getGeecoConfiguration(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
			Long idIstanza) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza);
	}

	@Override
	public Response getSelectedData(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idIstanza) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza);
	}

	@Override
	public Response listaToponomastica(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			String query,
			String siglaComune, 
			String nomeComune, 
			int page, 
			int size) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, query, siglaComune, 
				nomeComune,
				page, 
				size);
	}
	
		
	@Override
	public Response recuperaDatiGeeco(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idIstanza) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza);
	}
	
	@Override
	public Response saveSelectedJson(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									Long idIstanza, 
									String selectedJson) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, selectedJson);
	}
	
}
