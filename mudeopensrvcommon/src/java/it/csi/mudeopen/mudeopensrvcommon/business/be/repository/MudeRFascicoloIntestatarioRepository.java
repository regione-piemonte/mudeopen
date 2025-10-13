/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIntestatario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MudeRFascicoloIntestatarioRepository extends BaseRepository<MudeRFascicoloIntestatario, Long> {

    @Query(value = "SELECT * FROM mudeopen_r_fascicolo_intestatario WHERE data_fine IS NULL AND id_fascicolo = :idFascicolo LIMIT 1", nativeQuery = true)
    MudeRFascicoloIntestatario findActiveByFascicolo(@Param("idFascicolo") Long idFascicolo);

    @Query("delete from MudeRFascicoloIntestatario o where o.fascicolo.id = :idFascicolo")
    @Modifying
    void deleteByFascicolo(@Param("idFascicolo")Long idFascicolo);
}