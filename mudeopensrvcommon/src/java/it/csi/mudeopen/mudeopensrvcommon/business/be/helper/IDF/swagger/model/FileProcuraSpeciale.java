/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.File;
import javax.validation.constraints.*;
////import io.swagger.v3.oas.annotations.media.Schema;


//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaResteasyEapServerCodegen", date = "2024-12-18T22:40:48.348410165Z[GMT]")
public class FileProcuraSpeciale   {
  private String nomeFile = null;
  private String mimeType = null;
  private String contenutoFisico = null;

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("nomeFile")
  @NotNull
  public String getNomeFile() {
    return nomeFile;
  }
  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("mimeType")
  @NotNull
  public String getMimeType() {
    return mimeType;
  }
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("contenutoFisico")
  @NotNull
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
    FileProcuraSpeciale fileProcuraSpeciale = (FileProcuraSpeciale) o;
    return Objects.equals(nomeFile, fileProcuraSpeciale.nomeFile) &&
        Objects.equals(mimeType, fileProcuraSpeciale.mimeType) &&
        Objects.equals(contenutoFisico, fileProcuraSpeciale.contenutoFisico);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nomeFile, mimeType, contenutoFisico);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileProcuraSpeciale {\n");
    
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
