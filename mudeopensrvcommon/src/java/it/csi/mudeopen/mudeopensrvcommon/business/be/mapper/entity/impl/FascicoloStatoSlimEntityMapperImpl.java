/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloStatoSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.StatoFascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloStatoSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloStatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FascicoloStatoSlimEntityMapperImpl implements FascicoloStatoSlimEntityMapper {
    @Autowired
    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private StatoFascicoloEntityMapper statoFascicoloEntityMapper;

    @Override
    public FascicoloStatoSlimVO mapEntityToVO(MudeRFascicoloStato dto) {
        FascicoloStatoSlimVO vo = null;
        if(null != dto){
            vo = new FascicoloStatoSlimVO();
            vo.setId(dto.getId());
            vo.setIdFascicolo(dto.getFascicolo().getId());
            vo.setStatoFascicolo(statoFascicoloEntityMapper.mapEntityToVO(dto.getStatoFascicolo()));
        }
        return vo;
    }
}