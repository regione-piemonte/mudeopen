/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoOpera;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaTipoOpera;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaTipoOperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoOperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoOperaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTipoIstanzaTipoOperaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoIstanzaTipoOperaService;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaTipoOperaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoIstanzaTipoOperaServiceImpl implements TipoIstanzaTipoOperaService {
    private static Logger logger = Logger.getLogger(TipoIstanzaTipoOperaServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRTipoIstanzaTipoOperaRepository mudeRTipoIstanzaTipoOperaRepository;

    @Autowired
    private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;

    @Autowired
    private MudeDTipoOperaRepository mudeDTipoOperaRepository;

    @Autowired
    private TipoIstanzaTipoOperaEntityMapper tipoIstanzaTipoOperaEntityMapper;

    @Autowired
    private TipoOperaEntityMapper tipoOperaEntityMapper;

    @Autowired
    private TipoIstanzaEntityMapper tipoIstanzaEntityMapper;

    @Override
    public List<TipoIstanzaTipoOperaVO> findByTipoIstanza(String codiceTipoIstanza) {
        List<TipoIstanzaTipoOperaVO> voList = new ArrayList<>();
        List<MudeRTipoIstanzaTipoOpera> entities = mudeRTipoIstanzaTipoOperaRepository.findByMudeDTipoIstanza_CodiceAndAbilitatoIsTrueOrderByMudeDTipoOpera_descrizione(codiceTipoIstanza);
        if (!entities.isEmpty()) {
            voList = tipoIstanzaTipoOperaEntityMapper.mapListEntityToListVO(entities);
        } else {
            MudeDTipoIstanza mudeDTipoIstanza = mudeDTipoIstanzaRepository.findOne(codiceTipoIstanza);
            TipoIstanzaVO tipoIstanzaVO = tipoIstanzaEntityMapper.mapEntityToVO(mudeDTipoIstanza);
            List<String> codiciTipoOpera = mudeRTipoIstanzaTipoOperaRepository.findByTipoIstanzaViaSpeciePratica(codiceTipoIstanza);
            List<MudeDTipoOpera> mudeDTipoOperaList = mudeDTipoOperaRepository.findByCodiceInOrderByDescrizioneEstesa(codiciTipoOpera);
            for (MudeDTipoOpera mudeDTipoOpera : mudeDTipoOperaList) {
                TipoIstanzaTipoOperaVO vo = new TipoIstanzaTipoOperaVO();
                vo.setDiretta(Boolean.FALSE);
                vo.setTipoIstanza(tipoIstanzaVO);
                vo.setTipoOpera(tipoOperaEntityMapper.mapEntityToVO(mudeDTipoOpera));
                voList.add(vo);
            }
        }
        return voList;
    }

    @Override
    public List<TipoIstanzaTipoOperaVO> findByTipoOpera(String codiceTipoOpera) {
        List<TipoIstanzaTipoOperaVO> voList = new ArrayList<>();
        List<MudeRTipoIstanzaTipoOpera> entities = mudeRTipoIstanzaTipoOperaRepository.findByMudeDTipoOpera_CodiceAndAbilitatoIsTrue(codiceTipoOpera);
        if (!entities.isEmpty()) {
            voList = tipoIstanzaTipoOperaEntityMapper.mapListEntityToListVO(entities);
        } else {
            MudeDTipoOpera mudeDTipoOpera = mudeDTipoOperaRepository.findOne(codiceTipoOpera);
            DizionarioVO tipoOperaVO = tipoOperaEntityMapper.mapEntityToVO(mudeDTipoOpera);
            List<String> codiciTipoIstanza = mudeRTipoIstanzaTipoOperaRepository.findByTipoOperaViaSpeciePratica(codiceTipoOpera);
            List<MudeDTipoIstanza> mudeDTipoIstanzaList = mudeDTipoIstanzaRepository.findByCodiceInOrderByDescrizioneEstesa(codiciTipoIstanza);
            for (MudeDTipoIstanza mudeDTipoIstanza : mudeDTipoIstanzaList) {
                TipoIstanzaTipoOperaVO vo = new TipoIstanzaTipoOperaVO();
                vo.setDiretta(Boolean.FALSE);
                vo.setTipoIstanza(tipoIstanzaEntityMapper.mapEntityToVO(mudeDTipoIstanza));
                vo.setTipoOpera(tipoOperaVO);
                voList.add(vo);
            }
        }
        return voList;
    }

}