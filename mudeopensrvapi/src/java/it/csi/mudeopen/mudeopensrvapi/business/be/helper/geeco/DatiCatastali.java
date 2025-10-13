/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

public class DatiCatastali {
    String censito_al_catasto;
    String foglioN;
    String map;
    String sub;
    String text_fieldsezione;

    public String getCensito_al_catasto() {
        return censito_al_catasto;
    }

    public void setCensito_al_catasto(String censito_al_catasto) {
        this.censito_al_catasto = censito_al_catasto;
    }

    public String getFoglioN() {
        return foglioN;
    }

    public void setFoglioN(String foglioN) {
        this.foglioN = foglioN;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getText_fieldsezione() {
        return text_fieldsezione;
    }

    public void setText_fieldsezione(String text_fieldsezione) {
        this.text_fieldsezione = text_fieldsezione;
    }

    @Override
    public String toString() {
        return "DatiCatastali{" +
                "censito_al_catasto='" + censito_al_catasto + '\'' +
                ", foglioN='" + foglioN + '\'' +
                ", map='" + map + '\'' +
                ", sub='" + sub + '\'' +
                ", text_fieldsezione='" + text_fieldsezione + '\'' +
                '}';
    }
}
