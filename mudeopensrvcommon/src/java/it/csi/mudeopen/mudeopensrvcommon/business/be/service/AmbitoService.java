/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.AmbitoVO;

import java.util.List;

public interface AmbitoService {

    List<AmbitoVO> loadAmbitiAttivi();

    AmbitoVO loadAmbitoById(Long idAmbito);

    AmbitoVO loadAmbitoByCode(String codAmbito);

    Long saveAmbito(AmbitoVO ambito);

    Long updateAmbito(AmbitoVO ambito);

    void deleteAmbito(Long idAmbito);

}