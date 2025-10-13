/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaTipoInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTipoIstanzaTipoInterventoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoIstanzaTipoInterventoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

@Service
public class TipoIstanzaTipoInterventoServiceImpl implements TipoIstanzaTipoInterventoService {
    private static Logger logger = Logger.getLogger(TipoIstanzaTipoInterventoServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRTipoIstanzaTipoInterventoRepository mudeRTipoIstanzaTipoInterventoRepository;

    @Autowired
    private TipoIstanzaTipoInterventoEntityMapper tipoIstanzaTipoInterventoEntityMapper;

    @Override
    public List<DizionarioVO> findByTipoIstanza(List<String> codiceTipoIstanza) {
        List<MudeRTipoIstanzaTipoIntervento> entities = mudeRTipoIstanzaTipoInterventoRepository.findDistinctMudeDTipoIntervento_codiceAndMudeDTipoIstanza_codiceByMudeDTipoIstanza_CodiceInOrderByMudeDTipoIntervento_descrizione(codiceTipoIstanza);
        return tipoIstanzaTipoInterventoEntityMapper.mapListEntityToListVO(entities);
    }

}