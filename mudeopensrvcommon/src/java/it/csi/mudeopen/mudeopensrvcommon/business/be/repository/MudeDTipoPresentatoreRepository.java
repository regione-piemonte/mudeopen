/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoPresentatore;

@Repository
public interface MudeDTipoPresentatoreRepository extends BaseRepository<MudeDTipoPresentatore, Long> {

    @Query("select o from MudeDTipoPresentatore o where o.dataFine is null ORDER BY o.idTipo ASC")
	List<MudeDTipoPresentatore> findAllOrderByDenominazioneAsc();

    MudeDTipoPresentatore findByDenominazione(String denominazione);

    MudeDTipoPresentatore findByIdTipo(Long idTipo);

}