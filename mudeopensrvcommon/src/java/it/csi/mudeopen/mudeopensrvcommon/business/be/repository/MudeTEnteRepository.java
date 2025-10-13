/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteEnteComuneRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeTEnteRepository extends BaseRepository<MudeTEnte, Long> {

    @Query("select o from MudeTEnte o where o.codice = :codice")
    MudeTEnte findByCodice(@Param("codice") String codice);

    @Query("select o from MudeTEnte o where o.comune.idComune = :idComune and o.dataFine is null")
    List<MudeTEnte> findAllActiveByComune(@Param("idComune") Long idComune);

    @Query("select o from MudeTEnte o where o.comune.idComune = :idComune and o.tipoEnte.codice = :idTipoEnte and o.dataFine is null")
    MudeTEnte findActiveByComuneAndTipoEnte(@Param("idComune") Long idComune, @Param("idTipoEnte") String codiceTipoEnte);

    @Query(value = "SELECT mte.* "
    		+ "  FROM mudeopen_t_ente mte"
    		+ "    INNER JOIN mudeopen_r_ente_comune_tipo_istanza mreti ON (mreti.id_ente = mte.id_ente AND (mreti.data_fine IS NULL OR mreti.data_fine > NOW()) AND mreti.competenza_principale = true)"
    		+ "  WHERE  mte.data_fine IS NULL"
    		+ "    AND mreti.id_comune = :idComune"
    		+ "    AND mreti.codice_tipo_istanza = :codTipoIstanza"
    		+ "    AND (mreti.codice_specie_pratica IS NULL OR mreti.codice_specie_pratica = :codSpeciePratica)"
    		+ "  ORDER BY codice_specie_pratica DESC NULLS LAST"
    		+ "  LIMIT 1", nativeQuery = true)
	MudeTEnte retrieveMainActive(@Param("idComune") Long idComune, @Param("codTipoIstanza") String codTipoIstanza, @Param("codSpeciePratica") String codSpeciePratica);

    @Query(value = "SELECT mte.* "
    		+ "  FROM mudeopen_t_ente mte"
    		+ "    INNER JOIN mudeopen_r_ente_comune_tipo_istanza mreti ON (mreti.id_ente = mte.id_ente AND mreti.competenza_principale = true)"
    		+ "  WHERE  mte.data_fine IS NULL"
    		+ "    AND mreti.id_comune = :idComune"
    		+ "    AND mreti.codice_tipo_istanza = :codTipoIstanza"
    		+ "    AND (mreti.codice_specie_pratica IS NULL OR mreti.codice_specie_pratica = :codSpeciePratica)"
    		+ "  ORDER BY codice_specie_pratica DESC NULLS LAST, mreti.data_fine DESC NULLS FIRST"
    		+ "  LIMIT 1", nativeQuery = true)
	MudeTEnte retrieveLastActive(@Param("idComune") Long idComune, @Param("codTipoIstanza") String codTipoIstanza, @Param("codSpeciePratica") String codSpeciePratica);

    @Query(value = "select distinct mte.* from " + 
    		"mudeopen_t_ente mte, " + 
    		"mudeopen_t_istanza mti, " +  
    		"mudeopen.mudeopen_r_istanza_ente mrie, " + 
    		"mudeopen_r_utente_ente_comune_ruolo mruecr " + 
    		"where " + 
    		"mruecr.id_utente = :idUtente " + 
    		"and mrie.id_istanza =:idIstanza  " + 
    		"and mti.id_comune = mruecr.id_comune  " + 
    		"and mruecr.id_ente = mrie.id_ente " + 
    		"and mrie.id_ente = mte.id_ente " +
    		"and mti.id_istanza = mrie.id_istanza " + 
    		"and mrie.ente_ricevente =true " +
    		"and mrie.data_fine is null ", nativeQuery = true)
    MudeTEnte getEnteRiceventeByUtenteIstanza(@Param("idUtente") Long idUtente, @Param("idIstanza") Long idIstanza);

    @Query(value = "select mte.* "
		+ "  from mudeopen_t_ente mte, mudeopen_r_istanza_ente mrie "
		+ "  WHERE mrie.id_istanza = :idIstanza" 
		+ "  and mrie.ente_ricevente = true "
		+ "  and mrie.data_fine is null "
		+ " and mrie.id_ente = mte.id_ente" , nativeQuery = true)
    MudeTEnte getEnteRiceventeByIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT * FROM mudeopen.mudeopen_t_ente\n"
    		+ "WHERE data_fine IS NULL OR data_fine > now()\n"
    		+ "ORDER BY codice", nativeQuery = true)
	List<MudeTEnte> listaEnti();

    @Query(value = "SELECT mte.* "
    		+ "    FROM (SELECT id_ente, id_comune, id_utente FROM mudeopen_r_utente_ente_comune_ruolo WHERE data_fine IS NULL  GROUP BY id_ente, id_comune, id_utente) mrue "
    		+ "      INNER JOIN mudeopen_t_istanza mti ON mti.id_comune = mrue.id_comune AND  mti.data_fine IS NULL "
    		+ "      INNER JOIN mudeopen_r_istanza_ente mrie ON mrie.id_ente = mrue.id_ente AND mrie.id_istanza = mti.id_istanza AND mrie.data_fine IS NULL "
    		+ "      INNER JOIN mudeopen_t_ente mte ON mte.id_ente = mrue.id_ente AND  mte.data_fine IS NULL "
    		+ "    WHERE mrue.id_utente = :idUserOperator AND mrie.id_istanza = :idIstanza "
    		+ "    LIMIT 1", nativeQuery = true)
	MudeTEnte retrieveEnteFromOperator(@Param("idUserOperator") Long idUserOperator, @Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT array_to_string(array_agg(mtu.mail), ', ')"
		    		+ "  FROM mudeopen_r_utente_ente mrue "
		    		+ "    INNER JOIN mudeopen_t_user mtu ON mrue.id_utente = mtu.id_user AND mtu.fine_validita IS NULL AND mtu.mail IS NOT NULL"
		    		+ " WHERE id_ente = :idEnte", nativeQuery = true)
	String retrieveEnteOperatorsEmails(@Param("idEnte") Long idEnte);

   @Query(value = "SELECT mte.* "
    		+ "  FROM mudeopen_t_ente mte"
    		+ "    INNER JOIN mudeopen_r_ente_comune_tipo_istanza mreti ON (mreti.id_ente = mte.id_ente AND (mreti.data_fine IS NULL OR mreti.data_fine > NOW()))"
    		+ "  WHERE  mte.data_fine IS NULL"
    		+ "    AND mreti.id_comune = :idComune"
    		+ "    AND mreti.codice_tipo_istanza = :codTipoIstanza"
    		+ "    AND (mreti.codice_specie_pratica IS NULL OR mreti.codice_specie_pratica = :codSpeciePratica)"
    		+ "    AND id_tipo_ente='TENTE07UTR'"
    		+ "  ORDER BY codice_specie_pratica DESC NULLS LAST"
    		+ "  LIMIT 1", nativeQuery = true)
	MudeTEnte getUTR(@Param("idComune") Long idComune, @Param("codTipoIstanza") String codTipoIstanza, @Param("codSpeciePratica") String codSpeciePratica);

}