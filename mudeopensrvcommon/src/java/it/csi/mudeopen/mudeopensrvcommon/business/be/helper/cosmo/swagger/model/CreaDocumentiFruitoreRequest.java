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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaDocumentoFruitoreRequest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;

//@ApiModel(description="Model che contiene i campi relativi ai documenti da creare")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class CreaDocumentiFruitoreRequest   {

  private List<CreaDocumentoFruitoreRequest> documenti = new ArrayList<CreaDocumentoFruitoreRequest>();
  private String idPratica = null;
  private String codiceIpaEnte = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("documenti")
  @NotNull
  public List<CreaDocumentoFruitoreRequest> getDocumenti() {
    return documenti;
  }
  public void setDocumenti(List<CreaDocumentoFruitoreRequest> documenti) {
    this.documenti = documenti;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("idPratica")
  @NotNull
  @Size(min=1,max=255)
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
  @Size(min=1,max=255)
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
    CreaDocumentiFruitoreRequest creaDocumentiFruitoreRequest = (CreaDocumentiFruitoreRequest) o;
    return Objects.equals(documenti, creaDocumentiFruitoreRequest.documenti) &&
        Objects.equals(idPratica, creaDocumentiFruitoreRequest.idPratica) &&
        Objects.equals(codiceIpaEnte, creaDocumentiFruitoreRequest.codiceIpaEnte);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documenti, idPratica, codiceIpaEnte);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreaDocumentiFruitoreRequest {\n");

    sb.append("    documenti: ").append(toIndentedString(documenti)).append("\n");
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

