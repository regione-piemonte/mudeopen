/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;

/**
 * The interface Mude t contatto repository.
 */
@Repository
public interface MudeTContattoRepository extends BaseRepository<MudeTContatto, Long>{

    /**
     * Find by cf list.
     *
     * @param cf the cf
     * @return the list
     */
    List<MudeTContatto> findByCf(String cf);

    /**
     * Find by cf and data cessazione null optional.
     *
     * @param cf     the cf
     * @param userCF the user cf
     * @return the optional
     */
    @Query("SELECT o FROM MudeTContatto o WHERE o.cf = :cf AND  o.mudeTUser.cf = :userCF AND o.dataCessazione IS NULL")
    Optional<MudeTContatto> findByCfAndDataCessazioneNull(@Param("cf") String cf, @Param("userCF") String userCF);

    @Query("SELECT o FROM MudeTContatto o WHERE (o.guid = :guid OR o.id = :idOldContatto) AND o.mudeTUser.idUser = :idUser AND o.dataCessazione IS NULL")
    List<MudeTContatto> findAllToCease(@Param("idOldContatto") Long idOldContatto, @Param("guid") String guid, @Param("idUser") Long idUser);

    /**
     * Find accredited contact by cf mude t contatto.
     *
     * @param cf     the cf
     * @param userCF the user cf
     * @return the mude t contatto
     */
    @Query("SELECT o FROM MudeTContatto o WHERE o.cf = :cf AND (o.mudeTUser.cf = o.cf OR o.mudeTUser.cf = :userCF) AND o.dataCessazione IS NULL AND o.tipoContatto = 'PF' ")
    MudeTContatto findAccreditedContactByCF(@Param("cf") String cf, @Param("userCF") String userCF);

    /**
     * Find by uuid and data cessazione null optional.
     *
     * @param guid the guid
     * @return the optional
     */
    MudeTContatto findFirstByGuidAndMudeTUser_IdUserAndDataCessazioneNullOrderByIdDesc(String guid, Long idUser);
    @Query(value = "SELECT mtc.* "
    		+ "  FROM mudeopen_r_fascicolo_soggetto mrfs"
    		+ "    INNER JOIN mudeopen_t_contatto mtc ON mrfs.guid_soggetto = mtc.guid AND mtc.id_user = mrfs.id_user_ins AND mtc.data_cessazione IS NULL"
    		+ " WHERE mrfs.data_fine IS NULL "
    		+ "    AND id_fascicolo = :idFascicolo "
    		+ "    AND guid_soggetto = :guid"
            + "  LIMIT 1", nativeQuery = true)
    MudeTContatto findInUserFromFolder(@Param("guid") String guid, @Param("idFascicolo") Long idFascicolo);
    /**
     * Find by piva or piva com and data cessazione null optional.
     *
     * @param piva   the piva
     * @param userCF the user cf
     * @return the optional
     */
    @Query("SELECT o FROM MudeTContatto o WHERE (o.piva = :piva  OR o.pivaComunitaria = :piva) AND  o.mudeTUser.cf = :userCF AND o.dataCessazione IS NULL")
    MudeTContatto findByPivaOrPivaComAndDataCessazioneNull(@Param("piva") String piva, @Param("userCF") String userCF);

    /**
     * Find by cf and mude t user list.
     *
     * @param codiceFiscale the codice fiscale
     * @param mudeTUser     the mude t user
     * @return the list
     */
    List<MudeTContatto> findByCfAndMudeTUser(String codiceFiscale, MudeTUser mudeTUser);
    @Query("SELECT o.id FROM MudeTContatto o WHERE o.cf = :cf AND o.mudeTUser.cf = :cf AND o.tipoContatto = 'PF'")
    List<Long> findIdContattoByCfAndMudeTUser(@Param("cf") String codiceFiscale);

    /**
     * Find by piva and mude t user list.
     *
     * @param partitaIva the partita iva
     * @param mudeTUser  the mude t user
     * @return the list
     */
    List<MudeTContatto> findByPivaAndMudeTUser(String partitaIva, MudeTUser mudeTUser);

//    List<MudeTContatto> findAllByPivaComunitariaAndMudeTUser(String pivaComunitaria, MudeTUser mudeTUser);

