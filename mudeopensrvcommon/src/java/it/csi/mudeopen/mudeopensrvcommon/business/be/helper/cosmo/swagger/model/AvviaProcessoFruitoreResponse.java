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
public class AvviaProcessoFruitoreResponse   {

  private String idProcesso = null;
  private Integer idPraticaCosmo = null;
  private String idPratica = null;
  private String codiceIpaEnte = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("idProcesso")
  @NotNull
  public String getIdProcesso() {
    return idProcesso;
  }
  public void setIdProcesso(String idProcesso) {
    this.idProcesso = idProcesso;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("idPraticaCosmo")
  @NotNull
  public Integer getIdPraticaCosmo() {
    return idPraticaCosmo;
  }
  public void setIdPraticaCosmo(Integer idPraticaCosmo) {
    this.idPraticaCosmo = idPraticaCosmo;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("idPratica")
  @NotNull
  @Size(max=255)
  public String getIdPratica() {
    return idPratica;
  }
  public void setIdPratica(String idPratica) {
    this.idPratica = idPratica;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codiceIpaEnte")
  @NotNull
  @Size(max=255)
  public String getCodiceIpaEnte() {
    return codiceIpaEnte;
  }
  public void setCodiceIpaEnte(String codiceIpaEnte) {
    this.codiceIpaEnte = codiceIpaEnte;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AvviaProcessoFruitoreResponse avviaProcessoFruitoreResponse = (AvviaProcessoFruitoreResponse) o;
    return Objects.equals(idProcesso, avviaProcessoFruitoreResponse.idProcesso) &&
        Objects.equals(idPraticaCosmo, avviaProcessoFruitoreResponse.idPraticaCosmo) &&
        Objects.equals(idPratica, avviaProcessoFruitoreResponse.idPratica) &&
        Objects.equals(codiceIpaEnte, avviaProcessoFruitoreResponse.codiceIpaEnte);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idProcesso, idPraticaCosmo, idPratica, codiceIpaEnte);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvviaProcessoFruitoreResponse {\n");

    sb.append("    idProcesso: ").append(toIndentedString(idProcesso)).append("\n");
    sb.append("    idPraticaCosmo: ").append(toIndentedString(idPraticaCosmo)).append("\n");
    sb.append("    idPratica: ").append(toIndentedString(idPratica)).append("\n");
    sb.append("    codiceIpaEnte: ").append(toIndentedString(codiceIpaEnte)).append("\n");
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

