/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQualifica;

@Repository
public interface MudeDQualificaRepository extends BaseRepository<MudeDQualifica, Long> {

    @Query(value = "SELECT * FROM mudeopen_d_qualifica WHERE data_fine IS NULL ORDER BY cardinal_pos, denominazione", nativeQuery = true)
	List<MudeDQualifica> findAllOrdered();

    MudeDQualifica findByDenominazione(String denominazione);

}