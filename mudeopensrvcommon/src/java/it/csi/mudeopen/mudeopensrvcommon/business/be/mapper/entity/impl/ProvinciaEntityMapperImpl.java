/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ProvinciaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;

@Component
public class ProvinciaEntityMapperImpl implements ProvinciaEntityMapper {

	@Override
	public ProvinciaVO mapEntityToVO(MudeDProvincia dto) {
		if (dto == null)
			return null;
		ProvinciaVO provinciaVO = new ProvinciaVO();
		provinciaVO.setDescrizione(dto.getDenomProvincia());
		provinciaVO.setId(dto.getIdProvincia());
		return provinciaVO;
	}

}