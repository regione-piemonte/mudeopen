/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloIntestatarioSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloIntestatarioVO;

public interface FascicoloIntestatarioService {

    List<FascicoloIntestatarioVO> findAllFascicoliByIntestatario(Long idIntestatario);

    List<FascicoloIntestatarioVO> findAllByFascicolo(Long idFascicolo);

    FascicoloIntestatarioVO findActiveByFascicolo(Long idFascicolo);

    FascicoloIntestatarioSlimVO findActiveByFascicoloSlim(Long idFascicolo);

    void saveFascicoloIntestatario(MudeTIstanza istanza, Long idIntestatario, MudeTUser mudeTUser, boolean isFirstIstance);

    void disableActiveIntestatarioFascicolo(long idFascicolo);
}