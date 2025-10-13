/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;

import java.util.List;

public interface QuadroService {
    QuadroVO loadQuadroByTipoQuadroDesc(String descTipoQuadro);

    QuadroVO loadQuadroByIdTipoQuadro(Long idTipoQuadro);

    Long saveQuadro(QuadroVO quadro);

    int updateQuadro(QuadroVO quadro);

    void deleteQuadro(Long idQuadro);
}