/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;

@Repository
public interface MudeRIstanzaSoggettoRepository extends BaseRepository<MudeRIstanzaSoggetto, Long> {

    List<MudeRIstanzaSoggetto> findByMudeTContattoIn(List<MudeTContatto> contattiConCFUtente);

    MudeRIstanzaSoggetto findByMudeTContattoAndMudeTIstanza(MudeTContatto mudeTContatto, MudeTIstanza mudeTIstanza);

    List<MudeRIstanzaSoggetto> findByMudeTIstanzaAndRuoli(MudeTIstanza mudeTIstanza, String ruolo);

    List<MudeRIstanzaSoggetto> findByMudeTIstanza(MudeTIstanza mudeTIstanza);

    @Query(value = "select ruoli from mudeopen_r_istanza_soggetto_ruoli i where i.id_istanza_soggetto = ?1", nativeQuery = true)
	List<String> findRuoliByIdIstanzaSoggetto(Long idIstanzaSoggetto);

    List<MudeRIstanzaSoggetto> findAllByMudeTContattoId(Long idContatto);

    @Query("SELECT o.mudeTIstanza.mudeTFascicolo.id FROM MudeRIstanzaSoggetto o WHERE o.mudeTContatto.id =:idContatto")
    Set<Long> findAllIdFascicoloByMudeTContattoId(@Param("idContatto") Long idContatto);

    List<MudeRIstanzaSoggetto> findAllByMudeTContattoIdAndMudeTIstanza_id(Long idContatto, Long idIstanza);

//    /**
//     * Count by soggetto and stato istanza long.
//     *
//     * @param id           the id
//     * @param statoIstanza the stato istanza
//     * @return the long
//     */
//    @Query("SELECT count(o) FROM MudeRIstanzaSoggetto o WHERE o.mudeTContatto.id = :id AND o.mudeTIstanza.stato = :statoIstanza")
//    Long countBySoggettoAndStatoIstanza(@Param("id")Long id, @Param("statoIstanza")StatoIstanza statoIstanza);
    @Query(value = "SELECT count(o.id_istanza_soggetto) FROM mudeopen_r_istanza_soggetto o INNER JOIN mudeopen_r_istanza_stato s ON s.id_istanza = o.id_istanza AND s.data_fine is null WHERE o.id_soggetto = :id AND s.codice_stato_istanza not in (:statoIstanza)", nativeQuery = true)
    Long countBySoggettoAndStatoIstanza(@Param("id")Long id, @Param("statoIstanza")String... statoIstanza);
    @Query("SELECT count(o) FROM MudeRIstanzaSoggetto o WHERE o.mudeTContatto.id = :id")
    Long countBySoggetto(@Param("id")Long id);

    @Query("SELECT o FROM MudeRIstanzaSoggetto o WHERE o.mudeTIstanza.id=:idIstanza AND o.mudeTContatto.cf NOT IN (:excludeUserIDs)")
    List<MudeRIstanzaSoggetto> findByMudeTIstanzaAndEscludeUsers(@Param("idIstanza") Long idIstanza, @Param("excludeUserIDs") String[] excludeUserIDs);
    MudeRIstanzaSoggetto findMudeRIstanzaSoggettoByMudeTIstanza_IdAndMudeTContatto_Id(Long idIstanza, Long idContatto);

    @Query(value = "SELECT mrisr.ruoli FROM mudeopen_r_istanza_soggetto mris " +
            "INNER JOIN mudeopen_r_istanza_soggetto_ruoli mrisr ON mris.id_istanza_soggetto = mrisr.id_istanza_soggetto " +
            "INNER JOIN mudeopen_d_ruolo_soggetto mdrs ON mrisr.ruoli = mdrs.codice " +
            "WHERE mdrs.categoria_ruolo = :categoriaRuolo AND mris.id_soggetto = :idSoggetto AND mris.id_istanza = :idIstanza", nativeQuery = true)
    List<String> findRuoliByCategoriaAndSoggettoAndIstanza(@Param("categoriaRuolo") String categoriaRuolo, @Param("idSoggetto") Long idSoggetto, @Param("idIstanza") Long idIstanza);
    @Query(value = "SELECT mris.* "
			    		+ "    FROM mudeopen_r_istanza_soggetto mris "
			    		+ "      INNER JOIN mudeopen_t_contatto mtc ON mris.id_soggetto=mtc.id_contatto "
			    		+ "      INNER JOIN mudeopen_t_istanza mti ON mris.id_istanza=mti.id_istanza"
			    		+ "      INNER JOIN mudeopen_r_istanza_stato mris2 ON mris2.id_istanza=mti.id_istanza AND mris2.data_fine IS NULL"
			    		+ "    WHERE mris2.codice_stato_istanza IN ('BZZ', 'RPA')"
			    		+ "      AND guid = :contactGuid"
			    		+ "      AND mtc.id_user = :idUser", nativeQuery = true)
    List<MudeRIstanzaSoggetto> findAllInstancesByContactGuid(@Param("contactGuid") String contactGuid, @Param("idUser") Long idUser);

