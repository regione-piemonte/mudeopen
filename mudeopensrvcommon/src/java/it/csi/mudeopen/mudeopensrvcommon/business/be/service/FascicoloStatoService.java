/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloStatoSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloStatoVO;

import java.util.List;
public interface FascicoloStatoService {

    List<FascicoloStatoVO> findAllFascicoliByStato(String codiceStatoFascicolo);

    FascicoloStatoVO findStatoByFascicolo(Long idFascicolo);

    FascicoloStatoSlimVO findStatoByFascicoloSlim(Long idFascicolo);
    void saveFascicoloStato(Long idFascicolo, String codiceStatoFascicolo);
}