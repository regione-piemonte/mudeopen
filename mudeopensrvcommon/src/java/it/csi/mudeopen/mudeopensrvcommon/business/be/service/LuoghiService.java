/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.NazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.RegioneVO;

public interface LuoghiService {

    List<RegioneVO> getRegioni();

    List<ProvinciaVO> getProvinceByIdRegione(Long idRegione);

    List<ComuneVO> getComuniByIdProvincia(String filters);

    List<NazioneVO> getNazioni();

    List<NazioneVO> getStatiMembriUE();

    List<RegioneVO> getRegioniByIdNazione(Long idNazione);

    List<ProvinciaVO> getProvince();

    /*nuova gestione comuni/province per creazione istanza*/

    List<SelectVO> findProvinceForComuniRegistered();

    List<SelectVO> findComuniRegisteredForProvincia(Long idProvincia);
}