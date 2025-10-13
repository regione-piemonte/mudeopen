/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.allegato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class TipoAllegatoExtendedVO extends TipoAllegatoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -3560345444209305072L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("ordinamento")
    private Integer ordinamento;

    @JsonProperty("obbligatorio")
    private Boolean obbligatorio;

    @JsonProperty("ripetibile")
    private Boolean ripetibile;

    @JsonProperty("espressione_obbligatorieta")
    private String espressioneObbligatorieta;

    @JsonProperty("espressione_ripetibilita")
    private String espressioneRipetibilita;

    @JsonProperty("modello_allegato")
    private ModelloVO modello;

    @JsonProperty("flag_ricorrente")
    private Boolean flagRicorrente;

    @JsonProperty("id_template")
    private Long idTemplate;

    @JsonProperty("uploadable")
    private Boolean uploadable;

    public Integer getOrdinamento() {
        return ordinamento;
    }

    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }

    public Boolean getObbligatorio() {
        return obbligatorio == null? false : obbligatorio;
    }

    public void setObbligatorio(Boolean obbligatorio) {
        this.obbligatorio = obbligatorio;
    }

    public Boolean getRipetibile() {
        return ripetibile == null? false : ripetibile;
    }

    public void setRipetibile(Boolean ripetibile) {
        this.ripetibile = ripetibile;
    }

    public String getEspressioneObbligatorieta() {
        return espressioneObbligatorieta;
    }

    public void setEspressioneObbligatorieta(String espressioneObbligatorieta) {
        this.espressioneObbligatorieta = espressioneObbligatorieta;
    }

    public String getEspressioneRipetibilita() {
        return espressioneRipetibilita;
    }

    public void setEspressioneRipetibilita(String espressioneRipetibilita) {
        this.espressioneRipetibilita = espressioneRipetibilita;
    }

    public ModelloVO getModello() {
        return modello;
    }

    public void setModello(ModelloVO modello) {
        this.modello = modello;
    }

    public Boolean getFlagRicorrente() {
        return flagRicorrente == null? false : flagRicorrente;
    }

    public void setFlagRicorrente(Boolean flagRicorrente) {
        this.flagRicorrente = flagRicorrente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        TipoAllegatoExtendedVO that = (TipoAllegatoExtendedVO) o;
        return Objects.equals(ordinamento, that.ordinamento) && Objects.equals(obbligatorio, that.obbligatorio) && Objects.equals(ripetibile, that.ripetibile) && Objects.equals(espressioneObbligatorieta, that.espressioneObbligatorieta) && Objects.equals(espressioneRipetibilita, that.espressioneRipetibilita) && Objects.equals(modello, that.modello) && Objects.equals(flagRicorrente, that.flagRicorrente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ordinamento, obbligatorio, ripetibile, espressioneObbligatorieta, espressioneRipetibilita, modello, flagRicorrente);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoAllegatoExtendedVO {");
        sb.append(super.toString());
        sb.append("         ordinamento:").append(ordinamento);
        sb.append(",         obbligatorio:").append(obbligatorio);
        sb.append(",         ripetibile:").append(ripetibile);
        sb.append(",         espressioneObbligatorieta:'").append(espressioneObbligatorieta).append("'");
        sb.append(",         espressioneRipetibilita:'").append(espressioneRipetibilita).append("'");
        sb.append(",         modello:").append(modello);
        sb.append(",         flagRicorrente:").append(flagRicorrente);
        sb.append("}");
        return sb.toString();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}

	public Boolean getUploadable() {
		return uploadable;
	}

	public void setUploadable(Boolean uploadable) {
		this.uploadable = uploadable;
	}
}