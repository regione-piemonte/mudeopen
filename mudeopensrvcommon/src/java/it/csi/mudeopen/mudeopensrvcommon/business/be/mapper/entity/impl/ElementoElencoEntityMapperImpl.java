/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDElementoElenco;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ElementoElencoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

@Component
public class ElementoElencoEntityMapperImpl implements ElementoElencoEntityMapper {

    /**
     * Map entity to vo vo type.
     *
     * @param dto the dto
     * @return the vo type
     */
    @Override
    public DizionarioVO mapEntityToVO(MudeDElementoElenco dto) {
        DizionarioVO vo = new DizionarioVO();

        vo.setCodice(dto.getCodice());
        vo.setDescrizione(dto.getDescrizione());
        vo.setDescrizioneEstesa(dto.getDescrizioneEstesa());

        return vo;
    }
}