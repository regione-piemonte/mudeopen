/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaTipoIntervento;

@Repository
public interface MudeRTipoIstanzaTipoInterventoRepository extends BaseRepository<MudeRTipoIstanzaTipoIntervento, Long> {

	@Query(value = "SELECT DISTINCT ON (mdti2.descrizione) "
					+ "    mrtiti.* "
					+ "FROM "
					+ "    mudeopen_r_tipo_istanza_tipo_intervento mrtiti "
					+ "    INNER JOIN mudeopen_d_tipo_istanza mdti ON  mrtiti.codice_tipo_istanza = mdti.codice "
					+ "    INNER JOIN mudeopen_d_tipo_intervento mdti2 ON mrtiti.codice_tipo_intervento = mdti2.codice "
					+ "WHERE "
					+ "    mdti.codice in (:tipoistanze) "
					+ "ORDER BY"
					+ "    mdti2.descrizione ASC", nativeQuery = true)
    List<MudeRTipoIstanzaTipoIntervento> findDistinctMudeDTipoIntervento_codiceAndMudeDTipoIstanza_codiceByMudeDTipoIstanza_CodiceInOrderByMudeDTipoIntervento_descrizione(@Param("tipoistanze") List<String>  codiceTipoIstanza);

}