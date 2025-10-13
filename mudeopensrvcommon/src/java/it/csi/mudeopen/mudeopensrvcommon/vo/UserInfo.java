/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
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
	private String mudeopenApiScope = null;
	
	@JsonProperty("nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@JsonProperty("cognome")
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	@JsonProperty("codFisc")
	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
	    this.codFisc = codFisc;
	}

    @JsonProperty("ente")
	public String getEnte() {
	    return ente;
	}

	public void setEnte(String ente) {
	    this.ente = ente;
	}

    @JsonProperty("ruolo")
	public String getRuolo() {
	    return ruolo;
	}

	public void setRuolo(String ruolo) {
	    this.ruolo = ruolo;
	}

    @JsonProperty("cod_ruolo")
	public String getCodRuolo() {
	    return codRuolo;
	}

	public void setCodRuolo(String codRuolo) {
	    this.codRuolo = codRuolo;
	}

    @JsonProperty("livAuth")
	public Integer getLivAuth() {
	    return livAuth;
	}

    public void setLivAuth(Integer livAuth) {
	    this.livAuth = livAuth;
    }

    @JsonProperty("community")
	public String getCommunity() {
	    return community;
	}

    public void setCommunity(String community) {
    	this.community = community;
    }

	public String getMudeopenApiScope() {
		return mudeopenApiScope;
	}
	
	public void setMudeopenApiScope(String mudeopenApiScope) {
		this.mudeopenApiScope = mudeopenApiScope;
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
		sb.append("    mdeopenApiScope: ").append(toIndentedString(mudeopenApiScope)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(Object o) {

		if (o == null)
		  return "null";
		return o.toString().replace("\n", "\n    ");
	}
}