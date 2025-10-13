/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaSpeciePratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaSpeciePraticaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTipoIstanzaSpeciePraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoIstanzaSpeciePraticaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeRTipoIstanzaSpeciePraticaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PageUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaSpeciePraticaVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TipoIstanzaSpeciePraticaServiceImpl implements TipoIstanzaSpeciePraticaService {

    private static Logger logger = Logger.getLogger(TipoIstanzaSpeciePraticaServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRTipoIstanzaSpeciePraticaRepository mudeRTipoIstanzaSpeciePraticaRepository;

    @Autowired
    private TipoIstanzaSpeciePraticaEntityMapper tipoIstanzaSpeciePraticaEntityMapper;

    @Override
    public List<TipoIstanzaSpeciePraticaVO> findByTipoIstanza(String codiceTipoIstanza) {
        List<MudeRTipoIstanzaSpeciePratica> entities = mudeRTipoIstanzaSpeciePraticaRepository.findByCodiceTipoIstanza(codiceTipoIstanza);
        return tipoIstanzaSpeciePraticaEntityMapper.mapListEntityToListVO(entities);
    }

    @Override
    public List<TipoIstanzaSpeciePraticaVO> findBySpeciePratica(String codiceSpeciePratica) {
        List<MudeRTipoIstanzaSpeciePratica> entities = mudeRTipoIstanzaSpeciePraticaRepository.findByCodiceSpeciePratica(codiceSpeciePratica);
        return tipoIstanzaSpeciePraticaEntityMapper.mapListEntityToListVO(entities);
    }

    @Override
    public List<TipoIstanzaSpeciePraticaVO> search(String filter, int page, int size, String sortExp) {

        Pageable pageble = PageUtil.getPageable(page, size, sortExp);

        Map speciePratica = FilterUtil.getValue(filter, "codiceSpeciePratica");
        Map tipoIstanza = FilterUtil.getValue(filter, "codiceTipoIstanza");

        Specification<MudeRTipoIstanzaSpeciePratica> isAbilitato = MudeRTipoIstanzaSpeciePraticaSpecification.isAbilitato();
        Specification<MudeRTipoIstanzaSpeciePratica> hasSpeciePratica = MudeRTipoIstanzaSpeciePraticaSpecification.hasSpeciePratica(speciePratica);
        Specification<MudeRTipoIstanzaSpeciePratica> hasTipoIstanza = MudeRTipoIstanzaSpeciePraticaSpecification.hasTipoIstanza(tipoIstanza);

        Specification<MudeRTipoIstanzaSpeciePratica> filters = Specifications.where(isAbilitato).and(hasSpeciePratica).and(hasTipoIstanza);

        Page<MudeRTipoIstanzaSpeciePratica> resultPage = mudeRTipoIstanzaSpeciePraticaRepository.findAll(filters,pageble);

        return tipoIstanzaSpeciePraticaEntityMapper.mapListEntityToListVO(resultPage.getContent());
    }
}