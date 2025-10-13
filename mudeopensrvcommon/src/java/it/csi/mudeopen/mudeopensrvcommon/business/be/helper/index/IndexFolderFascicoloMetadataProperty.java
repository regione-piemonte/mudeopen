/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index;

public class IndexFolderFascicoloMetadataProperty {
    String numeroIntervento;
    String codiceIstatComune;
    String annoPresentazione;

    public String getNumeroIntervento() {
        return numeroIntervento;
    }

    public void setNumeroIntervento(String numeroIntervento) {
        this.numeroIntervento = numeroIntervento;
    }

    public String getCodiceIstatComune() {
        return codiceIstatComune;
    }

    public void setCodiceIstatComune(String codiceIstatComune) {
        this.codiceIstatComune = codiceIstatComune;
    }

    public String getAnnoPresentazione() {
        return annoPresentazione;
    }

    public void setAnnoPresentazione(String annoPresentazione) {
        this.annoPresentazione = annoPresentazione;
    }
}