/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTLogNumeriMude;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

public interface MudeTLogNumeriMudeRepository extends BaseRepository<MudeTLogNumeriMude, Long> {

	@Query("select t from MudeTLogNumeriMude t where "
			+ "t.numeroMude = :numeroMude and t.tipo = :tipo and "
			+ "t.mudeDFruitore.codiceFruitore = :codiceFruitore")
	MudeTLogNumeriMude findByNumeroTipoCodiceFruitore(@Param("numeroMude") String numeroMude,@Param("tipo") String tipo,@Param("codiceFruitore") String codiceFruitore);
	
    @Query(value = " select nextval('mudeopen_numero_mude_seq')", nativeQuery = true)
    Long getNextIdNumeroMude();
	
}
