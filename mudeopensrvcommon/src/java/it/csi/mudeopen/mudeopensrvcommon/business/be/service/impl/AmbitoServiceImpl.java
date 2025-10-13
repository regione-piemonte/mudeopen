/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAmbito;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AmbitoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDAmbitoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AmbitoService;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.AmbitoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmbitoServiceImpl implements AmbitoService {

	@Autowired
	private MudeDAmbitoRepository mudeDAmbitoRepository;

	@Autowired
	private AmbitoEntityMapper ambitoEntityMapper;

	@Override
	public List<AmbitoVO> loadAmbitiAttivi() {
		List<MudeDAmbito> itemsE = mudeDAmbitoRepository.findAllByFlgAttivoAndDataFineIsNull(1L);
		List<AmbitoVO> itemsVO = ambitoEntityMapper.mapListEntityToListVO(itemsE, null);
		return itemsVO;
//		return ambitoEntityMapper.mapListEntityToListVO(mudeDAmbitoRepository.findAllByFlgAttivo(1));
	}

	@Override
	public AmbitoVO loadAmbitoById(Long idAmbito) {
		return ambitoEntityMapper.mapEntityToVO(mudeDAmbitoRepository.findOne(idAmbito), null);
	}

	@Override
	public AmbitoVO loadAmbitoByCode(String codAmbito) {
		return ambitoEntityMapper.mapEntityToVO(mudeDAmbitoRepository.findByCodAmbito(codAmbito), null);
	}

	@Override
	public Long saveAmbito(AmbitoVO ambito) {
		MudeDAmbito entity = ambitoEntityMapper.mapVOtoEntity(ambito, null);
		if (null == entity) {
			throw new BusinessException("Ambito non presente sul db.");
		}
		mudeDAmbitoRepository.saveDAO(entity);
		return entity.getIdAmbito();
	}

	@Override
	public Long updateAmbito(AmbitoVO ambito) {
		MudeDAmbito entity = ambitoEntityMapper.mapVOtoEntity(ambito, null);
		if (null == entity) {
			throw new BusinessException("Ambito non presente sul db.");
		}
		mudeDAmbitoRepository.saveDAO(entity);
		return entity.getIdAmbito();
	}

	@Override
	public void deleteAmbito(Long idAmbito) {
		throw new UnsupportedOperationException();
	}
}