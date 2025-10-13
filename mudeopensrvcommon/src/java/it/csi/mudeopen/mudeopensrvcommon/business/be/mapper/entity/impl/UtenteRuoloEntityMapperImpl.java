/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteRuoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
@Component
public class UtenteRuoloEntityMapperImpl implements UtenteRuoloEntityMapper {
	
	@Autowired
	private MudeDRuoloUtenteRepository mudeDRuoloUtenteRepository;
	
	@Override
    public List<SelectVO> mapListEntityToListVO(List<MudeRUtenteRuolo> tl) {
        return UtenteRuoloEntityMapper.super.mapListEntityToListVO(tl);
    }

	@Override
	public SelectVO mapEntityToVO(MudeRUtenteRuolo dto) {
		SelectVO vo = mapEntityToSlimVO(dto);
        if(vo != null) {
	        //vo.setRuoloUtente(ruoloUtenteEntityMapper.mapEntityToVO(dto.getRuoloUtente()));
        }

        return vo;
    }
	
	public SelectVO mapEntityToSlimVO(MudeRUtenteRuolo dto) {
        if(null == dto)
            return null;

        SelectVO vo = new SelectVO();
        if(vo != null) {
	        vo.setId(dto.getRuoloUtente());
	        MudeDRuoloUtente mudeDRuoloUtente = mudeDRuoloUtenteRepository.findByCodice(dto.getRuoloUtente());
	        vo.setDescrizione(mudeDRuoloUtente.getDescrizione());
        }

        return vo;
    }

}