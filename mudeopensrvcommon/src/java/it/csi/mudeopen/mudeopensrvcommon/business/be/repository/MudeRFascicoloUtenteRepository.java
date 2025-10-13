/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloUtente;

@Repository
public interface MudeRFascicoloUtenteRepository extends BaseRepository<MudeRFascicoloUtente, Long> {

	List<MudeRFascicoloUtente> findAllByFascicolo_IdAndDataFineIsNull(Long idFascicolo);

	@Query(value = "SELECT"
				+ "    (count(mfiu.id_fascicolo_utente) > 0) as has_funzione"
				+ " FROM mudeopen_r_fascicolo_utente mfiu "
				+ "    INNER JOIN mudeopen_d_abilitazione mdf ON mdf.id_abilitazione = mfiu.id_abilitazione "
				+ " WHERE  mfiu.data_fine is null"
				+ "    AND mdf.data_fine is null"
				+ "    AND mdf.codice_abilitazione in (:codiceFunzione)"
				+ "    AND mfiu.id_fascicolo = :idFascicolo"
				+ "    AND mfiu.id_utente = :idUtente", nativeQuery = true)
	Boolean hasAbilitazionePerFascicolo(@Param("codiceFunzione") String[] codiceFunzione, @Param("idFascicolo") Long idFascicolo, @Param("idUtente") Long idUtente);

	@Query(value = "SELECT (count(mfiu.id_fascicolo_utente) > 0) as has_funzione "
			+ "FROM mudeopen_r_fascicolo_utente mfiu "
			+ "  INNER JOIN mudeopen_r_abilitazione_funzione mraf ON mraf.id_abilitazione = mfiu.id_abilitazione "
			+ "  INNER JOIN mudeopen_d_funzione mdf ON mdf.id_funzione =mraf.id_funzione "
			+ "WHERE "
			+ "    mfiu.data_fine IS NULL"
			+ "    AND mraf.data_fine IS NULL"
			+ "    AND mdf.data_fine IS NULL"
			+ "    AND mdf.codice_funzione IN (:codiceFunzione) "
			+ "    AND mfiu.id_fascicolo = :idFascicolo "
			+ "    AND mfiu.id_utente = :idUtente", nativeQuery = true)
	Boolean hasFunzionePerFascicolo(@Param("codiceFunzione") String[] codiceFunzione, @Param("idFascicolo") Long idFascicolo, @Param("idUtente") Long idUtente);
	

	MudeRFascicoloUtente findMudeRFascicoloUtenteByFascicolo_IdAndAbilitazione_IdAndUtente_IdUserAndDataFineIsNull(Long idIstanza, Long idAbilitazione, Long idUser);

	List<MudeRFascicoloUtente> findAllByFascicolo_IdAndAbilitazione_IdAndDataFineIsNull(Long idFascicolo, Long idAbilitazione);

	@Modifying
	@Query("delete from MudeRFascicoloUtente o where o.fascicolo.id = :idFascicolo")
	void deleteByFascicolo(@Param("idFascicolo") Long idFascicolo);
	
	@Query("SELECT o FROM MudeRFascicoloUtente o "
			+ "  WHERE o.abilitatore.idUser = :idUserAbilitatore "
			+ "    AND o.fascicolo.id = :idFascicolo "
			+ "    AND o.dataFine IS NULL")
	List<MudeRFascicoloUtente> findAllAbilitati(@Param("idFascicolo") Long idFascicolo, @Param("idUserAbilitatore") Long idUserAbilitatore);
	
}