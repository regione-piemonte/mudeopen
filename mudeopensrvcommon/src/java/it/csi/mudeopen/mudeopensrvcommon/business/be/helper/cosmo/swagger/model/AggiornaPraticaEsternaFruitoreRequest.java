/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//import io.swagger.annotations.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AggiornaPraticaEsternaFruitoreRequest   {

  private String codiceIpaEnte = null;
  private String tipoPratica = null;
  private String stato = null;
  private String riassunto = null;
  private String linkPratica = null;
  private List<AggiornaPraticaEsternaAttivitaFruitoreRequest> attivita = new ArrayList<AggiornaPraticaEsternaAttivitaFruitoreRequest>();
  private Date dataFinePratica = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codiceIpaEnte")
  @NotNull
  public String getCodiceIpaEnte() {
    return codiceIpaEnte;
  }
  public void setCodiceIpaEnte(String codiceIpaEnte) {
    this.codiceIpaEnte = codiceIpaEnte;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("tipoPratica")
  public String getTipoPratica() {
    return tipoPratica;
  }
  public void setTipoPratica(String tipoPratica) {
    this.tipoPratica = tipoPratica;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("stato")
  public String getStato() {
    return stato;
  }
  public void setStato(String stato) {
    this.stato = stato;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("riassunto")
  public String getRiassunto() {
    return riassunto;
  }
  public void setRiassunto(String riassunto) {
    this.riassunto = riassunto;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("linkPratica")
  public String getLinkPratica() {
    return linkPratica;
  }
  public void setLinkPratica(String linkPratica) {
    this.linkPratica = linkPratica;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("attivita")
  public List<AggiornaPraticaEsternaAttivitaFruitoreRequest> getAttivita() {
    return attivita;
  }
  public void setAttivita(List<AggiornaPraticaEsternaAttivitaFruitoreRequest> attivita) {
    this.attivita = attivita;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dataFinePratica")
  public Date getDataFinePratica() {
    return dataFinePratica;
  }
  public void setDataFinePratica(Date dataFinePratica) {
    this.dataFinePratica = dataFinePratica;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggiornaPraticaEsternaFruitoreRequest aggiornaPraticaEsternaFruitoreRequest = (AggiornaPraticaEsternaFruitoreRequest) o;
    return Objects.equals(codiceIpaEnte, aggiornaPraticaEsternaFruitoreRequest.codiceIpaEnte) &&
        Objects.equals(tipoPratica, aggiornaPraticaEsternaFruitoreRequest.tipoPratica) &&
        Objects.equals(stato, aggiornaPraticaEsternaFruitoreRequest.stato) &&
        Objects.equals(riassunto, aggiornaPraticaEsternaFruitoreRequest.riassunto) &&
        Objects.equals(linkPratica, aggiornaPraticaEsternaFruitoreRequest.linkPratica) &&
        Objects.equals(attivita, aggiornaPraticaEsternaFruitoreRequest.attivita) &&
        Objects.equals(dataFinePratica, aggiornaPraticaEsternaFruitoreRequest.dataFinePratica);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceIpaEnte, tipoPratica, stato, riassunto, linkPratica, attivita, dataFinePratica);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiornaPraticaEsternaFruitoreRequest {\n");

    sb.append("    codiceIpaEnte: ").append(toIndentedString(codiceIpaEnte)).append("\n");
    sb.append("    tipoPratica: ").append(toIndentedString(tipoPratica)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    riassunto: ").append(toIndentedString(riassunto)).append("\n");
    sb.append("    linkPratica: ").append(toIndentedString(linkPratica)).append("\n");
    sb.append("    attivita: ").append(toIndentedString(attivita)).append("\n");
    sb.append("    dataFinePratica: ").append(toIndentedString(dataFinePratica)).append("\n");
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

