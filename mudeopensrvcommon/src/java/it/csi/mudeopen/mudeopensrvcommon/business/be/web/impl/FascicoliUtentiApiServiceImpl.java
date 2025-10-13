/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AbilitazioneService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.FascicoliUtentiApi;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FascicoloUtenteVO;

@Component
public class FascicoliUtentiApiServiceImpl extends AbstractApi implements FascicoliUtentiApi {

    private static final String ERRORE_VALIDAZIONE = "errore_validazione";

	@Autowired
    private FascicoloUtenteService fascicoloUtenteService;

    @Autowired
    private AbilitazioneService abilitazioneService;

    @Override
    public Response saveFascicoloUtente(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
    		FascicoloUtenteVO fascicoloUtenteVO) {

        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);
        Long idFascicolo = fascicoloUtenteVO.getFascicolo().getIdFascicolo();

        AbilitazioneVO abilitazioneVO = abilitazioneService.findById(fascicoloUtenteVO.getAbilitazione().getId());
        if (abilitazioneVO.getTipo() == 'S' 
        		&& fascicoloUtenteService.findByFascicoloAndAbilitazione(idFascicolo, abilitazioneVO.getId()).size() > 0)
        	throw new BusinessException("Abilitazione [" + abilitazioneVO.getDescrizione() + "] già assegnata. Non è possibile assegnare questa abilitazione a più utenti", "403", ERRORE_VALIDAZIONE, null);

        return voToResponse(fascicoloUtenteService.save(idFascicolo, abilitazioneVO.getCodice(), mudeTUser), 200);
    }

    @Override
    public Response saveAbilitazioniFunzioniPerFascicoloUtente(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idFascicolo, AbilitazioneFunzioneCustomVO abilitazioneFunzioneCustomVO) {
        MudeTUser mudeTUser = headerUtil.getUserCF(httpHeaders, false);

        AbilitazioneVO abilitazioneVO = abilitazioneService.findById(abilitazioneFunzioneCustomVO.getAbilitazione().getId());

        if (abilitazioneVO.getTipo() == 'S' 
        		&& fascicoloUtenteService.findByFascicoloAndAbilitazione(idFascicolo, abilitazioneVO.getId()).size() > 0)
        	throw new BusinessException("Abilitazione [" + abilitazioneVO.getDescrizione() + "] già assegnata. Non è possibile assegnare questa abilitazione a più utenti", "403", ERRORE_VALIDAZIONE, null);

        fascicoloUtenteService.saveAbilitazioniFunzioniPerFascicoloUtente(idFascicolo, abilitazioneFunzioneCustomVO, mudeTUser);
        return okResponse();
    }

    @Override
    public Response deleteFascicoloUtente(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, Long idFascicoloUtente) {
        fascicoloUtenteService.delete(idFascicoloUtente);
        return okResponse();
    }
}