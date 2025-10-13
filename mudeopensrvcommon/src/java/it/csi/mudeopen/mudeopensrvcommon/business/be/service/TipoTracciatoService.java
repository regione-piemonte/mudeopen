/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TipoTracciatoVO;

import java.util.List;

public interface TipoTracciatoService {

    List<TipoTracciatoVO> findByCodice(String codice);

    TipoTracciatoVO findByCodiceAndVersione(String codice, String versione);

//    List<TipoTracciatoVO> findTipoTracciatoAttivoPerTipoIstanza(String tipoIstanza);
}