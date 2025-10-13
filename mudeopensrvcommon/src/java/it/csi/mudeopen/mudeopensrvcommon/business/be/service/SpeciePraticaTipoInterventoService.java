/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.SpeciePraticaTipoInterventoVO;

import java.util.List;

public interface SpeciePraticaTipoInterventoService {

    List<SpeciePraticaTipoInterventoVO> findBySpeciePratica(String codiceSpeciePratica);

    List<SpeciePraticaTipoInterventoVO> findByTipoIntervento(String codiceTipoIntervento);

    List<SpeciePraticaTipoInterventoVO> search(String filter, int page, int size, String sortExp);
}