/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;

@Repository
//public interface MudeTIstanzaRepository extends CrudRepository<MudeTIstanza, Long>, JpaSpecificationExecutor<MudeTIstanza>{
public interface MudeTIstanzaRepository extends BaseRepository<MudeTIstanza, Long> {

    List<MudeTIstanza> findByMudeTFascicolo(MudeTFascicolo mudeTFascicolo);

	@Query(value = "SELECT count(id_istanza) FROM mudeopen_t_istanza WHERE id_fascicolo = :idFascicolo AND data_fine IS NULL", nativeQuery = true)
    int countIstanzeByFascicolo(@Param("idFascicolo") Long idFascicolo);

    @Query("SELECT jsonData FROM MudeTIstanza o WHERE o.id = :idIstanza")
    String getJDataByIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query("SELECT o FROM MudeTIstanza o WHERE o.codiceIstanza = :codIstanza and o.filename = :filename")
    Optional<MudeTIstanza> findByCodIstanzaAndFilename(@Param("codIstanza") String codIstanza, @Param("filename") String filename);

    @Query("SELECT o FROM MudeTIstanza o WHERE REPLACE(o.codiceIstanza, '-', '') = REPLACE(:codIstanza, '-', '')")
    MudeTIstanza findByCodIstanza(@Param("codIstanza") String codIstanza);

    MudeTIstanza findByIdIstanzaRiferimento(Long IStanzaRifeimento);

    List<MudeTIstanza> findByTemplate_IdTemplate(Long idTemplate);

    @Query(value = "select o.*, NULL as formula_col FROM  mudeopen_t_istanza o inner join mudeopen_r_istanza_stato s on s.id_istanza = o.id_istanza " 
    				+ " and s.data_fine is null where (s.codice_stato_istanza = 'BZZ' OR s.codice_stato_istanza = 'RPA') " 
    				+ "and o.id_template in (select t.id_template FROM mudeopen_d_template t WHERE t.id_tipo_istanza = :idTipoIstanza )", nativeQuery = true)
    List<MudeTIstanza> findByAllCodeTipoIstanza(@Param("idTipoIstanza") String idTipoIstanza);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM MudeTIstanza o WHERE o.id = ?1")
    MudeTIstanza findOneAndLockRow(Long id);

    @Query(value = "SELECT DISTINCT mti.*, NULL as formula_col " 
			+ "  FROM         mudeopen_r_fascicolo_soggetto       mrfs " 
			+ "    INNER JOIN     mudeopen_r_istanza_soggetto       mris   ON(mrfs.id_istanza_soggetto = mris.id_istanza_soggetto) " 
			+ "    INNER JOIN     mudeopen_r_istanza_soggetto_ruoli     mrisr   ON(mrisr.id_istanza_soggetto = mris.id_istanza_soggetto) " 
			+ "    INNER JOIN     mudeopen_t_istanza             mti   ON(mris.id_istanza = mti.id_istanza) " 
			+ "  WHERE 1=1 " 
			+ "    AND mrfs.id_fascicolo = :idFascicolo " 
			+ "    AND mrfs.guid_soggetto = :guidSoggetto " 
			+ "    AND mrisr.ruoli = :ruolo", nativeQuery = true)
    List<MudeTIstanza> getInstancesByIdFascicoloAndGuidSoggettoAndRuoli(@Param("idFascicolo") Long idFascicolo, @Param("guidSoggetto") String guidSoggetto, @Param("ruolo") String ruolo);

