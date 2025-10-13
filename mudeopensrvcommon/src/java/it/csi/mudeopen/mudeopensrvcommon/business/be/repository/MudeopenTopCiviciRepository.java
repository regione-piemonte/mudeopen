/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenTopCivici;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeopenTopCiviciRepository extends BaseRepository<MudeopenTopCivici, Integer> {

    @Query(value = "select mtc from MudeopenTopCivici mtc where mtc.pkSequCivico = ?1")
    List<MudeopenTopCivici> findByFkCivici(int fkCivici);

}
