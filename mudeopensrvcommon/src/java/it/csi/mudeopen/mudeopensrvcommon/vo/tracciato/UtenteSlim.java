/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.tracciato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UtenteSlim implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -2040832238990324193L;

    @JsonProperty("bo_user")
    private Boolean backofficeUser = Boolean.FALSE;

    @JsonProperty("cf")
    private String cf;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cognome")
    private String cognome;

    public Boolean getBackofficeUser() {
        return backofficeUser;
    }

    public void setBackofficeUser(Boolean backofficeUser) {
        this.backofficeUser = backofficeUser;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}