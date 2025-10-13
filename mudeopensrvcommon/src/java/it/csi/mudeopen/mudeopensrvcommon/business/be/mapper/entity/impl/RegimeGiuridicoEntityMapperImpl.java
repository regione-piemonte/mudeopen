/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegimeGiuridico;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.RegimeGiuridicoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.RegimeGiuridicoVO;

@Component
public class RegimeGiuridicoEntityMapperImpl implements RegimeGiuridicoEntityMapper {

	@Override
	public RegimeGiuridicoVO mapEntityToVO(MudeDRegimeGiuridico dto) {
		if (dto == null)
			return null;

		RegimeGiuridicoVO regimeVO = new RegimeGiuridicoVO();
		regimeVO.setDenominazione(dto.getDenominazione());
		regimeVO.setPriorita(dto.getPriorita());
		regimeVO.setIdRegime(dto.getIdRegime());
		
		return regimeVO;
	}

}