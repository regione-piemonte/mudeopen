/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRComuneFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaFruitore;

public interface MudeopenDFruitoreService {

	public List<MudeDFruitore> findActive();
	
	public MudeDFruitore findByCodiceFruitore(String codiceFruitore);
	
	public List<MudeRComuneFruitore> findComuniByCodiceFruitore(String codiceFruitore);

	public List<MudeRTipoIstanzaFruitore> findTipiIstanzaByCodiceFruitore(String codiceFruitore);
	
}