/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAmbito;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AmbitoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDAmbitoRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.AmbitoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class AmbitoEntityMapperImpl implements AmbitoEntityMapper {

	@Autowired
	private MudeDAmbitoRepository mudeDAmbitoRepository;

	@Override
	public AmbitoVO mapEntityToVO(MudeDAmbito dto, MudeTUser user) {
		if (dto == null)
			return null;
		AmbitoVO vo = new AmbitoVO();
		vo.setDesAmbito(dto.getDesAmbito());
		vo.setCodAmbito(dto.getCodAmbito());
		vo.setIdAmbito(dto.getIdAmbito());
		vo.setFlgAttivo(dto.getFlgAttivo());
		vo.setDesEstesaAmbito(dto.getDesEstesaAmbito());
		return vo;
	}

	@Override
	public MudeDAmbito mapVOtoEntity(AmbitoVO vo, MudeTUser user) {
		if (vo == null)	return null;

		MudeDAmbito entity = null;
		// if update
		if (null != vo.getIdAmbito()) {
			// get entity from db
			entity = mudeDAmbitoRepository.findOne(vo.getIdAmbito());
			if (null == entity) {
				return null;
			}
		}
		else{
			entity = new MudeDAmbito();
			entity.setDataInizio(Calendar.getInstance().getTime());
		}
		entity.setCodAmbito(vo.getCodAmbito());
		entity.setDesAmbito(vo.getDesAmbito());
		entity.setDesEstesaAmbito(vo.getDesEstesaAmbito());
		entity.setFlgAttivo(vo.getFlgAttivo());
		return entity;
	}
}