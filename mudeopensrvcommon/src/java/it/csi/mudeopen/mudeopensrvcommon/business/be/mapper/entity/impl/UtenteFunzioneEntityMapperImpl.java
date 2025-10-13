/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRRuoloFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteFunzioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDFunzioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FunzioneUtenteVO;
@Component
public class UtenteFunzioneEntityMapperImpl implements UtenteFunzioneEntityMapper {
	
	@Autowired
	private MudeDFunzioneRepository mudeDFunzioneRepository;
	
	@Override
    public List<FunzioneUtenteVO> mapListEntityToListVO(List<MudeRRuoloFunzione> tl) {
        return UtenteFunzioneEntityMapper.super.mapListEntityToListVO(tl);
    }

	@Override
	public FunzioneUtenteVO mapEntityToVO(MudeRRuoloFunzione dto) {
        return mapEntityToSlimVO(dto);
    }
	
	public FunzioneUtenteVO mapEntityToSlimVO(MudeRRuoloFunzione dto) {
        if(null == dto)
            return null;

        FunzioneUtenteVO vo = new FunzioneUtenteVO();
        if(vo != null) {
        	MudeDFunzione mudeDFunzione = mudeDFunzioneRepository.findById(dto.getFunzione());
	        vo.setCodFunzione(mudeDFunzione.getCodice());
        }

        return vo;
    }

}