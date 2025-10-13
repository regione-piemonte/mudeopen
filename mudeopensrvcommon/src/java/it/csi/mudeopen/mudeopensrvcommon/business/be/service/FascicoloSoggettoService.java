/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloRuoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloSoggettoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.RuoloPossibileVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.SoggettoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.RuoliFascicoloRequest;

public interface FascicoloSoggettoService {

    void delete(Long idFascicoloSoggetto);

    List<FascicoloRuoloVO> getRuoliFascicolo(Long idFascicolo, MudeTUser user);

    List<SoggettoIstanzaVO> retrieveSoggettiDaFascicolo(Long idIstanza, List<RuoloPossibileVO> ruoliPossibili, List<RuoliFascicoloRequest> ruoli, MudeTUser mudeTUser);

    void migrazioneSoggettiInFascicolo(MudeTIstanza istanza, MudeTUser mudeTUser);
    
    void recoverMigrazioneSoggettiInFascicolo();

}