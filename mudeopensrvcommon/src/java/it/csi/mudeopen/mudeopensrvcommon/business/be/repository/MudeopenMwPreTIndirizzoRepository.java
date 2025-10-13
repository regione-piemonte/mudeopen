/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenMwPreTIndirizzo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeopenMwPreTIndirizzoRepository extends BaseRepository<MudeopenMwPreTIndirizzo, Integer> {

    @Query(value = "select mtfc from MudeopenMwPreTIndirizzo mtfc where mtfc.idCivicoTopon = ?1")
    List<MudeopenMwPreTIndirizzo> findByIdCivicoTopon(int idCivicoTopon);

}
