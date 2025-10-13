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
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.AggiornaRelazionePraticaResponseRelazioniPratica;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;

//@ApiModel(description="Model contenente i dati delle relazioni di cui si è richiesto l'inserimento o l'aggiornamento e gli esiti di tali operazioni")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AggiornaRelazionePraticaResponse   {

  private String idPraticaExtDa = null;
  private List<AggiornaRelazionePraticaResponseRelazioniPratica> relazioniPratica = new ArrayList<AggiornaRelazionePraticaResponseRelazioniPratica>();

  /**
   * id esterno della pratica da cui partono le relazioni
   **/

  @ApiModelProperty(required = true, value = "id esterno della pratica da cui partono le relazioni")
  @JsonProperty("idPraticaExtDa")
  @NotNull
  public String getIdPraticaExtDa() {
    return idPraticaExtDa;
  }
  public void setIdPraticaExtDa(String idPraticaExtDa) {
    this.idPraticaExtDa = idPraticaExtDa;
  }

  /**
   * lista contenente i dati delle relazioni di cui è stato richiesto l&#39;inserimento o l&#39;aggiornamento
   **/

  @ApiModelProperty(value = "lista contenente i dati delle relazioni di cui è stato richiesto l'inserimento o l'aggiornamento")
  @JsonProperty("relazioniPratica")
  public List<AggiornaRelazionePraticaResponseRelazioniPratica> getRelazioniPratica() {
    return relazioniPratica;
  }
  public void setRelazioniPratica(List<AggiornaRelazionePraticaResponseRelazioniPratica> relazioniPratica) {
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
    AggiornaRelazionePraticaResponse aggiornaRelazionePraticaResponse = (AggiornaRelazionePraticaResponse) o;
    return Objects.equals(idPraticaExtDa, aggiornaRelazionePraticaResponse.idPraticaExtDa) &&
        Objects.equals(relazioniPratica, aggiornaRelazionePraticaResponse.relazioniPratica);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPraticaExtDa, relazioniPratica);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggiornaRelazionePraticaResponse {\n");

    sb.append("    idPraticaExtDa: ").append(toIndentedString(idPraticaExtDa)).append("\n");
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

