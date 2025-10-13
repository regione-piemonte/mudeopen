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
public class CreaEventoFruitoreRequest   {

  private String codiceIpaEnte = null;
  private String destinatario = null;
  private String titolo = null;
  private String descrizione = null;
  private Date scadenza = null;

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

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("destinatario")
  @NotNull
  @Size(min=1,max=255)
  public String getDestinatario() {
    return destinatario;
  }
  public void setDestinatario(String destinatario) {
    this.destinatario = destinatario;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("titolo")
  @NotNull
  @Size(min=1,max=255)
  public String getTitolo() {
    return titolo;
  }
  public void setTitolo(String titolo) {
    this.titolo = titolo;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("descrizione")
  @NotNull
  @Size(min=1)
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("scadenza")
  @NotNull
  public Date getScadenza() {
    return scadenza;
  }
  public void setScadenza(Date scadenza) {
    this.scadenza = scadenza;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreaEventoFruitoreRequest creaEventoFruitoreRequest = (CreaEventoFruitoreRequest) o;
    return Objects.equals(codiceIpaEnte, creaEventoFruitoreRequest.codiceIpaEnte) &&
        Objects.equals(destinatario, creaEventoFruitoreRequest.destinatario) &&
        Objects.equals(titolo, creaEventoFruitoreRequest.titolo) &&
        Objects.equals(descrizione, creaEventoFruitoreRequest.descrizione) &&
        Objects.equals(scadenza, creaEventoFruitoreRequest.scadenza);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceIpaEnte, destinatario, titolo, descrizione, scadenza);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreaEventoFruitoreRequest {\n");

    sb.append("    codiceIpaEnte: ").append(toIndentedString(codiceIpaEnte)).append("\n");
    sb.append("    destinatario: ").append(toIndentedString(destinatario)).append("\n");
    sb.append("    titolo: ").append(toIndentedString(titolo)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    scadenza: ").append(toIndentedString(scadenza)).append("\n");
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

