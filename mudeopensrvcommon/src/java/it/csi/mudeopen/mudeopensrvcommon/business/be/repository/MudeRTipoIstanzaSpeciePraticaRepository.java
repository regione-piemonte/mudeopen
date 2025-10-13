/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaSpeciePratica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeRTipoIstanzaSpeciePraticaRepository extends BaseRepository<MudeRTipoIstanzaSpeciePratica, Long> {

    @Query("SELECT o FROM MudeRTipoIstanzaSpeciePratica o where o.mudeDTipoIstanza.codice = :codiceTipoIstanza")
    List<MudeRTipoIstanzaSpeciePratica> findByCodiceTipoIstanza(@Param("codiceTipoIstanza") String codiceTipoIstanza);

    @Query("SELECT o FROM MudeRTipoIstanzaSpeciePratica o where o.mudeDSpeciePratica.codice = :codiceSpeciePratica")
    List<MudeRTipoIstanzaSpeciePratica> findByCodiceSpeciePratica(@Param("codiceSpeciePratica") String codiceSpeciePratica);
}