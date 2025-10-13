/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.CategoriaQuadroVO;

import java.io.Serializable;

@JsonInclude(Include.ALWAYS)
public class FunzioneVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5524010973075082732L;

    @JsonProperty("id_funzione")
    private Long id;

    @JsonProperty("codice_funzione")
    private String codice;

    @JsonProperty("desc_funzione")
    private String descrizione;

    @JsonProperty("tipo_funzione")
    private Character tipo;

    @JsonProperty("categoria_quadro")
    private CategoriaQuadroVO categoriaQuadro;

    @JsonProperty("previsto_pm")
    private Boolean previstoPM;

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

    public CategoriaQuadroVO getCategoriaQuadro() {
        return categoriaQuadro;
    }

    public void setCategoriaQuadro(CategoriaQuadroVO categoriaQuadro) {
        this.categoriaQuadro = categoriaQuadro;
    }

    public Boolean getPrevistoPM() {
        return previstoPM;
    }

    public void setPrevistoPM(Boolean previstoPM) {
        this.previstoPM = previstoPM;
    }
}