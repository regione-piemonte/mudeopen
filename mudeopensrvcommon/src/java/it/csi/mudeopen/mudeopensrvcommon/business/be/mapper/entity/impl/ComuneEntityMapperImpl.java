/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ComuneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;

@Component
public class ComuneEntityMapperImpl implements ComuneEntityMapper {

	@Override
	public ComuneVO mapEntityToVO(MudeDComune dto) {
		if (dto == null)
			return null;
		ComuneVO comuneVO = new ComuneVO();
		comuneVO.setDescrizione(dto.getDenomComune());
		comuneVO.setId(String.valueOf(dto.getIdComune()));
		comuneVO.setCodBelfiore(dto.getCodBelfioreComune());
		comuneVO.setCodIstat(dto.getIstatComune());
		return comuneVO;
	}

}