    @Query(value = "WITH istancesInFolder AS (SELECT iv.* FROM mudeopen_t_istanza iv"
    		+ "                    INNER JOIN mudeopen_r_istanza_stato isv ON (iv.id_istanza=isv.id_istanza AND isv.data_fine IS NULL)"
    		+ "                    INNER JOIN mudeopen_d_stato_istanza mdsi ON (mdsi.codice=isv.codice_stato_istanza AND mdsi.livello>=100 AND mdsi.data_fine IS NULL)"
    		+ "                  WHERE id_fascicolo=:idFascicolo)"
    		+ "SELECT distinct mti.*, NULL as formula_col "
    		+ "  FROM istancesInFolder  i_dn1"
    		+ "      LEFT JOIN istancesInFolder  dn1_up1 ON dn1_up1.id_istanza_riferimento=i_dn1.id_istanza"
    		+ "      LEFT JOIN istancesInFolder  dn1_up2 ON dn1_up2.id_istanza_riferimento=dn1_up1.id_istanza"
    		+ "      LEFT JOIN istancesInFolder  dn1_up3 ON dn1_up3.id_istanza_riferimento=dn1_up2.id_istanza"
    		+ "    LEFT JOIN istancesInFolder  i_dn2 ON i_dn2.id_istanza=i_dn1.id_istanza_riferimento "
    		+ "      LEFT JOIN istancesInFolder  dn2_up1 ON dn2_up1.id_istanza_riferimento=i_dn2.id_istanza"
    		+ "      LEFT JOIN istancesInFolder  dn2_up2 ON dn2_up2.id_istanza_riferimento=dn2_up1.id_istanza"
    		+ "      LEFT JOIN mudeopen_t_istanza dn2_up3 ON dn2_up3.id_istanza_riferimento=dn2_up2.id_istanza"
    		+ "    LEFT JOIN istancesInFolder  i_dn3 ON i_dn3.id_istanza=i_dn2.id_istanza_riferimento"
    		+ "      LEFT JOIN istancesInFolder  dn3_up1 ON dn3_up1.id_istanza_riferimento=i_dn3.id_istanza"
    		+ "      LEFT JOIN istancesInFolder  dn3_up2 ON dn3_up2.id_istanza_riferimento=dn3_up1.id_istanza"
    		+ "      LEFT JOIN istancesInFolder  dn3_up3 ON dn3_up3.id_istanza_riferimento=dn3_up2.id_istanza"
    		+ "    INNER JOIN istancesInFolder  mti ON (  "
    		+ "                        mti.id_istanza=i_dn1.id_istanza"
    		+ "                    OR     mti.id_istanza=dn1_up1.id_istanza"
    		+ "                    OR     mti.id_istanza=dn1_up2.id_istanza"
    		+ "                    OR     mti.id_istanza=dn1_up3.id_istanza"
    		+ "                    OR    mti.id_istanza=i_dn2.id_istanza"
    		+ "                    OR     mti.id_istanza=dn2_up1.id_istanza"
    		+ "                    OR     mti.id_istanza=dn2_up2.id_istanza"
    		+ "                    OR     mti.id_istanza=dn2_up3.id_istanza"
    		+ "                    OR    mti.id_istanza=i_dn3.id_istanza"
    		+ "                    OR     mti.id_istanza=dn3_up1.id_istanza"
    		+ "                    OR     mti.id_istanza=dn3_up2.id_istanza"
    		+ "                    OR     mti.id_istanza=dn3_up3.id_istanza"
    		+ "      )"
    		+ "  WHERE i_dn1.id_istanza =:idIstanza AND NOT(mti.id_istanza = :idIstanza)", nativeQuery = true)
    List<MudeTIstanza> getAllConnectedInstances(@Param("idFascicolo") Long idFascicolo, @Param("idIstanza") Long idIstanza);

    List<MudeTIstanza> findAllByMudeTFascicolo_idAndMudeTUser_idUser(Long idFascicolo, Long idCreatorUser);

    @Query(value = 
    		"select CAST(" + 
    		" json_data->'TAB_QUALIF_1'->'opere_in_precario_su_suolo_pubblico' as varchar(10)) as opere_precarie" + 
    		" from mudeopen_t_istanza mti where mti.id_istanza = :idIstanza",
    		nativeQuery = true)
    String findOccSuoloPubblico(@Param("idIstanza") Long idIstanza);

    @Query(value = 
    		"select CAST(json_data as varchar(50000))" + 
    		" from mudeopen_t_istanza mti where mti.id_istanza = :idIstanza",
    		nativeQuery = true)
    String findJSonData(@Param("idIstanza") Long idIstanza);

