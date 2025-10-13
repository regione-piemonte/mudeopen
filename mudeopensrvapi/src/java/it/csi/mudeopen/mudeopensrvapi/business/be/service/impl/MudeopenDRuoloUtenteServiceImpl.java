/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenDRuoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDRuoloUtenteService;
import it.csi.mudeopen.mudeopensrvapi.vo.RuoloVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloUtente;

/**
 * The type MudeopenDTipoDocpa service.
 */
@Service
public class MudeopenDRuoloUtenteServiceImpl implements MudeopenDRuoloUtenteService {

	@Autowired
	private MudeopenDRuoloUtenteRepository mudeopenDRuoloUtenteRepository;

	@Override
	public List<MudeDRuoloUtente> findAllActive() {
		return mudeopenDRuoloUtenteRepository.findAllActive();
	}

	@Override
	public List<RuoloVO> findByIdFunzione(String fruitoreID) {
		//return mudeopenDRuoloUtenteRepository.findAllByFruitore(fruitoreID).stream().map(x -> { return new RuoloVO(x.getCodice(), x.getDescrizioneEstesa()); }).collect(Collectors.toList());
		return mudeopenDRuoloUtenteRepository.findAllActive().stream().filter(x -> "IS RP OS OR".indexOf(x.getCodice()) > -1).map(x -> { return new RuoloVO(x.getCodice(), x.getDescrizioneEstesa()); }).collect(Collectors.toList());
	}

}