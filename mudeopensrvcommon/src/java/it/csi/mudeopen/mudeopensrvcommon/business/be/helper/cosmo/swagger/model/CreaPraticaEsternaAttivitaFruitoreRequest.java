/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaPraticaEsternaAssegnazioneAttivitaFruitoreRequest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class CreaPraticaEsternaAttivitaFruitoreRequest   {

  private String linkAttivita = null;
  private String nome = null;
  private String descrizione = null;
  private List<CreaPraticaEsternaAssegnazioneAttivitaFruitoreRequest> assegnazione = new ArrayList<CreaPraticaEsternaAssegnazioneAttivitaFruitoreRequest>();

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("linkAttivita")
  @NotNull
  public String getLinkAttivita() {
    return linkAttivita;
  }
  public void setLinkAttivita(String linkAttivita) {
    this.linkAttivita = linkAttivita;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("nome")
  @NotNull
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("assegnazione")
  public List<CreaPraticaEsternaAssegnazioneAttivitaFruitoreRequest> getAssegnazione() {
    return assegnazione;
  }
  public void setAssegnazione(List<CreaPraticaEsternaAssegnazioneAttivitaFruitoreRequest> assegnazione) {
    this.assegnazione = assegnazione;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreaPraticaEsternaAttivitaFruitoreRequest creaPraticaEsternaAttivitaFruitoreRequest = (CreaPraticaEsternaAttivitaFruitoreRequest) o;
    return Objects.equals(linkAttivita, creaPraticaEsternaAttivitaFruitoreRequest.linkAttivita) &&
        Objects.equals(nome, creaPraticaEsternaAttivitaFruitoreRequest.nome) &&
        Objects.equals(descrizione, creaPraticaEsternaAttivitaFruitoreRequest.descrizione) &&
        Objects.equals(assegnazione, creaPraticaEsternaAttivitaFruitoreRequest.assegnazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(linkAttivita, nome, descrizione, assegnazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreaPraticaEsternaAttivitaFruitoreRequest {\n");

    sb.append("    linkAttivita: ").append(toIndentedString(linkAttivita)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    assegnazione: ").append(toIndentedString(assegnazione)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

