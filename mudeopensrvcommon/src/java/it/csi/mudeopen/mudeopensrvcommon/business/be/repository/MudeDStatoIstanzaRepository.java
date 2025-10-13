/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;

@Repository
public interface MudeDStatoIstanzaRepository extends BaseDictionaryRepository<MudeDStatoIstanza, String> {

	@Query(value = "select t from MudeDStatoIstanza t where t.dataFine is null" +
			" and t.codice not in ('DFR','FRM','BZZ')")		// esclusione degli stati antecedenti lo stato "DEPOSITATA"
	List<MudeDStatoIstanza> findAllActive();
	
    @Query(value = " SELECT o.* FROM mudeopen_d_stato_istanza o where o.codice=?1", nativeQuery = true)
    MudeDStatoIstanza findMudeDStatoIstanzaByCodice(String codice);
    
    @Query(value = "SELECT * FROM mudeopen_d_stato_istanza WHERE data_fine IS NULL ORDER BY cardinal_pos, livello", nativeQuery = true)
	List<MudeDStatoIstanza> findAllOrdered();
    
}