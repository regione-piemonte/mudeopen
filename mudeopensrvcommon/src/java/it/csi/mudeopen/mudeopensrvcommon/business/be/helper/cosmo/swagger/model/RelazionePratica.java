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
import java.util.Date;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;

//@ApiModel(description="Model contenente i dati relativi alla relazione pratica")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class RelazionePratica   {

  private String idPraticaExtA = null;
  private String tipoRelazione = null;
//  private String dtInizioValidita = null;
//  private String dtFineValidita = null;

  /**
   * id esterno della pratica verso cui è diretta una relazione
   **/

  @ApiModelProperty(required = true, value = "id esterno della pratica verso cui è diretta una relazione")
  @JsonProperty("idPraticaExtA")
  @NotNull
  public String getIdPraticaExtA() {
    return idPraticaExtA;
  }
  public void setIdPraticaExtA(String idPraticaExtA) {
    this.idPraticaExtA = idPraticaExtA;
  }

  /**
   * tipo della relazione
   **/

  @ApiModelProperty(example = "'DIPENDE_DA', 'DIPENDENTE_DA', 'DUPLICA'", required = true, value = "tipo della relazione")
  @JsonProperty("tipoRelazione")
  @NotNull
  public String getTipoRelazione() {
    return tipoRelazione;
  }
  public void setTipoRelazione(String tipoRelazione) {
    this.tipoRelazione = tipoRelazione;
  }

/*
  @ApiModelProperty(value = "data di inizio validità della relazione")
  @JsonProperty("dtInizioValidita")
  public String getDtInizioValidita() {
    return dtInizioValidita;
  }
  public void setDtInizioValidita(String dtInizioValidita) {
    this.dtInizioValidita = dtInizioValidita;
  }

  @ApiModelProperty(value = "data di fine validità della relazione")
  @JsonProperty("dtFineValidita")
  public String getDtFineValidita() {
    return dtFineValidita;
  }
  public void setDtFineValidita(String dtFineValidita) {
    this.dtFineValidita = dtFineValidita;
  }
*/
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RelazionePratica relazionePratica = (RelazionePratica) o;
    return Objects.equals(idPraticaExtA, relazionePratica.idPraticaExtA) &&
        Objects.equals(tipoRelazione, relazionePratica.tipoRelazione) /*&&
        Objects.equals(dtInizioValidita, relazionePratica.dtInizioValidita) &&
        Objects.equals(dtFineValidita, relazionePratica.dtFineValidita)*/;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPraticaExtA, tipoRelazione/*, dtInizioValidita, dtFineValidita*/);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RelazionePratica {\n");

    sb.append("    idPraticaExtA: ").append(toIndentedString(idPraticaExtA)).append("\n");
    sb.append("    tipoRelazione: ").append(toIndentedString(tipoRelazione)).append("\n");
    /*
    sb.append("    dtInizioValidita: ").append(toIndentedString(dtInizioValidita)).append("\n");
    sb.append("    dtFineValidita: ").append(toIndentedString(dtFineValidita)).append("\n");
    */
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

