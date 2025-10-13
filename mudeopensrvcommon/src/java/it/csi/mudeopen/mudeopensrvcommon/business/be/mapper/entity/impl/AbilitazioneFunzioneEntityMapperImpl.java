/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRAbilitazioneFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AbilitazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AbilitazioneFunzioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FunzioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AbilitazioneFunzioneEntityMapperImpl implements AbilitazioneFunzioneEntityMapper {

    @Autowired
    private AbilitazioneEntityMapper abilitazioneEntityMapper;

    @Autowired
    private FunzioneEntityMapper funzioneEntityMapper;

    @Override
    public AbilitazioneFunzioneVO mapEntityToVO(MudeRAbilitazioneFunzione dto) {
        if(null == dto)
            return null;

        AbilitazioneFunzioneVO vo = new AbilitazioneFunzioneVO();
        vo.setId(dto.getId());
        vo.setAbilitazione(abilitazioneEntityMapper.mapEntityToVO(dto.getAbilitazione()));
        vo.setFunzione(funzioneEntityMapper.mapEntityToVO(dto.getFunzione()));

        return vo;
    }

    @Override
    public List<AbilitazioneFunzioneVO> mapListEntityToListVO(List<MudeRAbilitazioneFunzione> tl) {
        return AbilitazioneFunzioneEntityMapper.super.mapListEntityToListVO(tl);
    }
}