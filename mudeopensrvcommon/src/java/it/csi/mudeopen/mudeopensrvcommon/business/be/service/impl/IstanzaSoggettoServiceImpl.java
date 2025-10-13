/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaSoggettoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IstanzaSoggettoServiceImpl implements IstanzaSoggettoService {
    private static Logger logger = Logger.getLogger(IstanzaServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRIstanzaSoggettoRepository mudeRIstanzaSoggettoRepository;

    @Autowired
    private MudeDRuoloSoggettoRepository mudeDRuoloSoggettoRepository;

    @Override
    public Long countBySoggettoAndStatoIstanza(Long id, StatoIstanza statoIstanza) {
        return mudeRIstanzaSoggettoRepository.countBySoggettoAndStatoIstanza(id, statoIstanza.getValue());
    }

    @Override
    public Long countBySoggetto(Long id) {
        return mudeRIstanzaSoggettoRepository.countBySoggetto(id);
    }

    @Override
    public Boolean isSoggettoInIStanzaConRuolo(Long idIstanza, Long idSoggetto, String categoriaRuolo) {
        List<String> ruoliSoggetto = mudeRIstanzaSoggettoRepository.findRuoliByCategoriaAndSoggettoAndIstanza(categoriaRuolo, idSoggetto, idIstanza);
        if(!ruoliSoggetto.isEmpty()) return Boolean.TRUE;
        return Boolean.FALSE;
    }
}