/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ComuneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.EnteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoEnteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ente.EnteVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnteEntityMapperImpl implements EnteEntityMapper {

    @Autowired
    private ComuneEntityMapper comuneEntityMapper;

    @Autowired
    private TipoEnteEntityMapper tipoEnteEntityMapper;

    @Override
    public EnteVO mapEntityToVO(MudeTEnte dto) {
        if (dto == null)
            return null;
        EnteVO vo = new EnteVO();
        vo.setId(dto.getId());
        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setDescrizioneEstesa(dto.getDescrizioneEstesa());
        vo.setIndirizzoEnte(dto.getIndirizzoEnte());
        vo.setCapEnte(dto.getCapEnte());
        vo.setResponsabileEnte(dto.getResponsabileEnte());
        vo.setPecEnte(dto.getPecEnte());
        ComuneVO comuneVO = comuneEntityMapper.mapEntityToVO(dto.getComune());
        vo.setComune(comuneVO);
        DizionarioVO tipoEnteVO = tipoEnteEntityMapper.mapEntityToVO(dto.getTipoEnte());
        vo.setTipoEnte(tipoEnteVO);

        return vo;
    }

    @Override
    public List<EnteVO> mapListEntityToListVO(List<MudeTEnte> tl) {
        return EnteEntityMapper.super.mapListEntityToListVO(tl);
    }
}