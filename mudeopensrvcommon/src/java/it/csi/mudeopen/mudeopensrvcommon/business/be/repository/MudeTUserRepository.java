/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;

@Repository
public interface MudeTUserRepository extends BaseRepository<MudeTUser, Long> {

    @Query(value = "SELECT * FROM mudeopen_t_user o WHERE o.cf = :cf AND (o.fine_validita IS NULL OR o.fine_validita > NOW())", nativeQuery = true)
    MudeTUser findByCf(@Param("cf") String cf);
	
    @Query(value = "SELECT id_user FROM mudeopen_t_user o WHERE o.cf = :cf AND (o.fine_validita IS NULL OR o.fine_validita > NOW())", nativeQuery = true)
    Long findIdByCf(@Param("cf") String cf);

    @Query(value = 
    		"SELECT distinct u.* FROM mudeopen_t_user u "
    		+ "   INNER JOIN mudeopen_r_istanza_utente mriu on mriu.id_utente  = u.id_user AND mriu.data_fine is null "
    		+ "   INNER JOIN mudeopen_t_istanza mti on mti.id_istanza  = mriu.id_istanza "
    		+ "   INNER JOIN mudeopen_d_abilitazione b on b.id_abilitazione = mriu.id_abilitazione AND b.data_fine is null "
    		+ " WHERE b.codice_abilitazione in ('PM_RUP_PM_OBB','PM_RUP_PM_OPZ') "
    		+ "   AND mti.id_istanza = :idIstanza"
    		+ " LIMIT 1",
    		nativeQuery = true)
    MudeTUser findPm(@Param("idIstanza") Long idIstanza);
	
    @Query(value = 
    		"(SELECT CONCAT(mtu.nome, ' ', mtu.cognome, ' (', mtu.cf, ')') as name FROM mudeopen_t_user mtu WHERE cf = :CfOrFruitore) "
    		+ "union all "
    		+ "(SELECT mdf.codice_fruitore AS name FROM mudeopen_d_fruitore mdf WHERE codice_fruitore = :CfOrFruitore) "
    		+ "union all "
    		+ "(SELECT 'N/D' AS name) "
    		+ "LIMIT 1 ", nativeQuery = true)
    String getLoggedUser(@Param("CfOrFruitore") String CfOrFruitore);
	
}