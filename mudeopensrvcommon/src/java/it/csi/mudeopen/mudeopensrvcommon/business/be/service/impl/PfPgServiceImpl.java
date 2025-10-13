/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRPfPg;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.PfPgEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRPfPgRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.PfPgService;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.PfPgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PfPgServiceImpl implements PfPgService {

    @Autowired
    private MudeRPfPgRepository mudeRPfPgRepository;

    @Autowired
    private PfPgEntityMapper pfPgEntityMapper;

    @Override
    public PfPgVO findByPersonaGiuridica(ContattoVO pgvo) {
        PfPgVO vo = null;
        Optional<MudeRPfPg> dtoOpt = mudeRPfPgRepository.retrieveLawPresenter(pgvo.getId());
        if (dtoOpt.isPresent()) {
            vo = pfPgEntityMapper.mapEntityToVO(dtoOpt.get(), null);
        }
        return vo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PfPgVO save(PfPgVO vo, MudeTUser user) {
        MudeRPfPg dto = pfPgEntityMapper.mapVOtoEntity(vo, user);
        mudeRPfPgRepository.saveDAO(dto);
        PfPgVO pfPgVO = pfPgEntityMapper.mapEntityToVO(dto, null);
        return pfPgVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByPG(Long idPG) {
        mudeRPfPgRepository.deleteByPG(idPG);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void savePfPg(Long idPF, Long idPG, String id_titolo) {
        mudeRPfPgRepository.savePfPg(idPF, idPG, id_titolo);
    }
}