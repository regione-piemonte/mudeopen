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
import java.util.List;
import javax.validation.constraints.*;
////import io.swagger.v3.oas.annotations.media.Schema;


//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaResteasyEapServerCodegen", date = "2024-12-18T22:40:48.348410165Z[GMT]")
public class CreaIstanzaVincoloBody   {
	
  private IstanzaMude datiIstanza = null;
  private Soggetto richiedente = null;
  private Soggetto delegato = null;
  private List<Ubicazione> ubicazioni = new ArrayList<Ubicazione>();
  private FileProcuraSpeciale fileProcuraSpeciale = null;
  
  //@Schema(description = "")
  @JsonProperty("datiIstanza")
  @NotNull
  public IstanzaMude getDatiIstanza() {
    return datiIstanza;
  }
  public void setDatiIstanza(IstanzaMude istanzaMude) {
    this.datiIstanza = istanzaMude;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("richiedente")
  @NotNull
  public Soggetto getRichiedente() {
    return richiedente;
  }
  public void setRichiedente(Soggetto richiedente) {
    this.richiedente = richiedente;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("delegato")
  @NotNull
  public Soggetto getDelegato() {
    return delegato;
  }
  public void setDelegato(Soggetto delegato) {
    this.delegato = delegato;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("ubicazioni")
  @NotNull
  public List<Ubicazione> getUbicazioni() {
    return ubicazioni;
  }
  public void setUbicazioni(List<Ubicazione> ubicazioni) {
    this.ubicazioni = ubicazioni;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("fileProcuraSpeciale")
  @NotNull
  public FileProcuraSpeciale getFileProcuraSpeciale() {
    return fileProcuraSpeciale;
  }
  public void setFileProcuraSpeciale(FileProcuraSpeciale fileProcuraSpeciale) {
    this.fileProcuraSpeciale = fileProcuraSpeciale;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreaIstanzaVincoloBody creaIstanzaVincoloBody = (CreaIstanzaVincoloBody) o;
    return Objects.equals(datiIstanza, creaIstanzaVincoloBody.datiIstanza) &&
        Objects.equals(richiedente, creaIstanzaVincoloBody.richiedente) &&
        Objects.equals(delegato, creaIstanzaVincoloBody.delegato) &&
        Objects.equals(ubicazioni, creaIstanzaVincoloBody.ubicazioni) &&
        Objects.equals(fileProcuraSpeciale, creaIstanzaVincoloBody.fileProcuraSpeciale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datiIstanza, richiedente, delegato, ubicazioni, fileProcuraSpeciale);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreaIstanzaVincoloBody {\n");
    
    sb.append("    istanzaMude: ").append(toIndentedString(datiIstanza)).append("\n");
    sb.append("    richiedente: ").append(toIndentedString(richiedente)).append("\n");
    sb.append("    delegato: ").append(toIndentedString(delegato)).append("\n");
    sb.append("    ubicazioni: ").append(toIndentedString(ubicazioni)).append("\n");
    sb.append("    fileProcuraSpeciale: ").append(toIndentedString(fileProcuraSpeciale)).append("\n");
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
