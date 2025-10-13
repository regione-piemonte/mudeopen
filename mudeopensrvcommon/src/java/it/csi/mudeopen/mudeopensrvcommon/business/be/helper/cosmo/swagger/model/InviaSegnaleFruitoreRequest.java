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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.InviaSegnaleFruitoreVariabileRequest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class InviaSegnaleFruitoreRequest   {

  private String codiceSegnale = null;
  private Boolean richiediCallback = null;
  private List<InviaSegnaleFruitoreVariabileRequest> variabili = new ArrayList<InviaSegnaleFruitoreVariabileRequest>();

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codiceSegnale")
  @NotNull
  public String getCodiceSegnale() {
    return codiceSegnale;
  }
  public void setCodiceSegnale(String codiceSegnale) {
    this.codiceSegnale = codiceSegnale;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("richiediCallback")
  public Boolean isRichiediCallback() {
    return richiediCallback;
  }
  public void setRichiediCallback(Boolean richiediCallback) {
    this.richiediCallback = richiediCallback;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("variabili")
  public List<InviaSegnaleFruitoreVariabileRequest> getVariabili() {
    return variabili;
  }
  public void setVariabili(List<InviaSegnaleFruitoreVariabileRequest> variabili) {
    this.variabili = variabili;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InviaSegnaleFruitoreRequest inviaSegnaleFruitoreRequest = (InviaSegnaleFruitoreRequest) o;
    return Objects.equals(codiceSegnale, inviaSegnaleFruitoreRequest.codiceSegnale) &&
        Objects.equals(richiediCallback, inviaSegnaleFruitoreRequest.richiediCallback) &&
        Objects.equals(variabili, inviaSegnaleFruitoreRequest.variabili);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceSegnale, richiediCallback, variabili);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InviaSegnaleFruitoreRequest {\n");

    sb.append("    codiceSegnale: ").append(toIndentedString(codiceSegnale)).append("\n");
    sb.append("    richiediCallback: ").append(toIndentedString(richiediCallback)).append("\n");
    sb.append("    variabili: ").append(toIndentedString(variabili)).append("\n");
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

