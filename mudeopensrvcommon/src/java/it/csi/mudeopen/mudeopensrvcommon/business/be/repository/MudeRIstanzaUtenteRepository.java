/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtente;

@Repository
public interface MudeRIstanzaUtenteRepository extends BaseRepository<MudeRIstanzaUtente,Long> {

	List<MudeRIstanzaUtente> findAllByIstanza_IdAndDataFineIsNull(Long idIstanza);

	// retrieve just changeable permissions (to be used for "CREAZIONE..." esclusion)
    //@Query("SELECT u FROM MudeRIstanzaUtente u WHERE u.istanza.id=:idIstanza AND u.abilitazione.codice LIKE CONCAT(:exclPermPref, '%')")
	//List<MudeRIstanzaUtente> retrieveCheangleableUsers(@Param("idIstanza") Long idIstanza, @Param("exclPermPref") String exclude_permission_prefix);

	List<MudeRIstanzaUtente> findAllByIstanza_IdAndAbilitazione_IdAndDataFineIsNull(Long idIstanza, Long idAbilitazione);
	@Query("select o from MudeRIstanzaUtente o where o.abilitazione.codice in (:codiciAbilitazione) and o.istanza.id = :idIstanza and o.dataFine is null")
	List<MudeRIstanzaUtente> findAllByIstanza_IdAndAbilitazione_CodiceAndDataFineIsNull(@Param("idIstanza") Long idIstanza, @Param("codiciAbilitazione") String... codiciAbilitazione);
	MudeRIstanzaUtente findMudeRIstanzaUtenteByIstanza_IdAndAbilitazione_IdAndUtente_IdUserAndDataFineIsNull(Long idIstanza, Long idAbilitazione, Long idUser);

	@Query(value = "SELECT (count(mriu.id_istanza_utente) > 0) as has_funzione "
			+ "  FROM mudeopen_r_istanza_utente mriu "
			+ "    INNER JOIN mudeopen_r_abilitazione_funzione mraf ON mraf.id_abilitazione = mriu.id_abilitazione "
			+ "    INNER JOIN mudeopen_d_funzione mdf ON mdf.id_funzione =mraf.id_funzione "
			+ "  WHERE mriu.data_fine IS NULL "
			+ "    AND mraf.data_fine IS NULL "
			+ "    AND mdf.data_fine  IS NULL "
			+ "    AND mdf.codice_funzione in (:codiceFunzione) "
			+ "    AND mriu.id_istanza = :idIstanza "
			+ "    AND mriu.id_utente = :idUtente", nativeQuery = true)
	Boolean hasFunzionePerIstanza(@Param("codiceFunzione") String[] codiceFunzione, @Param("idIstanza") Long idIstanza, @Param("idUtente") Long idUtente);

	@Query("SELECT (count(o.mudeRIstanzaUtente) > 0) "
			+ "  FROM MudeRIstanzaUtenteQuadro o "
			+ "    INNER JOIN o.mudeRIstanzaUtente "
			+ "  WHERE o.dataFine IS NULL"
			+ "    AND o.mudeRIstanzaUtente.dataFine IS NULL "
			+ "    AND o.mudeRIstanzaUtente.istanza.id = :idIstanza "
			+ "    AND o.mudeRIstanzaUtente.utente.idUser = :idUtente "
			+ "    AND o.mudeDQuadro.idQuadro = :idQuadro")
	Boolean hasFunzionePerIstanzaQuadro(@Param("idQuadro") Long idQuadro, @Param("idIstanza") Long idIstanza, @Param("idUtente") Long idUtente);

	@Query(value = "SELECT (count(mriu.id_istanza_utente) > 0) AS has_funzione "
			+ "FROM mudeopen_r_istanza_utente mriu "
			+ "    INNER JOIN mudeopen_d_abilitazione mraf on mraf.id_abilitazione = mriu.id_abilitazione "
			+ "WHERE mraf.codice_abilitazione in (:codiceAbilitazione) "
			+ "    AND mriu.data_fine IS NULL"
			+ "    AND mraf.data_fine IS NULL"
			+ "    AND mriu.id_istanza = :idIstanza "
			+ "    AND mriu.id_utente = :idUtente", nativeQuery = true)
	Boolean findByIstanza_IdAndAbilitazione_CodiceAndUtente_IdUserAndDataFineIsNull(@Param("codiceAbilitazione") String[] codiceAbilitazione, @Param("idIstanza") Long idIstanza, @Param("idUtente") Long idUtente);

