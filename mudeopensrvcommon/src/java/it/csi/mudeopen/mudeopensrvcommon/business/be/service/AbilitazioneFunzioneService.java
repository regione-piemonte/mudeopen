/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneSlimCustomVO;

public interface AbilitazioneFunzioneService {

    AbilitazioneFunzioneCustomVO loadByCodiceAbilitazione(String codiceAbilitazione);

    List<AbilitazioneFunzioneCustomVO> loadFunzioniAbilitazioni(Boolean isFascicolo, Boolean isPmRequired, Long idFascicolo, Long idIstanza, Boolean excludeFilters, Long idUser);

    List<AbilitazioneFunzioneSlimCustomVO> loadFunzioniAbilitazioniByIdIstanzaAndIdUser(Long idIstanza, Long IdUser);

}