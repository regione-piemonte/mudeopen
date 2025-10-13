/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.WizardApi;
import it.csi.mudeopen.mudeopenfoweb.common.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;

/**
 * The type Wizard api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class WizardApiServiceImpl extends BaseAPI<WizardApi> implements WizardApi {

	@Override
	public Response recuperaElementi(String userCf, String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		logger.debug("[WizardApiServiceImpl::recuperaElementi");
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
		try {

			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
			WebTarget target = client.target(configurationHelper.getEndpointBase());
			ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
			WizardApi wizardApi = rtarget.proxy(WizardApi.class);

			Response response = wizardApi.recuperaElementi(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR),
					securityContext, httpHeaders, httpRequest);
			logger.debug("WizardApiServiceImpl::recuperaElementi] END");
			return response;
		} catch (Throwable t) {

			throw new RemoteException();
		}
	}

	@Override
	public Response recuperaAdempimenti(String userCf, String XRequestId, String XForwardedFor, String tipologia,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

		logger.debug("[WizardApiServiceImpl::recuperaAdempimenti");
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);

		try {

			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
			WebTarget target = client.target(configurationHelper.getEndpointBase());
			ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
			WizardApi wizardApi = rtarget.proxy(WizardApi.class);

			Response response = wizardApi.recuperaAdempimenti(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR),
					tipologia, securityContext, httpHeaders, httpRequest);
			logger.debug("WizardApiServiceImpl::recuperaAdempimenti] END");
			return response;
		} catch (Throwable t) {

			throw new RemoteException();
		}
	}

	@Override
	public Response recuperaTipoIstanza(String userCf, String XRequestId, String XForwardedFor, Long idRegimeGiuridico,
			Long idRegimeAggiuntivo, SecurityContext securityContext, HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {

		logger.debug("[WizardApiServiceImpl::recuperaTipoIstanza");
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);

		try {

			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
			WebTarget target = client.target(configurationHelper.getEndpointBase());
			ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
			WizardApi wizardApi = rtarget.proxy(WizardApi.class);

			Response response = wizardApi.recuperaTipoIstanza(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), 
					idRegimeGiuridico,idRegimeAggiuntivo, securityContext, httpHeaders, httpRequest);
			logger.debug("WizardApiServiceImpl::recuperaTipoIstanza] END");
			return response;
		} catch (Throwable t) {

			throw new RemoteException();
		}

	}

}