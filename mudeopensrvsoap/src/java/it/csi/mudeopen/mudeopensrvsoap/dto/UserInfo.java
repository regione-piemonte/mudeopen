/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopensrvsoap.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type User info.
 */
public class UserInfo   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 

  private String nome = null;
  private String cognome = null;
  private String codFisc = null;
  private String ente = null;
  private String ruolo = null;
  private String codRuolo = null;
  private Integer livAuth = null;
  private String community = null;

    /**
     * nome dell&#39;utente
     *
      the nome
     */
    @JsonProperty("nome")

  public String getNome() {
    return nome;
  }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
    this.nome = nome;
  }

    /**
     * cognome dell&#39;utente
     *
      the cognome
     */
    @JsonProperty("cognome")

  public String getCognome() {
    return cognome;
  }

    /**
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(String cognome) {
    this.cognome = cognome;
  }

    /**
     * codice fiscale dell&#39;utente
     *
      the cod fisc
     */
    @JsonProperty("codFisc")

  public String getCodFisc() {
    return codFisc;
  }

    /**
     * Sets cod fisc.
     *
     * @param codFisc the cod fisc
     */
    public void setCodFisc(String codFisc) {
    this.codFisc = codFisc;
  }

    /**
     * nome dell&#39;ente
     *
      the ente
     */
    @JsonProperty("ente")

  public String getEnte() {
    return ente;
  }

    /**
     * Sets ente.
     *
     * @param ente the ente
     */
    public void setEnte(String ente) {
    this.ente = ente;
  }

    /**
     * nome del ruolo dell&#39;utente
     *
      the ruolo
     */
    @JsonProperty("ruolo")

  public String getRuolo() {
    return ruolo;
  }

    /**
     * Sets ruolo.
     *
     * @param ruolo the ruolo
     */
    public void setRuolo(String ruolo) {
    this.ruolo = ruolo;
  }

    /**
     * codice del ruolo dell&#39;utente
     *
      the cod ruolo
     */
    @JsonProperty("cod_ruolo")

  public String getCodRuolo() {
    return codRuolo;
  }

    /**
     * Sets cod ruolo.
     *
     * @param codRuolo the cod ruolo
     */
    public void setCodRuolo(String codRuolo) {
    this.codRuolo = codRuolo;
  }

    /**
     * livello di autenticazione utilizzato per accedere
     *
      the liv auth
     */
    @JsonProperty("livAuth")

  public Integer getLivAuth() {
    return livAuth;
  }

    /**
     * Sets liv auth.
     *
     * @param livAuth the liv auth
     */
    public void setLivAuth(Integer livAuth) {
    this.livAuth = livAuth;
  }

    /**
     * community di utenti utilizzata per accedere
     *
      the community
     */
    @JsonProperty("community")

  public String getCommunity() {
    return community;
  }

    /**
     * Sets community.
     *
     * @param community the community
     */
    public void setCommunity(String community) {
    this.community = community;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfo userInfo = (UserInfo) o;
    return Objects.equals(nome, userInfo.nome) &&
        Objects.equals(cognome, userInfo.cognome) &&
        Objects.equals(codFisc, userInfo.codFisc) &&
        Objects.equals(ente, userInfo.ente) &&
        Objects.equals(ruolo, userInfo.ruolo) &&
        Objects.equals(codRuolo, userInfo.codRuolo) &&
        Objects.equals(livAuth, userInfo.livAuth) &&
        Objects.equals(community, userInfo.community);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, cognome, codFisc, ente, ruolo, codRuolo, livAuth, community);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfo {\n");

    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    codFisc: ").append(toIndentedString(codFisc)).append("\n");
    sb.append("    ente: ").append(toIndentedString(ente)).append("\n");
    sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
    sb.append("    codRuolo: ").append(toIndentedString(codRuolo)).append("\n");
    sb.append("    livAuth: ").append(toIndentedString(livAuth)).append("\n");
    sb.append("    community: ").append(toIndentedString(community)).append("\n");
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