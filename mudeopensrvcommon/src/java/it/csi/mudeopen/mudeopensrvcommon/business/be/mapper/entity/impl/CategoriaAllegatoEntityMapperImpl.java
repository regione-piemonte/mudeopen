/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDCategoriaAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaAllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDCategoriaAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.CategoriaAllegatoVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoriaAllegatoEntityMapperImpl implements CategoriaAllegatoEntityMapper {

	@Autowired
	MudeDCategoriaAllegatoRepository mudeDCategoriaAllegatoRepository;

    @Override
    public CategoriaAllegatoVO mapEntityToVO(MudeDCategoriaAllegato dto, MudeTUser user) {
        if (dto == null)
            return null;
        CategoriaAllegatoVO vo = new CategoriaAllegatoVO();
        vo.setCodice(dto.getCodice());
//        vo.setDescrizione(dto.getDescrizione());
//        vo.setDescrizioneEstesa(dto.getDescrizioneEstesa());

        vo.setDescrizione(dto.getCodice());
        vo.setDescrizioneEstesa(dto.getDescrizione());
        return vo;
    }

    @Override
    public MudeDCategoriaAllegato mapVOtoEntity(CategoriaAllegatoVO vo, MudeTUser user) {
    	MudeDCategoriaAllegato dto;
    	
    	if(vo.getCodice() == null)
        	dto = new MudeDCategoriaAllegato();
    	else
    		dto = mudeDCategoriaAllegatoRepository.findOne(vo.getCodice());

    	dto.setDescrizione(vo.getCodice());
    	dto.setDescrizioneEstesa(vo.getDescrizione());

    	return dto;
	}
	
    @Override
    public List<CategoriaAllegatoVO> mapListEntityToListVO(List<MudeDCategoriaAllegato> tl, MudeTUser user) {
        return CategoriaAllegatoEntityMapper.super.mapListEntityToListVO(tl, user);
    }
}