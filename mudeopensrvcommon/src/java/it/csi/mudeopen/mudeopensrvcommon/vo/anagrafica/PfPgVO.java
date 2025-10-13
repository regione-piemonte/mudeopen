/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;

public class PfPgVO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("soggetto_pf")
    private ContattoVO soggettoPf;

    @JsonProperty("soggetto_pg")
    private ContattoVO soggettoPg;

    @JsonProperty("id_titolo")
    private String idTitolo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContattoVO getSoggettoPf() {
        return soggettoPf;
    }

    public void setSoggettoPf(ContattoVO soggettoPf) {
        this.soggettoPf = soggettoPf;
    }

    public ContattoVO getSoggettoPg() {
        return soggettoPg;
    }

    public void setSoggettoPg(ContattoVO soggettoPg) {
        this.soggettoPg = soggettoPg;
    }

    public String getIdTitolo() {
		return idTitolo;
	}

    public void setIdTitolo(String idTitolo) {
		this.idTitolo = idTitolo;
	}
}