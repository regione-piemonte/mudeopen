/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web.impl;
import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopenboweb.business.be.web.AllegatiApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AllegatiService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;
/**
 * The type Allegati api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class AllegatiApiServiceImpl  extends BaseAPI<AllegatiApi> implements AllegatiApi {
    private static final String BACKOFFICE = "backoffice";

	@Autowired
    private AllegatiService allegatiService;
    @Override
    public Response downloadAllegato(String uuid, String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String scope,Boolean con_firma) {
        LoggerUtil.debug(logger, "[AllegatiApiServiceImpl::downloadAllegato]");
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
            AllegatiApi allegatiApi = rtarget.proxy(AllegatiApi.class);
            Response response = allegatiApi.downloadAllegato(uuid, userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest, BACKOFFICE, con_firma);
            return response;
        }

        catch(Throwable t) {
            throw new RemoteException();
        }
    }
    @Override
    public Response downloadAllegatoTest(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
    		String scope,Boolean con_firma) {
        LoggerUtil.debug(logger, "[AllegatiApiServiceImpl::downloadAllegatoTest]");
        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
        try {
            Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
            AllegatiApi allegatiApi = rtarget.proxy(AllegatiApi.class);
            Response response = allegatiApi.downloadAllegatoTest(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), securityContext, httpHeaders, httpRequest, BACKOFFICE, con_firma);
            return response;
        }

        catch(Throwable t) {
            throw new RemoteException();
        }
    }
    @Override
    public Response loadAllegatiIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
							    		Long idIstanza, 
							    		String tipo_allegato,
							    		String scope) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, tipo_allegato,
    				   scope);
    }
	@Override
	public Response exportExcelAllegatiIstanza(Long idIstanza, String userCf, String XRequestId, String XForwardedFor,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
		List<AllegatoVO> vos = allegatiService.loadAllegatiIstanza(idIstanza,mudeTUser);
		byte[] model = allegatiService.exportExcelAllegatiIstanza(vos);
        String outputFilename = new StringBuilder().append("Allegati").append(".xls").toString();
        return Response.ok(model, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + outputFilename + "\"").build();
	}
}