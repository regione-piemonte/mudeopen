/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AbilitazioneFunzioneService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TemplateService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.AbilitazioneFunzioneApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateVO;

@Component
public class AbilitazioneFunzioneApiServiceImpl extends AbstractApi implements AbilitazioneFunzioneApi {

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private AbilitazioneFunzioneService abilitazioneFunzioneService;

    @Override
    public Response loadFunzioniAbilitazioni(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idFascicolo, Long idIstanza, Boolean excludeFilters) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        Boolean isFascicolo = Boolean.FALSE;
        Boolean isPmObbligatorio = Boolean.FALSE;
        if(null != idFascicolo) isFascicolo = Boolean.TRUE;

        if(null != idIstanza){
            IstanzaVO istanzaVO = istanzaService.recuperaIstanza(mudeTUser,idIstanza);
            Long idTemplate = istanzaVO.getIdTemplate();
            TemplateVO templateVO = templateService.loadTemplateById(idTemplate);
            isPmObbligatorio = templateVO.getObbligatoriaNominaPM();
            idFascicolo = istanzaVO.getIdFascicolo();
        }

        List<AbilitazioneFunzioneCustomVO> list = abilitazioneFunzioneService.loadFunzioniAbilitazioni(isFascicolo,
																			        					isPmObbligatorio, 
																			        					idFascicolo, 
																			        					idIstanza, 
																			        					excludeFilters, 
																			        					mudeTUser.getIdUser());
        return Response.ok().entity(list).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(list)).build();
    }

    @Override
    public Response loadFunzioniByAbilitazione(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String codiceAbilitazione) {
        AbilitazioneFunzioneCustomVO vo = abilitazioneFunzioneService.loadByCodiceAbilitazione(codiceAbilitazione);
        return Response.ok().entity(vo).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY_CONTENT_ENCODING).header(HttpHeaders.CONTENT_LENGTH, getContentLen(vo)).build();
    }
}