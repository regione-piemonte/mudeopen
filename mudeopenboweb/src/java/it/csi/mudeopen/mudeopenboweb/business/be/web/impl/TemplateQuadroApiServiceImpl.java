/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopenboweb.business.be.web.TemplateQuadroApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;

/**
 * The type Template quadro api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class TemplateQuadroApiServiceImpl extends BaseAPI<TemplateQuadroApi> implements TemplateQuadroApi {

	@Override
	public Response loadTemplateQuadriByTipoIstanza(String userCf, String XRequestId, String XForwardedFor,
													String tipoIstanza, SecurityContext securityContext,
													HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
		try {

			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
			WebTarget target = client.target(configurationHelper.getEndpointBase());
			ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
			TemplateQuadroApi api = rtarget.proxy(TemplateQuadroApi.class);

			Response response = api.loadTemplateQuadriByTipoIstanza(userInfo.getCodFisc(),XRequestId,XForwardedFor,
					tipoIstanza,securityContext, httpHeaders, httpRequest);
			return response;
		} catch (Throwable t) {

			throw new RemoteException();
		}
	}

	@Override
    public Response loadTemplateQuadriByCodeTemplate(String userCf, String XRequestId, String XForwardedFor, 
											    		String idTipoIstanza,
											    		String boTemplateOverride,
											    		Long idIstanza,
											    		SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
		try {

			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
			WebTarget target = client.target(configurationHelper.getEndpointBase());
			ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
			TemplateQuadroApi api = rtarget.proxy(TemplateQuadroApi.class);

			Response response = api.loadTemplateQuadriByCodeTemplate(userInfo.getCodFisc(),XRequestId,XForwardedFor,
					idTipoIstanza, boTemplateOverride, idIstanza, securityContext, httpHeaders, httpRequest);
			return response;
		} catch (Throwable t) {

			throw new RemoteException();
		}
	}
	
	@Override
	public Response loadTemplateQuadriByIdTemplateQuadro(String userCf, String XRequestId, String XForwardedFor, Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
		try {

			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
			WebTarget target = client.target(configurationHelper.getEndpointBase());
			ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
			TemplateQuadroApi api = rtarget.proxy(TemplateQuadroApi.class);

			Response response = api.loadTemplateQuadriByIdTemplateQuadro(userInfo.getCodFisc(),XRequestId,XForwardedFor,
					idTemplateQuadro,securityContext, httpHeaders, httpRequest);
			return response;
		} catch (Throwable t) {

			throw new RemoteException();
		}
	}
	
	
	@Override
	public Response loadQuadroById(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
							Long idQuadro,
							Long idIstanza,
							Long idFascicolo) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idQuadro, idIstanza, 
				idFascicolo);
	}
	@Override
	public Response loadJDataIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
										Long idIstanza,
										Long idQuadro,
										String codTipoQuadro,
										Boolean isObbligatoriaNominaPM,
										String requestType) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, idQuadro,
				codTipoQuadro,
				isObbligatoriaNominaPM,
				requestType);
	}

	@Override
	public Response retrieveAllQuadriInTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
							Long idTemplate,
							Long idIstanza,
							Long idUser, 
							Boolean includeJson) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idTemplate, idIstanza,
				idUser,
				includeJson);
	}
		
}