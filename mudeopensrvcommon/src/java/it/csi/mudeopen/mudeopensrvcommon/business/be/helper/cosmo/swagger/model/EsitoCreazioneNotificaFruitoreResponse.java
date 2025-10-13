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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.Esito;

import javax.validation.constraints.*;
//import io.swagger.annotations.*;

//@ApiModel(description="Model che contiene i campi relativi all'esito dell'invio di una notifica")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class EsitoCreazioneNotificaFruitoreResponse   {

  private String destinatario = null;
  private String utenteDestinatario = null;
  private String gruppoDestinatario = null;
  private Esito esito = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("destinatario")
  @Size(min=1,max=255)
  public String getDestinatario() {
    return destinatario;
  }
  public void setDestinatario(String destinatario) {
    this.destinatario = destinatario;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("utenteDestinatario")
  @Size(min=1,max=255)
  public String getUtenteDestinatario() {
    return utenteDestinatario;
  }
  public void setUtenteDestinatario(String utenteDestinatario) {
    this.utenteDestinatario = utenteDestinatario;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("gruppoDestinatario")
  @Size(min=1,max=255)
  public String getGruppoDestinatario() {
    return gruppoDestinatario;
  }
  public void setGruppoDestinatario(String gruppoDestinatario) {
    this.gruppoDestinatario = gruppoDestinatario;
  }

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("esito")
  @NotNull
  public Esito getEsito() {
    return esito;
  }
  public void setEsito(Esito esito) {
    this.esito = esito;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EsitoCreazioneNotificaFruitoreResponse esitoCreazioneNotificaFruitoreResponse = (EsitoCreazioneNotificaFruitoreResponse) o;
    return Objects.equals(destinatario, esitoCreazioneNotificaFruitoreResponse.destinatario) &&
        Objects.equals(utenteDestinatario, esitoCreazioneNotificaFruitoreResponse.utenteDestinatario) &&
        Objects.equals(gruppoDestinatario, esitoCreazioneNotificaFruitoreResponse.gruppoDestinatario) &&
        Objects.equals(esito, esitoCreazioneNotificaFruitoreResponse.esito);
  }

  @Override
  public int hashCode() {
    return Objects.hash(destinatario, utenteDestinatario, gruppoDestinatario, esito);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsitoCreazioneNotificaFruitoreResponse {\n");

    sb.append("    destinatario: ").append(toIndentedString(destinatario)).append("\n");
    sb.append("    utenteDestinatario: ").append(toIndentedString(utenteDestinatario)).append("\n");
    sb.append("    gruppoDestinatario: ").append(toIndentedString(gruppoDestinatario)).append("\n");
    sb.append("    esito: ").append(toIndentedString(esito)).append("\n");
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

