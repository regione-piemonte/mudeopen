/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FunzioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.FunzioneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FunzioneEntityMapperImpl implements FunzioneEntityMapper {

    @Autowired
    private CategoriaQuadroEntityMapper categoriaQuadroEntityMapper;

    @Override
    public FunzioneVO mapEntityToVO(MudeDFunzione dto) {
        if (dto == null)
            return null;

        FunzioneVO vo = new FunzioneVO();
        vo.setId(dto.getId());
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setTipo(dto.getTipo());
        vo.setCategoriaQuadro(categoriaQuadroEntityMapper.mapEntityToVO(dto.getCategoriaQuadro()));
        vo.setPrevistoPM(null != dto.getPrevistoPM()? dto.getPrevistoPM() : Boolean.FALSE);

        return vo;
    }

    @Override
    public List<FunzioneVO> mapListEntityToListVO(List<MudeDFunzione> tl) {
        return FunzioneEntityMapper.super.mapListEntityToListVO(tl);
    }
}