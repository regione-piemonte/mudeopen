/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloStatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloStatoSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeRFascicoloStatoSpecification;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloStatoSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloStatoVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class FascicoloStatoServiceImpl implements FascicoloStatoService {
    private static Logger logger = Logger.getLogger(FascicoloStatoServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRFascicoloStatoRepository mudeRFascicoloStatoRepository;

    @Autowired
    private MudeTFascicoloRepository mudeTFascicoloRepository;

    @Autowired
    private MudeDStatoFascicoloRepository mudeDStatoFascicoloRepository;

    @Autowired
    private FascicoloStatoEntityMapper fascicoloStatoEntityMapper;

    @Autowired
    private FascicoloStatoSlimEntityMapper fascicoloStatoSlimEntityMapper;

//    @Autowired
//    private FascicoloEntityMapper fascicoloEntityMapper;
//
//    @Autowired
//    private StatoFascicoloEntityMapper statoFascicoloEntityMapper;

    @Override
    public List<FascicoloStatoVO> findAllFascicoliByStato(String codiceStatoFascicolo) {
        Specification<MudeRFascicoloStato> isNotDeleted = MudeRFascicoloStatoSpecification.isNotDeleted();
        Specification<MudeRFascicoloStato> statoEquals = MudeRFascicoloStatoSpecification.hasStatoEquals(codiceStatoFascicolo);
        Specification<MudeRFascicoloStato> filters = Specifications.where(isNotDeleted).and(statoEquals);

        List<MudeRFascicoloStato> list = mudeRFascicoloStatoRepository.findAll(filters);
        return fascicoloStatoEntityMapper.mapListEntityToListVO(list);
    }

    @Override
    public FascicoloStatoVO findStatoByFascicolo(Long idFascicolo) {
        MudeRFascicoloStato entity = mudeRFascicoloStatoRepository.findStatoByFascicolo(idFascicolo);
        return fascicoloStatoEntityMapper.mapEntityToVO(entity);
    }

    @Override
    public FascicoloStatoSlimVO findStatoByFascicoloSlim(Long idFascicolo) {
        MudeRFascicoloStato entity = mudeRFascicoloStatoRepository.findStatoByFascicolo(idFascicolo);
        return fascicoloStatoSlimEntityMapper.mapEntityToVO(entity);
    }

    @Override
    public void saveFascicoloStato(Long idFascicolo, String codiceStatoFascicolo) {
        MudeTFascicolo fascicolo = mudeTFascicoloRepository.findOne(idFascicolo);
        MudeDStatoFascicolo statoFascicolo = mudeDStatoFascicoloRepository.findOne(codiceStatoFascicolo);

        MudeRFascicoloStato fascicoloStato = mudeRFascicoloStatoRepository.findStatoByFascicolo(idFascicolo);
        boolean createNew = false;
        if(null == fascicoloStato){
            createNew = true;
        }
        else if (!codiceStatoFascicolo.equalsIgnoreCase(fascicoloStato.getStatoFascicolo().getCodice())) {
            fascicoloStato.setDataFine(Calendar.getInstance().getTime());
            mudeRFascicoloStatoRepository.saveDAO(fascicoloStato);
            createNew = true;
        }

        if(createNew){
            MudeRFascicoloStato fascicoloStatoNew = new MudeRFascicoloStato();
            fascicoloStatoNew.setFascicolo(fascicolo);
            fascicoloStatoNew.setStatoFascicolo(statoFascicolo);
            fascicoloStatoNew.setDataInizio(Calendar.getInstance().getTime());
            mudeRFascicoloStatoRepository.saveDAO(fascicoloStatoNew);
        }
    }
}