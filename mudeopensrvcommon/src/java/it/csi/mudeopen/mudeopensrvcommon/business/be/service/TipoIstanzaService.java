/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;

public interface TipoIstanzaService {

    TipoIstanzaVO findByRegimeGiuridicoAndRegimeAggiuntivo(Long idRegimeGiuridico, Long idRegimeAggiuntivo);

    List<TipoIstanzaVO> recuperaTipologieIstanze(Long idComune,
			String codiceIstanzaPadre, 
			Long idIstanza, 
			Long idFascicolo);
}