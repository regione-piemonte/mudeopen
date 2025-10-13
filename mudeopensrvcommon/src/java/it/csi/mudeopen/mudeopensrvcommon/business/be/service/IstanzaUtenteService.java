/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneCustomVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;

public interface IstanzaUtenteService {

    IstanzaUtenteVO save(Long idIstanza, String codiceAbilitazione, MudeTUser user);

    void saveAbilitazioniFunzioniPerIstanzaUtente(Long idIstanza, AbilitazioneFunzioneCustomVO vo, MudeTUser user);

    void delete(Long id);

    IstanzaUtenteVO findById(Long id);

    List<IstanzaUtenteVO> recuperaAbilitazioniIstanza(Long idIstanza, boolean complete);

    List<IstanzaUtenteVO> findByUtente(String cfUtente);

    List<IstanzaUtenteVO> findByIstanzaAndAbilitazione(Long idIstanza, Long idAbilitazione);

    List<IstanzaUtenteVO> findByIstanzaAndCodiceAbilitazione(Long idIstanza, String... codiceAbilitazione);

    List<IstanzaUtenteVO> findAll();

    IstanzaUtenteVO findByIstanzaAndAbilitazioneAndUtente(Long idIstanza, Long idAbilitazione, Long idUtente);

    List<IstanzaUtenteVO> findByIstanzaAndUtente(Long idIstanza, Long idUser, String cf);

    void setAbilitazionePerIstanzaUtente(Long idIstanza, String permitionCode, Long userToAssign, MudeTUser userAssigning);
}