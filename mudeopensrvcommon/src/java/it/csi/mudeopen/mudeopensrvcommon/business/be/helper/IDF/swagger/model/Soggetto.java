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
public class Soggetto   {
  private String cognome = null;
  private String nome = null;
  private String codiceFiscale = null;
  private String tipoPersona = null;
  private String piva = null;
  private String ragioneSociale = null;
  private String indirizzo = null;
  private String civico = null;
  private String comune = null;
  private String istatIndirizzo = null;
  private String email = null;
  private String pec = null;
  private String telefono = null;
  private String qualifica = null;

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("cognome")
  @NotNull
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   **/
  
  //@Schema(description = "")
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
  
  //@Schema(description = "")
  @JsonProperty("codiceFiscale")
  @NotNull
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("tipoPersona")
  @NotNull
  public String getTipoPersona() {
    return tipoPersona;
  }
  public void setTipoPersona(String tipoPersona) {
    this.tipoPersona = tipoPersona;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("piva")
  @NotNull
  public String getPiva() {
    return piva;
  }
  public void setPiva(String piva) {
    this.piva = piva;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("ragioneSociale")
  @NotNull
  public String getRagioneSociale() {
    return ragioneSociale;
  }
  public void setRagioneSociale(String ragioneSociale) {
    this.ragioneSociale = ragioneSociale;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("indirizzo")
  @NotNull
  public String getIndirizzo() {
    return indirizzo;
  }
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("civico")
  @NotNull
  public String getCivico() {
    return civico;
  }
  public void setCivico(String civico) {
    this.civico = civico;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("comune")
  @NotNull
  public String getComune() {
    return comune;
  }
  public void setComune(String comune) {
    this.comune = comune;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("istatIndirizzo")
  @NotNull
  public String getIstatIndirizzo() {
    return istatIndirizzo;
  }
  public void setIstatIndirizzo(String istatIndirizzo) {
    this.istatIndirizzo = istatIndirizzo;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("email")
  @NotNull
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("pec")
  @NotNull
  public String getPec() {
    return pec;
  }
  public void setPec(String pec) {
    this.pec = pec;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("telefono")
  @NotNull
  public String getTelefono() {
    return telefono;
  }
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  /**
   **/
  
  //@Schema(description = "")
  @JsonProperty("qualifica")
  @NotNull
  public String getQualifica() {
    return qualifica;
  }
  public void setQualifica(String qualifica) {
    this.qualifica = qualifica;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Soggetto soggetto = (Soggetto) o;
    return Objects.equals(cognome, soggetto.cognome) &&
        Objects.equals(nome, soggetto.nome) &&
        Objects.equals(codiceFiscale, soggetto.codiceFiscale) &&
        Objects.equals(tipoPersona, soggetto.tipoPersona) &&
        Objects.equals(piva, soggetto.piva) &&
        Objects.equals(ragioneSociale, soggetto.ragioneSociale) &&
        Objects.equals(indirizzo, soggetto.indirizzo) &&
        Objects.equals(civico, soggetto.civico) &&
        Objects.equals(comune, soggetto.comune) &&
        Objects.equals(istatIndirizzo, soggetto.istatIndirizzo) &&
        Objects.equals(email, soggetto.email) &&
        Objects.equals(pec, soggetto.pec) &&
        Objects.equals(telefono, soggetto.telefono) &&
        Objects.equals(qualifica, soggetto.qualifica);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cognome, nome, codiceFiscale, tipoPersona, piva, ragioneSociale, indirizzo, civico, comune, istatIndirizzo, email, pec, telefono, qualifica);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Soggetto {\n");
    
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
    sb.append("    tipoPersona: ").append(toIndentedString(tipoPersona)).append("\n");
    sb.append("    piva: ").append(toIndentedString(piva)).append("\n");
    sb.append("    ragioneSociale: ").append(toIndentedString(ragioneSociale)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    civico: ").append(toIndentedString(civico)).append("\n");
    sb.append("    comune: ").append(toIndentedString(comune)).append("\n");
    sb.append("    istatIndirizzo: ").append(toIndentedString(istatIndirizzo)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    pec: ").append(toIndentedString(pec)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
    sb.append("    qualifica: ").append(toIndentedString(qualifica)).append("\n");
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
