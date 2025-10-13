/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoFiltroVeloce;

@Repository
public interface MudeDStatoFiltroVeloceRepository extends BaseRepository<MudeDStatoFiltroVeloce, String> {

    @Override
	@Query(value = " SELECT o.* FROM Mudeopen_D_Stato_Filtro_Veloce o where o.data_fine is null",nativeQuery = true)
    List<MudeDStatoFiltroVeloce> findAll();

}