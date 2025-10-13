/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenFpCiviciFull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeopenFpCiviciFullRepository extends BaseRepository<MudeopenFpCiviciFull, Long> {

    @Query(value = "select mtfc from MudeopenFpCiviciFull mtfc where mtfc.idCivico = ?1")
    List<MudeopenFpCiviciFull> findByIdCivico(Long idCivico);

}
