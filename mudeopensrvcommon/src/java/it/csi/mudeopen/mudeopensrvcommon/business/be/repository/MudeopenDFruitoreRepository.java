/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
/**
 * The interface Mudeopen d fruitore repository.
 */
@Repository
public interface MudeopenDFruitoreRepository extends BaseRepository<MudeDFruitore, Long> {

	@Query(value = "select t from MudeDFruitore t where t.dataFine is null")
	List<MudeDFruitore> findActive();
	
	//@Query(value = "select t from MudeDFruitore t where t.codiceFruitore = :codiceFruitore and t.dataFine is null")
	//Optional<MudeDFruitore> findByCodiceFruitore(@Param("codiceFruitore") String codiceFruitore);

	MudeDFruitore findByCodiceFruitore(String codiceFruitore);
	MudeDFruitore findByCodiceFruitoreAndDataFineIsNull(String codiceFruitore);
	
	@Query(value = "SELECT COUNT(1) > 0 FROM mudeopen_d_fruitore mdf WHERE mdf.id_fruitore = ? AND data_fine IS NULL", nativeQuery = true)
	boolean existsAtLeastOneComune(Long idFruitore);

	@Query(value = "SELECT COALESCE(MAX(id_fruitore), -1) FROM mudeopen_d_fruitore WHERE codice_fruitore = ? AND data_fine IS NULL", nativeQuery = true)
	long getIdFruitore(String codFruitore);
	
}