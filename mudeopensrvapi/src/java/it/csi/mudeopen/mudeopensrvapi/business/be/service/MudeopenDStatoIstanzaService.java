/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service;

import java.util.List;
import it.csi.mudeopen.mudeopensrvapi.vo.StatiIstanzaAmmessiVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;

public interface MudeopenDStatoIstanzaService {

	public List<MudeDStatoIstanza> findAllActive();
	
	public MudeDStatoIstanza findStato(String stato);
	
	public List<StatiIstanzaAmmessiVO> findStatiAmmessi(String statoIniziale) ;
	
}