	@Query(value="SELECT o.*"
			+ "  FROM"
			+ "    mudeopen_r_istanza_utente o"
			+ "    INNER JOIN mudeopen_t_user u ON o.id_utente=u.id_user"
			+ "  WHERE o.data_fine IS NULL"
			+ "    AND o.id_istanza = :idIstanza"
			+ "    AND o.id_utente = :idUser"
			+ "    AND u.cf = :userCF", nativeQuery = true)
	List<MudeRIstanzaUtente> associateIstanzaUtenteAndSoggetto(@Param("idIstanza") Long idIstanza, @Param("idUser") Long idUser, @Param("userCF") String userCF);

	@Query(value = "SELECT (count(mriu.id_istanza_utente) > 0) as has_funzione "
			+ "  FROM mudeopen_r_istanza_utente mriu "
			+ "    INNER JOIN mudeopen_r_abilitazione_funzione mraf ON mraf.id_abilitazione = mriu.id_abilitazione "
			+ "    INNER JOIN mudeopen_d_funzione mdf ON mdf.id_funzione =mraf.id_funzione "
			+ "    INNER JOIN mudeopen_d_abilitazione mra on mra.id_abilitazione = mriu.id_abilitazione "
			+ "WHERE mra.codice_abilitazione in (:codiceAbilitazione) "
			+ "    AND NOT(mra.necessaria_selezione_quadro)"
			+ "    AND mra.data_fine IS NULL"
			+ "    AND mriu.data_fine IS NULL "
			+ "    AND mraf.data_fine IS NULL "
			+ "    AND mdf.data_fine  IS NULL "
			+ "    AND mdf.codice_funzione in (:codiceFunzione) "
			+ "    AND mriu.id_istanza = :idIstanza "
			+ "    AND mriu.id_utente = :idUtente", nativeQuery = true)
	Boolean hasPermissionWithNoQuadroSpecific(	@Param("codiceAbilitazione") String[] codiceAbilitazione, 
													@Param("codiceFunzione") String[] codiceFunzione, 
													@Param("idIstanza") Long idIstanza, 
													@Param("idUtente") Long idUtente);

	@Modifying
	@Query("delete from MudeRIstanzaUtente o where o.istanza.id = :idIstanza")
	void deleteByIstanza(@Param("idIstanza") Long idIstanza);

	@Query("SELECT o FROM MudeRIstanzaUtente o "
			+ "  WHERE o.abilitatore.idUser = :idUserAbilitatore "
			+ "    AND o.istanza.id = :idIstanza "
			+ "    AND o.dataFine IS NULL"
			+ "    AND o.dataInizio > :fromDate")
	List<MudeRIstanzaUtente> findAllAbilitati(@Param("idIstanza") Long idIstanza, 
											  @Param("idUserAbilitatore") 
											  Long idUserAbilitatore, 
											  @Param("fromDate") Date fromDate);
	
	@Query(value = "SELECT COUNT(id_utente_ente_comune_ruolo) > 0 \n"
					+ "  FROM mudeopen_r_utente_ente_comune_ruolo mruecr \n"
					+ "    INNER JOIN mudeopen_r_istanza_ente mrie ON mruecr.id_ente = mrie.id_ente \n"
					+ "    INNER JOIN mudeopen_t_istanza mti ON mti.id_istanza = mrie.id_istanza AND  mti.id_comune = mruecr.id_comune \n"
					+ "WHERE mruecr.id_utente = :idUser AND mti.id_istanza = :idIstanza", nativeQuery = true)
	boolean hasBoUserHasInstanceVisibility(@Param("idIstanza") Long idIstanza, @Param("idUser") Long idUser);
}