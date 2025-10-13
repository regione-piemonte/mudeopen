/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.CsiLogAudit;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIntestatario;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloIntestatarioEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloIntestatarioSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloIntestatarioRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloIntestatarioService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtilsTraceCsiLogAuditService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeRFascicoloIntestatarioSpecification;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloIntestatarioSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloIntestatarioVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class FascicoloIntestatarioServiceImpl implements FascicoloIntestatarioService {
    private static Logger logger = Logger.getLogger(FascicoloIntestatarioServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRFascicoloIntestatarioRepository mudeRFascicoloIntestatarioRepository;

    @Autowired
    private MudeTFascicoloRepository mudeTFascicoloRepository;

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private FascicoloIntestatarioEntityMapper fascicoloIntestatarioEntityMapper;

    @Autowired
    private FascicoloIntestatarioSlimEntityMapper fascicoloIntestatarioSlimEntityMapper;

    @Autowired
    private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;

    @Override
    public List<FascicoloIntestatarioVO> findAllFascicoliByIntestatario(Long idIntestatario) {
        Specification<MudeRFascicoloIntestatario> isNotDeleted = MudeRFascicoloIntestatarioSpecification.isNotDeleted();
        Specification<MudeRFascicoloIntestatario> intestatarioEquals = MudeRFascicoloIntestatarioSpecification.hasIntestatarioEquals(idIntestatario);
        Specification<MudeRFascicoloIntestatario> filters = Specifications.where(isNotDeleted).and(intestatarioEquals);
        List<MudeRFascicoloIntestatario> list = mudeRFascicoloIntestatarioRepository.findAll(filters);
        return fascicoloIntestatarioEntityMapper.mapListEntityToListVO(list);
    }

    @Override
    public List<FascicoloIntestatarioVO> findAllByFascicolo(Long idFascicolo) {
        //        Specification<MudeRFascicoloIntestatario> isNotDeleted = MudeRFascicoloIntestatarioSpecification.isNotDeleted();
        Specification<MudeRFascicoloIntestatario> fascicoloEquals = MudeRFascicoloIntestatarioSpecification.hasFascicoloEquals(idFascicolo);
        Specification<MudeRFascicoloIntestatario> filters = Specifications.where(fascicoloEquals);
        List<MudeRFascicoloIntestatario> list = mudeRFascicoloIntestatarioRepository.findAll(filters);
        return fascicoloIntestatarioEntityMapper.mapListEntityToListVO(list);
    }

    @Override
    public FascicoloIntestatarioVO findActiveByFascicolo(Long idFascicolo) {
        MudeRFascicoloIntestatario fascicoloIntestatario = mudeRFascicoloIntestatarioRepository.findActiveByFascicolo(idFascicolo);
        if(fascicoloIntestatario == null)
        	return null;

        return fascicoloIntestatarioEntityMapper.mapEntityToVO(fascicoloIntestatario);
    }

    @Override
    public FascicoloIntestatarioSlimVO findActiveByFascicoloSlim(Long idFascicolo) {
        MudeRFascicoloIntestatario fascicoloIntestatario = mudeRFascicoloIntestatarioRepository.findActiveByFascicolo(idFascicolo);
        return fascicoloIntestatarioSlimEntityMapper.mapEntityToVO(fascicoloIntestatario);
    }

    @Override
    public void saveFascicoloIntestatario(MudeTIstanza istanza, Long idIntestatario, MudeTUser mudeTUser, boolean isFirstIstance) {
        MudeTFascicolo fascicolo = istanza.getMudeTFascicolo();
        MudeTContatto contatto = mudeTContattoRepository.findOne(idIntestatario);

        MudeRFascicoloIntestatario fascicoloIntestatario = mudeRFascicoloIntestatarioRepository.findActiveByFascicolo(istanza.getMudeTFascicolo().getId());
        if(fascicoloIntestatario != null) {
        	if(idIntestatario.longValue() == fascicoloIntestatario.getIntestatario().getId().longValue()
        			|| (!istanza.getTipoIstanza().getCambioIntestatario() && !isFirstIstance))
        		return;

        	if(isFirstIstance)
                mudeRFascicoloIntestatarioRepository.delete(fascicoloIntestatario);
        	else {
                fascicoloIntestatario.setDataFine(Calendar.getInstance().getTime());
                mudeRFascicoloIntestatarioRepository.saveDAO(fascicoloIntestatario);
        	}
        }

        MudeRFascicoloIntestatario entity = new MudeRFascicoloIntestatario();
        entity.setFascicolo(fascicolo);
        entity.setIntestatario(contatto);
        entity.setDataInizio(Calendar.getInstance().getTime());
        mudeRFascicoloIntestatarioRepository.saveDAO(entity);

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.ASSOCIA_INTESTATARIO_FASCICOLO.getOperation(), fascicolo.getTableName(), "idFascicolo=" + fascicolo.getId() + ", idIstanza=" + istanza.getId() + ", idContatto=" + idIntestatario, mudeTUser == null ? "WS" : mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Override
    public void disableActiveIntestatarioFascicolo(long idFascicolo) {
        MudeRFascicoloIntestatario fascicoloIntestatario = mudeRFascicoloIntestatarioRepository.findActiveByFascicolo(idFascicolo);
        fascicoloIntestatario.setDataFine(Calendar.getInstance().getTime());
        mudeRFascicoloIntestatarioRepository.saveDAO(fascicoloIntestatario);
    }
}