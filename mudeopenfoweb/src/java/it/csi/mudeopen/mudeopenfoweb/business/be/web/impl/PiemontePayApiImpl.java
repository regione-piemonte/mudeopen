/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenfoweb.business.be.web.PiemontePayApi;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.PiemontePayService;

/**
 * The type Contatti api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class PiemontePayApiImpl extends BaseAPI<PiemontePayApi> implements PiemontePayApi {

    @Autowired
    PiemontePayService piemontePayService;

    @Override
    public Response recuperaDettagliPagamento(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    										  Long idIstanza) {
    	return voToResponse(piemontePayService.recuperaDettagliPagamento(idIstanza), 200);
    }

    @Override
    public Response avviaPagamento(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
    								  Long idIstanza,
    								  String extraParams) {
    	
    	MudeTUser mudeTUser = getMudeTUser(httpHeaders);
    	return voToResponse(piemontePayService.avviaPagamento(idIstanza, extraParams, mudeTUser), 200);
    }

	@Override
	public Response ppayCallback(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
								 String idPagamento, 
								 String descEsito, 
								 String codEsito, 
								 String source) {
    	return voToResponse(piemontePayService.ppayCallback(idPagamento, codEsito, descEsito, getMudeTUser(httpHeaders)), 200);
	}

}