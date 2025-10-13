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
public class FirmaDocumentoFruitore   {

  private Date data = null;
  private String firmatario = null;
  private String organizzazione = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("data")
  public Date getData() {
    return data;
  }
  public void setData(Date data) {
    this.data = data;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("firmatario")
  public String getFirmatario() {
    return firmatario;
  }
  public void setFirmatario(String firmatario) {
    this.firmatario = firmatario;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("organizzazione")
  public String getOrganizzazione() {
    return organizzazione;
  }
  public void setOrganizzazione(String organizzazione) {
    this.organizzazione = organizzazione;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FirmaDocumentoFruitore firmaDocumentoFruitore = (FirmaDocumentoFruitore) o;
    return Objects.equals(data, firmaDocumentoFruitore.data) &&
        Objects.equals(firmatario, firmaDocumentoFruitore.firmatario) &&
        Objects.equals(organizzazione, firmaDocumentoFruitore.organizzazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, firmatario, organizzazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FirmaDocumentoFruitore {\n");

    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    firmatario: ").append(toIndentedString(firmatario)).append("\n");
    sb.append("    organizzazione: ").append(toIndentedString(organizzazione)).append("\n");
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

