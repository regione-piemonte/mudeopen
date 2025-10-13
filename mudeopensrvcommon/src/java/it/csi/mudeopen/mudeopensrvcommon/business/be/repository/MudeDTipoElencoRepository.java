/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoElenco;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MudeDTipoElencoRepository extends BaseRepository<MudeDTipoElenco, Long>{
    Optional<MudeDTipoElenco> findMudeDTipoElencoById(Long id);
}