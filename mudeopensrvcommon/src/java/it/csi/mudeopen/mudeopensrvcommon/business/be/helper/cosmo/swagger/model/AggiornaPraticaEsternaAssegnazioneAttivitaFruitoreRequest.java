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
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest   {

  private String utente = null;
  private String gruppo = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("utente")
  public String getUtente() {
    return utente;
  }
  public void setUtente(String utente) {
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
    AggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest aggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest = (AggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest) o;
    return Objects.equals(utente, aggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest.utente) &&
        Objects.equals(gruppo, aggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest.gruppo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(utente, gruppo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiornaPraticaEsternaAssegnazioneAttivitaFruitoreRequest {\n");

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