    @Modifying
    @Query(value = "UPDATE mudeopen_t_istanza SET json_data = jsonb_set("
    					+ "                        jsonb_set("
    					+ "                            jsonb_set(json_data, ARRAY['QDR_SOGGETTO_COINV', 'subjectList', :contactGuid, 'idSoggetto'], (:idNewContact)\\:\\:jsonb),"
    					+ "                             ARRAY['QDR_SOGGETTO_COINV', 'subjectList', :contactGuid, 'contact'], (:contactJsonAsString)\\:\\:jsonb), "
    					+ "                        '{QDR_SOGGETTO_COINV, _VALIDATION_TIME}', 'null'\\:\\:jsonb) "
			    		+ "WHERE (NOT(:idIstanza = -1) AND id_istanza = :idIstanza) OR (:idIstanza = -1 AND id_istanza IN ("
			    		+ "  SELECT DISTINCT mris.id_istanza "
			    		+ "    FROM mudeopen_r_istanza_soggetto mris "
			    		+ "      INNER JOIN mudeopen_t_contatto mtc ON mris.id_soggetto=mtc.id_contatto "
			    		+ "      INNER JOIN mudeopen_t_istanza mti ON mris.id_istanza=mti.id_istanza"
			    		+ "      INNER JOIN mudeopen_r_istanza_stato mris2 ON mris2.id_istanza=mti.id_istanza AND mris2.data_fine IS NULL"
			    		+ "    WHERE mris2.codice_stato_istanza IN ('BZZ', 'RPA')"
			    		+ "      AND guid = :contactGuid"
			    		+ "      AND mtc.id_user = :idUser"
			    		+ "))", nativeQuery = true)
    void updateContactForInstances(@Param("idNewContact") String idNewContact, @Param("contactGuid") String contactGuid, @Param("idUser") Long idUser, @Param("contactJsonAsString") String contactJsonAsString, @Param("idIstanza") Long idIstanza);

    @Modifying
    @Query(value = "UPDATE mudeopen_r_istanza_soggetto SET id_soggetto = :idNewContact \n"
			    		+ "WHERE id_istanza_soggetto IN (\n"
			    		+ "  SELECT mris.id_istanza_soggetto \n"
			    		+ "    FROM mudeopen_r_istanza_soggetto mris \n"
			    		+ "      INNER JOIN mudeopen_t_contatto mtc ON mris.id_soggetto=mtc.id_contatto \n"
			    		+ "      INNER JOIN mudeopen_t_istanza mti ON mris.id_istanza=mti.id_istanza\n"
			    		+ "      INNER JOIN mudeopen_r_istanza_stato mris2 ON mris2.id_istanza=mti.id_istanza AND mris2.data_fine IS NULL\n"
			    		+ "    WHERE mris2.codice_stato_istanza IN ('BZZ', 'RPA')\n"
			    		+ "      AND guid = :contactGuid\n"
			    		+ "      AND mtc.id_user = :idUser\n"
			    		+ ")\n", nativeQuery = true)
    void updateSubjectsForInstances(@Param("idNewContact") Long idNewContact, @Param("contactGuid") String contactGuid, @Param("idUser") Long idUser);

    @Modifying
 //   @Query("delete from MudeRIstanzaSoggetto o where o.mudeTIstanza.id = :idIstanza")
    void deleteByMudeTIstanza_id(@Param("idIstanza") Long idIstanza);
    
    @Query(value = "SELECT mris.* "
    		+ "    FROM mudeopen_r_istanza_soggetto mris "
    		+ "      INNER JOIN mudeopen_t_istanza mti ON mris.id_istanza=mti.id_istanza and mti.id_fascicolo = :idFascicolo "
    		+ "      INNER JOIN mudeopen_t_contatto mtc ON mris.id_soggetto=mtc.id_contatto "
    		+ "    WHERE guid = :contactGuid "
    		+ "      AND mtc.id_user = :idUser "
    		+ "    ORDER BY mtc.data_cessazione NULLS FIRST "
    		+ "	LIMIT 1", nativeQuery = true)
    MudeRIstanzaSoggetto findByContactGuidAndIstanza(@Param("idFascicolo") Long idFascicolo, @Param("contactGuid") String contactGuid, @Param("idUser") Long idUser);

    

}