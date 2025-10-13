/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.CategoriaInterventoVO;

import java.util.List;

public interface CategorieInterventoService {

    List<CategoriaInterventoVO> findAll();

    CategoriaInterventoVO findOne(Long idCategoria);
}