/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.CategoriaAllegatoVO;

import java.util.List;

public interface CategoriaAllegatoService {

    List<CategoriaAllegatoVO> findAllOrderByDescrizione();

    CategoriaAllegatoVO findById(String codiceCategoriaAllegato);
}