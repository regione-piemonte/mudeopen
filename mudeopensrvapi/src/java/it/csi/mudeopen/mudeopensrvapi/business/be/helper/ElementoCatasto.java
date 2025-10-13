/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper;

import it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco.GeecoParticella;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni.DettaglioDatiTerreno;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.titolari.Titolarita;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu.DettaglioDatiFabbricato;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu.Indirizzo;

import java.util.List;

public class ElementoCatasto{
    String foglio = null;
    String mappale = null;
    String sezione = null;
    String belfiore = null;

    public String getBelfiore() {
        return belfiore;
    }

    public void setBelfiore(String belfiore) {
        this.belfiore = belfiore;
    }

    public String getFoglio() {
        return foglio;
    }

    public void setFoglio(String foglio) {
        this.foglio = foglio;
    }

    public String getMappale() {
        return mappale;
    }

    public void setMappale(String mappale) {
        this.mappale = mappale;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }
    private String subalterno;
    private String sezioneUrbana;
    private String censito_al_catasto;

    private List<Indirizzo> indirizzi;
    private List<Titolarita> titolarita;
    private DettaglioDatiFabbricato datiFabbricato;
    private DettaglioDatiTerreno datiTerreno;

    public String getSubalterno() {
        return subalterno;
    }

    public void setSubalterno(String subalterno) {
        this.subalterno = subalterno;
    }

    public String getSezioneUrbana() {
        return sezioneUrbana;
    }

    public void setSezioneUrbana(String sezioneUrbana) {
        this.sezioneUrbana = sezioneUrbana;
    }

    public String getCensito_al_catasto() {
        return censito_al_catasto;
    }

    public void setCensito_al_catasto(String censito_al_catasto) {
        this.censito_al_catasto = censito_al_catasto;
    }

    public List<Indirizzo> getIndirizzi() {
        return indirizzi;
    }

    public void setIndirizzi(List<Indirizzo> indirizzi) {
        this.indirizzi = indirizzi;
    }

    public List<Titolarita> getTitolarita() {
        return titolarita;
    }

    public void setTitolarita(List<Titolarita> titolarita) {
        this.titolarita = titolarita;
    }

    public DettaglioDatiFabbricato getDatiFabbricato() {
        return datiFabbricato;
    }

    public void setDatiFabbricato(DettaglioDatiFabbricato datiFabbricato) {
        this.datiFabbricato = datiFabbricato;
    }

    public DettaglioDatiTerreno getDatiTerreno() {
        return datiTerreno;
    }

    public void setDatiTerreno(DettaglioDatiTerreno datiTerreno) {
        this.datiTerreno = datiTerreno;
    }
}
