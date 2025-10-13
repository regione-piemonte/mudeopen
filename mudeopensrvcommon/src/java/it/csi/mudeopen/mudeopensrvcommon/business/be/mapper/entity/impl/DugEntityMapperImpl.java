/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.DugEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DugVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DugEntityMapperImpl implements DugEntityMapper {

    @Override
    public DugVO mapEntityToVO(MudeDDug dto) {
        if (dto == null) {
            return null;
        }
        DugVO vo = new DugVO();
        vo.setIdDug(dto.getIdDug());
        vo.setDenominazione(dto.getDenominazione());

        return vo;
    }

    @Override
    public List<DugVO> mapListEntityToListVO(List<MudeDDug> tl) {
        return DugEntityMapper.super.mapListEntityToListVO(tl);
    }
}