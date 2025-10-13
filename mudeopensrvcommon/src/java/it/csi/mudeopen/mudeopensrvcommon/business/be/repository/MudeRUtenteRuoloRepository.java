/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteRuolo;

@Repository
public interface MudeRUtenteRuoloRepository extends BaseRepository<MudeRUtenteRuolo,Long> {

	@Query(value="SELECT o.*"
			+ "  FROM"
			+ "    mudeopen_r_utente_ruolo o"
			+ "    INNER JOIN mudeopen_t_user u ON o.id_utente=u.id_user"
			+ "    INNER JOIN mudeopen_d_ruolo_utente r ON o.codice_ruolo_utente=r.codice"
			+ "  WHERE o.data_inizio <= now()"
			+ "    AND (o.data_fine IS NULL OR o.data_fine > now())"
			+ "    AND u.inizio_validita <= now()"
			+ "    AND (u.fine_validita IS NULL OR u.fine_validita > now())"
			+ "    AND r.data_inizio <= now()"
			+ "    AND (r.data_fine IS NULL OR r.data_fine > now())"
			+ "    AND o.id_utente = :idUser", nativeQuery = true)
	List<MudeRUtenteRuolo> findByIdUser(@Param("idUser") Long idUser);

	
}