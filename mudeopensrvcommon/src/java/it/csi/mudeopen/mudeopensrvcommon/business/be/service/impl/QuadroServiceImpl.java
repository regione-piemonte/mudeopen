/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.QuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.QuadroService;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuadroServiceImpl implements QuadroService {

	@Autowired
	private MudeDQuadroRepository mudeDQuadroRepository;

	@Autowired
	private QuadroEntityMapper quadroEntityMapper;

	@Override
	public QuadroVO loadQuadroByIdTipoQuadro(Long idTipoQuadro) {
		return quadroEntityMapper.mapEntityToVO(mudeDQuadroRepository.findByMudeDTipoQuadro_IdTipoQuadro(idTipoQuadro), null);
	}

	@Override
	public QuadroVO loadQuadroByTipoQuadroDesc(String descTipoQuadro) {
		return quadroEntityMapper.mapEntityToVO(mudeDQuadroRepository.findByMudeDTipoQuadro_DesTipoQuadro(descTipoQuadro), null);
	}

	@Override
	public Long saveQuadro(QuadroVO quadro) {
		return null;
	}

	@Override
	public int updateQuadro(QuadroVO quadro) {
		return 0;
	}

	@Override
	public void deleteQuadro(Long idQuadro) {
		throw new UnsupportedOperationException();
	}

}