/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDCategoriaInterventoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.CategorieInterventoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.CategoriaInterventoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieInterventoServiceImpl implements CategorieInterventoService {

	@Autowired
	private MudeDCategoriaInterventoRepository mudeDCategoriaInterventoRepository;

	@Autowired
	private CategoriaInterventoEntityMapper categoriaEntityMapper;

	@Override
	public List<CategoriaInterventoVO> findAll() {
		return categoriaEntityMapper.mapListEntityToListVO(mudeDCategoriaInterventoRepository.findAll());
	}

	@Override
	public CategoriaInterventoVO findOne(Long idCategoria) {
		return categoriaEntityMapper.mapEntityToVO(mudeDCategoriaInterventoRepository.findOne(idCategoria));
	}
}