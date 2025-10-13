/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTipoIstanzaTipoTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoTracciatoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TipoTracciatoVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoTracciatoServiceImpl implements TipoTracciatoService {
    private static Logger logger = Logger.getLogger(TipoTracciatoServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeDTipoTracciatoRepository mudeDTipoTracciatoRepository;

    @Autowired
    private MudeRTipoIstanzaTipoTracciatoRepository mudeRTipoIstanzaTipoTracciatoRepository;

    @Autowired
    private TipoTracciatoEntityMapper tipoTracciatoEntityMapper;

    @Override
    public List<TipoTracciatoVO> findByCodice(String codice) {
        List<MudeDTipoTracciato> entities = mudeDTipoTracciatoRepository.findByCodiceAndDataFineIsNull(codice);
        return tipoTracciatoEntityMapper.mapListEntityToListVO(entities);
    }

    @Override
    public TipoTracciatoVO findByCodiceAndVersione(String codice, String versione) {
        MudeDTipoTracciato entity = mudeDTipoTracciatoRepository.findByCodiceAndVersioneAndDataFineIsNull(codice,versione);
        return tipoTracciatoEntityMapper.mapEntityToVO(entity);
    }

//    @Override
//    public List<TipoTracciatoVO> findTipoTracciatoAttivoPerTipoIstanza(String tipoIstanza) {
//        List<MudeRTipoIstanzaTipoTracciato> entities = mudeRTipoIstanzaTipoTracciatoRepository.findByMudeDTipoIstanza_CodiceAndDataFineIsNull(tipoIstanza);
//        List<TipoTracciatoVO> tipoTracciatoList = new ArrayList<>();
//        for (MudeRTipoIstanzaTipoTracciato entity : entities) {
//            TipoTracciatoVO vo = tipoTracciatoEntityMapper.mapEntityToVO(entity.getMudeDTipoTracciato());
//            tipoTracciatoList.add(vo);
//        }
//        return tipoTracciatoList;
//    }
}