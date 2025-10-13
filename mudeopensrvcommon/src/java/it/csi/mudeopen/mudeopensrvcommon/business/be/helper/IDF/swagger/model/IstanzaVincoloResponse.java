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
import javax.validation.constraints.*;
////import io.swagger.v3.oas.annotations.media.Schema;


//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaResteasyEapServerCodegen", date = "2024-12-18T22:40:48.348410165Z[GMT]")
public class IstanzaVincoloResponse   {
  private String status = null;
  private String idIstanza = null;

  /**
   * Stato della risposta
   **/
  
  //@Schema(example = "OK", description = "Stato della risposta")
  @JsonProperty("status")
  @NotNull
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * ID dell&#x27;istanza creata
   **/
  
  //@Schema(example = "12345", description = "ID dell'istanza creata")
  @JsonProperty("idIstanza")
  @NotNull
  public String getIdIstanza() {
    return idIstanza;
  }
  public void setIdIstanza(String idIstanza) {
    this.idIstanza = idIstanza;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IstanzaVincoloResponse istanzaVincoloResponse = (IstanzaVincoloResponse) o;
    return Objects.equals(status, istanzaVincoloResponse.status) &&
        Objects.equals(idIstanza, istanzaVincoloResponse.idIstanza);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, idIstanza);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IstanzaVincoloResponse {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    idIstanza: ").append(toIndentedString(idIstanza)).append("\n");
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