    /**
     * Find by id optional.
     *
     * @param id     the id
     * @param userCF the user cf
     * @return the optional
     */
    @Query("SELECT o FROM MudeTContatto o WHERE o.id = :id AND o.mudeTUser.cf = :userCF AND o.dataCessazione IS NULL")
    Optional<MudeTContatto> findById(@Param("id") Long id, @Param("userCF") String userCF);

    /**
     * Find all by piva or piva com list.
     *
     * @param piva   the piva
     * @param userCF the user cf
     * @return the list
     */
    @Query("SELECT o FROM MudeTContatto o WHERE (o.piva = :piva  OR o.pivaComunitaria = :piva) AND  o.mudeTUser.cf = :userCF")
    List<MudeTContatto> findAllByPivaOrPivaCom(@Param("piva") String piva, @Param("userCF") String userCF);

    /**
     * Find all by cf list.
     *
     * @param cf     the cf
     * @param userCF the user cf
     * @return the list
     */
    @Query("SELECT o FROM MudeTContatto o WHERE o.cf = :cf AND  o.mudeTUser.cf = :userCF AND o.tipoContatto = 'PF'")
    List<MudeTContatto> findAllByCf(@Param("cf") String cf, @Param("userCF") String userCF);

    /**
     * Find by id user mude t contatto.
     *
     * @param idUser the id user
     * @return the mude t contatto
     */
    @Query("SELECT o FROM MudeTContatto o WHERE o.mudeTUser.idUser=:idUser AND o.cf=o.mudeTUser.cf AND o.dataCessazione IS NULL AND o.tipoContatto = 'PF'")
    MudeTContatto findByIdUser(@Param("idUser") Long idUser);

    @Query(value = "SELECT o.*"
    		+ "  FROM mudeopen_t_contatto o"
    		+ "    INNER JOIN mudeopen_t_user mtu ON o.cf = mtu.cf and mtu.id_user = :idAccreditedUser AND o.tipo_contatto = 'PF' "
    		+ "  WHERE o.id_user = :idUser"
    		+ "    AND o.data_cessazione IS NULL"
            + "  LIMIT 1", nativeQuery = true)
    MudeTContatto findPrivateContactByAccreditedIdUser(@Param("idUser") Long idUser, @Param("idAccreditedUser") Long idAccreditedUser);
    /**
     * Find by idIstanza mude t contatto responsabile procedimento.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto

    @Query(value = "SELECT distinct (a.*) "
		+ "  from MUDEOPEN_T_CONTATTO as a"
		+ "    inner join mudeopen_r_ente_comune_tipo_istanza as b on a.id_contatto = b.id_responsabile_procedimento"
		+ "     inner join MUDEOPEN_R_ISTANZA_ENTE as ria on b.id_ente = ria.id_ente "
		+ "     inner join MUDEOPEN_T_ISTANZA as d on b.codice_tipo_istanza = d.id_tipo_istanza "
		+ "     inner join MUDEOPEN_T_ISTANZA as f on f.id_istanza = ria.id_istanza "
		+ "  WHERE ria.id_istanza = :idIstanza", nativeQuery = true)
    MudeTContatto findRespProcedimentoByIdIstanza(@Param("idIstanza") Long idIstanza);
    // TODO: replace, if necessary, "findRespProcedimentoByIdIstanza" with this version when "responsabile_procedimento" is converted to varchar:
	@Query(value = 
			"SELECT mreti.responsabile_procedimento"
			+ "  FROM mudeopen_t_istanza mti "
			+ "    INNER JOIN mudeopen_r_istanza_ente mrie ON mti.id_istanza = mrie.id_istanza  "
			+ "    INNER JOIN mudeopen_r_ente_comune_tipo_istanza mreti ON (mreti.id_ente = mrie.id_ente AND mti.id_tipo_istanza=mreti.codice_tipo_istanza AND mreti.data_fine IS NULL AND mreti.competenza_principale=true) "
			+ "  WHERE mti.id_istanza = :idIstanza"
			+ "    AND COALESCE(mreti.codice_specie_pratica, 'getnull') = (SELECT COALESCE(codice_specie_pratica, 'getnull') "
			+ "                            FROM mudeopen_r_ente_comune_tipo_istanza "
			+ "                            WHERE data_fine IS NULL "
			+ "                            AND competenza_principale = true "
			+ "                            AND (codice_specie_pratica IS NULL OR codice_specie_pratica = mti.id_tipo_istanza) "
			+ "                            ORDER BY codice_specie_pratica DESC NULLS LAST"
			+ "                            LIMIT 1)", nativeQuery = true)
	String findRespProcedimentoByIdIstanzaFINALVERSION(@Param("idIstanza") Long idIstanza);
    */
    
