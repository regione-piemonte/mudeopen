/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeDStatoFascicoloRepository extends BaseDictionaryRepository<MudeDStatoFascicolo, String> {

    MudeDStatoFascicolo findMudeDStatoFascicoloByCodice(String codice);

    @Query(value = "SELECT * FROM mudeopen_d_stato_fascicolo WHERE data_fine IS NULL ORDER BY cardinal_pos, codice", nativeQuery = true)
	List<MudeDStatoFascicolo> findAllOrdered();
    
}