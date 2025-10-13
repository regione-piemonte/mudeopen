/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDOrdine;

@Repository
public interface MudeDOrdineRepository extends BaseDictionaryRepository<MudeDOrdine, String> {

    @Query(value = "SELECT * FROM mudeopen_d_ordine WHERE data_fine IS NULL ORDER BY cardinal_pos, descrizione", nativeQuery = true)
	List<MudeDOrdine> findAllOrdered();

}