/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;

@Repository
public interface MudeDAbilitazioneRepository extends BaseRepository<MudeDAbilitazione,Long> {

    List<MudeDAbilitazione> findAllByDataFineNull();

    MudeDAbilitazione findBycodiceAndDataFineIsNull(String codiceAbilitazione);
    
    MudeDAbilitazione findMudeDAbilitazioneByCodiceEquals(String codiceAbilitazione);

    @Query("SELECT iu.abilitazione.codice FROM MudeRIstanzaUtente iu "
    		+ "    INNER JOIN iu.istanza "
    		+ "    INNER JOIN iu.abilitazione "
    		+ "  WHERE iu.dataFine IS Null "
    		+ "    AND iu.istanza.id = :idIstanza"
    		+ "    AND iu.utente.idUser = :idUtente")
    List<String> getListaAbilitazioniByIstanzaAndUtente(@Param("idIstanza") Long idIstanza, @Param("idUtente") Long idUtente);
/*    @Query(value = "SELECT DISTINCT da.* "
		    		+ "  FROM "
		    		+ "      mudeopen_r_fascicolo_utente fu"
		    		+ "    , mudeopen_r_istanza_utente iu"
		    		+ "    , mudeopen_r_abilitazione_funzione af"
		    		+ "    , mudeopen_d_funzione df"
		    		+ "    , mudeopen_d_abilitazione da"
		    		+ "  WHERE"
		    		+ "    ("
		    		+ "      (fu.id_fascicolo=:idFascicolo AND fu.id_utente=:idUtente AND fu.data_fine IS NULL AND af.data_fine IS NULL AND fu.id_abilitazione=af.id_abilitazione)"
		    		+ "      OR (iu.id_istanza=:idIstanza AND iu.id_utente=:idUtente AND iu.data_fine IS NULL AND af.data_fine IS NULL AND iu.id_abilitazione=af.id_abilitazione)"
		    		+ "    )"
		    		+ "    AND af.id_funzione=df.id_funzione"
		    		+ "    AND (df.nomina_abiltiazione IS NOT NULL AND da.codice_abilitazione LIKE CONCAT(df.nomina_abiltiazione, '%'))    ", nativeQuery = true)

		
 */
    @Query(value = "SELECT   da.*   "
    		+ "      FROM       mudeopen_r_fascicolo_utente fu    , "
    		+ "        mudeopen_r_abilitazione_funzione af    , "
    		+ "        mudeopen_d_funzione df    , "
    		+ "        mudeopen_d_abilitazione da  "
    		+ "        WHERE      (fu.id_fascicolo=:idFascicolo AND fu.id_utente=:idUtente AND fu.data_fine IS NULL AND af.data_fine IS NULL AND fu.id_abilitazione=af.id_abilitazione)"
    		+ "            AND af.id_funzione=df.id_funzione    AND (df.nomina_abiltiazione IS NOT NULL AND da.codice_abilitazione LIKE CONCAT(df.nomina_abiltiazione, '%'))    "
    		+ "union "
    		+ "      SELECT   da.*   "
    		+ "      FROM       "
    		+ "        mudeopen_r_istanza_utente iu    , "
    		+ "        mudeopen_r_abilitazione_funzione af    , "
    		+ "        mudeopen_d_funzione df    , "
    		+ "        mudeopen_d_abilitazione da  "
    		+ "        WHERE    (iu.id_istanza=:idIstanza AND iu.id_utente= :idUtente AND iu.data_fine IS NULL AND af.data_fine IS NULL AND iu.id_abilitazione=af.id_abilitazione)  "
    		+ "            AND af.id_funzione=df.id_funzione    AND (df.nomina_abiltiazione IS NOT NULL AND da.codice_abilitazione LIKE CONCAT(df.nomina_abiltiazione, '%'))", nativeQuery = true)
    List<MudeDAbilitazione> getPermissionsAssignableForUser(@Param("idUtente") Long idUtente, @Param("idFascicolo") Long idFascicolo, @Param("idIstanza") Long idIstanza);

    @Query("SELECT iu.utente.idUser FROM MudeRIstanzaUtente iu "
    		+ "    INNER JOIN iu.istanza "
    		+ "    INNER JOIN iu.abilitazione "
    		+ "  WHERE iu.dataFine IS Null "
    		+ "    AND iu.istanza.id = :idIstanza"
    		+ "    AND iu.abilitazione.codice = :codAbilt"
    		+ "    AND NOT(iu.utente.idUser = :idUser)")
    List<Long> getListaAbilitazioniByIstanzaAndCodiceAbilit(@Param("idIstanza") Long idIstanza, @Param("codAbilt") String codAbilt, @Param("idUser") Long idUser);

}