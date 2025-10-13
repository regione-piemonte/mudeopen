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
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class CreaEventoFruitoreResponse   {

  private String idEvento = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("idEvento")
  @NotNull
  @Size(min=1,max=255)
  public String getIdEvento() {
    return idEvento;
  }
  public void setIdEvento(String idEvento) {
    this.idEvento = idEvento;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreaEventoFruitoreResponse creaEventoFruitoreResponse = (CreaEventoFruitoreResponse) o;
    return Objects.equals(idEvento, creaEventoFruitoreResponse.idEvento);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idEvento);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreaEventoFruitoreResponse {\n");

    sb.append("    idEvento: ").append(toIndentedString(idEvento)).append("\n");
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

