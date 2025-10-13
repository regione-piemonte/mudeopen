/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaTracciatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaTracciatoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.IstanzaTracciatoVO;

@Service
public class IstanzaTracciatoServiceImpl implements IstanzaTracciatoService {
    @Autowired
    private MudeRIstanzaTracciatoRepository mudeRIstanzaTracciatoRepository;

    @Autowired
    private IstanzaTracciatoEntityMapper istanzaTracciatoEntityMapper;

    @Override
    public List<IstanzaTracciatoVO> findTracciatiPerIstanza(Long idIstanza, MudeTUser user) {
        List<MudeRIstanzaTracciato> entities = mudeRIstanzaTracciatoRepository.findAllByDataFineNullAndMudeTIstanza_Id(idIstanza);
        return istanzaTracciatoEntityMapper.mapListEntityToListVO(entities, user);
    }

    @Override
    public IstanzaTracciatoVO findIstanzaTracciato(Long idIstanza, Long idTipoTracciato, MudeTUser user) {
        MudeRIstanzaTracciato entity = mudeRIstanzaTracciatoRepository.findByIstanzaAndTipoTracciato(idIstanza, idTipoTracciato);
        return istanzaTracciatoEntityMapper.mapEntityToVO(entity, user);
    }

    @Override
    public IstanzaTracciatoVO saveIstanzaTracciato(Long idIstanza, Long idTipoTracciato, MudeTUser user) {
        return null;
    }

    @Override
    public void deleteIstanzaTracciato(Long idIStanzaTracciato) {
        MudeRIstanzaTracciato entity = mudeRIstanzaTracciatoRepository.findOne(idIStanzaTracciato);
        if(null != entity){
            entity.setDataFine(Calendar.getInstance().getTime());
            mudeRIstanzaTracciatoRepository.saveDAO(entity);
        }
    }
}