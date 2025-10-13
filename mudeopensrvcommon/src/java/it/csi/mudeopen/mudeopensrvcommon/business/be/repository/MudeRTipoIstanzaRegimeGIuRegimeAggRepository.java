/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegimeAggiuntivo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegimeGiuridico;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaRegimeGiuRegimeAgg;

@Repository
public interface MudeRTipoIstanzaRegimeGIuRegimeAggRepository extends BaseRepository<MudeRTipoIstanzaRegimeGiuRegimeAgg, Long> {

    MudeRTipoIstanzaRegimeGiuRegimeAgg findByMudeDRegimeGiuridicoAndMudeDRegimeAggiuntivo(MudeDRegimeGiuridico regGiu, MudeDRegimeAggiuntivo regAgg);
	
	
}