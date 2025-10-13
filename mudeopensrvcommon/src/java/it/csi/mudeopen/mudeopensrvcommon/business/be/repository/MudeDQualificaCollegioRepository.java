/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDOrdine;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQualificaCollegio;

@Repository
public interface MudeDQualificaCollegioRepository extends BaseDictionaryRepository<MudeDQualificaCollegio, String> {

    @Query("SELECT u FROM MudeDQualificaCollegio u WHERE u.dataFine IS NUll ORDER BY u.descrizione ASC")
	List<MudeDOrdine> findAllOrderByDescrizioneAsc();

}