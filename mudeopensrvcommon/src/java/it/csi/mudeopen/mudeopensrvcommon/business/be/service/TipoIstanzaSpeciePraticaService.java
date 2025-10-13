/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaSpeciePraticaVO;

import java.util.List;

public interface TipoIstanzaSpeciePraticaService {

    List<TipoIstanzaSpeciePraticaVO> findByTipoIstanza(String codiceTipoIstanza);

    List<TipoIstanzaSpeciePraticaVO> findBySpeciePratica(String codiceSpeciePratica);
    List<TipoIstanzaSpeciePraticaVO> search(String filter, int page, int size, String sortExp);

}