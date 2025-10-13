/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.OperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDOperaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.OperaService;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.OperaVO;

@Service
public class OperaServiceImpl implements OperaService {

	
	@Autowired
	private MudeDOperaRepository mudeDOperaRepository;

	@Autowired
	private OperaEntityMapper operaEntityMapper;
	
	
	@Override
	public List<OperaVO> getOpere() {
		return operaEntityMapper.mapListEntityToListVO(mudeDOperaRepository.findAll());
	}

	@Override
	public OperaVO getOpera(Long idOpera) {
		return operaEntityMapper.mapEntityToVO(mudeDOperaRepository.findOne(idOpera));
	}

}