/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRSpeciePraticaTipoOpera;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SpeciePraticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SpeciePraticaTipoOperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoOperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.SpeciePraticaTipoOperaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpeciePraticaTipoOperaEntityMapperImpl implements SpeciePraticaTipoOperaEntityMapper {

    @Autowired
    private SpeciePraticaEntityMapper speciePraticaEntityMapper;

    @Autowired
    private TipoOperaEntityMapper tipoOperaEntityMapper;

    @Override
    public SpeciePraticaTipoOperaVO mapEntityToVO(MudeRSpeciePraticaTipoOpera dto) {
        if(null == dto) {
            return null;
        }
        SpeciePraticaTipoOperaVO vo = new SpeciePraticaTipoOperaVO();
        vo.setId(dto.getId());
        vo.setAbilitato(dto.getAbilitato());
        DizionarioVO speciePraticaVO = speciePraticaEntityMapper.mapEntityToVO(dto.getMudeDSpeciePratica());
        vo.setSpeciePratica(speciePraticaVO);
        DizionarioVO tipoOperaVO = tipoOperaEntityMapper.mapEntityToVO(dto.getMudeDTipoOpera());
        vo.setTipoOpera(tipoOperaVO);

        return vo;
    }
}