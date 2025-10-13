/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

/**
 * The interface Mudeopen r tipoIstanza fruitore repository.
 */
@Repository
public interface MudeopenRTipoIstanzaFruitoreRepository extends BaseRepository<MudeRTipoIstanzaFruitore, Long> {

	MudeRTipoIstanzaFruitore findByMudeDFruitore_codiceFruitoreAndMudeDTipoIstanza_codice(String fruitoreID, String tipo_istanza);
	
	List<MudeRTipoIstanzaFruitore> findByMudeDFruitore_codiceFruitore(String fruitoreID);
	
	
}
