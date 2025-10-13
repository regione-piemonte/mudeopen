/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ElementoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ElementoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDElementoRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.ElementoVO;

@Service
public class ElementoServiceImpl implements ElementoService {

	@Autowired
	private MudeDElementoRepository mudeDElementoRepository;

	@Autowired
	private ElementoEntityMapper elementoEntityMapper;	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ElementoVO> getElementi() {
		return elementoEntityMapper.mapListEntityToListVO(mudeDElementoRepository.findAll());

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ElementoVO getElemento(Long idElemento) {
		return elementoEntityMapper.mapEntityToVO(mudeDElementoRepository.findOne(idElemento));
	}

}