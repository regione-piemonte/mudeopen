/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaTipoInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

@Component
public class TipoIstanzaTipoInterventoEntityMapperImpl implements TipoIstanzaTipoInterventoEntityMapper {

    /**
     * Map entity to vo vo type.
     *
     * @param dto the dto
     * @return the vo type
     */
    @Override
    public DizionarioVO mapEntityToVO(MudeRTipoIstanzaTipoIntervento dto) {
        DizionarioVO vo = new DizionarioVO();

        vo.setCodice(dto.getMudeDTipoIntervento().getCodice());
        vo.setDescrizione(dto.getMudeDTipoIntervento().getDescrizione());
        vo.setDescrizioneEstesa(dto.getMudeDTipoIntervento().getDescrizioneEstesa());

        return vo;
    }
}