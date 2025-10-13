/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTaskQuadro;

@Repository
public interface MudeDTaskQuadroRepository extends BaseRepository<MudeDTaskQuadro, String> {

	 @Query( 
	    		"SELECT mdtq "
	    		+ "  FROM MudeDTaskQuadro mdtq "
	    		+ "  WHERE mdtq.codiceTask = :codiceTask "
)
	    MudeDTaskQuadro findByCodiceTask(@Param("codiceTask") String codiceTask);

}