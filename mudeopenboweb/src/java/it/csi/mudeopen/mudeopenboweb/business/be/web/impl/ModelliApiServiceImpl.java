/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

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

import it.csi.mudeopen.mudeopenboweb.business.be.web.ModelliApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;

/**
 * The type Modelli api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class ModelliApiServiceImpl extends BaseAPI<ModelliApi> implements ModelliApi {

    @Override
    public Response loadModelli(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
            ModelliApi modelliApi = rtarget.proxy(ModelliApi.class);

            Response response = modelliApi.loadModelli(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest);

            return response;
        }
        catch(Throwable t) {

            throw new RemoteException();
        }
    }

    @Override
    public Response loadModello(Long idModello, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
            ModelliApi modelliApi = rtarget.proxy(ModelliApi.class);

            Response response = modelliApi.loadModello(idModello, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest);

            return response;
        }
        catch(Throwable t) {

            throw new RemoteException();
        }
    }

    @Override
    public Response loadModelloByDenominazione(String denomModello, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
            ModelliApi modelliApi = rtarget.proxy(ModelliApi.class);

            Response response = modelliApi.loadModelloByDenominazione(denomModello, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest);

            return response;
        }
        catch(Throwable t) {

            throw new RemoteException();
        }
    }

    @Override
    public Response downloadModello(Long idModello, Long idIstanza, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
            ModelliApi modelliApi = rtarget.proxy(ModelliApi.class);

            Response response = modelliApi.downloadModello(idModello, idIstanza, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest);

            return response;
        }
        catch(Throwable t) {

            throw new RemoteException();
        }
    }
}