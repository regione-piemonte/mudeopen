/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class InviaSegnaleFruitoreVariabileArrayRequest   {

  private String nome = null;
  private List<String> valore = null;

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

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("valore")
  @NotNull
  public List<String> getValore() {
    return valore;
  }
  public void setValore(List<String> valore) {
    this.valore = valore;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InviaSegnaleFruitoreVariabileArrayRequest inviaSegnaleFruitoreVariabileRequest = (InviaSegnaleFruitoreVariabileArrayRequest) o;
    return Objects.equals(nome, inviaSegnaleFruitoreVariabileRequest.nome) &&
        Objects.equals(valore, inviaSegnaleFruitoreVariabileRequest.valore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, valore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InviaSegnaleFruitoreVariabileRequest {\n");

    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    valore: ").append(toIndentedString(valore)).append("\n");
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

