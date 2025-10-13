/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;

public class TipoIstanzaVO extends SelectVO {
    @JsonProperty("legata")
    private Boolean legata;

    @JsonProperty("cambio_intestatario")
    private Boolean cambioIntestatario;

    @JsonProperty("almeno_un_ruolo")
    private Boolean almenoUnRuolo;

    @JsonProperty("soggetti_bloccati")
    private Boolean soggettiBloccati;

    @JsonProperty("escludi_branch")
    private String escludiBranch;

    public TipoIstanzaVO() {
    }

    public TipoIstanzaVO(String id, String descrizione) {
        super(id, descrizione);
    }

    public Boolean getLegata() {
        return legata;
    }

    public void setLegata(Boolean legata) {
        this.legata = legata;
    }

    public Boolean getAlmenoUnRuolo() {
        return almenoUnRuolo;
    }

    public void setAlmenoUnRuolo(Boolean almenoUnRuolo) {
        this.almenoUnRuolo = almenoUnRuolo;
    }

    public Boolean getCambioIntestatario() {
        return cambioIntestatario;
    }

    public void setCambioIntestatario(Boolean cambioIntestatario) {
        this.cambioIntestatario = cambioIntestatario;
    }

	public Boolean getSoggettiBloccati() {
		return soggettiBloccati;
	}

	public void setSoggettiBloccati(Boolean soggettiBloccati) {
		this.soggettiBloccati = soggettiBloccati;
	}

	public String getEscludiBranch() {
		return escludiBranch;
	}

	public void setEscludiBranch(String escludiBranch) {
		this.escludiBranch = escludiBranch;
	}

}