/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRRuoloFruitore;

public interface MudeRRuoloFruitoreRepository extends BaseRepository<MudeRRuoloFruitore, Long> {
	
	@Query(value="SELECT * "
					+ "  FROM "
					+ "    mudeopen_r_ruolo_fruitore "
					+ "  WHERE data_inizio <= now() "
					+ "    AND (data_fine IS NULL OR data_fine > now()) "
					+ "    AND id_fruitore = :idFruitore "
					+ "  ORDER BY id_tipo_istanza NULLS FIRST "
					+ "  LIMIT 1", nativeQuery = true)
	MudeRRuoloFruitore findByIdFruitoreNoType(@Param("idFruitore") Long idFruitore);

	@Query(value="SELECT * "
					+ "  FROM "
					+ "    mudeopen_r_ruolo_fruitore "
					+ "  WHERE data_inizio <= now() "
					+ "    AND (data_fine IS NULL OR data_fine > now()) "
					+ "    AND id_fruitore = :idFruitore "
					+ "    AND (id_tipo_istanza IS NULL OR id_tipo_istanza = :tipoIstanza) "
					+ "  ORDER BY id_tipo_istanza NULLS LAST "
					+ "  LIMIT 1", nativeQuery = true)
	MudeRRuoloFruitore findByIdFruitoreWithType(@Param("idFruitore") Long idFruitore, @Param("tipoIstanza") String tipoIstanza);

}
