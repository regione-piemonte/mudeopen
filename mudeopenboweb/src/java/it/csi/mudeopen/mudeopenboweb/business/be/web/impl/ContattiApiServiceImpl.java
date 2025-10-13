/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenboweb.business.be.web.ContattiApi;

/**
 * The type Contatti api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class ContattiApiServiceImpl extends BaseAPI<ContattiApi> implements ContattiApi {

    @Override
    public Response ricercaContatto(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
    		String filter, String sort, int page, int size) {
    	return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, filter, sort, page, size);
    }

}