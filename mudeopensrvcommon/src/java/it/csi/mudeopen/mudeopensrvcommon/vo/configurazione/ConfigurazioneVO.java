/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.configurazione;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class ConfigurazioneVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -3884774726552338403L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("valore")
    private String valore;

    @JsonProperty("note")
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ConfigurazioneVO that = (ConfigurazioneVO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(valore, that.valore) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, valore, note);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigurazioneVO {");
        sb.append("         id:").append(id);
        sb.append(",         nome:'").append(nome).append("'");
        sb.append(",         valore:'").append(valore).append("'");
        sb.append(",         note:'").append(note).append("'");
        sb.append("}");
        return sb.toString();
    }
}