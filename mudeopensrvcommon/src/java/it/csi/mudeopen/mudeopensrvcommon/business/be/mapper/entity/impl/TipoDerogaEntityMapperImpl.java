/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDeroga;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoDerogaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TipoDerogaEntityMapperImpl implements TipoDerogaEntityMapper {
    @Override
    public DizionarioVO mapEntityToVO(MudeDTipoDeroga dto) {
        if (dto == null)
            return null;
        DizionarioVO vo = new DizionarioVO();
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setDescrizioneEstesa(dto.getDescrizioneEstesa());
        return vo;
    }

    @Override
    public List<DizionarioVO> mapListEntityToListVO(List<MudeDTipoDeroga> tl) {
        return TipoDerogaEntityMapper.super.mapListEntityToListVO(tl);
    }
}