/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;

import java.util.List;

public interface SelectService {
    List<SelectVO> searchByDescrizione(String descrizione, String selectType, String... params);

    List<SelectVO> loadAllSelectValues(String selectType, String... params);
}