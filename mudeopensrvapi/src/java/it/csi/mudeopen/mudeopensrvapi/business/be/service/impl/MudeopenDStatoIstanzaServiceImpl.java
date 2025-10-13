/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenDStatoIstanzaService;
import it.csi.mudeopen.mudeopensrvapi.vo.StatiIstanzaAmmessiVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDWfStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDWfStatiRepository;

/**
 * The type MudeopenDStatoIstanza service.
 */
@Service
public class MudeopenDStatoIstanzaServiceImpl implements MudeopenDStatoIstanzaService {

	@Autowired
	private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;

	@Autowired
	private MudeDWfStatiRepository mudeDWfStatiRepository;
	
	@Override
	public List<MudeDStatoIstanza> findAllActive() {
		return mudeDStatoIstanzaRepository.findAllActive();
	}
	
	@Override
	public MudeDStatoIstanza findStato(String stato) {
		return mudeDStatoIstanzaRepository.findOne(stato);
	}
	
	@Override
	public List<StatiIstanzaAmmessiVO> findStatiAmmessi(String statoIniziale) {
		List<StatiIstanzaAmmessiVO> vos = new ArrayList<>();

		List<MudeDWfStato> statiAmmessi = mudeDWfStatiRepository.findDistinctByCodiceStatoStartAndDataFineNull(statoIniziale);
		if(statiAmmessi.isEmpty())
			throw new IllegalArgumentException("[PassaggioStatoImpossibileException] Passaggio Stato Impossibile");
		
		for (MudeDWfStato stato : statiAmmessi) {
			MudeDStatoIstanza mudeDStatoIstanza = mudeDStatoIstanzaRepository.findOne(stato.getCodiceStatoEnd());
			if(mudeDStatoIstanza != null)
				vos.add(new StatiIstanzaAmmessiVO(mudeDStatoIstanza.getCodice(), mudeDStatoIstanza.getDescrizione(), stato.getOggettoNotifica(), stato.getTestoNotifica()));						
		}
		
		return vos;
	}

	

}