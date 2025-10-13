/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenboweb.business.be.web.DizionariApi;
/**
 * The type Dizionari api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class DizionariApiServiceImpl extends BaseAPI<DizionariApi> implements DizionariApi {
    /**
     * Recupera voce dizionario by codice response.
     *
     * @param userCf          the user cf
     * @param XRequestId      the x request id
     * @param XForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param dictType        the dict type
     * @param codice          the codice
     * @return the response
     */
    @Override
    public Response ricercaDizionario(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
    								  String dictType, 
    								  String filter) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, dictType, filter);
    }

}