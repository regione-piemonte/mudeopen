/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaStatoSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.StatoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IstanzaStatoSlimEntityMapperImpl implements IstanzaStatoSlimEntityMapper {
    @Autowired
    private StatoIstanzaEntityMapper statoIstanzaEntityMapper;

    @Override
    public IstanzaStatoSlimVO mapEntityToVO(MudeRIstanzaStato dto) {
        IstanzaStatoSlimVO vo = null;
        if (dto != null) {
            vo = new IstanzaStatoSlimVO();
            vo.setId(dto.getId());
            vo.setIdIstanza(dto.getIstanza().getId());
            vo.setStatoIstanza(statoIstanzaEntityMapper.mapEntityToVO(dto.getStatoIstanza()));
        }
        return vo;
    }

}