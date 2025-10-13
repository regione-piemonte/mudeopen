/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import javax.persistence.Column;

@JsonInclude(Include.ALWAYS)
public class AbilitazioneVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 6956440228852172156L;

    @JsonProperty("id_abilitazione")
    private Long id;

    @JsonProperty("codice_abilitazione")
    private String codice;

    @JsonProperty("desc_abilitazione")
    private String descrizione;

    @JsonProperty("tipo_abilitazione")
    private Character tipo;

    @JsonProperty("necessaria_iscrizione_albo")
    private Boolean necessariaIscrizioneAlbo;

    @JsonProperty("necessaria_presenza_in_istanza")
    private Boolean necessariaPresenzaInIstanza;

    @JsonProperty("necessaria_selezione_quadro")
    private Boolean necessariaSelezioneQuadro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Boolean getNecessariaIscrizioneAlbo() {
        return necessariaIscrizioneAlbo;
    }

    public void setNecessariaIscrizioneAlbo(Boolean necessariaIscrizioneAlbo) {
        this.necessariaIscrizioneAlbo = necessariaIscrizioneAlbo;
    }

    public Boolean getNecessariaPresenzaInIstanza() {
        return necessariaPresenzaInIstanza;
    }

    public void setNecessariaPresenzaInIstanza(Boolean necessariaPresenzaInIstanza) {
        this.necessariaPresenzaInIstanza = necessariaPresenzaInIstanza;
    }

	public Boolean getNecessariaSelezioneQuadro() {
		return necessariaSelezioneQuadro;
	}

	public void setNecessariaSelezioneQuadro(Boolean necessariaSelezioneQuadro) {
		this.necessariaSelezioneQuadro = necessariaSelezioneQuadro;
	}

}