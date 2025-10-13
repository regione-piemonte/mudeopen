/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.cosmo;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.csi.mudeopen.mudeopensrvapi.vo.cosmo.ArchiviazioneDocumentoFruitore;
import it.csi.mudeopen.mudeopensrvapi.vo.cosmo.TipoDocumentoFruitore;

import javax.validation.constraints.*;
import io.swagger.annotations.*;

@ApiModel(description="")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-12-01T11:43:27.748Z")
public class DocumentoFruitore   {

  private String id = null;
  private String idPadre = null;
  private String titolo = null;
  private String descrizione = null;
  private String autore = null;
  private String mimeType = null;
  private String refURL = null;
  private TipoDocumentoFruitore tipo = null;
  private ArchiviazioneDocumentoFruitore archiviazione = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("id")
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
  public String getIdPadre() {
    return idPadre;
  }
  public void setIdPadre(String idPadre) {
    this.idPadre = idPadre;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("titolo")
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
  public String getAutore() {
    return autore;
  }
  public void setAutore(String autore) {
    this.autore = autore;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("mimeType")
  public String getMimeType() {
    return mimeType;
  }
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("refURL")
  public String getRefURL() {
    return refURL;
  }
  public void setRefURL(String refURL) {
    this.refURL = refURL;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("tipo")
  public TipoDocumentoFruitore getTipo() {
    return tipo;
  }
  public void setTipo(TipoDocumentoFruitore tipo) {
    this.tipo = tipo;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("archiviazione")
  public ArchiviazioneDocumentoFruitore getArchiviazione() {
    return archiviazione;
  }
  public void setArchiviazione(ArchiviazioneDocumentoFruitore archiviazione) {
    this.archiviazione = archiviazione;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentoFruitore documentoFruitore = (DocumentoFruitore) o;
    return Objects.equals(id, documentoFruitore.id) &&
        Objects.equals(idPadre, documentoFruitore.idPadre) &&
        Objects.equals(titolo, documentoFruitore.titolo) &&
        Objects.equals(descrizione, documentoFruitore.descrizione) &&
        Objects.equals(autore, documentoFruitore.autore) &&
        Objects.equals(mimeType, documentoFruitore.mimeType) &&
        Objects.equals(refURL, documentoFruitore.refURL) &&
        Objects.equals(tipo, documentoFruitore.tipo) &&
        Objects.equals(archiviazione, documentoFruitore.archiviazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, idPadre, titolo, descrizione, autore, mimeType, refURL, tipo, archiviazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentoFruitore {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idPadre: ").append(toIndentedString(idPadre)).append("\n");
    sb.append("    titolo: ").append(toIndentedString(titolo)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    autore: ").append(toIndentedString(autore)).append("\n");
    sb.append("    mimeType: ").append(toIndentedString(mimeType)).append("\n");
    sb.append("    refURL: ").append(toIndentedString(refURL)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    archiviazione: ").append(toIndentedString(archiviazione)).append("\n");
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

