/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopenfoweb.business.be.web.AbilitazioneFunzioneApi;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The type AbilitazioneFunzione api service.
 */
import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class AbilitazioneFunzioneApiServiceImpl extends BaseAPI<AbilitazioneFunzioneApi> implements AbilitazioneFunzioneApi {

    @Override
    public Response loadFunzioniAbilitazioni(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idFascicolo, Long idIstanza, Boolean excludeFilters) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idFascicolo, idIstanza, excludeFilters);
    }

    @Override
    public Response loadFunzioniByAbilitazione(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String codiceAbilitazione) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, codiceAbilitazione);
    }

}