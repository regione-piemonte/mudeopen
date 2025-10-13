/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.SpeciePraticaTipoOperaVO;

import java.util.List;

public interface SpeciePraticaTipoOperaService {

    List<SpeciePraticaTipoOperaVO> findByTipoOpera(String codiceTipoOpera);

    List<SpeciePraticaTipoOperaVO> findBySpeciePratica(String codiceSpeciePratica);
    List<SpeciePraticaTipoOperaVO> search(String filter, int page, int size, String sortExp);
}