/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.web.impl;

import it.csi.mudeopen.mudeopenfoweb.business.be.web.IstanzeUtentiApi;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import it.csi.mudeopen.mudeopensrvcommon.util.BaseAPI;
@Component
public class IstanzeUtentiApiServiceImpl extends BaseAPI<IstanzeUtentiApi> implements IstanzeUtentiApi {

    @Override
    public Response saveIstanzaUtente(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, IstanzaUtenteVO istanzaUtenteVO) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, istanzaUtenteVO);
    }

    @Override
    public Response saveAbilitazioniFunzioniPerIstanzaUtente(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanza, AbilitazioneFunzioneCustomVO abilitazioneFunzioneCustomVO) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanza, abilitazioneFunzioneCustomVO);
    }

    @Override
    public Response deleteIstanzaUtente(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idIstanzaUtente) {
        return callAPI(userCf, securityContext, httpHeaders, httpRequest, null, idIstanzaUtente);
    }
}