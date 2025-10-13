/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ConfigurazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.configurazione.ConfigurazioneVO;
import org.springframework.stereotype.Component;

@Component
public class ConfigurazioneEntityMapperImpl implements ConfigurazioneEntityMapper {
    @Override
    public ConfigurazioneVO mapEntityToVO(MudeCProprieta dto) {
        if(null == dto){
        return null;
        }
        ConfigurazioneVO vo = new ConfigurazioneVO();
        vo.setId(dto.getId());
        vo.setNome(dto.getNome());
        vo.setValore(dto.getValore());
        vo.setNote(dto.getNote());

        return vo;
    }
}