/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDNazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.NazioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.NazioneVO;

@Component
public class NazioneEntityMapperImpl implements NazioneEntityMapper {

	@Override
	public NazioneVO mapEntityToVO(MudeDNazione dto) {
		if (dto == null)
			return null;
		NazioneVO nazioneVO = new NazioneVO();
		nazioneVO.setDescrizione(dto.getDenomNazione());
		nazioneVO.setId(dto.getIdNazione());
		
		nazioneVO.setStatoMembroUE(dto.getStatoMembroUE());
		nazioneVO.setCodiceBelfiore(dto.getCodBelfioreNazione());
		
		return nazioneVO;
	}

}