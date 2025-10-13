/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDElementoElenco;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ElementoElencoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDElementoElencoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ElementoEslencoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

@Service
public class ElementoElenecoServiceImpl implements ElementoEslencoService {
    private static Logger logger = Logger.getLogger(ElementoElenecoServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeDElementoElencoRepository mudeDElementoElencoRepository;

    @Autowired
    private ElementoElencoEntityMapper elementoElencoEntityMapper;

    @Override
    public List<DizionarioVO> findByTipoElenco(List<Number> codiceTipoIstanza) {
        List<MudeDElementoElenco> entities = mudeDElementoElencoRepository.findByIdTipoElencoInAndDataFineIsNullOrderByOrdinamento(codiceTipoIstanza.stream().map(x -> { 
        	return Long.valueOf(x.longValue()); 
        } ).collect(Collectors.toList()));
        return elementoElencoEntityMapper.mapListEntityToListVO(entities);
    }

}