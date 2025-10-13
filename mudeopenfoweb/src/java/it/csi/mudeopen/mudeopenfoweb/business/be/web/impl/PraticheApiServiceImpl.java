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

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.PraticheApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;

/**
 * The type Pratiche api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class PraticheApiServiceImpl extends BaseAPI<PraticheApi> implements PraticheApi {

    @Override
    public Response recuperaPratiche(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									String filter, 
									String sort, 
									int page, 
									int size,
									String scope) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, filter, sort, 
        		page, 
        		size,
        		"frontoffice");
    }

	@Override
	public Response downloadDocumento(String uuid, String userCf, String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);

        Client client = ClientBuilder.newClient();
		client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));

        WebTarget target = client.target(configurationHelper.getEndpointBase());
        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
        PraticheApi praticheApi = rtarget.proxy(PraticheApi.class);

        Response response = praticheApi.downloadDocumento(uuid, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest);
        return response;
	}
}