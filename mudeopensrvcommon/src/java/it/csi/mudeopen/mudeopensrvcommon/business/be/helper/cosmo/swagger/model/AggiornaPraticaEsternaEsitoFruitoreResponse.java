/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AggiornaPraticaEsternaEsitoFruitoreResponse   {

  private String code = null;
  private Integer status = null;
  private String idPratica = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("code")
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("status")
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("id_pratica")
  public String getIdPratica() {
    return idPratica;
  }
  public void setIdPratica(String idPratica) {
    this.idPratica = idPratica;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggiornaPraticaEsternaEsitoFruitoreResponse aggiornaPraticaEsternaEsitoFruitoreResponse = (AggiornaPraticaEsternaEsitoFruitoreResponse) o;
    return Objects.equals(code, aggiornaPraticaEsternaEsitoFruitoreResponse.code) &&
        Objects.equals(status, aggiornaPraticaEsternaEsitoFruitoreResponse.status) &&
        Objects.equals(idPratica, aggiornaPraticaEsternaEsitoFruitoreResponse.idPratica);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, status, idPratica);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiornaPraticaEsternaEsitoFruitoreResponse {\n");

    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    idPratica: ").append(toIndentedString(idPratica)).append("\n");
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

