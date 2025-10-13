/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AbilitazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloUtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FascicoloUtenteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FascicoloUtenteEntityMapperImpl implements FascicoloUtenteEntityMapper {

    @Autowired
    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

    @Autowired
    private AbilitazioneEntityMapper abilitazioneEntityMapper;

    @Override
    public FascicoloUtenteVO mapEntityToVO(MudeRFascicoloUtente dto) {
        if (null == dto)
            return null;

        FascicoloUtenteVO vo = new FascicoloUtenteVO();
        vo.setId(dto.getId());
        vo.setFascicolo(fascicoloEntityMapper.mapEntityToVO(dto.getFascicolo(), null));
        vo.setUtente(utenteEntityMapper.mapEntityToVO(dto.getUtente()));
        vo.setAbilitazione(abilitazioneEntityMapper.mapEntityToVO(dto.getAbilitazione()));
        vo.setAbilitatore(utenteEntityMapper.mapEntityToVO(dto.getAbilitatore()));

        vo.setModificabile(!dto.getAbilitazione().getCodice().equals("FASCIC_CREATORE"));

        return vo;
    }

    @Override
    public List<FascicoloUtenteVO> mapListEntityToListVO(List<MudeRFascicoloUtente> tl) {
        return FascicoloUtenteEntityMapper.super.mapListEntityToListVO(tl);
    }
}