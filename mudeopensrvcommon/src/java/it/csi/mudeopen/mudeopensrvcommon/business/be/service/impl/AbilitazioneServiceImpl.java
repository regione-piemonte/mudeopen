/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AbilitazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDAbilitazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AbilitazioneService;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbilitazioneServiceImpl implements AbilitazioneService {

    @Autowired
    private MudeDAbilitazioneRepository mudeDAbilitazioneRepository;

    @Autowired
    private AbilitazioneEntityMapper abilitazioneEntityMapper;

    @Override
    public AbilitazioneVO findById(Long id) {
        MudeDAbilitazione entity = mudeDAbilitazioneRepository.findOne(id);

        return abilitazioneEntityMapper.mapEntityToVO(entity);
    }
}