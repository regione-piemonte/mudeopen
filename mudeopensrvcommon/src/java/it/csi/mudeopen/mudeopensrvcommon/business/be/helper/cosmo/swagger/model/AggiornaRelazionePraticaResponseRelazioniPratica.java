/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;

//import io.swagger.annotations.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

//@ApiModel(description="Model rappresentante la relazione di cui è stato richiesto l'inserimento o l'aggiornamento e l'esito di tale operazione")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AggiornaRelazionePraticaResponseRelazioniPratica   {

  private RelazionePratica relazionePratica = null;
  private Esito esito = null;

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("relazionePratica")
  @NotNull
  public RelazionePratica getRelazionePratica() {
    return relazionePratica;
  }
  public void setRelazionePratica(RelazionePratica relazionePratica) {
    this.relazionePratica = relazionePratica;
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
    AggiornaRelazionePraticaResponseRelazioniPratica aggiornaRelazionePraticaResponseRelazioniPratica = (AggiornaRelazionePraticaResponseRelazioniPratica) o;
    return Objects.equals(relazionePratica, aggiornaRelazionePraticaResponseRelazioniPratica.relazionePratica) &&
        Objects.equals(esito, aggiornaRelazionePraticaResponseRelazioniPratica.esito);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relazionePratica, esito);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiornaRelazionePraticaResponseRelazioniPratica {\n");

    sb.append("    relazionePratica: ").append(toIndentedString(relazionePratica)).append("\n");
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

