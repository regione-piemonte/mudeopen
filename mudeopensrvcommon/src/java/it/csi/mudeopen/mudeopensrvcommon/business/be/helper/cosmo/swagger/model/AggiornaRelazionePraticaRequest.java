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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.RelazionePratica;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;

//@ApiModel(description="Model contenente i dati delle relazioni tra pratiche che si vuole inserire o aggiornare")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AggiornaRelazionePraticaRequest   {

  private String codiceIpaEnte = null;
  private List<RelazionePratica> relazioniPratica = new ArrayList<RelazionePratica>();

  /**
   * codice Ipa dell&#39;ente
   **/

  @ApiModelProperty(required = true, value = "codice Ipa dell'ente")
  @JsonProperty("codiceIpaEnte")
  @NotNull
  public String getCodiceIpaEnte() {
    return codiceIpaEnte;
  }
  public void setCodiceIpaEnte(String codiceIpaEnte) {
    this.codiceIpaEnte = codiceIpaEnte;
  }

  /**
   * lista delle relazioni tra pratiche che si vuole inserire o aggiornare
   **/

  @ApiModelProperty(value = "lista delle relazioni tra pratiche che si vuole inserire o aggiornare")
  @JsonProperty("relazioniPratica")
  public List<RelazionePratica> getRelazioniPratica() {
    return relazioniPratica;
  }
  public void setRelazioniPratica(List<RelazionePratica> relazioniPratica) {
    this.relazioniPratica = relazioniPratica;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggiornaRelazionePraticaRequest aggiornaRelazionePraticaRequest = (AggiornaRelazionePraticaRequest) o;
    return Objects.equals(codiceIpaEnte, aggiornaRelazionePraticaRequest.codiceIpaEnte) &&
        Objects.equals(relazioniPratica, aggiornaRelazionePraticaRequest.relazioniPratica);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceIpaEnte, relazioniPratica);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiornaRelazionePraticaRequest {\n");

    sb.append("    codiceIpaEnte: ").append(toIndentedString(codiceIpaEnte)).append("\n");
    sb.append("    relazioniPratica: ").append(toIndentedString(relazioniPratica)).append("\n");
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

