/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenboweb.business.be.web.FascicoliApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.exception.RemoteException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloSoggettoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.HeaderUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.UserInfo;

/**
 * The type Fascicoli api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class FascicoliApiServiceImpl extends BaseAPI<FascicoliApi> implements FascicoliApi {
	
	private static final String BACKOFFICE = "backoffice";
	@Autowired
	private FascicoliService fascicoliService;

	@Override
    public Response recuperaFascicoli(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									String filter, 
									String sort, 
									int page, 
									int size,
									String scope) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, filter, sort, 
        		page, 
        		size,
        		BACKOFFICE);
	}

	@Override
	public Response recuperaFascicolo(String userCf, String XRequestId, String XForwardedFor, Long idFascicolo,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);
		try {
			
			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(userCf, userInfo));
            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget)target;           
            FascicoliApi fascicoliApi = rtarget.proxy(FascicoliApi.class);

            Response response = fascicoliApi.recuperaFascicolo(userInfo.getCodFisc(), (String) MDC.get(Constants.X_REQUEST_ID),(String) MDC.get(Constants.X_FORWARDED_FOR), idFascicolo, securityContext, httpHeaders, httpRequest);
            return response;
		}
		catch(Throwable t) {
			throw new RemoteException();
		}
	}

	@Override
    public Response exportExcelFascicoli(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String filter, String sort, String scope) {
        LoggerUtil.debug(logger, "[FascicoliApiServiceImpl::exportExcelFascicoli]");

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
		String codiceTipoIntervento = FilterUtil.getStringValue(filter, "id_tipo_intervento");
		String idIntestatarioPf = FilterUtil.getStringValue(filter, "id_intestatario_pf");
		String idIntestatarioPg = FilterUtil.getStringValue(filter, "id_intestatario_pg");
		String idPm = FilterUtil.getStringValue(filter, "id_pm");
		Long idComune = FilterUtil.getLongValue(filter, "id_comune");		
		Long idProvincia = FilterUtil.getLongValue(filter, "id_provincia");
		Long idDug = FilterUtil.getLongValue(filter, "id_dug");
		String duf = FilterUtil.getStringValue(filter, "duf");
		LocalDate creationDateForm = FilterUtil.getDateFromValue(filter, "data_creazione");
		LocalDate creationDateTo = FilterUtil.getDateToValue(filter, "data_creazione");
		String state = FilterUtil.getStringValue(filter, "stato");
		String codiceFascicolo = FilterUtil.getStringValue(filter, "codice_fascicolo");

        byte[] model = fascicoliService.exportExcelFascicoliUtente(mudeTUser, codiceTipoIntervento, idIntestatarioPf, idIntestatarioPg, idPm, idComune, idProvincia, idDug, duf, creationDateForm, creationDateTo, codiceFascicolo, state, BACKOFFICE);
        String outputFilename = new StringBuilder().append("Fascicoli").append(".xls").toString();

        return Response.ok(model, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + outputFilename + "\"").build();
    }
}