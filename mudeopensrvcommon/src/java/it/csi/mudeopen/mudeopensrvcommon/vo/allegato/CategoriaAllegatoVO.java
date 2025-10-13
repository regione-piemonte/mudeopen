/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.allegato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

//todo non più utilizzato ... verificare per rimozione
public class CategoriaAllegatoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5402388954929555419L;

    @JsonProperty("id_categoria_allegato")
    private String codice;

    @JsonProperty("descrizione_categoria_allegato")
    private String descrizione;

    @JsonProperty("descrizione_estesa_categoria_allegato")
    private String descrizioneEstesa;

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

    public String getDescrizioneEstesa() {
        return descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {
        this.descrizioneEstesa = descrizioneEstesa;
    }

}