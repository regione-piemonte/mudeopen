/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenboweb.business.be.web.NotificheApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoNotificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.NotificaService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

/**
 * The type Notifiche api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class NotificheApiServiceImpl extends BaseAPI<NotificheApi> implements NotificheApi {

    @Autowired
    private NotificaService notificaService;

	@Autowired
	private MudeTUserRepository mudeTUserRepository;

    @Autowired
    MudeDTipoNotificaRepository mudeDTipoNotificaRepository;
	
	@Autowired
    MudeTDocumentoRepository mudeTDocumentoRepository;

	@Override
	public Response loadNotificheIstanza(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
										Long idIstanza, 
										int sort, 
										int page, 
										int size) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, sort, page, size);
	}

    @Override
    public Response loadTipiNotifica(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<SelectVO> listStatiVO = notificaService.recuperaTipiNotifica();
        return Response.ok(listStatiVO).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(listStatiVO)).build();
    }

    @Override
	public Response reuperoTemplateFormIoNotifica(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
													Long idIstanza, 
													Long idTipoNotifica) {
		MudeTUser mudeTUser; 
		if(!StringUtils.isBlank(userCf))
			mudeTUser = mudeTUserRepository.findByCf(userCf);
		else 
			mudeTUser = headerUtil.getUserCF(httpHeaders, false);
    	
    	List<SelectVO> listNotificaVO = notificaService.reuperoTemplateFormIoNotifica(idIstanza, mudeTUser, idTipoNotifica);
        return Response.ok(listNotificaVO).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(listNotificaVO)).build();
	}

	@Override
	public Response nuovaNotifica(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanza,
									IstanzaVO istanza,
									Long idTipoNotifica,
									String scope) {
		return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, istanza, 
        		idTipoNotifica, "backoffice");
	}

}