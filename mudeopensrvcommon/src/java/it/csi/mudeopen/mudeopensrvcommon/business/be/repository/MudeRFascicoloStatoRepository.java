/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloStato;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeRFascicoloStatoRepository extends BaseRepository<MudeRFascicoloStato, Long> {

    @Query("select o from MudeRFascicoloStato o where o.dataFine is null and o.fascicolo.id = :idFascicolo")
    MudeRFascicoloStato findStatoByFascicolo(@Param("idFascicolo") Long idFascicolo);

    @Query("delete from MudeRFascicoloStato o where o.fascicolo.id = :idFascicolo")
    @Modifying
    void deleteStatiByFascicolo(@Param("idFascicolo") Long idFascicolo);
}