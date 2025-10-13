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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.DocumentoFruitore;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;

//@ApiModel(description="Model che contiene i campi relativi al documento da creare")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class DocumentoFruitore   {

  private String id = null;
  private String idPadre = null;
  private String idPratica = null;
  private String codiceStato = null;
  private String codiceTipo = null;
  private String titolo = null;
  private String descrizione = null;
  private String autore = null;
  private List<DocumentoFruitore> allegati = new ArrayList<DocumentoFruitore>();

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("id")
  @NotNull
  @Size(max=255)
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
  @Size(max=255)
  public String getIdPadre() {
    return idPadre;
  }
  public void setIdPadre(String idPadre) {
    this.idPadre = idPadre;
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
  @JsonProperty("codiceStato")
  @NotNull
  @Size(max=100)
  public String getCodiceStato() {
    return codiceStato;
  }
  public void setCodiceStato(String codiceStato) {
    this.codiceStato = codiceStato;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("codiceTipo")
  @Size(max=100)
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
  @Size(max=255)
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
  @Size(max=100)
  public String getAutore() {
    return autore;
  }
  public void setAutore(String autore) {
    this.autore = autore;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("allegati")
  public List<DocumentoFruitore> getAllegati() {
    return allegati;
  }
  public void setAllegati(List<DocumentoFruitore> allegati) {
    this.allegati = allegati;
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
        Objects.equals(idPratica, documentoFruitore.idPratica) &&
        Objects.equals(codiceStato, documentoFruitore.codiceStato) &&
        Objects.equals(codiceTipo, documentoFruitore.codiceTipo) &&
        Objects.equals(titolo, documentoFruitore.titolo) &&
        Objects.equals(descrizione, documentoFruitore.descrizione) &&
        Objects.equals(autore, documentoFruitore.autore) &&
        Objects.equals(allegati, documentoFruitore.allegati);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, idPadre, idPratica, codiceStato, codiceTipo, titolo, descrizione, autore, allegati);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentoFruitore {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    idPadre: ").append(toIndentedString(idPadre)).append("\n");
    sb.append("    idPratica: ").append(toIndentedString(idPratica)).append("\n");
    sb.append("    codiceStato: ").append(toIndentedString(codiceStato)).append("\n");
    sb.append("    codiceTipo: ").append(toIndentedString(codiceTipo)).append("\n");
    sb.append("    titolo: ").append(toIndentedString(titolo)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    autore: ").append(toIndentedString(autore)).append("\n");
    sb.append("    allegati: ").append(toIndentedString(allegati)).append("\n");
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

