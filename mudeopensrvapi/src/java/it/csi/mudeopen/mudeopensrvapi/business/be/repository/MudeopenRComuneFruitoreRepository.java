/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRComuneFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

/**
 * The interface Mudeopen r comune fruitore repository.
 */
@Repository
public interface MudeopenRComuneFruitoreRepository extends BaseRepository<MudeRComuneFruitore, Long> {

	@Query(value = "select t from MudeRComuneFruitore t where t.dataFine is null")
	List<MudeRComuneFruitore> findActive();
	
	List<MudeRComuneFruitore> findByComuneAndFruitore(MudeDComune comune, MudeDFruitore fruitore);
	
	MudeRComuneFruitore findByComuneIstatComuneAndFruitoreCodiceFruitoreAndDataFineNull(String istatComune, String codiceFruitore);

	List<MudeRComuneFruitore> findByFruitore_codiceFruitore(String fruitoreID);
}