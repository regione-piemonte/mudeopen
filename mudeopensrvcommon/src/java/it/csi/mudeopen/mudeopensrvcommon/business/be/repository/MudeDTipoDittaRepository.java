/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDitta;

@Repository
public interface MudeDTipoDittaRepository extends BaseRepository<MudeDTipoDitta, Long> {

    @Query(value = "SELECT * FROM mudeopen_d_tipo_ditta WHERE data_fine IS NULL ORDER BY cardinal_pos, denominazione", nativeQuery = true)
	List<MudeDTipoDitta> findAllOrdered();

    MudeDTipoDitta findByDenominazione(String denominazioneTipoDitta);

}