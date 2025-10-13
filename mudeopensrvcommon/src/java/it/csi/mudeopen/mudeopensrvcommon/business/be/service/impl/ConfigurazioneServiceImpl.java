/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ConfigurazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ConfigurazioneService;
import it.csi.mudeopen.mudeopensrvcommon.vo.configurazione.ConfigurazioneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurazioneServiceImpl implements ConfigurazioneService {

    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    private ConfigurazioneEntityMapper configurazioneEntityMapper;

    @Override
    public ConfigurazioneVO findConfigurazioneByName(String name) {
        Optional<MudeCProprieta> configOPT = mudeCProprietaRepository.findByName(name);
        ConfigurazioneVO vo = null;
        if (configOPT.isPresent()) {
            MudeCProprieta config = configOPT.get();
            vo = new ConfigurazioneVO();
            vo.setId(config.getId());
            vo.setNome(config.getNome());
            vo.setValore(config.getValore());
            vo.setNote(config.getNote());
        }
        return vo;
    }
}