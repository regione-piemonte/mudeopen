/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

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
import it.csi.mudeopen.mudeopenfoweb.business.be.web.TipiAllegatoApi;
import it.csi.mudeopen.mudeopenfoweb.common.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;

/**
 * The type Tipi allegato api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class TipiAllegatoApiServiceImpl extends BaseAPI<TipiAllegatoApi> implements TipiAllegatoApi {

    @Override
    public Response loadTipiAllegato(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {

            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            TipiAllegatoApi api = rtarget.proxy(TipiAllegatoApi.class);

            Response response = api.loadTipiAllegato(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR),
                    securityContext, httpHeaders, httpRequest);

            return response;
        } catch (Throwable t) {

            throw new RemoteException();
        }
    }

    @Override
    public Response loadTipoAllegatoByCode(String codTipoAllegato, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {

            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            TipiAllegatoApi api = rtarget.proxy(TipiAllegatoApi.class);

            Response response = api.loadTipoAllegatoByCode(codTipoAllegato, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR),
                    securityContext, httpHeaders, httpRequest);

            return response;
        } catch (Throwable t) {

            throw new RemoteException();
        }
    }

    @Override
    public Response loadTipiAllegatoByCategoriaAllegato(String codiceCategoriaAllegato, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            TipiAllegatoApi api = rtarget.proxy(TipiAllegatoApi.class);

            Response response = api.loadTipiAllegatoByCategoriaAllegato(codiceCategoriaAllegato, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR),
                    securityContext, httpHeaders, httpRequest);

            return response;
        } catch (Throwable t) {

            throw new RemoteException();
        }
    }

    @Override
    public Response loadByCodTipoAllegatoAndSubCodeTipoAllegato(String codTipoAllegato, String subCodTipoAllegato, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {

            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
            TipiAllegatoApi api = rtarget.proxy(TipiAllegatoApi.class);

            Response response = api.loadByCodTipoAllegatoAndSubCodeTipoAllegato(codTipoAllegato, subCodTipoAllegato, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR),
                    securityContext, httpHeaders, httpRequest);

            return response;
        } catch (Throwable t) {

            throw new RemoteException();
        }
    }

	@Override
	public Response loadTipiAllegatoByTemplateQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idIstanza,
			Long idTemplateQuadro, 
			String tipo_allegato) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, idTemplateQuadro, 
				tipo_allegato);
	}

}