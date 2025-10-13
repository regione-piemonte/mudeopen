/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.ente.EnteVO;

import java.util.List;

public interface EntiService {

    List<EnteVO> findAllActiveByComune(long idComune);

	void assegnaEntiAdIstanza(MudeTIstanza istanza);
	
    List<EnteVO> findActiveByDescrizione(String descrizione);

    EnteVO findByCodice(String codice);

    EnteVO findActiveByComuneAndTipoEnte(Long idComune, String codiceTipoEnte);
}