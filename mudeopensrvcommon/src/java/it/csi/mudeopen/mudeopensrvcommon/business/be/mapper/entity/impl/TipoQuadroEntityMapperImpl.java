/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TipoQuadroVO;

@Component
public class TipoQuadroEntityMapperImpl implements TipoQuadroEntityMapper {

    @Autowired
    private MudeDTipoQuadroRepository mudeDTipoQuadroRepository;
	
    @Autowired
    private MudeRTemplateQuadroRepository mudeRTemplateQuadroRepository;

	@Override
	public TipoQuadroVO mapEntityToSlimVO(MudeDTipoQuadro dto, MudeTUser user) {
		TipoQuadroVO tipoQuadroVO = mapEntityToVO(dto, user);

		// in case of extended "tipo quadro", override it with the parent version!  
		if(tipoQuadroVO.getTipoQuadroPadre() != null) {
			TipoQuadroVO tipoQuadroVOparent = tipoQuadroVO.getTipoQuadroPadre();
			// set current quadro info
			tipoQuadroVOparent.setDesTipoQuadro(tipoQuadroVO.getIdTipoQuadro() +" - "+ tipoQuadroVO.getCodTipoQuadro());
			
			return tipoQuadroVOparent;
		}
		
		return tipoQuadroVO;
	}

	@Override
	public TipoQuadroVO mapEntityToVO(MudeDTipoQuadro dto, MudeTUser user, String filters) {
		if (dto == null) return null;
		
		TipoQuadroVO vo = new TipoQuadroVO();
		vo.setIdTipoQuadro(dto.getIdTipoQuadro());
		vo.setCodTipoQuadro(dto.getCodTipoQuadro());
		vo.setDesTipoQuadro(dto.getDesTipoQuadro());

		vo.setIdCategoriaQuadro(dto.getIdCategoriaQuadro());
		
        if(!hasFilter(filters, "essential")) {
	        String[] permRequiredForQuadro = mudeDTipoQuadroRepository.getAllFunctionsByCodTipoQuadro(dto.getCodTipoQuadro());
	        if(permRequiredForQuadro.length > 0)
	        	vo.setFunzioniRichieste(String.join(",", permRequiredForQuadro));
	
			// set modify/delete flags
			vo.setModificabile(!mudeRTemplateQuadroRepository.findAllVirtualTemplateQuadroByIdTipoQuadroCounter(vo.getIdTipoQuadro())); // modificable only if there is not any template linked
			
			if(dto.getTipoQuadroPadre() != null)
				vo.setTipoQuadroPadre(mapEntityToVO(dto.getTipoQuadroPadre(), user));
        }

		return vo;
	}
	
    @Override
    public MudeDTipoQuadro mapVOtoEntity(TipoQuadroVO vo, MudeTUser user) {
        if(vo == null) return null;

        MudeDTipoQuadro dto;
        if(vo.getIdTipoQuadro() == null)
        	dto = new MudeDTipoQuadro();
        else
        	dto = mudeDTipoQuadroRepository.findByIdTipoQuadro(vo.getIdTipoQuadro());

		dto.setCodTipoQuadro(vo.getCodTipoQuadro());
		dto.setDesTipoQuadro(vo.getDesTipoQuadro());
		dto.setIdCategoriaQuadro(vo.getIdCategoriaQuadro());
		
		if(vo.getTipoQuadroPadre() != null)
			dto.setTipoQuadroPadre(mapVOtoEntity(vo.getTipoQuadroPadre(), user));
		
        return dto;
    }

}