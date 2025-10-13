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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;

@Repository
public interface MudeRNotificaUtenteRepository extends BaseRepository<MudeRNotificaUtente, Long> {
	
	List<MudeRNotificaUtente> findByMudeTNotifica(MudeTNotifica mudeTNotifica);

    @Modifying
    @Query("DELETE FROM MudeRNotificaUtente " +
			"  WHERE mudeTNotifica.id IN (SELECT n.id FROM MudeTNotifica n WHERE n.istanza.id = :idIstanza)")
    void deleteByIstanza_id(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT MAX(data_lettura) FROM  mudeopen_r_notifica_utente mrnu WHERE id_notifica = :idNotifica", nativeQuery = true)
    Date getReadDate(@Param("idNotifica") Long idNotifica);

}