/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRFabbrCivici;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeopenRFabbrCiviciRepository extends BaseRepository<MudeopenRFabbrCivici, Integer> {

    @Query(value = "select mtfc from MudeopenRFabbrCivici mtfc where mtfc.fkFabbricati = ?1")
    List<MudeopenRFabbrCivici> findByIdFabbricato(int idFabbricato);

}
