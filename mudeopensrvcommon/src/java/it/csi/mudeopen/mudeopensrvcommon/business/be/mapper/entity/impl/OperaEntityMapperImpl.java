/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDOpera;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.CategoriaInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.OperaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.OperaVO;

@Component
public class OperaEntityMapperImpl implements OperaEntityMapper {

	@Autowired
	private CategoriaInterventoEntityMapper categoriaEntityMapper;

	@Override
	public OperaVO mapEntityToVO(MudeDOpera dto) {
		if (dto == null)
			return null;
		OperaVO operaVO = new OperaVO();
		operaVO.setDenominazione(dto.getDenominazione());
		operaVO.setIdOpera(dto.getIdOpera());
		operaVO.setElemento(null);

		if (null != dto.getCategoria()) {
			operaVO.setCategoria(categoriaEntityMapper.mapEntityToVO(dto.getCategoria()));
		}

		return operaVO;
	}

}