/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.RegioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.RegioneVO;

@Component
public class RegioniEntityMapperImpl implements RegioneEntityMapper {

	@Override
	public RegioneVO mapEntityToVO(MudeDRegione dto) {
		if (dto == null)
			return null;
		RegioneVO regioneVO = new RegioneVO();
		regioneVO.setDescrizione(dto.getDenomRegione());
		regioneVO.setId(dto.getIdRegione());
		return regioneVO;
	}

}