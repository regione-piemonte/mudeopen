/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
////import io.swagger.v3.oas.annotations.media.Schema;


//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaResteasyEapServerCodegen", date = "2024-12-18T22:40:48.348410165Z[GMT]")
public class IstanzaMude   {
  private String id = null;
  private String codice = null;
  private String tipo = null;
  private String descrizioneIntervento = null;
  private String competenza = null;

  /**
   * ID univoco dell&#x27;istanza
   **/
  
  //@Schema(description = "ID univoco dell'istanza")
  @JsonProperty("id")
  @NotNull
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Codice identificativo dell&#x27;istanza
   **/
  
  //@Schema(description = "Codice identificativo dell'istanza")
  @JsonProperty("codice")
  @NotNull
  public String getCodice() {
    return codice;
  }
  public void setCodice(String codice) {
    this.codice = codice;
  }

  /**
   * Tipo dell&#x27;istanza
   **/
  
  //@Schema(description = "Tipo dell'istanza")
  @JsonProperty("tipo")
  @NotNull
  public String getTipo() {
    return tipo;
  }
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  /**
   * Descrizione dell&#x27;intervento
   **/
  
  //@Schema(description = "Descrizione dell'intervento")
  @JsonProperty("descrizioneIntervento")
  @NotNull
  public String getDescrizioneIntervento() {
    return descrizioneIntervento;
  }
  public void setDescrizioneIntervento(String descrizioneIntervento) {
    this.descrizioneIntervento = descrizioneIntervento;
  }

  /**
   * Dettaglio della competenza
   **/
  
  //@Schema(description = "Dettaglio della competenza")
  @JsonProperty("competenza")
  @NotNull
  public String getCompetenza() {
    return competenza;
  }
  public void setCompetenza(String competenza) {
    this.competenza = competenza;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IstanzaMude istanzaMude = (IstanzaMude) o;
    return Objects.equals(id, istanzaMude.id) &&
        Objects.equals(codice, istanzaMude.codice) &&
        Objects.equals(tipo, istanzaMude.tipo) &&
        Objects.equals(descrizioneIntervento, istanzaMude.descrizioneIntervento) &&
        Objects.equals(competenza, istanzaMude.competenza);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, codice, tipo, descrizioneIntervento, competenza);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IstanzaMude {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    descrizioneIntervento: ").append(toIndentedString(descrizioneIntervento)).append("\n");
    sb.append("    competenza: ").append(toIndentedString(competenza)).append("\n");
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
