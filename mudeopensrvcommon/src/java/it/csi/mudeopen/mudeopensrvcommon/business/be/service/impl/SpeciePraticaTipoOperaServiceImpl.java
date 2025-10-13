/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRSpeciePraticaTipoOpera;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SpeciePraticaTipoOperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRSpeciePraticaTipoOperaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.SpeciePraticaTipoOperaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeRSpeciePraticaTipoOperaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PageUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.SpeciePraticaTipoOperaVO;
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
public class SpeciePraticaTipoOperaServiceImpl implements SpeciePraticaTipoOperaService {
    private static Logger logger = Logger.getLogger(SpeciePraticaTipoOperaServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRSpeciePraticaTipoOperaRepository mudeRSpeciePraticaTipoOperaRepository;

    @Autowired
    private SpeciePraticaTipoOperaEntityMapper speciePraticaTipoOperaEntityMapper;

    @Override
    public List<SpeciePraticaTipoOperaVO> findByTipoOpera(String codiceTipoOpera) {
        List<MudeRSpeciePraticaTipoOpera> entities = mudeRSpeciePraticaTipoOperaRepository.findByMudeDTipoOpera_CodiceAndAbilitatoIsTrue(codiceTipoOpera);
        return speciePraticaTipoOperaEntityMapper.mapListEntityToListVO(entities);
    }

    @Override
    public List<SpeciePraticaTipoOperaVO> findBySpeciePratica(String codiceSpeciePratica) {
        List<MudeRSpeciePraticaTipoOpera> entities = mudeRSpeciePraticaTipoOperaRepository.findByMudeDSpeciePratica_CodiceAndAbilitatoIsTrueOrderByMudeDTipoOpera_descrizione(codiceSpeciePratica);
        return speciePraticaTipoOperaEntityMapper.mapListEntityToListVO(entities);
    }

    @Override
    public List<SpeciePraticaTipoOperaVO> search(String filter, int page, int size, String sortExp) {
        Pageable pageble = PageUtil.getPageable(page, size, sortExp);

        Map speciePratica = FilterUtil.getValue(filter, "codiceSpeciePratica");
        Map tipoOpera = FilterUtil.getValue(filter, "codiceTipoOpera");

        Specification<MudeRSpeciePraticaTipoOpera> isAbilitato = MudeRSpeciePraticaTipoOperaSpecification.isAbilitato();
        Specification<MudeRSpeciePraticaTipoOpera> hasSpeciePratica = MudeRSpeciePraticaTipoOperaSpecification.hasSpeciePratica(speciePratica);
        Specification<MudeRSpeciePraticaTipoOpera> hasTipoOpera = MudeRSpeciePraticaTipoOperaSpecification.hasTipoOpera(tipoOpera);

        Specification<MudeRSpeciePraticaTipoOpera> filters = Specifications.where(isAbilitato).and(hasSpeciePratica).and(hasTipoOpera);

        Page<MudeRSpeciePraticaTipoOpera> resultPage = mudeRSpeciePraticaTipoOperaRepository.findAll(filters,pageble);

        return speciePraticaTipoOperaEntityMapper.mapListEntityToListVO(resultPage.getContent());
    }
}