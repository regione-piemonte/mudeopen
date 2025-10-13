/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.tracciato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TipoTracciatoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -6150236365534845000L;

    @JsonProperty("id_tipo_tracciato")
    private Long id;

    @JsonProperty("codice_tipo_tracciato")
    private String codice;

    @JsonProperty("descrizione_tipo_tracciato")
    private String descrizione;

    @JsonProperty("versione")
    private String versione;

    @JsonProperty("xsd_validazione")
    private String xsdValidazione;

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

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
    }

    public String getXsdValidazione() {
        return xsdValidazione;
    }

    public void setXsdValidazione(String xsdValidazione) {
        this.xsdValidazione = xsdValidazione;
    }
}