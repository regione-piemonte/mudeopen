/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ModelloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.QuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTModelloRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;

@Component
public class QuadroEntityMapperImpl implements QuadroEntityMapper {

    @Autowired
    private MudeDQuadroRepository mudeDQuadroRepository;

	@Autowired
	private TipoQuadroEntityMapper tipoQuadroEntityMapper;
	
	@Autowired
	private ModelloEntityMapper modelloEntityMapper;
	
	@Autowired
	private MudeDTipoQuadroRepository mudeDTipoQuadroRepository;
	
	@Autowired
	private MudeTModelloRepository mudeTModelloRepository;
	
    @Autowired
    private MudeDTemplateRepository mudeDTemplateRepository;

	@Override
	public QuadroVO mapEntityToSlimVO(MudeDQuadro dto, MudeTUser user, String filters) {
		QuadroVO vo = mapEntityToVO(dto, user, filters);
		
		if (null != dto.getMudeDTipoQuadro())
			vo.setTipoQuadro(tipoQuadroEntityMapper.mapEntityToSlimVO(dto.getMudeDTipoQuadro(), null));
		
		return vo;
	}
	
	@Override
	public QuadroVO mapEntityToVO(MudeDQuadro dto, MudeTUser user, String filters) {
		if (dto == null)
			return null;
		QuadroVO vo = new QuadroVO();

		vo.setIdQuadro(dto.getIdQuadro());
		vo.setJsonConfiguraQuadro(dto.getJsonConfiguraQuadro());
		vo.setNumVersione(dto.getNumVersione());

		vo.setValidationScript(dto.getValidationScript());
		vo.setFlgTipoGestione(dto.getFlgTipoGestione());
		vo.setJsonDefault(dto.getJsonDefault());
		
		if (null != dto.getMudeDTipoQuadro())
			vo.setTipoQuadro(tipoQuadroEntityMapper.mapEntityToVO(dto.getMudeDTipoQuadro(), null, filters));

        if(!hasFilter(filters, "essential")) {
			if(!hasFilter(filters, "no-model") && dto.getModello() != null)  {
				vo.setModello(modelloEntityMapper.mapEntityToVO(dto.getModello(), null));
				vo.getModello().setFileContent(null);
			}
	
	    	Long maxVersion = mudeDQuadroRepository.findFirstByMudeDTipoQuadro_IdTipoQuadroOrderByNumVersioneDescSLIM(vo.getTipoQuadro().getIdTipoQuadro());
			
			// set modify/delete flags
			// overwritten: vo.setFlgAttivo(dto.getFlgAttivo());
			vo.setFlgAttivo(mudeDTemplateRepository.findActiveIdQuadroInTemplatesCounter(dto.getIdQuadro()) ? 1l : 0l);
	    	
	    	if(maxVersion == null || (vo.getFlgAttivo() == 0 && vo.getNumVersione() == maxVersion.longValue())) {
	        	vo.setFlgEliminabile(true);
	        	vo.setFlgModificabile(true);
	    	}
	    	else // set create flags
		    	vo.setFlgCreabile(vo.getNumVersione() == maxVersion.longValue());
	
	    	vo.setUltimaVersione(vo.getNumVersione() == (maxVersion == null? 1l : maxVersion.longValue()));
        }

		return vo;
	}
	
    @Override
    public MudeDQuadro mapVOtoEntity(QuadroVO vo, MudeTUser user) {
    	MudeDQuadro dto;
    	
    	if(vo.getIdQuadro() == null) {
        	dto = new MudeDQuadro();
    		dto.setNumVersione(vo.getNumVersione());
    	}
    	else
    		dto = mudeDQuadroRepository.findOne(vo.getIdQuadro());
    	
		dto.setJsonConfiguraQuadro(vo.getJsonConfiguraQuadro());
		
		dto.setValidationScript(vo.getValidationScript());
		dto.setFlgTipoGestione(vo.getFlgTipoGestione());
		dto.setJsonDefault(vo.getJsonDefault());

		if(vo.getTipoQuadro() != null && vo.getTipoQuadro().getCodTipoQuadro() != null)
			dto.setMudeDTipoQuadro(mudeDTipoQuadroRepository.findByCodTipoQuadro(vo.getTipoQuadro().getCodTipoQuadro()));

		if(vo.getModello() != null && vo.getModello().getId() != null)
			dto.setModello(vo.getModello().getDimensioneFile() == -1 /* delete */ ? null : mudeTModelloRepository.findOne(vo.getModello().getId()));
		
    	return dto;
    }

}