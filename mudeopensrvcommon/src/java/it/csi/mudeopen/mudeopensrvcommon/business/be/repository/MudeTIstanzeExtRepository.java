/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzeExt;

@Repository
public interface MudeTIstanzeExtRepository extends BaseRepository<MudeTIstanzeExt, Long> {

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE mudeopen_t_istanze_ext SET dps_script_stato = :newState WHERE id_istanza = :idIstanza", nativeQuery = true)
    void setDPSScriptState(@Param("idIstanza") Long idIstanza, @Param("newState") String newState);
	
	
}