	/*
    @Query(value = "SELECT mtc.guid "
    		+ "  FROM mudeopen_r_istanza_soggetto mris"
    		+ "    INNER JOIN mudeopen_r_istanza_soggetto_ruoli mrisr ON mris.id_istanza_soggetto = mrisr.id_istanza_soggetto "
    		+ "    INNER JOIN mudeopen_t_contatto mtc ON mris.id_soggetto = mtc.id_contatto  "
    		+ "  WHERE id_istanza = :idIstanza "
    		+ "    AND ruoli in ('IN')"
    		+ " LIMIT 1", nativeQuery = true)
    String recuperaGuidIntestatarioIstanza(@Param("idIstanza") Long idIstanza);
	*/

    /**
     * Find by idIstanza soggetto mude t contatto intestatario istanza.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto
     */
    @Query(value = "SELECT con.*"
		    		+ "  FROM MUDEOPEN_T_CONTATTO con"
		    		+ "    INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO b ON con.id_contatto = b.id_soggetto "
		    		+ "    INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO_RUOLI c ON b.id_istanza_soggetto = c.id_istanza_soggetto and c.ruoli = 'IN' "
		    		+ "  WHERE b.id_istanza = :idIstanza"
		            + "  LIMIT 1", nativeQuery = true)
    MudeTContatto findIntestatarioByIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT mtc.*"
	    		+ " FROM mudeopen_t_contatto con"
	    		+ "  INNER JOIN mudeopen_r_istanza_soggetto b ON con.id_contatto = b.id_soggetto "
	    		+ "  INNER JOIN mudeopen_r_istanza_soggetto_ruoli c ON b.id_istanza_soggetto = c.id_istanza_soggetto and c.ruoli IN (:ruoliSoggetto) "
	    		+ "  INNER JOIN mudeopen_t_contatto mtc ON con.guid = mtc.guid and mtc.data_cessazione IS NULL AND con.id_user = mtc.id_user  "
	    		+ " WHERE b.id_istanza = :idIstanza "
	    		+ " ORDER BY b.id_istanza_soggetto", nativeQuery = true)
    List<MudeTContatto> findRoleByIdIstanza(@Param("idIstanza") Long idIstanza, @Param("ruoliSoggetto") String[] ruoliSoggetto);

    @Query(value = "SELECT c.nome, c.cognome, c.ragione_sociale, c.tipo_contatto, c.id_contatto, c.cellulare, c.cf, c.data_nascita, c.mail, c.pec, c.piva, c.sesso, c.telefono, null as id_comune_nascita, null as id_user, null as id_provincia_nascita, null as id_stato_nascita, null as nazionalita, c.piva_comunitaria, null as tipo_attivita, null as id_stato_membro, c.guid, c.data_creazione, c.data_cessazione, null as citta_estera, null as titolare, null as impresa_lavori, null as professionista, null as loguser, c.data_modifica "
    		+ "  FROM MUDEOPEN_T_CONTATTO c"
    		+ "    INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO b ON c.id_contatto = b.id_soggetto "
    		+ "    INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO_RUOLI s ON b.id_istanza_soggetto = s.id_istanza_soggetto and s.ruoli = 'IN' "
    		+ "  WHERE b.id_istanza = :idIstanza"
            + "  LIMIT 1", nativeQuery = true)
    MudeTContatto getIntestatarioNameByIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT CAST(json_data->'QDR_SOGGETTO_COINV'->'subjectList'->:guidRT->'rappresentati' AS varchar) "
		    		+ "  FROM mudeopen_t_istanza "
		    		+ "  WHERE id_istanza = :idIstanza ", nativeQuery = true)
    String findPresentedByRT(@Param("idIstanza") Long idIstanza, @Param("guidRT") String guidRT);

    @Query(value = "SELECT mtc.* "
		    		+ "    FROM (SELECT mti.id_istanza, tbl.keyid guidRT "
		    		+ "                FROM (select id_istanza, jsonb_object_keys(json_data->'QDR_SOGGETTO_COINV'->'subjectList') as keyid from mudeopen_t_istanza) tbl "
		    		+ "                    INNER JOIN mudeopen_t_istanza mti ON mti.id_istanza=tbl.id_istanza "
		    		+ "                WHERE CAST(json_data #>> ARRAY['QDR_SOGGETTO_COINV', 'subjectList', tbl.keyid, 'rappresentati'] as varchar) LIKE :guidRepresented "
		    		+ "                    AND mti.id_istanza = :idIstanza "
		    		+ "                LIMIT 1) rt "
		    		+ "        INNER JOIN mudeopen_r_istanza_soggetto mris ON mris.id_istanza=rt.id_istanza "
		    		+ "        INNER JOIN mudeopen_t_contatto mtc ON mris.id_soggetto=mtc.id_contatto AND mtc.guid=rt.guidRT", nativeQuery = true)
    MudeTContatto findRT(@Param("idIstanza") Long idIstanza, @Param("guidRepresented") String guidRepresented);
    
    // 

    @Query(value = "SELECT mtc.* "
    		+ "  FROM mudeopen_t_contatto con "
    		+ "    INNER JOIN mudeopen_r_istanza_soggetto b ON con.id_contatto = b.id_soggetto "
    		+ "    INNER JOIN mudeopen_r_istanza_soggetto_ruoli c ON b.id_istanza_soggetto = c.id_istanza_soggetto and c.ruoli = 'IN' "
    		+ "    INNER JOIN mudeopen_t_contatto mtc ON con.guid = mtc.guid and mtc.data_cessazione IS NULL AND con.id_user = mtc.id_user  "
    		+ "  WHERE b.id_istanza = :idIstanza"
    		+ "  LIMIT 1", nativeQuery = true)
    MudeTContatto findIntestatarioUpdatedByIdIstanza(@Param("idIstanza") Long idIstanza);
	/**
	 * Find by idIstanza soggetto mude t contatto intestatario istanza.
	 *
	 * @param idIstanza the id istanza
	 * @return the mude t contatto
	 */
	@Query(value = "SELECT concat_ws('$$$', coalesce(con.nome, 'null_string') , coalesce(con.cognome, 'null_string') , coalesce(con.ragione_sociale, 'null_string') , coalesce(con.tipo_contatto, 'null_string'))"
			+ "  FROM MUDEOPEN_T_CONTATTO con"
			+ "    INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO b ON con.id_contatto = b.id_soggetto "
			+ "    INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO_RUOLI c ON b.id_istanza_soggetto = c.id_istanza_soggetto and c.ruoli = 'IN' "
			+ "  WHERE b.id_istanza = :idIstanza"
			+ "  LIMIT 1", nativeQuery = true)
	String findIntestatarioAsStringByIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT "
    		+ "      lmtc.id_contatto"
    		+ "    , lmtc.cellulare"
    		+ "    , lmtc.cf"
    		+ "    , lmtc.cognome"
    		+ "    , lmtc.data_nascita"
    		+ "    , lmtc.mail"
    		+ "    , lmtc.nome"
    		+ "    , lmtc.pec"
    		+ "    , lmtc.piva"
    		+ "    , lmtc.ragione_sociale"
    		+ "    , lmtc.sesso"
    		+ "    , lmtc.telefono"
    		+ "    , lmtc.tipo_contatto"
    		+ "    , lmtc.id_comune_nascita"
    		+ "    , lmtc.id_user"
    		+ "    , lmtc.id_provincia_nascita"
    		+ "    , lmtc.id_stato_nascita"
    		+ "    , lmtc.nazionalita"
    		+ "    , lmtc.piva_comunitaria"
    		+ "    , lmtc.tipo_attivita"
    		+ "    , lmtc.id_stato_membro"
    		+ "    , CONCAT(mtc.id_contatto, '~', COALESCE(lmtc.guid, '')) AS guid"
    		+ "    , lmtc.data_creazione"
    		+ "    , lmtc.data_cessazione"
    		+ "    , lmtc.citta_estera"
    		+ "    , lmtc.titolare"
    		+ "    , lmtc.impresa_lavori"
    		+ "    , lmtc.professionista"
    		+ "    , lmtc.loguser"
    		+ "    , lmtc.data_modifica"
    		+ "  FROM mudeopen_t_istanza mti \n"
    		+ "    INNER JOIN mudeopen_r_istanza_soggetto mris ON mris.id_istanza = mti.id_istanza_riferimento \n"
    		+ "    INNER JOIN mudeopen_r_istanza_soggetto_ruoli mrisr ON mris.id_istanza_soggetto = mrisr.id_istanza_soggetto and mrisr.ruoli = 'IN'\n"
    		+ "    INNER JOIN mudeopen_t_contatto mtc  ON mtc.id_contatto = mris.id_soggetto\n"
    		+ "    INNER JOIN mudeopen_t_contatto lmtc ON mtc.id_user = lmtc.id_user AND mtc.guid = lmtc.guid AND lmtc.data_cessazione IS NULL"
    		+ "  WHERE mti.id_istanza = :idIstanzaRif\n"
		    + "  LIMIT 1", nativeQuery = true)
    MudeTContatto findIntestatarioByIdIstanzaRiferimento(@Param("idIstanzaRif") Long idIstanzaRif);

    /**
     * Find by idIstanza mude t contatto professionista istanza.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto
     * NON USATA */
    @Query(value = " SELECT c.* "
    	+ "  FROM  MUDEOPEN_R_ISTANZA_UTENTE mriu "
    	+ "       INNER JOIN MUDEOPEN_D_ABILITAZIONE mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.CODICE_ABILITAZIONE in ('PM_RUP_PM_OBB','PM_RUP_PM_OPZ'))"
        + "       INNER JOIN MUDEOPEN_T_USER u ON mriu.id_utente = u.id_user "
        + "       INNER JOIN mudeopen_t_contatto c ON c.id_user=u.id_user AND u.cf = c.cf AND c.tipo_contatto = 'PF' "// AND data_cessazione IS NULL 
        + "       INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO si ON si.id_soggetto=c.id_contatto AND si.id_istanza=mriu.id_istanza "
        + "  WHERE mriu.data_fine IS NULL AND mriu.id_istanza = :idIstanza"
        + "  LIMIT 1", nativeQuery = true)
    MudeTContatto findProfessionistaByIdIstanza(@Param("idIstanza") Long idIstanza);

	/**
	 * Find by idIstanza mude t contatto professionista istanza.
	 *
	 * @param idIstanza the id istanza
	 * @return the mude t contatto
	 * NON USATA */
	@Query(value = " SELECT concat_ws('$$$', coalesce(c.nome, 'null_string') , coalesce(c.cognome, 'null_string') , coalesce(c.ragione_sociale, 'null_string') , coalesce(c.tipo_contatto, 'null_string')) "
			+ "  FROM  MUDEOPEN_R_ISTANZA_UTENTE mriu "
			+ "       INNER JOIN MUDEOPEN_D_ABILITAZIONE mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.CODICE_ABILITAZIONE in ('PM_RUP_PM_OBB','PM_RUP_PM_OPZ'))"
			+ "       INNER JOIN MUDEOPEN_T_USER u ON mriu.id_utente = u.id_user "
			+ "	     INNER JOIN mudeopen_t_contatto c ON c.id_user=u.id_user AND u.cf = c.cf AND c.tipo_contatto = 'PF'" //AND data_cessazione IS NULL "
			+ "       INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO si ON si.id_soggetto=c.id_contatto AND si.id_istanza=mriu.id_istanza "
			+ "  WHERE mriu.data_fine IS NULL AND mriu.id_istanza = :idIstanza"
			+ "  LIMIT 1", nativeQuery = true)
	String findProfessionistaAsStringByIdIstanza(@Param("idIstanza") Long idIstanza);
    /**
     * Find by idIstanza mude t contatto Creatore istanza.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto
     */
    @Query(value = "SELECT c.*"
    		+ "  FROM mudeopen_r_istanza_utente mriu"
    		+ "    INNER JOIN mudeopen_d_abilitazione mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.codice_abilitazione in ('CREATORE_IST_PM_OBB','CREATORE_IST_PM_OPZ'))"
    		+ "    INNER JOIN mudeopen_t_user u ON mriu.id_utente = u.id_user"
    		+ "    INNER JOIN mudeopen_t_contatto c ON u.cf = c.guid"
    		+ "    INNER JOIN mudeopen_r_istanza_soggetto si ON si.id_soggetto=c.id_contatto AND si.id_istanza=mriu.id_istanza"
    		+ "   WHERE mriu.data_fine IS NULL AND mriu.id_istanza = :idIstanza", nativeQuery = true)
    List<MudeTContatto> findCreatoreByIdIstanza(@Param("idIstanza") Long idIstanza);

    /**
     * Find by idIstanza mude t contatto Creatore istanza.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto
     */
    @Query(value = " SELECT c.* "
    	+ "  FROM  MUDEOPEN_R_ISTANZA_UTENTE mriu "
    	+ "       INNER JOIN MUDEOPEN_D_ABILITAZIONE mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.CODICE_ABILITAZIONE in ('CREATORE_IST_PM_OBB','CREATORE_IST_PM_OPZ'))"
        + "       INNER JOIN MUDEOPEN_T_USER u ON mriu.id_utente = u.id_user "
        + "       INNER JOIN mudeopen_t_contatto c ON c.id_user=u.id_user AND u.cf = c.cf AND c.data_cessazione IS NULL AND c.tipo_contatto = 'PF' "       	     
        + "  WHERE mriu.data_fine IS NULL AND mriu.id_istanza = :idIstanza and c.guid=:guid"
        + "  LIMIT 1", nativeQuery = true)
    MudeTContatto findCreatoreByIdIstanzaAndDataCessazioneNull(@Param("idIstanza") Long idIstanza, @Param("guid") String guid);

    /**
     * Find by idIstanza mude t contatto collaboratore istanza.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto
     */
    @Query(value = " SELECT c.* "
    	+ "  FROM  MUDEOPEN_R_ISTANZA_UTENTE mriu "
    	+ "       INNER JOIN MUDEOPEN_D_ABILITAZIONE mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.CODICE_ABILITAZIONE in ('COLLAB_PM_RUP_PM_OBB','COLLAB_PM_RUP_PM_OPZ'))"
        + "       INNER JOIN MUDEOPEN_T_USER u ON mriu.id_utente = u.id_user "
        + "       INNER JOIN mudeopen_t_contatto c ON c.id_user=u.id_user AND u.cf = c.cf AND c.data_cessazione IS NULL AND c.tipo_contatto = 'PF'"
        + "       INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO si ON si.id_soggetto=c.id_contatto AND si.id_istanza=mriu.id_istanza "
        + "  WHERE mriu.data_fine IS NULL AND mriu.id_istanza = :idIstanza"
        + "  LIMIT 1", nativeQuery = true)
    MudeTContatto findCollaboratoreByIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT distinct (a.*) "
    		+ "  from MUDEOPEN_T_CONTATTO as a"
    		+ "    inner join MUDEOPEN_R_ISTANZA_SOGGETTO as b on a.id_contatto = b.id_soggetto "
    		+ "     inner join MUDEOPEN_R_ISTANZA_SOGGETTO_RUOLI as c on b.id_istanza_soggetto = c.id_istanza_soggetto and c.ruoli = :ruolo "
    		+ "  WHERE b.id_istanza = :idIstanza", nativeQuery = true)
    List<MudeTContatto> findContattoByIdIstanzaAndRuolo(@Param("idIstanza") Long idIstanza, @Param("ruolo") String ruolo);
    /**
     * Find by idIstanza mude t contatto professionista istanza.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto
     * */
    /* da prioritario all'ultimo contatto in rubrica oppure se non presente al contatto accreditato */
    @Query(value = " SELECT c.* "
    	+ "  FROM  MUDEOPEN_R_ISTANZA_UTENTE mriu "
    	+ "       INNER JOIN MUDEOPEN_D_ABILITAZIONE mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.CODICE_ABILITAZIONE in ('PM_RUP_PM_OBB','PM_RUP_PM_OPZ'))"
        + "       INNER JOIN MUDEOPEN_T_USER u ON mriu.id_utente = u.id_user "
        + "       INNER JOIN mudeopen_t_contatto c ON u.cf = c.cf "
        + "       WHERE  mriu.id_istanza = :idIstanza and c.id_contatto = ( "
        + "       select mtc.id_contatto  from mudeopen_t_contatto mtc "
        + "       LEFT JOIN MUDEOPEN_T_USER u1 ON u1.id_user = mtc.id_user and u1.cf = mtc.cf "
        + "       where mriu.data_fine IS NULL AND data_cessazione IS null and mtc.cf= u.cf and "
        + "       (mtc.id_user = :idUser or mtc.id_user = mriu.id_utente) "
        + "    order by u1.id_user nulls first limit 1) "
        + "  LIMIT 1", nativeQuery = true)
    MudeTContatto findPMByIdIstanza(@Param("idIstanza") Long idIstanza, @Param("idUser") Long idUser);
    /**
     * Find by idIstanza mude t contatto collaboratore del PM istanza.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto
     * */
    /* da prioritario all'ultimo contatto in rubrica oppure se non presente al contatto accreditato */
    @Query(value = " SELECT c.* "
    	+ "  FROM  MUDEOPEN_R_ISTANZA_UTENTE mriu "
    	+ "       INNER JOIN MUDEOPEN_D_ABILITAZIONE mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.CODICE_ABILITAZIONE in ('COLLAB_PM_RUP_PM_OBB','COLLAB_PM_RUP_PM_OPZ'))"
        + "       INNER JOIN MUDEOPEN_T_USER u ON mriu.id_utente = u.id_user "
        + "       INNER JOIN mudeopen_t_contatto c ON u.cf = c.cf "
        + "       WHERE  mriu.id_istanza = :idIstanza and c.id_contatto = ( "
        + "       select mtc.id_contatto  from mudeopen_t_contatto mtc "
        + "       LEFT JOIN MUDEOPEN_T_USER u1 ON u1.id_user = mtc.id_user and u1.cf = mtc.cf "
        + "       where mriu.data_fine IS NULL AND data_cessazione IS null and mtc.cf= u.cf and "
        + "       (mtc.id_user = :idUser or mtc.id_user = mriu.id_utente) "
        + "    order by u1.id_user nulls first limit 1)"
        + "  LIMIT 1", nativeQuery = true)
    MudeTContatto findCollaboratorePMByIdIstanza(@Param("idIstanza") Long idIstanza, @Param("idUser") Long idUser);

	@Query("SELECT o FROM MudeTContatto o WHERE o.guid = :fruitoreID AND CONCAT(o.nome, o.cognome, o.ragioneSociale) = :nomeCognomeRagione")
	MudeTContatto findContattoByFruitore(@Param("fruitoreID") String fruitoreID, @Param("nomeCognomeRagione") String nomeCognomeRagione);

    /**
     * Find by idIstanza mude t contatto professionista istanza.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto
     * NON USATA */
    @Query(value = "SELECT c.* "
		    		+ "  FROM  mudeopen_r_istanza_utente mriu "
		    		+ "       INNER JOIN mudeopen_d_abilitazione mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.codice_abilitazione in ('PM_RUP_PM_OBB','PM_RUP_PM_OPZ'))"
		    		+ "       INNER JOIN mudeopen_t_user u ON mriu.id_utente = u.id_user "
		    		+ "       INNER JOIN mudeopen_t_contatto c ON c.id_user=u.id_user AND u.cf = c.cf AND c.data_cessazione IS NULL AND c.tipo_contatto = 'PF' "
		    		+ "       INNER JOIN mudeopen_r_istanza_soggetto si ON si.id_soggetto=c.id_contatto AND si.id_istanza=mriu.id_istanza "
		    		+ "  WHERE mriu.data_fine IS NULL AND mriu.id_istanza = :idIstanza "
		    		+ "  LIMIT 1", nativeQuery = true)
    MudeTContatto findUpdatedPMByIdIstanza(@Param("idIstanza") Long idIstanza);

    /**
     * Find by idIstanza mude t contatto professionista istanza.
     *
     * @param idIstanza the id istanza
     * @return the mude t contatto
     * NON USATA */
    @Query(value = "SELECT DISTINCT mtc.* FROM mudeopen_r_istanza_quadro_utente mriqu "
	    		+ "    INNER JOIN mudeopen_t_user mtu ON mtu.id_user=mriqu.id_utente AND fine_validita IS NULL "
	    		+ "    INNER JOIN mudeopen_t_contatto mtc ON mtc.cf=mtu.cf AND mtc.id_user=mtu.id_user AND mtc.data_cessazione IS NULL AND mtc.tipo_contatto = 'PF' "
	    		+ "  WHERE id_istanza = :idIstanza", nativeQuery = true)
    List<MudeTContatto> retrieveInstanceWorkingContacts(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT CONCAT(lmtc.guid, '~', MAX(lmtc.id_contatto)) "
	    		+ "    FROM mudeopen_r_istanza_soggetto mris "
	    		+ "      INNER JOIN mudeopen_t_contatto mtc  ON mtc.id_contatto = mris.id_soggetto\n"
	    		+ "      INNER JOIN mudeopen_t_contatto lmtc ON mtc.id_contatto != lmtc.id_contatto AND mtc.id_user = lmtc.id_user AND mtc.guid = lmtc.guid AND lmtc.data_cessazione IS NULL\n"
	    		+ "    WHERE mris.id_istanza = :idIstanza\n"
	    		+ "    GROUP BY lmtc.guid", nativeQuery = true)
    List<String> findUpdatedInstanceSubjectIDs(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT mtc.guid"
	    		+ "    FROM mudeopen_r_istanza_soggetto mris "
	    		+ "      INNER JOIN mudeopen_r_istanza_soggetto_ruoli mrisr ON mris.id_istanza_soggetto=mrisr.id_istanza_soggetto"
	    		+ "      INNER JOIN ("
	    		+ "        SELECT roles.position as roleOrderById, REPLACE(CAST(roles.item_object AS varchar), '\"', '') as ruolo"
	    		+ "            FROM ("
	    		+ "                    SELECT CAST ('['||REGEXP_REPLACE(COALESCE(propIfExists.valore, InstanceRolesOrderedById.roles), '([A-Z]+)', '\"\\1\"', 'g')||']' as jsonb) as confOrd"
	    		+ "                        FROM    ("
	    		+ "                                    SELECT MAX(valore) as valore FROM mudeopen_c_proprieta mcp WHERE nome = ('PDF_ORDINE_RUOLI_SOGGETTI_' || REPLACE(:tipoIstanza, '-', '_'))"
	    		+ "                                ) AS propIfExists,"
	    		+ "                                ("
	    		+ "                                    SELECT string_agg(r.ruoli, ',') as roles FROM ("
	    		+ "                                        SELECT mrisr.ruoli "
	    		+ "                                            FROM mudeopen_r_istanza_soggetto mris "
	    		+ "                                              INNER JOIN mudeopen_r_istanza_soggetto_ruoli mrisr ON mris.id_istanza_soggetto=mrisr.id_istanza_soggetto"
	    		+ "                                            WHERE mris.id_istanza = :idIstanza"
	    		+ "                                            GROUP BY mrisr.ruoli"
	    		+ "                                            ORDER BY MAX(mrisr.id_istanza_soggetto)"
	    		+ "                                        ) AS r"
	    		+ "                                ) AS InstanceRolesOrderedById"
	    		+ "                ) AS roleRows, "
	    		+ "                jsonb_array_elements(roleRows.confOrd) with ordinality roles(item_object, position)"
	    		+ "      ) AS orderedRoles ON mrisr.ruoli=orderedRoles.ruolo"
	    		+ "      INNER JOIN mudeopen_t_contatto mtc ON mris.id_soggetto=mtc.id_contatto"
	    		+ "    WHERE mris.id_istanza = :idIstanza"
	    		+ "    GROUP BY mtc.guid"
	    		+ "    ORDER BY MIN(orderedRoles.roleOrderById)", nativeQuery = true)
    List<String> getContactGuidsOrderedByConf(@Param("idIstanza") Long idIstanza, @Param("tipoIstanza") String tipoIstanza);

    @Query(value = "SELECT mtc.guid"
	    		+ " FROM mudeopen_t_contatto con"
	    		+ "  INNER JOIN mudeopen_r_istanza_soggetto b ON con.id_contatto = b.id_soggetto "
	    		+ "  INNER JOIN mudeopen_r_istanza_soggetto_ruoli c ON b.id_istanza_soggetto = c.id_istanza_soggetto and c.ruoli IN (:ruoliSoggetto) "
	    		+ "  INNER JOIN mudeopen_t_contatto mtc ON con.guid = mtc.guid and mtc.data_cessazione IS NULL AND con.id_user = mtc.id_user  "
	    		+ " WHERE b.id_istanza = :idIstanza "
	    		+ " ORDER BY b.id_istanza_soggetto", nativeQuery = true)
    List<String> findGUIDsByIstanzaRoles(@Param("idIstanza") Long idIstanza, @Param("ruoliSoggetto") String[] ruoliSoggetto);

}