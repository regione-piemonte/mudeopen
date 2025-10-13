/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRSpeciePraticaTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SpeciePraticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SpeciePraticaTipoInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.SpeciePraticaTipoInterventoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpeciePraticaTipoInterventoEntityMapperImpl implements SpeciePraticaTipoInterventoEntityMapper {

    @Autowired
    private SpeciePraticaEntityMapper speciePraticaEntityMapper;

    @Autowired
    private TipoInterventoEntityMapper tipoInterventoEntityMapper;

    @Override
    public SpeciePraticaTipoInterventoVO mapEntityToVO(MudeRSpeciePraticaTipoIntervento dto) {
        if (dto == null) {
            return null;
        }

        SpeciePraticaTipoInterventoVO vo = new SpeciePraticaTipoInterventoVO();
        vo.setId(dto.getId());
        vo.setAbilitato(dto.getAbilitato());
        DizionarioVO speciePraticaVO = speciePraticaEntityMapper.mapEntityToVO(dto.getMudeDSpeciePratica());
        vo.setSpeciePratica(speciePraticaVO);
        DizionarioVO tipoInterventoVO = tipoInterventoEntityMapper.mapEntityToVO(dto.getMudeDTipoIntervento());
        vo.setTipoIntervento(tipoInterventoVO);

        return vo;
    }
}