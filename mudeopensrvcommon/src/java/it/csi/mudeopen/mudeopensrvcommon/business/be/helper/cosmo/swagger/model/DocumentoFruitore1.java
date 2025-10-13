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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.ArchiviazioneDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.FirmaDocumentoFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.TipoPraticaFruitore;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;

//@ApiModel(description="")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class DocumentoFruitore1   {

  private String id = null;
  private String idPadre = null;
  private String nomeFile = null;
  private Long dimensione = null;
  private String titolo = null;
  private String descrizione = null;
  private String autore = null;
  private String mimeType = null;
  private List<FirmaDocumentoFruitore> firme = new ArrayList<FirmaDocumentoFruitore>();
  private ArchiviazioneDocumento archiviazione = null;
  private String refURL = null;
  private TipoPraticaFruitore tipo = null;
  private TipoPraticaFruitore stato = null;
  private String idCosmo = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("id")
  @NotNull
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
  @JsonProperty("nomeFile")
  public String getNomeFile() {
    return nomeFile;
  }
  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dimensione")
  public Long getDimensione() {
    return dimensione;
  }
  public void setDimensione(Long dimensione) {
    this.dimensione = dimensione;
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
  @JsonProperty("firme")
  public List<FirmaDocumentoFruitore> getFirme() {
    return firme;
  }
  public void setFirme(List<FirmaDocumentoFruitore> firme) {
    this.firme = firme;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("archiviazione")
  public ArchiviazioneDocumento getArchiviazione() {
    return archiviazione;
  }
  public void setArchiviazione(ArchiviazioneDocumento archiviazione) {
    this.archiviazione = archiviazione;
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
  public TipoPraticaFruitore getTipo() {
    return tipo;
  }
  public void setTipo(TipoPraticaFruitore tipo) {
    this.tipo = tipo;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("stato")
  @NotNull
  public TipoPraticaFruitore getStato() {
    return stato;
  }
  public void setStato(TipoPraticaFruitore stato) {
    this.stato = stato;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("idCosmo")
  public String getIdCosmo() {
    return idCosmo;
  }
  public void setIdCosmo(String idCosmo) {
    this.idCosmo = idCosmo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentoFruitore1 documentoFruitore1 = (DocumentoFruitore1) o;
    return Objects.equals(id, documentoFruitore1.id) &&
        Objects.equals(idPadre, documentoFruitore1.idPadre) &&
        Objects.equals(nomeFile, documentoFruitore1.nomeFile) &&
        Objects.equals(dimensione, documentoFruitore1.dimensione) &&
        Objects.equals(titolo, documentoFruitore1.titolo) &&
        Objects.equals(descrizione, documentoFruitore1.descrizione) &&
        Objects.equals(autore, documentoFruitore1.autore) &&
        Objects.equals(mimeType, documentoFruitore1.mimeType) &&
        Objects.equals(firme, documentoFruitore1.firme) &&
        Objects.equals(archiviazione, documentoFruitore1.archiviazione) &&
        Objects.equals(refURL, documentoFruitore1.refURL) &&
        Objects.equals(tipo, documentoFruitore1.tipo) &&
        Objects.equals(stato, documentoFruitore1.stato) &&
        Objects.equals(idCosmo, documentoFruitore1.idCosmo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, idPadre, nomeFile, dimensione, titolo, descrizione, autore, mimeType, firme, archiviazione, refURL, tipo, stato, idCosmo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentoFruitore1 {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idPadre: ").append(toIndentedString(idPadre)).append("\n");
    sb.append("    nomeFile: ").append(toIndentedString(nomeFile)).append("\n");
    sb.append("    dimensione: ").append(toIndentedString(dimensione)).append("\n");
    sb.append("    titolo: ").append(toIndentedString(titolo)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    autore: ").append(toIndentedString(autore)).append("\n");
    sb.append("    mimeType: ").append(toIndentedString(mimeType)).append("\n");
    sb.append("    firme: ").append(toIndentedString(firme)).append("\n");
    sb.append("    archiviazione: ").append(toIndentedString(archiviazione)).append("\n");
    sb.append("    refURL: ").append(toIndentedString(refURL)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    idCosmo: ").append(toIndentedString(idCosmo)).append("\n");
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

