/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCRegione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;

public interface MudeopenCRegioneService {

	public MudeCRegione save(MudeCRegione regione);
	
	public Long getProgressive(MudeDRegione dRegione) throws Exception;

}