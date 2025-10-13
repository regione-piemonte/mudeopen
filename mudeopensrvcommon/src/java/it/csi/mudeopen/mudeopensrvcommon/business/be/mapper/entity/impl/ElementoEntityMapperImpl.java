/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDElemento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDOpera;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ElementoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.OperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.ElementoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.OperaVO;

@Component
public class ElementoEntityMapperImpl implements ElementoEntityMapper {

	@Autowired
	private OperaEntityMapper operaEntityMapper;

	@Override
	public ElementoVO mapEntityToVO(MudeDElemento dto) {
		if (dto == null)
			return null;

		ElementoVO elementoVO = new ElementoVO();
		elementoVO.setPosizione(dto.getPosizione());
		elementoVO.setDenominazione(dto.getDenominazione());
		elementoVO.setIdElemento(dto.getIdElemento());

		if (null != dto.getOpere()) {
			Set<OperaVO> opere = new HashSet<>();
			for (MudeDOpera iterator : dto.getOpere()) {
				opere.add(operaEntityMapper.mapEntityToVO(iterator));
			}
			elementoVO.setOpere(opere);
		}
		return elementoVO;
	}

}