/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAdempimento.TipoAdempimento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AdempimentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDAdempimentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AdempimentoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.AdempimentoVO;

@Service
public class AdempimentoServiceImpl implements AdempimentoService {

	@Autowired
	private MudeDAdempimentoRepository mudeDAdempimentoRepository;

	@Autowired
	private AdempimentoEntityMapper adempimentoEntityMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<AdempimentoVO> getAdempimenti(TipoAdempimento tipoAdempimento) {
		return adempimentoEntityMapper.mapListEntityToListVO(mudeDAdempimentoRepository.findByTipoAdempimentoAndDataFineIsNull(tipoAdempimento));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public AdempimentoVO getAdempimento(Long idAdempimento) {
		return adempimentoEntityMapper.mapEntityToVO(mudeDAdempimentoRepository.findOne(idAdempimento));
	}

}