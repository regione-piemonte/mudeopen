/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.response;

import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.RuoloObbligatorioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.RuoloPossibileVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.SoggettoIstanzaVO;

import java.util.List;

public class RuoliIstanzaResponse extends DefaultResponse {

    private static final long serialVersionUID = 1L;

    private List<RuoloObbligatorioVO> ruoliObbligatori;
    private List<RuoloPossibileVO> ruoliPossibili;
    private List<SoggettoIstanzaVO> soggettoPFList;
    private List<SoggettoIstanzaVO> soggettoPGList;
    private ContattoVO soggettoPFRappresentato;
    private ContattoVO soggettoPGRappresentato;

    private boolean almenoUnRuolo;

    public List<RuoloObbligatorioVO> getRuoliObbligatori() {
        return ruoliObbligatori;
    }

    public void setRuoliObbligatori(List<RuoloObbligatorioVO> ruoliObbligatori) {
        this.ruoliObbligatori = ruoliObbligatori;
    }

    public List<RuoloPossibileVO> getRuoliPossibili() {
        return ruoliPossibili;
    }

    public void setRuoliPossibili(List<RuoloPossibileVO> ruoliPossibili) {
        this.ruoliPossibili = ruoliPossibili;
    }

    public List<SoggettoIstanzaVO> getSoggettoPFList() {
        return soggettoPFList;
    }

    public void setSoggettoPFList(List<SoggettoIstanzaVO> soggettoPFList) {
        this.soggettoPFList = soggettoPFList;
    }

    public List<SoggettoIstanzaVO> getSoggettoPGList() {
        return soggettoPGList;
    }

    public void setSoggettoPGList(List<SoggettoIstanzaVO> soggettoPGList) {
        this.soggettoPGList = soggettoPGList;
    }

    public ContattoVO getSoggettoPFRappresentato() {
        return soggettoPFRappresentato;
    }

    public void setSoggettoPFRappresentato(ContattoVO soggettoPFRappresentato) {
        this.soggettoPFRappresentato = soggettoPFRappresentato;
    }

    public ContattoVO getSoggettoPGRappresentato() {
        return soggettoPGRappresentato;
    }

    public void setSoggettoPGRappresentato(ContattoVO soggettoPGRappresentato) {
        this.soggettoPGRappresentato = soggettoPGRappresentato;
    }

    public boolean isAlmenoUnRuolo() {
        return almenoUnRuolo;
    }

    public void setAlmenoUnRuolo(boolean almenoUnRuolo) {
        this.almenoUnRuolo = almenoUnRuolo;
    }

}