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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaDocumentoFruitoreContenutoRequest;

import javax.validation.constraints.*;
//import io.swagger.annotations.*;

//@ApiModel(description="Model che contiene i campi relativi al documento da creare")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class CreaDocumentoFruitoreRequest   {

  private String id = null;
  private String idPadre = null;
  private String codiceTipo = null;
  private String titolo = null;
  private String descrizione = null;
  private String autore = null;
  private String uploadUUID = null;
  private CreaDocumentoFruitoreContenutoRequest contenuto = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("id")
  @Size(min=1,max=255)
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("idPadre")
  @Size(min=1,max=255)
  public String getIdPadre() {
    return idPadre;
  }
  public void setIdPadre(String idPadre) {
    this.idPadre = idPadre;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codiceTipo")
  @NotNull
  @Size(min=1,max=100)
  public String getCodiceTipo() {
    return codiceTipo;
  }
  public void setCodiceTipo(String codiceTipo) {
    this.codiceTipo = codiceTipo;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("titolo")
  @Size(min=1,max=255)
  public String getTitolo() {
    return titolo;
  }
  public void setTitolo(String titolo) {
    this.titolo = titolo;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("autore")
  @Size(min=0,max=100)
  public String getAutore() {
    return autore;
  }
  public void setAutore(String autore) {
    this.autore = autore;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("uploadUUID")
  @Size(min=1,max=100)
  public String getUploadUUID() {
    return uploadUUID;
  }
  public void setUploadUUID(String uploadUUID) {
    this.uploadUUID = uploadUUID;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("contenuto")
  public CreaDocumentoFruitoreContenutoRequest getContenuto() {
    return contenuto;
  }
  public void setContenuto(CreaDocumentoFruitoreContenutoRequest contenuto) {
    this.contenuto = contenuto;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreaDocumentoFruitoreRequest creaDocumentoFruitoreRequest = (CreaDocumentoFruitoreRequest) o;
    return Objects.equals(id, creaDocumentoFruitoreRequest.id) &&
        Objects.equals(idPadre, creaDocumentoFruitoreRequest.idPadre) &&
        Objects.equals(codiceTipo, creaDocumentoFruitoreRequest.codiceTipo) &&
        Objects.equals(titolo, creaDocumentoFruitoreRequest.titolo) &&
        Objects.equals(descrizione, creaDocumentoFruitoreRequest.descrizione) &&
        Objects.equals(autore, creaDocumentoFruitoreRequest.autore) &&
        Objects.equals(uploadUUID, creaDocumentoFruitoreRequest.uploadUUID) &&
        Objects.equals(contenuto, creaDocumentoFruitoreRequest.contenuto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, idPadre, codiceTipo, titolo, descrizione, autore, uploadUUID, contenuto);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreaDocumentoFruitoreRequest {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idPadre: ").append(toIndentedString(idPadre)).append("\n");
    sb.append("    codiceTipo: ").append(toIndentedString(codiceTipo)).append("\n");
    sb.append("    titolo: ").append(toIndentedString(titolo)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    autore: ").append(toIndentedString(autore)).append("\n");
    sb.append("    uploadUUID: ").append(toIndentedString(uploadUUID)).append("\n");
    sb.append("    contenuto: ").append(toIndentedString(contenuto)).append("\n");
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

