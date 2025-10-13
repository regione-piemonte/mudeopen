/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;

public interface MudeopenTSessioneService {

	public MudeopenTSessione save(MudeopenTSessione sessione);
	
	// public MudeopenTSessione findByToken(UUID token);
	
	public void delete(MudeopenTSessione sessione);

	public String getUUID();

}