/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.QuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class TemplateQuadroEntityMapperImpl implements TemplateQuadroEntityMapper {

	@Autowired
	private QuadroEntityMapper quadroEntityMapper;

	@Autowired
	private TemplateEntityMapper templateEntityMapper;
	
	@Autowired
	TemplateQuadroEntityMapper templateQuadroEntityMapper;
	
	@Autowired
	MudeRTemplateQuadroRepository mudeRTemplateQuadroRepository;

	@Autowired
	MudeDQuadroRepository mudeDQuadroRepository;
	
	@Autowired
	MudeDTemplateRepository mudeDTemplateRepository;
	
	@Override
	public TemplateQuadroVO mapEntityToSlimVO(MudeRTemplateQuadro dto, MudeTUser user) {
		TemplateQuadroVO vo = new TemplateQuadroVO();
		
		if (null != dto.getMudeDQuadro())
			vo.setQuadro(quadroEntityMapper.mapEntityToSlimVO(dto.getMudeDQuadro(), null));
		
		return vo;
	}
	
	@Override
	public TemplateQuadroVO mapEntityToVO(MudeRTemplateQuadro dto, MudeTUser user) {
		if (dto == null)
			return null;
		TemplateQuadroVO vo = new TemplateQuadroVO();

		vo.setIdTemplateQuadro(dto.getIdTemplateQuadro());
		vo.setOrdinamentoTemplateQuadro(dto.getOrdinamentoTemplateQuadro());
		vo.setFlgQuadroObbigatorio(BooleanUtils.toBoolean(dto.getFlgQuadroObbigatorio()));
		vo.setProprieta(dto.getProprieta());

		if (null != dto.getMudeDQuadro()) {
			vo.setQuadro(quadroEntityMapper.mapEntityToVO(dto.getMudeDQuadro(), null));
		}

		if (null != dto.getMudeDTemplate()){
			vo.setTemplate(templateEntityMapper.mapEntityToVO(dto.getMudeDTemplate(), null));
		}
		
		return vo;
	}

    @Override
    public MudeRTemplateQuadro mapVOtoEntity(TemplateQuadroVO vo, MudeTUser user) {
		MudeRTemplateQuadro dto;
		
		if(vo.getIdTemplateQuadro() == null)
	    	dto = new MudeRTemplateQuadro();
		else
			dto = mudeRTemplateQuadroRepository.findOne(vo.getIdTemplateQuadro());
		
		dto.setOrdinamentoTemplateQuadro(vo.getOrdinamentoTemplateQuadro());
		dto.setFlgQuadroObbigatorio(vo.getFlgQuadroObbigatorio() != null && vo.getFlgQuadroObbigatorio() ? 1 : 0);
		
		if(vo.getQuadro() != null && vo.getQuadro().getIdQuadro() != null)
			dto.setMudeDQuadro(mudeDQuadroRepository.findByIdQuadro(vo.getQuadro().getIdQuadro()));
		
		if(vo.getTemplate() != null && vo.getTemplate().getIdTemplate() != null)
			dto.setMudeDTemplate(mudeDTemplateRepository.findByIdTemplate(vo.getTemplate().getIdTemplate()));

		dto.setProprieta(vo.getProprieta());

		return dto;
	}
}