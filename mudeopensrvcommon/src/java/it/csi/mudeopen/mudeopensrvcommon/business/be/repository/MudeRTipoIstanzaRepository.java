/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanza;

@Repository
public interface MudeRTipoIstanzaRepository extends BaseRepository<MudeRTipoIstanza, Long> {

    List<MudeRTipoIstanza> findByMudeDTipoIstanzaPadre(MudeDTipoIstanza mudeDTipoIstanzaPadre);

}