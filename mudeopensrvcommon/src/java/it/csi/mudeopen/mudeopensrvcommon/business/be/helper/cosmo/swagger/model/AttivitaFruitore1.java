/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//import io.swagger.annotations.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

//@ApiModel(description="attività assegnate durante la pratica in ordine cronologico")
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class AttivitaFruitore1   {

  private String nome = null;
  private String descrizione = null;
  private Date dataInizio = null;
  private Date dataFine = null;
  private List<AssegnazioneFruitore> assegnazione = new ArrayList<AssegnazioneFruitore>();
  private List<SottoAttivitaFruitore> sottoAttivita = new ArrayList<SottoAttivitaFruitore>();
  private List<MessaggioFruitore> messaggiCollaboratori = new ArrayList<MessaggioFruitore>();

  /**
   **/

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("nome")
  @NotNull
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dataInizio")
  public Date getDataInizio() {
    return dataInizio;
  }
  public void setDataInizio(Date dataInizio) {
    this.dataInizio = dataInizio;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("dataFine")
  public Date getDataFine() {
    return dataFine;
  }
  public void setDataFine(Date dataFine) {
    this.dataFine = dataFine;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("assegnazione")
  public List<AssegnazioneFruitore> getAssegnazione() {
    return assegnazione;
  }
  public void setAssegnazione(List<AssegnazioneFruitore> assegnazione) {
    this.assegnazione = assegnazione;
  }

  /**
   * l&#39;oggetto sottotask ha un assegnatario
   **/

  @ApiModelProperty(value = "l'oggetto sottotask ha un assegnatario")
  @JsonProperty("sottoAttivita")
  public List<SottoAttivitaFruitore> getSottoAttivita() {
    return sottoAttivita;
  }
  public void setSottoAttivita(List<SottoAttivitaFruitore> sottoAttivita) {
    this.sottoAttivita = sottoAttivita;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("messaggiCollaboratori")
  public List<MessaggioFruitore> getMessaggiCollaboratori() {
    return messaggiCollaboratori;
  }
  public void setMessaggiCollaboratori(List<MessaggioFruitore> messaggiCollaboratori) {
    this.messaggiCollaboratori = messaggiCollaboratori;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttivitaFruitore1 attivitaFruitore1 = (AttivitaFruitore1) o;
    return Objects.equals(nome, attivitaFruitore1.nome) &&
        Objects.equals(descrizione, attivitaFruitore1.descrizione) &&
        Objects.equals(dataInizio, attivitaFruitore1.dataInizio) &&
        Objects.equals(dataFine, attivitaFruitore1.dataFine) &&
        Objects.equals(assegnazione, attivitaFruitore1.assegnazione) &&
        Objects.equals(sottoAttivita, attivitaFruitore1.sottoAttivita) &&
        Objects.equals(messaggiCollaboratori, attivitaFruitore1.messaggiCollaboratori);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, descrizione, dataInizio, dataFine, assegnazione, sottoAttivita, messaggiCollaboratori);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AttivitaFruitore1 {\n");

    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    dataInizio: ").append(toIndentedString(dataInizio)).append("\n");
    sb.append("    dataFine: ").append(toIndentedString(dataFine)).append("\n");
    sb.append("    assegnazione: ").append(toIndentedString(assegnazione)).append("\n");
    sb.append("    sottoAttivita: ").append(toIndentedString(sottoAttivita)).append("\n");
    sb.append("    messaggiCollaboratori: ").append(toIndentedString(messaggiCollaboratori)).append("\n");
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

