/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaRuoloSoggetto;

@Repository
public interface MudeRTipoIstanzaRuoloSoggettoRepository extends BaseRepository<MudeRTipoIstanzaRuoloSoggetto, Long> {

    List<MudeRTipoIstanzaRuoloSoggetto> findByMudeDTipoIstanzaOrderByMudeDRuoloSoggetto_descrizione(MudeDTipoIstanza mudeDTipoIstanza);
	
	
}