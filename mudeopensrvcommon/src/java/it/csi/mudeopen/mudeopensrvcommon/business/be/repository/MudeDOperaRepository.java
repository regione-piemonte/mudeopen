/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDOpera;

@Repository
public interface MudeDOperaRepository extends BaseRepository<MudeDOpera, Long> {

	@Override
	@Query("select o from MudeDOpera o where o.dataFine is null ORDER BY o.idOpera ASC")
	List<MudeDOpera> findAll();

	
	
}