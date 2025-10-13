/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloSoggettoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SoggettoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloSoggettoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

@Component
public class FascicoloSoggettoEntityMapperImpl implements FascicoloSoggettoEntityMapper {

    @Autowired
    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private SoggettoIstanzaEntityMapper soggettoIstanzaEntityMapper;

    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

    @Override
    public FascicoloSoggettoVO mapEntityToVO(MudeRFascicoloSoggetto dto) {
        if (dto == null)
            return null;
        FascicoloSoggettoVO vo = mapEntityToSlimVO(dto);

        UtenteVO utenteVO = utenteEntityMapper.mapEntityToVO(dto.getUser());
        vo.setUser(utenteVO);
        FascicoloVO fascicoloVO = fascicoloEntityMapper.mapEntityToVO(dto.getFascicolo(), dto.getUser());
        vo.setFascicolo(fascicoloVO);
        /*
        SoggettoIstanzaVO soggettoIstanzaVO = soggettoIstanzaEntityMapper.mapEntityToVO(dto.getIstanzaSoggetto(), dto.getUser());
        vo.setIstanzaSoggetto(soggettoIstanzaVO);
        */

        return vo;
    }

    @Override
    public List<FascicoloSoggettoVO> mapListEntityToListVO(List<MudeRFascicoloSoggetto> tl) {
        return FascicoloSoggettoEntityMapper.super.mapListEntityToListVO(tl);
    }

    @Override
    public List<FascicoloSoggettoVO> mapListEntityToListSlimVO(List<MudeRFascicoloSoggetto> tl) {
        return FascicoloSoggettoEntityMapper.super.mapListEntityToListSlimVO(tl);
    }

    @Override
    public FascicoloSoggettoVO mapEntityToSlimVO(MudeRFascicoloSoggetto dto) {
        FascicoloSoggettoVO vo = new FascicoloSoggettoVO();

        vo.setId(dto.getId());
        vo.setGuidSoggetto(dto.getGuidSoggetto());
        UtenteVO user = new UtenteVO();
        user.setId(dto.getUser().getIdUser());
		vo.setUser(user);

        return vo;
    }
}