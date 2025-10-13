/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaAllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoAllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDCategoriaAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoVO;

@Component
public class TipoAllegatoEntityMapperImpl implements TipoAllegatoEntityMapper {

    @Autowired
    private CategoriaAllegatoEntityMapper categoriaAllegatoEntityMapper;

    @Autowired
    private MudeDTipoAllegatoRepository mudeDTipoAllegatoRepository;

    @Autowired
    private MudeDCategoriaAllegatoRepository mudeDCategoriaAllegatoRepository;

    @Override
    public TipoAllegatoVO mapEntityToVO(MudeDTipoAllegato dto, MudeTUser user) {
        if (dto == null) {
            return null;
        }
        TipoAllegatoVO tipoAllegatoVO = new TipoAllegatoVO();

        tipoAllegatoVO.setCodice(dto.getCodice());
        tipoAllegatoVO.setDescrizione(dto.getDescrizione());
        tipoAllegatoVO.setDescrizioneEstesa(dto.getDescrizioneEstesa());
        tipoAllegatoVO.setCategoriaAllegato(categoriaAllegatoEntityMapper.mapEntityToVO(dto.getCategoriaAllegato(), null));
        tipoAllegatoVO.setSubCodeTipoAllegato(dto.getSubCodeTipoAllegato());
        tipoAllegatoVO.setFirmaObbligatoria(dto.getFirmaObbligatoria());
        tipoAllegatoVO.setValidaFirma(dto.getValidaFirma());
        return tipoAllegatoVO;
    }

    @Override
    public MudeDTipoAllegato mapVOtoEntity(TipoAllegatoVO vo, MudeTUser user) {
    	MudeDTipoAllegato dto;
    	
    	if(vo.getCodice() == null)
        	dto = new MudeDTipoAllegato();
    	else
    		dto = mudeDTipoAllegatoRepository.findOne(vo.getCodice());
    	
        dto.setCodice(vo.getCodice());
        dto.setDescrizione(vo.getDescrizione());
        dto.setDescrizioneEstesa(vo.getDescrizioneEstesa());
        dto.setSubCodeTipoAllegato(vo.getSubCodeTipoAllegato());
        dto.setFirmaObbligatoria(vo.getFirmaObbligatoria());
        dto.setValidaFirma(vo.getValidaFirma());

        if(vo.getCategoriaAllegato() != null)
        	dto.setCategoriaAllegato(mudeDCategoriaAllegatoRepository.findByCodice(vo.getCategoriaAllegato().getCodice()));

    	return dto;
    }

}