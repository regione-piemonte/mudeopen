/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAdempimento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AdempimentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.RegimeAggiuntivoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.AdempimentoVO;

@Component
public class AdempimentoEntityMapperImpl implements AdempimentoEntityMapper {
	
	@Autowired
	private RegimeAggiuntivoEntityMapper regimeAggiuntivoEntityMapper;

	@Override
	public AdempimentoVO mapEntityToVO(MudeDAdempimento dto) {
		if (dto == null)
			return null;
		AdempimentoVO adempimentoVO = new AdempimentoVO();
		adempimentoVO.setDenominazione(dto.getDenominazione());
		adempimentoVO.setAmbito(dto.getAmbito());
		adempimentoVO.setIdAdempimento(dto.getIdAdempimento());
		adempimentoVO.setTipologia(dto.getTipoAdempimento().name());
		adempimentoVO.setPosizione(dto.getPosizione());
		if (null != dto.getMudeDRegimeAggiuntivo()) {
			adempimentoVO.setRegimeAggiuntivo(regimeAggiuntivoEntityMapper.mapEntityToVO(dto.getMudeDRegimeAggiuntivo()));
		} 

		return adempimentoVO;
	}

}