/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTitolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TitoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TitoloEntityMapperImpl implements TitoloEntityMapper {
    @Override
    public DizionarioVO mapEntityToVO(MudeDTitolo dto) {
        if (dto == null)
            return null;
        DizionarioVO vo = new DizionarioVO();
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setDescrizioneEstesa(dto.getDescrizioneEstesa());
        return vo;
    }

    @Override
    public List<DizionarioVO> mapListEntityToListVO(List<MudeDTitolo> tl) {
        return TitoloEntityMapper.super.mapListEntityToListVO(tl);
    }
}