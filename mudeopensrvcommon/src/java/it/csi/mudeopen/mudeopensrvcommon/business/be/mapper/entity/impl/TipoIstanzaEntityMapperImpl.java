/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TipoIstanzaEntityMapperImpl implements TipoIstanzaEntityMapper {

    @Autowired
    private TipoTracciatoEntityMapper tipoTracciatoEntityMapper;

    @Override
    public TipoIstanzaVO mapEntityToVO(MudeDTipoIstanza dto) {
        if (dto == null)
            return null;

        TipoIstanzaVO vo = new TipoIstanzaVO();
        vo.setDescrizione(dto.getDescrizioneEstesa());
        vo.setId(dto.getCodice());
        vo.setLegata(dto.getLegata());
        vo.setAlmenoUnRuolo(dto.getAlmenoUnRuolo());
        vo.setCambioIntestatario(dto.getCambioIntestatario());
        vo.setSoggettiBloccati(dto.getSoggettiBloccati());
        vo.setEscludiBranch(dto.getEscludiBranch());

        return vo;
    }

}