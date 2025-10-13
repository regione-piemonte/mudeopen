/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvapi.vo.RuoloVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloUtente;

public interface MudeopenDRuoloUtenteService {

	public List<MudeDRuoloUtente> findAllActive();
	
	public List<RuoloVO> findByIdFunzione(String codiceFunzione) ;

}