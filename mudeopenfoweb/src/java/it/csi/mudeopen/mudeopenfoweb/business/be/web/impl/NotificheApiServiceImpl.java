/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenfoweb.business.be.web.NotificheApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.NotificaService;

/**
 * The type Notifiche api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class NotificheApiServiceImpl extends BaseAPI<NotificheApi> implements NotificheApi {

    @Autowired
    private NotificaService notificaService;

    @Override
    public Response loadNotificheFO(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    								String filter, int sort, int page, int size) {
        return notificaService.loadNotificheFO(filter, page, size, headerUtil.getUserCF(httpHeaders, false));
    }

	
    @Override
	public Response notificaLettaFO(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    		Long idNotifica) {
    	
        return voToResponse(notificaService.notificaLettaFO(idNotifica, headerUtil.getUserCF(httpHeaders, false)), 200);
    }

	
}