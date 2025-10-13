/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

public interface MudeopenTNotificaRepository extends BaseRepository<MudeTNotifica, Long> {

	@Query(value = "select mtn from MudeTNotifica mtn join mtn.istanza mti where mti.codiceIstanza = ?1")
	List<MudeTNotifica> findByCodiceIstanza(String codiceIstanza);
	
	@Query(value = "select mtn from MudeTNotifica mtn join mtn.istanza mti where mti.codiceIstanza = ?1 and mtn.id = ?2")
	Optional<MudeTNotifica> findByCodiceIstanzaAndIdNotifica(String codiceIstanza, Long idNotifica);
	
}
