/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.localizzazione;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataGrid{
	@JsonProperty("n")
    protected String civico;

	@JsonProperty("bis")
    protected String bis;	

	@JsonProperty("cap")
    protected String cap;	
	
	@JsonProperty("int")
    protected String interno;	
	
	@JsonProperty("piano")
    protected String piano;	
	
	@JsonProperty("scala")
    protected String scala;	
	
	@JsonProperty("sedime")
    protected String sedime;	
	
	@JsonProperty("interno2")
    protected String interno2;
	
	@JsonProperty("bisInterno")
    protected String bisInterno;
	
	@JsonProperty("secondario")
    protected String secondario;
	
	@JsonProperty("denominazione")
    protected String denominazione;	

	@JsonProperty("apriMappaConGeeco")
    protected boolean apriMappaConGeeco=true;

	@JsonProperty("selezionare_se_si_tratta_di_indirizzo_principale")
    protected boolean principale=false;

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getBis() {
		return bis;
	}

	public void setBis(String bis) {
		this.bis = bis;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getScala() {
		return scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getSedime() {
		return sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}

	public String getInterno2() {
		return interno2;
	}

	public void setInterno2(String interno2) {
		this.interno2 = interno2;
	}

	public String getBisInterno() {
		return bisInterno;
	}

	public void setBisInterno(String bisInterno) {
		this.bisInterno = bisInterno;
	}

	public String getSecondario() {
		return secondario;
	}

	public void setSecondario(String secondario) {
		this.secondario = secondario;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public boolean isApriMappaConGeeco() {
		return apriMappaConGeeco;
	}

	public void setApriMappaConGeeco(boolean apriMappaConGeeco) {
		this.apriMappaConGeeco = apriMappaConGeeco;
	}

	public boolean isPrincipale() {
		return principale;
	}

	public void setPrincipale(boolean principale) {
		this.principale = principale;
	}

}
