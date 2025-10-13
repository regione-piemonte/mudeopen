/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRSpeciePraticaTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.SpeciePraticaTipoInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRSpeciePraticaTipoInterventoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.SpeciePraticaTipoInterventoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeRSpeciePraticaTipoInterventoSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PageUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.SpeciePraticaTipoInterventoVO;
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
public class SpeciePraticaTipoInterventoServiceImpl implements SpeciePraticaTipoInterventoService {
    private static Logger logger = Logger.getLogger(SpeciePraticaTipoInterventoServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRSpeciePraticaTipoInterventoRepository mudeRSpeciePraticaTipoInterventoRepository;

    @Autowired
    private SpeciePraticaTipoInterventoEntityMapper speciePraticaTipoInterventoEntityMapper;

    @Override
    public List<SpeciePraticaTipoInterventoVO> findBySpeciePratica(String codiceSpeciePratica) {
        List<MudeRSpeciePraticaTipoIntervento> entities = mudeRSpeciePraticaTipoInterventoRepository.findByMudeDSpeciePratica_CodiceAndAbilitatoIsTrueOrderByMudeDTipoIntervento_descrizione(codiceSpeciePratica);
        return speciePraticaTipoInterventoEntityMapper.mapListEntityToListVO(entities);
    }

    @Override
    public List<SpeciePraticaTipoInterventoVO> findByTipoIntervento(String codiceTipoIntervento) {
        List<MudeRSpeciePraticaTipoIntervento> entities = mudeRSpeciePraticaTipoInterventoRepository.findByMudeDTipoIntervento_CodiceAndAbilitatoIsTrue(codiceTipoIntervento);
        return speciePraticaTipoInterventoEntityMapper.mapListEntityToListVO(entities);
    }

    @Override
    public List<SpeciePraticaTipoInterventoVO> search(String filter, int page, int size, String sortExp) {
        Pageable pageble = PageUtil.getPageable(page, size, sortExp);

        Map speciePratica = FilterUtil.getValue(filter, "codiceSpeciePratica");
        Map tipoIntervento = FilterUtil.getValue(filter, "codiceTipoIntervento");

        Specification<MudeRSpeciePraticaTipoIntervento> isAbilitato = MudeRSpeciePraticaTipoInterventoSpecification.isAbilitato();
        Specification<MudeRSpeciePraticaTipoIntervento> hasSpeciePratica = MudeRSpeciePraticaTipoInterventoSpecification.hasSpeciePratica(speciePratica);
        Specification<MudeRSpeciePraticaTipoIntervento> hasTipoIntervento = MudeRSpeciePraticaTipoInterventoSpecification.hasTipoIntervento(tipoIntervento);

        Specification<MudeRSpeciePraticaTipoIntervento> filters = Specifications.where(isAbilitato).and(hasSpeciePratica).and(hasTipoIntervento);

        Page<MudeRSpeciePraticaTipoIntervento> resultPage = mudeRSpeciePraticaTipoInterventoRepository.findAll(filters,pageble);

        return speciePraticaTipoInterventoEntityMapper.mapListEntityToListVO(resultPage.getContent());
    }
}