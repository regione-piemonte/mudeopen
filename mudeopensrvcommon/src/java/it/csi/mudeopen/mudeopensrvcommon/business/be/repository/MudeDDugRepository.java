/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;

@Repository
public interface MudeDDugRepository extends BaseRepository<MudeDDug, Long> {

    @Query(value = "SELECT * FROM mudeopen_d_dug WHERE data_fine IS NULL ORDER BY cardinal_pos, denominazione", nativeQuery = true)
	List<MudeDDug> findAllOrdered();

    MudeDDug findByDenominazioneAndDataFineIsNull(String denominazioneDug);

    MudeDDug findByIdDug(Long idDug);

    @Query(value = "SELECT o.denominazione FROM mudeopen_d_dug o where o.id_dug = :idDug AND o.data_fine IS NULL", nativeQuery = true)
	String getDenominazione(@Param("idDug") Long idDug);
}