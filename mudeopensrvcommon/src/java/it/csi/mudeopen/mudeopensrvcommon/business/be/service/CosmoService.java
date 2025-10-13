/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

public interface CosmoService {

	void processAllJobs();

	void addPraticaCosmoToQueue(MudeTIstanza istanza);
	
	List<IstanzaVO> markIstancesAsToBeChecked(List<IstanzaVO> istanze);
	
}