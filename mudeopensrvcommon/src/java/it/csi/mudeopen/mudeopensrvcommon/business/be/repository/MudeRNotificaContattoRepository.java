/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaContatto;

@Repository
public interface MudeRNotificaContattoRepository extends BaseRepository<MudeRNotificaContatto,Long> {
	
    @Query("SELECT nc " +
			"  FROM MudeRNotificaContatto nc " +
			"  WHERE nc.emailStatus in ('TO_SEND','IN_RETRY')")
    List<MudeRNotificaContatto> findEmailToSend();

    @Modifying
    @Query("DELETE FROM MudeRNotificaContatto " +
			"  WHERE mudeTNotifica.id IN (SELECT n.id FROM MudeTNotifica n WHERE n.istanza.id = :idIstanza)")
    void deleteByIstanza_id(@Param("idIstanza") Long idIstanza);

}