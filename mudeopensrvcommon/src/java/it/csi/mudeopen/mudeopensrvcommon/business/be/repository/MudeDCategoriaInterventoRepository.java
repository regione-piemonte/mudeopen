/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDCategoriaIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeDCategoriaInterventoRepository extends BaseRepository<MudeDCategoriaIntervento, Long> {

	@Query("select o from MudeDCategoriaIntervento o where o.dataFine is null ORDER BY o.denominazione ASC")
	List<MudeDCategoriaIntervento> findAll();
}