/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenRComuneFruitoreRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenRTipoIstanzaFruitoreRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDFruitoreService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRComuneFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenDFruitoreRepository;

/**
 * The type MudeDFruitore service.
 */
@Service
public class MudeopenDFruitoreServiceImpl implements MudeopenDFruitoreService {

	@Autowired
	private MudeopenDFruitoreRepository mudeopenDFruitoreRepository;

	@Autowired
	private MudeopenRComuneFruitoreRepository mudeopenRComuneFruitoreRepository;
	
	@Autowired
	private MudeopenRTipoIstanzaFruitoreRepository mudeopenRTipoIstanzaFruitoreRepository;
	
	@Override
	public List<MudeDFruitore> findActive() {
		return mudeopenDFruitoreRepository.findActive();
	}

	@Override
	public MudeDFruitore findByCodiceFruitore(String codiceFruitore) {
		return mudeopenDFruitoreRepository.findByCodiceFruitore(codiceFruitore);
	}

	@Override
	public List<MudeRComuneFruitore> findComuniByCodiceFruitore(String codiceFruitore) {
		return mudeopenRComuneFruitoreRepository.findByFruitore_codiceFruitore(codiceFruitore);
	}

	@Override
	public List<MudeRTipoIstanzaFruitore> findTipiIstanzaByCodiceFruitore(String codiceFruitore) {
		return mudeopenRTipoIstanzaFruitoreRepository.findByMudeDFruitore_codiceFruitore(codiceFruitore);
	}

}