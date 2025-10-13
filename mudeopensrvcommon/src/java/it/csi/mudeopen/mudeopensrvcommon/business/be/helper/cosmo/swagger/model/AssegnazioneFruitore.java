/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

//@ApiModel(description="utente o gruppo assegnatario dell'attività")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AssegnazioneFruitore   {

  private Utente utente = null;
  private String gruppo = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("utente")
  public Utente getUtente() {
    return utente;
  }
  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("gruppo")
  public String getGruppo() {
    return gruppo;
  }
  public void setGruppo(String gruppo) {
    this.gruppo = gruppo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssegnazioneFruitore assegnazioneFruitore = (AssegnazioneFruitore) o;
    return Objects.equals(utente, assegnazioneFruitore.utente) &&
        Objects.equals(gruppo, assegnazioneFruitore.gruppo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(utente, gruppo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssegnazioneFruitore {\n");

    sb.append("    utente: ").append(toIndentedString(utente)).append("\n");
    sb.append("    gruppo: ").append(toIndentedString(gruppo)).append("\n");
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

