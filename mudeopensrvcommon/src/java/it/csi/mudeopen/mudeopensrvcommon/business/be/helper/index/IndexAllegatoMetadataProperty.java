/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index;

public class IndexAllegatoMetadataProperty {

    private String codiceTipoAllegato;
    private String numeroMudeAllegato;
    private String numeroInterventoAllegato;
    private String author;

    public String getCodiceTipoAllegato() {
        return codiceTipoAllegato;
    }

    public void setCodiceTipoAllegato(String codiceTipoAllegato) {
        this.codiceTipoAllegato = codiceTipoAllegato;
    }

    public String getNumeroMudeAllegato() {
        return numeroMudeAllegato;
    }

    public void setNumeroMudeAllegato(String numeroMudeAllegato) {
        this.numeroMudeAllegato = numeroMudeAllegato;
    }

    public String getNumeroInterventoAllegato() {
        return numeroInterventoAllegato;
    }

    public void setNumeroInterventoAllegato(String numeroInterventoAllegato) {
        this.numeroInterventoAllegato = numeroInterventoAllegato;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}