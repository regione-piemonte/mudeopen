/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegimeAggiuntivo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.RegimeAggiuntivoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.RegimeAggiuntivoVO;

@Component
public class RegimeAggiuntivoEntityMapperImpl implements RegimeAggiuntivoEntityMapper {

	@Override
	public RegimeAggiuntivoVO mapEntityToVO(MudeDRegimeAggiuntivo dto) {
		if (dto == null)
			return null;

		RegimeAggiuntivoVO regimeVO = new RegimeAggiuntivoVO();
		regimeVO.setDenominazione(dto.getDenominazione());
		regimeVO.setPriorita(dto.getPriorita());
		regimeVO.setIdRegime(dto.getIdRegime());
		
		return regimeVO;
	}

}