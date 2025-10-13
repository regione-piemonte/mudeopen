/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAdempimento.TipoAdempimento;
import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.AdempimentoVO;

public interface AdempimentoService {

    List<AdempimentoVO> getAdempimenti(TipoAdempimento tipoAdempimento);

    AdempimentoVO getAdempimento(Long idAdempimento);

}