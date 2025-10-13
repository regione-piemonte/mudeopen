/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoEnteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TipoEnteEntityMapperImpl implements TipoEnteEntityMapper {

    @Override
    public DizionarioVO mapEntityToVO(MudeDTipoEnte dto) {
        if (dto == null)
            return null;
        DizionarioVO vo = new DizionarioVO();
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setDescrizioneEstesa(dto.getDescrizioneEstesa());
        return vo;
    }

    @Override
    public List<DizionarioVO> mapListEntityToListVO(List<MudeDTipoEnte> tl) {
        return TipoEnteEntityMapper.super.mapListEntityToListVO(tl);
    }
}