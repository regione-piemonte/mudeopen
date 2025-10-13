/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.vo.wizrad.OperaVO;

public interface OperaService {

    List<OperaVO> getOpere();

    OperaVO getOpera(Long idOpera);

}