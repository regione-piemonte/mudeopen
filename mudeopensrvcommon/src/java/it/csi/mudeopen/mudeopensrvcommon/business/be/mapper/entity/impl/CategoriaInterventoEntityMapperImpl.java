/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDCategoriaIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.RegimeGiuridicoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.CategoriaInterventoVO;

@Component
public class CategoriaInterventoEntityMapperImpl implements CategoriaInterventoEntityMapper {

	@Autowired
	private RegimeGiuridicoEntityMapper regimeGiuridicoEntityMapper;

	@Override
	public CategoriaInterventoVO mapEntityToVO(MudeDCategoriaIntervento dto) {
		if (dto == null)
			return null;
		CategoriaInterventoVO categoriaVO = new CategoriaInterventoVO();
		categoriaVO.setDenominazione(dto.getDenominazione());
		categoriaVO.setIdCategoria(dto.getIdCategoria());

		if (null != dto.getMudeDRegimeGiuridico()) {
			categoriaVO.setRegimeGiuridicoVO(regimeGiuridicoEntityMapper.mapEntityToVO(dto.getMudeDRegimeGiuridico()));
		}

		return categoriaVO;
	}

}