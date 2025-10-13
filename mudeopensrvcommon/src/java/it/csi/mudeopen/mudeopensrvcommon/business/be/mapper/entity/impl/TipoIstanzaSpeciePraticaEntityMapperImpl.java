/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaSpeciePratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SpeciePraticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaSpeciePraticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaSpeciePraticaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TipoIstanzaSpeciePraticaEntityMapperImpl implements TipoIstanzaSpeciePraticaEntityMapper {

    @Autowired
    private TipoIstanzaEntityMapper tipoIstanzaEntityMapper;

    @Autowired
    private SpeciePraticaEntityMapper speciePraticaEntityMapper;

    @Override
    public TipoIstanzaSpeciePraticaVO mapEntityToVO(MudeRTipoIstanzaSpeciePratica dto) {
        if(null == dto) {
            return null;
        }
        TipoIstanzaSpeciePraticaVO vo = new TipoIstanzaSpeciePraticaVO();
        vo.setId(dto.getId());
        vo.setAbilitato(dto.getAbilitato());
        DizionarioVO speciePraticaVO = speciePraticaEntityMapper.mapEntityToVO(dto.getMudeDSpeciePratica());
        vo.setSpeciePratica(speciePraticaVO);
        TipoIstanzaVO tipoIstanzaRegioneVO = tipoIstanzaEntityMapper.mapEntityToVO(dto.getMudeDTipoIstanza());
        vo.setTipoIstanzaVO(tipoIstanzaRegioneVO);

        return vo;
    }
}