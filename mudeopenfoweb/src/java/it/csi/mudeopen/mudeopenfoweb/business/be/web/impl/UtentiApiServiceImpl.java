/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenfoweb.business.be.helper.UtenteApiServiceHelper;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.UtentiApi;
/**
 * The type Utenti api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ProfiloResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;
@Component
public class UtentiApiServiceImpl extends BaseAPI<UtentiApi> implements UtentiApi {

	@Autowired
	private UtenteApiServiceHelper utenteApiServiceHelper;
	
	@Override
	public Response getInfoProfilo(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
		Response r = callAPI(userCf, securityContext, httpHeaders, httpRequest, null);

		if(r.getStatus() != 200)
			return r;
		
		GenericType<ProfiloResponse> dtoType = new GenericType<ProfiloResponse>() {};
		ProfiloResponse profiloResponse = r.readEntity(dtoType);
		UtenteVO utenteVO = profiloResponse.getInfoUtente();
		if(null == utenteVO.getId()){
			UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
			utenteVO.getContatto().getAnagrafica().setNome(userInfo.getNome());
			utenteVO.getContatto().getAnagrafica().setCognome(userInfo.getCognome());
//				profiloResponse.setInfoUtente(utenteVO);
		}

		return Response.ok().entity(profiloResponse).build();
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