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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaQuadroUtente;

@Repository
public interface MudeRIstanzaQuadroUtenteRepository extends BaseRepository<MudeRIstanzaQuadroUtente, Long>{

    @Modifying
    @Query("delete from MudeRIstanzaQuadroUtente o where o.idIstanza = :idIstanza")
    void deleteByIstanza(@Param("idIstanza") Long idIstanza);

    @Query("SELECT o "
    		+ "  FROM MudeRIstanzaQuadroUtente o "
    		+ "  WHERE o.idIstanza = :idIstanza "
    		+ "    AND o.idUtente = :idUserPermission"
    		+ "    AND o.dataModifica > :fromDate")
    List<MudeRIstanzaQuadroUtente> findAnyActivitySince(@Param("idIstanza") Long idIstanza, 
    															 @Param("idUserPermission") Long idUserPermission, 
    															 @Param("fromDate") Date fromDate);

    @Query("SELECT qu FROM MudeRIstanzaQuadroUtente qu,"
    		+ "      MudeTIstanza i,"
    		+ "      MudeTFascicolo f"
    		+ "    WHERE f.id = :idFascicolo"
    		+ "        AND i.mudeTFascicolo.id = f.id"
    		+ "        AND qu.idIstanza = i.id")
    List<MudeRIstanzaQuadroUtente> findAllByIdFascicolo(@Param("idFascicolo") Long idFascicolo);
}