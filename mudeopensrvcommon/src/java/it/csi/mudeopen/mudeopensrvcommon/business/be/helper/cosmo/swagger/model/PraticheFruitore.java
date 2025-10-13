/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//import io.swagger.annotations.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class PraticheFruitore   {

  private String idPraticaExt = null;
  private String apiManager = null;
  private String oggetto = null;
  private String statoPratica = null;
  private String tipoPratica = null;
  private String riassunto = null;
  private String dataCreazione = null;
  private String utenteCreazione = null;
  private List<AttivitaFruitore> attivita = new ArrayList<AttivitaFruitore>();
  private List<TagRidotto> tag = new ArrayList<TagRidotto>();

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("idPraticaExt")
  @NotNull
  public String getIdPraticaExt() {
    return idPraticaExt;
  }
  public void setIdPraticaExt(String idPraticaExt) {
    this.idPraticaExt = idPraticaExt;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("apiManager")
  @NotNull
  public String getApiManager() {
    return apiManager;
  }
  public void setApiManager(String apiManager) {
    this.apiManager = apiManager;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("oggetto")
  @NotNull
  public String getOggetto() {
    return oggetto;
  }
  public void setOggetto(String oggetto) {
    this.oggetto = oggetto;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("statoPratica")
  public String getStatoPratica() {
    return statoPratica;
  }
  public void setStatoPratica(String statoPratica) {
    this.statoPratica = statoPratica;
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

  @ApiModelProperty(value = "")
  @JsonProperty("dataCreazione")
  public String getDataCreazione() {
    return dataCreazione;
  }
  public void setDataCreazione(String dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("utenteCreazione")
  public String getUtenteCreazione() {
    return utenteCreazione;
  }
  public void setUtenteCreazione(String utenteCreazione) {
    this.utenteCreazione = utenteCreazione;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("attivita")
  public List<AttivitaFruitore> getAttivita() {
    return attivita;
  }
  public void setAttivita(List<AttivitaFruitore> attivita) {
    this.attivita = attivita;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("tag")
  public List<TagRidotto> getTag() {
    return tag;
  }
  public void setTag(List<TagRidotto> tag) {
    this.tag = tag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PraticheFruitore praticheFruitore = (PraticheFruitore) o;
    return Objects.equals(idPraticaExt, praticheFruitore.idPraticaExt) &&
        Objects.equals(apiManager, praticheFruitore.apiManager) &&
        Objects.equals(oggetto, praticheFruitore.oggetto) &&
        Objects.equals(statoPratica, praticheFruitore.statoPratica) &&
        Objects.equals(tipoPratica, praticheFruitore.tipoPratica) &&
        Objects.equals(riassunto, praticheFruitore.riassunto) &&
        Objects.equals(dataCreazione, praticheFruitore.dataCreazione) &&
        Objects.equals(utenteCreazione, praticheFruitore.utenteCreazione) &&
        Objects.equals(attivita, praticheFruitore.attivita) &&
        Objects.equals(tag, praticheFruitore.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPraticaExt, apiManager, oggetto, statoPratica, tipoPratica, riassunto, dataCreazione, utenteCreazione, attivita, tag);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PraticheFruitore {\n");

    sb.append("    idPraticaExt: ").append(toIndentedString(idPraticaExt)).append("\n");
    sb.append("    apiManager: ").append(toIndentedString(apiManager)).append("\n");
    sb.append("    oggetto: ").append(toIndentedString(oggetto)).append("\n");
    sb.append("    statoPratica: ").append(toIndentedString(statoPratica)).append("\n");
    sb.append("    tipoPratica: ").append(toIndentedString(tipoPratica)).append("\n");
    sb.append("    riassunto: ").append(toIndentedString(riassunto)).append("\n");
    sb.append("    dataCreazione: ").append(toIndentedString(dataCreazione)).append("\n");
    sb.append("    utenteCreazione: ").append(toIndentedString(utenteCreazione)).append("\n");
    sb.append("    attivita: ").append(toIndentedString(attivita)).append("\n");
    sb.append("    tag: ").append(toIndentedString(tag)).append("\n");
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

