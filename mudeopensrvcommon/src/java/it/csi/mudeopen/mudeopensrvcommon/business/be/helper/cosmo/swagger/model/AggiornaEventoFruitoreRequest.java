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
import java.util.Date;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AggiornaEventoFruitoreRequest   {

  private String codiceIpaEnte = null;
  private Date nuovaDataScadenza = null;
  private String nuovoTitolo = null;
  private String nuovaDescrizione = null;
  private Boolean eseguito = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codiceIpaEnte")
  @NotNull
  @Size(min=1,max=255)
  public String getCodiceIpaEnte() {
    return codiceIpaEnte;
  }
  public void setCodiceIpaEnte(String codiceIpaEnte) {
    this.codiceIpaEnte = codiceIpaEnte;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("nuovaDataScadenza")
  public Date getNuovaDataScadenza() {
    return nuovaDataScadenza;
  }
  public void setNuovaDataScadenza(Date nuovaDataScadenza) {
    this.nuovaDataScadenza = nuovaDataScadenza;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("nuovoTitolo")
  @Size(min=1,max=255)
  public String getNuovoTitolo() {
    return nuovoTitolo;
  }
  public void setNuovoTitolo(String nuovoTitolo) {
    this.nuovoTitolo = nuovoTitolo;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("nuovaDescrizione")
  @Size(min=1)
  public String getNuovaDescrizione() {
    return nuovaDescrizione;
  }
  public void setNuovaDescrizione(String nuovaDescrizione) {
    this.nuovaDescrizione = nuovaDescrizione;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("eseguito")
  public Boolean isEseguito() {
    return eseguito;
  }
  public void setEseguito(Boolean eseguito) {
    this.eseguito = eseguito;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggiornaEventoFruitoreRequest aggiornaEventoFruitoreRequest = (AggiornaEventoFruitoreRequest) o;
    return Objects.equals(codiceIpaEnte, aggiornaEventoFruitoreRequest.codiceIpaEnte) &&
        Objects.equals(nuovaDataScadenza, aggiornaEventoFruitoreRequest.nuovaDataScadenza) &&
        Objects.equals(nuovoTitolo, aggiornaEventoFruitoreRequest.nuovoTitolo) &&
        Objects.equals(nuovaDescrizione, aggiornaEventoFruitoreRequest.nuovaDescrizione) &&
        Objects.equals(eseguito, aggiornaEventoFruitoreRequest.eseguito);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceIpaEnte, nuovaDataScadenza, nuovoTitolo, nuovaDescrizione, eseguito);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiornaEventoFruitoreRequest {\n");

    sb.append("    codiceIpaEnte: ").append(toIndentedString(codiceIpaEnte)).append("\n");
    sb.append("    nuovaDataScadenza: ").append(toIndentedString(nuovaDataScadenza)).append("\n");
    sb.append("    nuovoTitolo: ").append(toIndentedString(nuovoTitolo)).append("\n");
    sb.append("    nuovaDescrizione: ").append(toIndentedString(nuovaDescrizione)).append("\n");
    sb.append("    eseguito: ").append(toIndentedString(eseguito)).append("\n");
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

