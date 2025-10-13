/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRRuoloFunzione;

@Repository
public interface MudeRRuoloFunzioneRepository extends BaseRepository<MudeRRuoloFunzione,Long> {

	@Query(value="SELECT o.*"
			+ "  FROM"
			+ "    mudeopen_r_ruolo_funzione o"
			+ "    INNER JOIN mudeopen_d_ruolo_utente u ON o.codice_ruolo_utente=u.codice"
			+ "    INNER JOIN mudeopen_d_funzione f ON o.id_funzione=f.id_funzione"
			+ "  WHERE o.data_inizio <= now() "
			+ "    AND (o.data_fine IS NULL OR o.data_fine > now())"
			+ "    AND f.data_inizio <= now()"
			+ "    AND (f.data_fine IS NULL OR f.data_fine > now())"
			+ "    AND u.data_inizio <= now()"
			+ "    AND (u.data_fine IS NULL OR u.data_fine > now())"
			+ "    AND o.codice_ruolo_utente = :codice", nativeQuery = true)
	List<MudeRRuoloFunzione> findByCodiceRuoloUtente(@Param("codice") String codice);

	
}