/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteEnteComuneRuolo;

@Repository
public interface MudeRUtenteEnteComuneRuoloRepository extends BaseRepository<MudeRUtenteEnteComuneRuolo, Long> {

    @Query(value = "select distinct mruecr.* from " + 
    		"mudeopen_t_istanza mti, " + 
    		"mudeopen_r_istanza_stato mris, " + 
    		"mudeopen.mudeopen_r_istanza_ente mrie, " + 
    		"mudeopen_r_utente_ente_comune_ruolo mruecr " + 
    		"where " + 
    		"mruecr.id_utente = :idUtente " + 
    		"and mti.id_istanza =:idIstanza  " + 
    		"and mti.id_comune = mruecr.id_comune  " + 
    		"and mruecr.id_ente = mrie.id_ente " + 
    		"and mti.id_istanza = mrie.id_istanza " + 
    		"and mti.id_istanza = mris.id_istanza " + 
    		"and mrie.ente_ricevente =true " +
    		"and mrie.data_fine is null "
    		+ "  AND mris.data_fine IS NULL"
    		+ "  AND mruecr.data_fine IS NULL", nativeQuery = true)
    MudeRUtenteEnteComuneRuolo getRuoloEnteRiceventeByUtenteIstanza(@Param("idUtente") Long idUtente, @Param("idIstanza") Long idIstanza);
    
    @Query(value = "select distinct mruecr.* from " + 
    		"mudeopen_t_istanza mti, " + 
    		"mudeopen_r_istanza_stato mris, " + 
    		"mudeopen.mudeopen_r_istanza_ente mrie, " + 
    		"mudeopen_r_utente_ente_comune_ruolo mruecr " + 
    		"where " + 
    		"mruecr.id_utente = :idUtente " + 
    		"and mti.id_istanza =:idIstanza  " + 
    		"and mti.id_comune = mruecr.id_comune  " + 
    		"and mruecr.id_ente = mrie.id_ente " + 
    		"and mti.id_istanza = mrie.id_istanza " + 
    		"and mti.id_istanza = mris.id_istanza " + 
    		"and mrie.ente_ricevente = false " +
    		"and mrie.data_fine is null "
    		+ "  AND mris.data_fine IS NULL"
    		+ "  AND mruecr.data_fine IS NULL", nativeQuery = true)
    List<MudeRUtenteEnteComuneRuolo> getRuoloEntiTerziByUtenteIstanza(@Param("idUtente") Long idUtente, @Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM mudeopen_r_utente_ente_comune_ruolo " +
    		" WHERE id_utente = :idUtente) ", nativeQuery = true)
    Boolean existsRuoloByUtente(@Param("idUtente") Long idUtente);

}