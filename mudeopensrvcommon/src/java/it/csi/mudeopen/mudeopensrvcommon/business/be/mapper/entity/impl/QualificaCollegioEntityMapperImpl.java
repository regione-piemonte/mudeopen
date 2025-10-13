/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQualificaCollegio;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.QualificaCollegioEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

@Component
public class QualificaCollegioEntityMapperImpl implements QualificaCollegioEntityMapper {
    @Override
    public DizionarioVO mapEntityToVO(MudeDQualificaCollegio dto) {
        if (dto == null)
            return null;
        DizionarioVO vo = new DizionarioVO();
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setDescrizioneEstesa(dto.getDescrizioneEstesa());
        return vo;
    }

    @Override
    public List<DizionarioVO> mapListEntityToListVO(List<MudeDQualificaCollegio> tl) {
        return QualificaCollegioEntityMapper.super.mapListEntityToListVO(tl);
    }
}