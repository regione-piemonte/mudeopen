/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//import io.swagger.annotations.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AggiornaPraticaEsternaAttivitaFruitoreRequest   {

  private String linkAttivita = null;
  private String nome = null;
  private String descrizione = null;
  private List<AggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest> assegnazione = new ArrayList<AggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest>();

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
  public List<AggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest> getAssegnazione() {
    return assegnazione;
  }
  public void setAssegnazione(List<AggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest> assegnazione) {
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
    AggiornaPraticaEsternaAttivitaFruitoreRequest aggiornaPraticaEsternaAttivitaFruitoreRequest = (AggiornaPraticaEsternaAttivitaFruitoreRequest) o;
    return Objects.equals(linkAttivita, aggiornaPraticaEsternaAttivitaFruitoreRequest.linkAttivita) &&
        Objects.equals(nome, aggiornaPraticaEsternaAttivitaFruitoreRequest.nome) &&
        Objects.equals(descrizione, aggiornaPraticaEsternaAttivitaFruitoreRequest.descrizione) &&
        Objects.equals(assegnazione, aggiornaPraticaEsternaAttivitaFruitoreRequest.assegnazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(linkAttivita, nome, descrizione, assegnazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiornaPraticaEsternaAttivitaFruitoreRequest {\n");

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

