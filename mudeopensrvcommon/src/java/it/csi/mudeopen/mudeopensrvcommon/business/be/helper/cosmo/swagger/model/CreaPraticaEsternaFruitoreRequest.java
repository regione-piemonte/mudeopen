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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.CreaPraticaEsternaAttivitaFruitoreRequest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class CreaPraticaEsternaFruitoreRequest   {

  private String codiceIpaEnte = null;
  private String tipoPratica = null;
  private String oggetto = null;
  private String stato = null;
  private String riassunto = null;
  private String utenteCreazionePratica = null;
  private String idPraticaExt = null;
  private String linkPratica = null;
  private List<CreaPraticaEsternaAttivitaFruitoreRequest> attivita = new ArrayList<CreaPraticaEsternaAttivitaFruitoreRequest>();

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codiceIpaEnte")
  @NotNull
  public String getCodiceIpaEnte() {
    return codiceIpaEnte;
  }
  public void setCodiceIpaEnte(String codiceIpaEnte) {
    this.codiceIpaEnte = codiceIpaEnte;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("tipoPratica")
  @NotNull
  public String getTipoPratica() {
    return tipoPratica;
  }
  public void setTipoPratica(String tipoPratica) {
    this.tipoPratica = tipoPratica;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("oggetto")
  @NotNull
  @Size(max=255)
  public String getOggetto() {
    return oggetto;
  }
  public void setOggetto(String oggetto) {
    this.oggetto = oggetto;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("stato")
  @NotNull
  public String getStato() {
    return stato;
  }
  public void setStato(String stato) {
    this.stato = stato;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("riassunto")
  public String getRiassunto() {
    return riassunto;
  }
  public void setRiassunto(String riassunto) {
    this.riassunto = riassunto;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("utenteCreazionePratica")
  @NotNull
  @Size(max=50)
  public String getUtenteCreazionePratica() {
    return utenteCreazionePratica;
  }
  public void setUtenteCreazionePratica(String utenteCreazionePratica) {
    this.utenteCreazionePratica = utenteCreazionePratica;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("idPraticaExt")
  @NotNull
  @Size(max=255)
  public String getIdPraticaExt() {
    return idPraticaExt;
  }
  public void setIdPraticaExt(String idPraticaExt) {
    this.idPraticaExt = idPraticaExt;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("linkPratica")
  @NotNull
  public String getLinkPratica() {
    return linkPratica;
  }
  public void setLinkPratica(String linkPratica) {
    this.linkPratica = linkPratica;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("attivita")
  @NotNull
  public List<CreaPraticaEsternaAttivitaFruitoreRequest> getAttivita() {
    return attivita;
  }
  public void setAttivita(List<CreaPraticaEsternaAttivitaFruitoreRequest> attivita) {
    this.attivita = attivita;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreaPraticaEsternaFruitoreRequest creaPraticaEsternaFruitoreRequest = (CreaPraticaEsternaFruitoreRequest) o;
    return Objects.equals(codiceIpaEnte, creaPraticaEsternaFruitoreRequest.codiceIpaEnte) &&
        Objects.equals(tipoPratica, creaPraticaEsternaFruitoreRequest.tipoPratica) &&
        Objects.equals(oggetto, creaPraticaEsternaFruitoreRequest.oggetto) &&
        Objects.equals(stato, creaPraticaEsternaFruitoreRequest.stato) &&
        Objects.equals(riassunto, creaPraticaEsternaFruitoreRequest.riassunto) &&
        Objects.equals(utenteCreazionePratica, creaPraticaEsternaFruitoreRequest.utenteCreazionePratica) &&
        Objects.equals(idPraticaExt, creaPraticaEsternaFruitoreRequest.idPraticaExt) &&
        Objects.equals(linkPratica, creaPraticaEsternaFruitoreRequest.linkPratica) &&
        Objects.equals(attivita, creaPraticaEsternaFruitoreRequest.attivita);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceIpaEnte, tipoPratica, oggetto, stato, riassunto, utenteCreazionePratica, idPraticaExt, linkPratica, attivita);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreaPraticaEsternaFruitoreRequest {\n");

    sb.append("    codiceIpaEnte: ").append(toIndentedString(codiceIpaEnte)).append("\n");
    sb.append("    tipoPratica: ").append(toIndentedString(tipoPratica)).append("\n");
    sb.append("    oggetto: ").append(toIndentedString(oggetto)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
    sb.append("    riassunto: ").append(toIndentedString(riassunto)).append("\n");
    sb.append("    utenteCreazionePratica: ").append(toIndentedString(utenteCreazionePratica)).append("\n");
    sb.append("    idPraticaExt: ").append(toIndentedString(idPraticaExt)).append("\n");
    sb.append("    linkPratica: ").append(toIndentedString(linkPratica)).append("\n");
    sb.append("    attivita: ").append(toIndentedString(attivita)).append("\n");
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

