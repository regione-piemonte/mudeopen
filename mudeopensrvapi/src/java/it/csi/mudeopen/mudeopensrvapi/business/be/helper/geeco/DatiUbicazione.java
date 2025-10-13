/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

public class DatiUbicazione {
    String sedime;
    String denominazione;
    String n;
    String bis;
    String scala;
    String piano;
    String interno;
    String bisInterno;
    String interno2;
    String secondario;
    String cap;
    boolean selezionare_se_si_tratta_di_indirizzo_principale;

    public String getSedime() {
        return sedime;
    }

    public void setSedime(String sedime) {
        this.sedime = sedime;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getBis() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public String getScala() {
        return scala;
    }

    public void setScala(String scala) {
        this.scala = scala;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }

    public String getInterno() {
        return interno;
    }

    public void setInterno(String interno) {
        this.interno = interno;
    }

    public String getBisInterno() {
        return bisInterno;
    }

    public void setBisInterno(String bisInterno) {
        this.bisInterno = bisInterno;
    }

    public String getInterno2() {
        return interno2;
    }

    public void setInterno2(String interno2) {
        this.interno2 = interno2;
    }

    public String getSecondario() {
        return secondario;
    }

    public void setSecondario(String secondario) {
        this.secondario = secondario;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public boolean isSelezionare_se_si_tratta_di_indirizzo_principale() {
        return selezionare_se_si_tratta_di_indirizzo_principale;
    }

    public void setSelezionare_se_si_tratta_di_indirizzo_principale(boolean selezionare_se_si_tratta_di_indirizzo_principale) {
        this.selezionare_se_si_tratta_di_indirizzo_principale = selezionare_se_si_tratta_di_indirizzo_principale;
    }

    @Override
    public String toString() {
        return "DatiUbicazione{" +
                "sedime='" + sedime + '\'' +
                ", denominazione='" + denominazione + '\'' +
                ", n='" + n + '\'' +
                ", bis='" + bis + '\'' +
                ", scala='" + scala + '\'' +
                ", piano='" + piano + '\'' +
                ", interno='" + interno + '\'' +
                ", bisInterno='" + bisInterno + '\'' +
                ", interno2='" + interno2 + '\'' +
                ", secondario='" + secondario + '\'' +
                ", cap='" + cap + '\'' +
                ", selezionare_se_si_tratta_di_indirizzo_principale=" + selezionare_se_si_tratta_di_indirizzo_principale +
                '}';
    }
}
