/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.allegato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TipoAllegatoVO  implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 5106760606195241272L;

    @JsonProperty("codice_tipo_allegato")
    private String codice;

    @JsonProperty("descrizione_tipo_allegato")
    private String descrizioneEstesa;

    @JsonProperty("descrizione_breve_tipo_allegato")
    private String descrizione;

    @JsonProperty("categoria_allegato")
    private CategoriaAllegatoVO categoriaAllegato;

    @JsonProperty("sub_cod_tipo_allegato")
    private String subCodeTipoAllegato;

    @JsonProperty("valida_firma")
    private Boolean validaFirma;

    @JsonProperty("firma_obbligatoria")
    private Boolean firmaObbligatoria;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizioneEstesa() {
        return descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {
        this.descrizioneEstesa = descrizioneEstesa;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public CategoriaAllegatoVO getCategoriaAllegato() {
        return categoriaAllegato;
    }

    public void setCategoriaAllegato(CategoriaAllegatoVO categoriaAllegato) {
        this.categoriaAllegato = categoriaAllegato;
    }

    public String getSubCodeTipoAllegato() {
        return subCodeTipoAllegato;
    }

    public void setSubCodeTipoAllegato(String subCodeTipoAllegato) {
        this.subCodeTipoAllegato = subCodeTipoAllegato;
    }

    public Boolean getFirmaObbligatoria() {
        return firmaObbligatoria == null? false : firmaObbligatoria;
    }

    public void setFirmaObbligatoria(Boolean firmaObbligatoria) {
        this.firmaObbligatoria = firmaObbligatoria;
    }

    public Boolean getValidaFirma() {
        return validaFirma == null? false : validaFirma;
    }

    public void setValidaFirma(Boolean validaFirma) {
        this.validaFirma = validaFirma;
    }

}