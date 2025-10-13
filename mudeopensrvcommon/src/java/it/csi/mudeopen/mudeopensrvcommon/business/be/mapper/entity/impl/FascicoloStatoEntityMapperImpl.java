/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloStatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.StatoFascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloStatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FascicoloStatoEntityMapperImpl implements FascicoloStatoEntityMapper {

    @Autowired
    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private StatoFascicoloEntityMapper statoFascicoloEntityMapper;

    @Override
    public FascicoloStatoVO mapEntityToVO(MudeRFascicoloStato dto) {
        FascicoloStatoVO vo = null;
        if(null != dto){
            vo = new FascicoloStatoVO();
            vo.setId(dto.getId());
            vo.setFascicolo(fascicoloEntityMapper.mapEntityToVO(dto.getFascicolo(), null));
            vo.setStatoFascicolo(statoFascicoloEntityMapper.mapEntityToVO(dto.getStatoFascicolo()));
        }
        return vo;
    }
}