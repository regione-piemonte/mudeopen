/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaTipoOperaVO;

import java.util.List;

public interface TipoIstanzaTipoOperaService {

    List<TipoIstanzaTipoOperaVO> findByTipoIstanza(String codiceTipoIstanza);

//    List<TipoIstanzaTipoOperaVO> findByTipoIstanzaViaSpeciePratica(String codiceTipoIstanza);

    List<TipoIstanzaTipoOperaVO> findByTipoOpera(String codiceTipoOpera);

//    List<TipoIstanzaTipoOperaVO> findByTipoOperaViaSpeciePratica(String codiceTipoOpera);
}