    // j874 
    @Query(value = "SELECT COALESCE(CASE WHEN (json_data->'TAB_QUALIF_1'->'opere_in_precario_su_suolo_pubblico') = 'null' THEN  null ELSE (json_data->'TAB_QUALIF_1'->'opere_in_precario_su_suolo_pubblico') END,\n"
    		+ "      CASE WHEN (json_data->'TAB_IND_PROC'->'opere_in_precario_su_suolo_pubblico') = 'null' THEN  null ELSE (json_data->'TAB_IND_PROC'->'opere_in_precario_su_suolo_pubblico') END, \n"
    		+ "      'false')\\:\\:boolean\n"
    		+ "  FROM mudeopen_t_istanza mti \n"
    		+ "  WHERE mti.id_istanza = :idIstanza", nativeQuery = true)
	boolean getOpereInPrecario(@Param("idIstanza") Long idIstanza);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "INSERT INTO mudeopen_t_istanze_ext (id_istanza, hash_file_istanza_generato, pdf_cache, id_user_ins, loguser) "
    		+ "    VALUES (:idIstanza, :hash, :content, :id_user, :loguser)"
    		+ "ON CONFLICT (id_istanza) DO UPDATE "
    		+ "    SET hash_file_istanza_generato = excluded.hash_file_istanza_generato, "
    		+ "       pdf_cache = excluded.pdf_cache,"
    		+ "        id_user_ins = excluded.id_user_ins,"
    		+ "        loguser = excluded.loguser", nativeQuery = true)
    void savePdfCache(@Param("idIstanza") Long idIstanza, @Param("hash") String hash, @Param("content") byte[] content, @Param("id_user") Long id_user, @Param("loguser") String loguser);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE mudeopen_t_istanze_ext SET hash_file_istanza_generato = null, pdf_cache = null WHERE id_istanza = :idIstanza", nativeQuery = true)
    void removePdfCache(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT exists (SELECT 1 FROM mudeopen_t_istanze_ext WHERE pdf_cache IS NOT NULL AND id_istanza = :idIstanza)", nativeQuery = true)
    boolean checkCachedPdfExists(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT hash_file_istanza_generato FROM mudeopen_t_istanze_ext WHERE pdf_cache IS NOT NULL AND id_istanza = :idIstanza", nativeQuery = true)
    String retrievePdfHashcode(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT pdf_cache FROM mudeopen_t_istanze_ext WHERE id_istanza = :idIstanza", nativeQuery = true)
    byte[] getCachedPdfIstanza(@Param("idIstanza") Long idIstanza);

    @Query("SELECT o FROM MudeTIstanza o WHERE o.id = :idIstanza")
    MudeTIstanza findByIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT CONCAT(codice_stato_istanza, '~', count(codice_stato_istanza)) "
    		+ " FROM mudeopen_r_istanza_stato "
    		+ " WHERE DATA_FINE IS NULL "
    		+ "   AND id_istanza IN (:idIstanze) "
    		+ " GROUP BY codice_stato_istanza", nativeQuery = true)
    List<String> getAllStateCunters(@Param("idIstanze") List<Long> idIstanze);

    @Query(value = "SELECT b.denom_comune "
    	+ " FROM mudeopen_t_istanza a "
    	+ "   INNER JOIN mudeopen_d_comune b ON a.id_comune = b.id_comune AND ((b.fine_validita IS NULL OR b.fine_validita > now()) AND (b.inizio_validita IS NULL OR b.inizio_validita <= now())) "
    	+ " WHERE a.id_istanza = :idIstanza ", nativeQuery = true)
    String getDenomComuneByIdIstanza(@Param("idIstanza") Long idIstanza);
    @Query(value = "select distinct mti.*, NULL as formula_col FROM " + 
    		"mudeopen_t_istanza mti, " + 
    		"mudeopen_r_istanza_stato mris, " + 
    		"mudeopen_r_istanza_ente mrie, " + 
    		"mudeopen_r_utente_ente_comune_ruolo mruecr " + 
    		"where " + 
    		"mruecr.id_utente = :idUtente " + 
    		"and mti.id_comune = mruecr.id_comune  " + 
    		"and mruecr.id_ente = mrie.id_ente " + 
    		"and mti.id_istanza = mrie.id_istanza " + 
    		"and mti.id_istanza = mris.id_istanza " + 
    		"and mrie.data_fine is null ", nativeQuery = true)
    List<MudeTIstanza> getInstancesByRuoli(@Param("idUtente") Long idUtente);

    @Query(value = "select distinct mti.*, NULL as formula_col FROM " + 
    		"mudeopen_t_istanza mti, " + 
    		"mudeopen_r_istanza_stato mris, " + 
    		"mudeopen_r_istanza_ente mrie, " + 
    		"mudeopen_r_utente_ente_comune_ruolo mruecr, " + 
    		"mudeopen_t_ente mte " +
    		"where " + 
    		"mruecr.id_utente = :idUtente " + 
    		"and mti.id_comune = mruecr.id_comune  " + 
    		"and mruecr.id_ente = mrie.id_ente " + 
    		"and mte.id_ente = mrie.id_ente " +
    		"and mti.id_istanza = mrie.id_istanza " + 
    		"and mrie.ente_ricevente = true " + 
    		"and mti.id_istanza = mris.id_istanza " + 
    		"and mrie.data_fine is null ", nativeQuery = true)
    List<MudeTIstanza> getInstancesBySportelloRicevente(@Param("idUtente") Long idUtente);
    @Query(value = "select distinct mti.*, NULL as formula_col FROM " + 
    		"mudeopen_t_istanza mti, " + 
    		"mudeopen_r_istanza_pratica mris, " + 
    		"mudeopen_t_pratica mtp " +     		
    		"where " + 
    		"mti.id_istanza = mris.id_istanza " + 
    		"and mris.id_pratica = mtp.id_pratica " + 
    		"and mris.id_pratica = :idPratica ", nativeQuery = true)
    List<MudeTIstanza> getInstancesByPratica(@Param("idPratica") Long idPratica);

    @Query(value = "select nextval('mudeopen_t_istanza_id_istanza_seq')", nativeQuery = true)
    Long getNextIdIstanza();

	@Query(value = "select mti.codice_istanza from mudeopen_t_istanza mti "
			+ "inner join mudeopen_r_istanza_stato mris on mris.id_istanza = mti.id_istanza "
			+ "where mti.id_fascicolo =:idFascicolo and mris.codice_stato_istanza=:statoIstanza and mris.data_fine is null "
			+ "and mti.id_tipo_istanza =:tipoIstanza and mti.id_istanza !=:idIstanza", nativeQuery = true)
    List<String> getCodiciIstanzeInFascicoloByTipoIstanzaAndStato(@Param("idFascicolo")Long idFascicolo, @Param("tipoIstanza")String tipoIstanza, @Param("statoIstanza")String statoIstanza, @Param("idIstanza")Long idIstanza);
	
	
    @Query("select distinct mti.id FROM " + 
    		"MudeTIstanza mti, " +     		
    		"MudeRIstanzaEnte mrie, " + 
    		"MudeRUtenteEnteComuneRuolo mruecr where " +
    		"mti.id = mrie.istanza.id and mrie.dataFine is null " +
    		"and mruecr.idEnte = mrie.ente.id and mruecr.dataFine is null " +
    		"and mti.comune.idComune = mruecr.idComune " + 
    		"and mruecr.idUtente = :idUtente ")
    List<Long> getInstancesIdByRuoli(@Param("idUtente") Long idUtente);
    @Query("select distinct mti.id FROM " + 
    		"MudeTIstanza mti, " + 
    		"MudeRIstanzaStato mris, " + 
    		"MudeRIstanzaEnte mrie, " + 
    		"MudeRUtenteEnteComuneRuolo mruecr where " +
    		"mti.id = mrie.istanza.id and mrie.dataFine is null " +
    		"and mruecr.idEnte = mrie.ente.id and mruecr.dataFine is null " +
    		"and mti.comune.idComune = mruecr.idComune " + 
    		"and mti.id = mris.istanza.id and mris.dataFine is null " +
    		"and mris.statoIstanza.codice in (:stati) " +
    		"and mruecr.idUtente = :idUtente ")
    List<Long> getInstancesIdByRuoliScrivania(@Param("idUtente") Long idUtente, @Param("stati") String[] stati);

    @Query("select distinct mti.id FROM " + 
    		"MudeTIstanza mti, " +     		
    		"MudeRIstanzaEnte mrie, " + 
    		"MudeRIstanzaPratica mrip, " + 
    		"MudeTPratica mtp, " + 
    		"MudeRUtenteEnteComuneRuolo mruecr where " +
    		"mti.id = mrie.istanza.id and mrie.dataFine is null " +
    		"and mruecr.idEnte = mrie.ente.id and mruecr.dataFine is null " +
    		"and mti.comune.idComune = mruecr.idComune " + 
    		"and mti.id = mrip.istanza.id and mtp.id = mrip.pratica.id " +
    		"and mtp.numeroPratica LIKE CONCAT('%',:numPratica,'%') " +
    		"and mruecr.idUtente = :idUtente ")
    List<Long> getInstancesIdNumPraticaByRuoli(@Param("idUtente") Long idUtente, @Param("numPratica") String numPratica);

    @Query("select distinct mti.id FROM " + 
    		"MudeTIstanza mti, " +     		
    		"MudeRIstanzaEnte mrie, " + 
    		"MudeTFascicolo mtf, " + 
    		"MudeRUtenteEnteComuneRuolo mruecr where " +
    		"mti.id = mrie.istanza.id and mrie.dataFine is null " +
    		"and mruecr.idEnte = mrie.ente.id and mruecr.dataFine is null " +
    		"and mti.comune.idComune = mruecr.idComune " + 
    		"and mti.mudeTFascicolo.id = mtf.id " + 
    		"and mtf.codiceFascicolo LIKE CONCAT('%',:codiceFascicolo,'%')  " +
    		"and mruecr.idUtente = :idUtente ")
    List<Long> getInstancesIdNumFascicoloByRuoli(@Param("idUtente") Long idUtente, @Param("codiceFascicolo") String codiceFascicolo);

    @Query("select distinct mti.id FROM " + 
    		"MudeTIstanza mti, " +     		
    		"MudeRIstanzaEnte mrie, " + 
    		"MudeRUtenteEnteComuneRuolo mruecr where " +
    		"mti.id = mrie.istanza.id and mrie.dataFine is null " +
    		"and mruecr.idEnte = mrie.ente.id and mruecr.dataFine is null " +
    		"and mti.comune.idComune = mruecr.idComune " + 
    		"and mti.codiceIstanza LIKE CONCAT('%',:codeIstanza,'%') " + 
    		"and mruecr.idUtente = :idUtente ")
    List<Long> getInstancesIdIstanzaByRuoli(@Param("idUtente") Long idUtente, @Param("codeIstanza") String codeIstanza);

    @Query("select distinct mti.id FROM " + 
    		"MudeTIstanza mti, " +     		
    		"MudeRIstanzaEnte mrie, " + 
    		"MudeRUtenteEnteComuneRuolo mruecr where " +
    		"mti.id = mrie.istanza.id and mrie.dataFine is null " +
    		"and mruecr.idEnte = mrie.ente.id and mruecr.dataFine is null " +
    		"and mti.comune.idComune = mruecr.idComune " + 
    		"and mti.comune.idComune = :idComune " + 
    		"and mruecr.idUtente = :idUtente ")
    List<Long> getInstancesIdComuneByRuoli(@Param("idUtente") Long idUtente, @Param("idComune") Long idComune);
    @Query(value = "select\n"
    		+ "  distinct mudetistan0_.id_istanza as col_0_0_\n"
    		+ "from mudeopen_t_istanza mudetistan0_\n"
    		+ "    INNER join mudeopen_r_istanza_ente muderistan1_ ON mudetistan0_.id_istanza = muderistan1_.id_istanza and (muderistan1_.data_fine is null)\n"
    		+ "    INNER join mudeopen_r_istanza_stato muderistan2_ ON mudetistan0_.id_istanza = muderistan2_.id_istanza and (muderistan2_.data_fine is null) \n"
    		+ "    INNER join mudeopen_r_utente_ente_comune_ruolo muderutent3_ ON muderutent3_.id_ente = muderistan1_.id_ente and mudetistan0_.id_comune = muderutent3_.id_comune and (muderutent3_.data_fine is null)\n"
    		+ "where muderutent3_.id_utente = :idUtente\n"
    		+ "  AND (:idTipoIstanza='' OR mudetistan0_.id_tipo_istanza = :idTipoIstanza)\n"
    		+ "  AND (:idComune=-1 OR mudetistan0_.id_comune = :idComune)\n"
    		+ "  AND (:codeIstanza='' OR mudetistan0_.codice_istanza LIKE CONCAT('%',:codeIstanza,'%'))\n"
    		+ "  AND ('' IN (:statoIstanza) OR muderistan2_.codice_stato_istanza in (:statoIstanza)) ", nativeQuery = true)
    List<Long> getInstancesIdByAllByRuoli(@Param("idUtente") Long idUtente, 
    									  @Param("idComune") Long idComune,
    									  @Param("idTipoIstanza") String idTipoIstanza,
    									  @Param("statoIstanza") String[] statoIstanza,
    									  @Param("codeIstanza") String codeIstanza);
    @Query(value = "select distinct mti.id_istanza as col_0_0_ FROM \n"
    		+ " mudeopen_t_istanza mti \n"   
    		+ "    INNER join mudeopen_r_istanza_ente mrie ON mti.id_istanza = mrie.id_istanza and (mrie.data_fine is null)\n"
    		+ "    INNER join mudeopen_r_istanza_pratica mrip ON mti.id_istanza = mrip.id_istanza \n"
    		+ "    INNER join mudeopen_t_pratica mtp ON mtp.id_pratica = mrip.id_pratica \n"
    		+ "    INNER join mudeopen_r_utente_ente_comune_ruolo mruecr ON mruecr.id_ente = mrie.id_ente and mti.id_comune = mruecr.id_comune and (mruecr.data_fine is null)\n"
    		+ " where mruecr.id_utente = :idUtente\n"
    		+ "  AND (:numPratica='' OR mtp.numero_pratica LIKE CONCAT('%',:numPratica,'%'))\n"
    		+ "  AND (:idPratica=-1 OR mtp.id_pratica = :idPratica)\n"
    		+ "  AND (:idComune=-1 OR mti.id_comune = :idComune)\n"
    		+ "  AND (:anno=-1 OR mtp.anno_pratica = :anno) ", nativeQuery = true)
    List<Long> getInstancesIdAllPraticaByRuoli(@Param("idUtente") Long idUtente, 
    									  @Param("numPratica") String numPratica,
    									  @Param("idPratica") Long idPratica,
    									  @Param("idComune") Long idComune,
    									  @Param("anno") Long anno);

    @Query(value = "select distinct mti.id_istanza as col_0_0_ FROM \n"
    		+ " mudeopen_t_istanza mti \n"   
    		+ "    INNER join mudeopen_r_istanza_ente mrie ON mti.id_istanza = mrie.id_istanza and (mrie.data_fine is null)\n"
    		+ "    INNER join mudeopen_t_fascicolo mtf ON mti.id_fascicolo = mtf.id_fascicolo \n"
    		+ "    INNER join mudeopen_r_fascicolo_stato mrfs ON mtf.id_fascicolo = mrfs.id_fascicolo and (mrfs.data_fine is null)\n"    		
    		+ "    INNER join mudeopen_r_utente_ente_comune_ruolo mruecr ON mruecr.id_ente = mrie.id_ente and mti.id_comune = mruecr.id_comune and (mruecr.data_fine is null)\n"
    		+ " where mruecr.id_utente = :idUtente\n"
    		+ "  AND (:codiceFascicolo='' OR mtf.codice_fascicolo LIKE CONCAT('%',:codiceFascicolo,'%'))\n"
    		+ "  AND (:idComune=-1 OR mti.id_comune = :idComune)\n"
    		+ "  AND (:longCreazioneDa=-1 OR extract(epoch from mtf.data_creazione) >= :longCreazioneDa)\n"
    		+ "  AND (:longCreazioneA=-1 OR extract(epoch from mtf.data_creazione) <= :longCreazioneA)\n"
    		+ "  AND (:statoFascicolo='' OR mrfs.codice_stato_fascicolo = :statoFascicolo) ", nativeQuery = true)
    List<Long> getInstancesIdAllFascicoloByRuoli(@Param("idUtente") Long idUtente, 
    											 @Param("codiceFascicolo") String codiceFascicolo,
    											 @Param("idComune") Long idComune,
    											 @Param("statoFascicolo") String statoFascicolo,
    											 @Param("longCreazioneDa") Long longCreazioneDa,
    											 @Param("longCreazioneA") Long longCreazioneA);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE mudeopen_t_istanza t \n"
    		+ "		SET json_data = jsonb_set(COALESCE(json_data, '{}'\\:\\:jsonb), '{_NEW_TEMPLATE}', ('\"\"')\\:\\:jsonb)\n"
    		+ "  FROM mudeopen_r_istanza_stato si\n"
    		+ "  WHERE\n"
    		+ "    t.id_istanza=si.id_istanza AND si.data_fine IS NULL AND si.codice_stato_istanza IN ('BZZ', 'RPA')\n"
    		+ "    AND id_tipo_istanza = :idTipoIstanza", nativeQuery = true)
    void markJsonNewTemplate(@Param("idTipoIstanza") String idTipoIstanza);

    @Query(value = "SELECT COUNT(mad_roles.id_ruolo_soggetto) = COUNT(sel_roles.ruoli) AS all_roles\n"
    		+ "    FROM (\n"
    		+ "        SELECT mti.id_istanza, mrtirs.id_ruolo_soggetto  \n"
    		+ "          FROM mudeopen_r_tipo_istanza_ruolo_sog mrtirs \n"
    		+ "            INNER JOIN mudeopen_t_istanza mti ON mrtirs.id_tipo_istanza = mti.id_tipo_istanza AND obbligatorio = true\n"
    		+ "      ) mad_roles\n"
    		+ "      LEFT JOIN (\n"
    		+ "        SELECT mris.id_istanza, mrisr.ruoli FROM mudeopen_r_istanza_soggetto mris \n"
    		+ "          INNER JOIN mudeopen_r_istanza_soggetto_ruoli mrisr ON mris.id_istanza_soggetto = mrisr.id_istanza_soggetto\n"
    		+ "      ) sel_roles ON mad_roles.id_istanza = sel_roles.id_istanza AND  sel_roles.id_istanza = mad_roles.id_istanza AND mad_roles.id_ruolo_soggetto = sel_roles.ruoli\n"
    		+ "  WHERE mad_roles.id_istanza = :idIstanza", nativeQuery = true)
    boolean areAllInstanceMandatoryRolesSelected(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT  COALESCE((mti.json_data->'TAB_QUALIF_1'->'container'->>'prot_estremi_titolo_autorizzativo_cartaceo')\\:\\:varchar, CONCAT(mtiPar.codice_istanza, ': ', mtiPar.id_tipo_istanza)) \n"
    		+ "      || '~' || \n"
    		+ "    COALESCE((mti.json_data->'TAB_QUALIF_1'->'container'->>'data_titolo_autorizzativo_cartaceo')\\:\\:varchar, TO_CHAR(mtiPar.data_dps, 'dd-mm-yyyy')) \n"
    		+ "  FROM mudeopen_t_istanza mti \n"
    		+ "    LEFT JOIN mudeopen_t_istanza mtiPar ON mtiPar.id_istanza = mti.id_istanza_riferimento\n"
    		+ "  WHERE mti.id_istanza = :idIstanza", nativeQuery = true)
    String retrieveDSReferenceInstance(@Param("idIstanza") Long idIstanza);

	// TAB_QUALIF_3[i].opera.value
    // @Query(value = "select json_array_elements((select json_data->'TAB_QUALIF_3' FROM mudeopen_t_istanza mti WHERE id_istanza = :idIstanza)\\:\\:json -> 'opere') #>> '{opera, value}'", nativeQuery = true)
    @Query(value = "SELECT json_data->'TAB_QUALIF_2'->>'titolo_intervento' FROM mudeopen_t_istanza WHERE id_istanza = :idIstanza", nativeQuery = true)
    List<String> retrieveOpereFromIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT CONCAT(COALESCE(CAST(json_data->'TAB_QUALIF_2'->>'titolo_intervento' as varchar), ''), '~', COALESCE(CAST(json_data->'TAB_QUALIF_2'->>'descrizione_intervento' as varchar), '')) FROM mudeopen_t_istanza WHERE id_istanza = :idIstanza", nativeQuery = true)
    String retrieveInterventoFromIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT CONCAT(COALESCE(CAST(mti.json_data->'TAB_QUALIF_2'->>'titolo_intervento' AS varchar), ''), '~', COALESCE(CAST(mti.json_data->'TAB_QUALIF_2'->>'descrizione_intervento' AS varchar), '')) "
	    		+ "    FROM mudeopen_t_pratica_cosmo pc "
	    		+ "        INNER JOIN mudeopen_t_pratica_cosmo mtpc ON pc.numero_pratica=mtpc.numero_pratica "
	    		+ "        INNER JOIN mudeopen_t_istanza mti ON mtpc.id_istanza=mti.id_istanza AND mti.id_tipo_istanza = 'DENUNCIA-SISMICA' "
	    		+ "    WHERE pc.id_istanza = :idIstanza "
	    		+ "    ORDER BY mti.id_istanza DESC "
	    		+ "    LIMIT 1", nativeQuery = true)
    String retrieveDSTitoloIntervento(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT mti.*, NULL as formula_col "
	    		+ "    FROM mudeopen_t_pratica_cosmo pc "
	    		+ "        INNER JOIN mudeopen_t_pratica_cosmo mtpc ON pc.numero_pratica=mtpc.numero_pratica "
	    		+ "        INNER JOIN mudeopen_t_istanza mti ON mtpc.id_istanza=mti.id_istanza AND mti.id_tipo_istanza = 'DENUNCIA-SISMICA' "
	    		+ "        INNER JOIN mudeopen_r_istanza_stato mris ON mris.id_istanza=mti.id_istanza AND mris.codice_stato_istanza NOT IN ('RES', 'ANN', 'ARC')"
	    		+ "    WHERE pc.id_istanza = :idIstanza "
	    		+ "    ORDER BY mti.id_istanza DESC "
	    		+ "    LIMIT 1", nativeQuery = true)
    MudeTIstanza retrieveMainDenunciaSismica(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT id_istanza_rif FROM mudeopen_r_fascicolo_indirizzo WHERE id_fascicolo = :idFascicolo AND data_fine IS NULL", nativeQuery = true)
    Long getIdIstanzaFromFascicoloIndirizzo(@Param("idFascicolo") Long idFascicolo);

	@Query(value = "SELECT mti.*, NULL as formula_col "
			+ "    FROM mudeopen_t_pratica_cosmo pc "
			+ "        INNER JOIN mudeopen_t_pratica_cosmo mtpc ON pc.numero_pratica=mtpc.numero_pratica "
			+ "        INNER JOIN mudeopen_t_istanza mti ON mtpc.id_istanza=mti.id_istanza "
			+ "        INNER JOIN mudeopen_r_istanza_stato mris ON mti.id_istanza=mris.id_istanza AND mris.data_fine IS NULL "
			+ "        INNER JOIN mudeopen_d_stato_istanza mdsi ON mris.codice_stato_istanza=mdsi.codice AND mdsi.livello < :livelloStatoMax "
			+ "    WHERE pc.id_istanza = :idIstanza AND NOT(mti.id_istanza = :idIstanza)", nativeQuery = true)
	List<MudeTIstanza> getIstenzeRelatedToCOSMO(@Param("idIstanza") Long idIstanza, @Param("livelloStatoMax") Integer livelloStatoMax);

}