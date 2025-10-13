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

//@ApiModel(description="Model che contiene i dati sul contenuto fisico")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class CreaDocumentoFruitoreContenutoRequest   {

  private String nomeFile = null;
  private String mimeType = null;
  private String contenutoFisico = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("nomeFile")
  @NotNull
  @Size(min=1,max=255)
  public String getNomeFile() {
    return nomeFile;
  }
  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("mimeType")
  @Size(min=1,max=255)
  public String getMimeType() {
    return mimeType;
  }
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("contenutoFisico")
  @NotNull
  @Pattern(regexp="^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$")
  public String getContenutoFisico() {
    return contenutoFisico;
  }
  public void setContenutoFisico(String contenutoFisico) {
    this.contenutoFisico = contenutoFisico;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreaDocumentoFruitoreContenutoRequest creaDocumentoFruitoreContenutoRequest = (CreaDocumentoFruitoreContenutoRequest) o;
    return Objects.equals(nomeFile, creaDocumentoFruitoreContenutoRequest.nomeFile) &&
        Objects.equals(mimeType, creaDocumentoFruitoreContenutoRequest.mimeType) &&
        Objects.equals(contenutoFisico, creaDocumentoFruitoreContenutoRequest.contenutoFisico);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nomeFile, mimeType, contenutoFisico);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreaDocumentoFruitoreContenutoRequest {\n");

    sb.append("    nomeFile: ").append(toIndentedString(nomeFile)).append("\n");
    sb.append("    mimeType: ").append(toIndentedString(mimeType)).append("\n");
    sb.append("    contenutoFisico: ").append(toIndentedString(contenutoFisico)).append("\n");
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

