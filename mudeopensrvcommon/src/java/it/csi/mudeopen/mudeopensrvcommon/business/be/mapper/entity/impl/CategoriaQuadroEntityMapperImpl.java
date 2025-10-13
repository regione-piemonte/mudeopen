/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDCategoriaQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.CategoriaQuadroVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoriaQuadroEntityMapperImpl implements CategoriaQuadroEntityMapper {
    @Override
    public CategoriaQuadroVO mapEntityToVO(MudeDCategoriaQuadro dto) {
        if (dto == null)
            return null;

        CategoriaQuadroVO vo = new CategoriaQuadroVO();
        vo.setId(dto.getId());
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());

        return vo;
    }

    @Override
    public List<CategoriaQuadroVO> mapListEntityToListVO(List<MudeDCategoriaQuadro> tl) {
        return CategoriaQuadroEntityMapper.super.mapListEntityToListVO(tl);
    }
}