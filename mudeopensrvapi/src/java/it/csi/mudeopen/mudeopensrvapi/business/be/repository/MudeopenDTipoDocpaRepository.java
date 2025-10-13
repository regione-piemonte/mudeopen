/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

/**
 * The interface Mude d_tipo_docpa repository.
 */
@Repository
public interface MudeopenDTipoDocpaRepository extends BaseRepository<MudeDTipoDocPA, Long> {

	@Query(value = "select t from MudeDTipoDocPA t where t.dataFine is null")
	List<MudeDTipoDocPA> findAllActive();

	Optional<MudeDTipoDocPA> findByCodeTipoDocpa(String codice);

}