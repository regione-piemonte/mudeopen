/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDCategoriaAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaAllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDCategoriaAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.CategoriaAllegatoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.CategoriaAllegatoVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaAllegatoServiceImpl implements CategoriaAllegatoService {
    private static Logger logger = Logger.getLogger(TipoAllegatoServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeDCategoriaAllegatoRepository mudeDCategoriaAllegatoRepository;

    @Autowired
    private CategoriaAllegatoEntityMapper categoriaAllegatoEntityMapper;

    @Override
    public List<CategoriaAllegatoVO> findAllOrderByDescrizione() {
        List<MudeDCategoriaAllegato> list = mudeDCategoriaAllegatoRepository.findAllOrderByDescrizione();
        return categoriaAllegatoEntityMapper.mapListEntityToListVO(list, null);
    }

    @Override
    public CategoriaAllegatoVO findById(String codiceCategoriaAllegato) {
        MudeDCategoriaAllegato item = mudeDCategoriaAllegatoRepository.findOne(codiceCategoriaAllegato);
        if(null != item){
            return categoriaAllegatoEntityMapper.mapEntityToVO(item, null);
        }
        return null;
    }
}