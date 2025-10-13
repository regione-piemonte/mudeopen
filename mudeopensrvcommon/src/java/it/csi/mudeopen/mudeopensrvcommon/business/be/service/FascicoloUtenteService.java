/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FascicoloUtenteVO;

public interface FascicoloUtenteService {

    FascicoloUtenteVO save(Long idFascicolo, String codiceAbilitazione, MudeTUser user);

    void saveAbilitazioniFunzioniPerFascicoloUtente(Long idFascicolo, AbilitazioneFunzioneCustomVO vo, MudeTUser user);

    List<FascicoloUtenteVO> save(List<FascicoloUtenteVO> vo);

    void delete(Long id);

    FascicoloUtenteVO findById(Long id);

    List<FascicoloUtenteVO> findByFascicolo(Long idFascicolo);

    List<FascicoloUtenteVO> findByUtente(String cfUtente);

    List<FascicoloUtenteVO> findAll();

    List<FascicoloUtenteVO> findByFascicoloAndAbilitazione(Long idFascicolo, Long idAbilitazione);

    List<FascicoloUtenteVO> recuperaAbilitazioniFascicolo(Long idIstanza);

    void setAbilitazionePerFascicoloUtente(Long idFascicolo, String permitionCode, Long userToEnable, MudeTUser user);
}