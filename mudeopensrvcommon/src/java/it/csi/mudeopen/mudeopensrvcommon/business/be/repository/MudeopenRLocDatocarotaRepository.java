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
import org.springframework.data.repository.query.Param;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocDatocarota;

@Repository
public interface MudeopenRLocDatocarotaRepository extends BaseRepository<MudeopenRLocDatocarota, Integer> {
	
    @Query(value = "SELECT t "
    		+ "  FROM MudeopenRLocDatocarota t"
    		+ "  WHERE  "
    		+ "    t.idGeoriferimento = ?1 ")
    List<MudeopenRLocDatocarota> findByIdGeoriferimento(Long idGeoriferimento);

	@Modifying
    @Query(value = "delete from MudeopenRLocDatocarota t where t.idGeoriferimento IN (:ids) ")
    void deleteByIdGeoriferimentoList(@Param("ids") List<Long> georiferimentiIdsOld);

}