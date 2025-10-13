/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaTipoTracciato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeRTipoIstanzaTipoTracciatoRepository extends BaseRepository<MudeRTipoIstanzaTipoTracciato, Long>{

    @Query(value = "SELECT o.* FROM mudeopen_r_tipo_istanza_tipo_tracciato o"
    		+ "    INNER JOIN ("
    		+ "        SELECT DISTINCT ON (id_tipo_tracciato,id_tipo_istanza) id_tipo_istanza_tipo_tracciato"
    		+ "          FROM ("
    		+ "            SELECT * FROM mudeopen_r_tipo_istanza_tipo_tracciato"
    		+ "              WHERE data_inizio <= ("
    		+ "                SELECT s.data_inizio "
    		+ "                  FROM mudeopen_r_istanza_stato s "
    		+ "                  WHERE s.codice_stato_istanza='DPS' "
    		+ "                    AND s.id_istanza = :idIstanza "
    		+ "                  ORDER BY s.data_inizio desc"
    		+ "                  LIMIT 1 "
    		+ "                )"
    		+ "              ORDER BY data_inizio DESC "
    		+ "          ) orderedTipoTracc"
    		+ "    ) tipi_tracc_ist ON tipi_tracc_ist.id_tipo_istanza_tipo_tracciato=o.id_tipo_istanza_tipo_tracciato"
    		+ "    LEFT JOIN mudeopen_r_istanza_tracciato mrit2 ON mrit2.id_tipo_tracciato = o.id_tipo_tracciato AND mrit2.id_istanza = :idIstanza"
    		+ "  WHERE 1=1"
    		+ "    AND o.id_tipo_istanza = :idTipoIstanza"
    		+ "    AND (mrit2.id_istanza_tracciato IS NULL OR mrit2.data_fine IS NOT NULL)"
    		+ "    AND (SELECT MAX(attiva\\:\\:varchar)\\:\\:boolean FROM mudeopen_r_tipo_istanza_tipo_tracciato WHERE id_tipo_istanza = :idTipoIstanza AND o.id_tipo_tracciato = id_tipo_tracciato) ", nativeQuery = true)
    List<MudeRTipoIstanzaTipoTracciato> findByTipoIstanza(@Param("idIstanza") Long idIstanza, @Param("idTipoIstanza") String codiceTipoIstanza);

    @Query(value = "select * from mudeopen_r_tipo_istanza_tipo_tracciato o where "
    		+ " o.data_fine is null and o.id_tipo_istanza = :idTipoIstanza", nativeQuery = true)
    List<MudeRTipoIstanzaTipoTracciato> findByTipoIstanza(@Param("idTipoIstanza") String codiceTipoIstanza);
}