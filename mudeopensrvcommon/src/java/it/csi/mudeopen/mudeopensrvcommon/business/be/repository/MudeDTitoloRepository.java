/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTitolo;

@Repository
public interface MudeDTitoloRepository extends BaseDictionaryRepository<MudeDTitolo, String> {

    List<MudeDTitolo> findByIdTipoPresentatoreAndDataFineIsNull(Long idTipoPresentatore);

	@Query(value = "SELECT * FROM mudeopen_d_titolo WHERE data_fine is null ORDER BY cardinal_pos", nativeQuery = true)
    List<MudeDTitolo> findAllOrdered();
    
}