/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.utente;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.IndirizzoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.QualificaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.NazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateDeserializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateSerializer;

public class UtenteVO implements Serializable{
	
	private static final long serialVersionUID = 1702327503486654745L;

	private Long id = null;
	
	
//	private String nome = null;
//	private String cognome = null;
//	private String codiceFiscale = null;
//
//	private String sesso = null;
//
//	@JsonSerialize(using = CustomDateSerializer.class)
//	@JsonDeserialize(using = CustomDateDeserializer.class)
//	private LocalDate dataNascita = null;
//	private NazioneVO statoNascita = null;
//	private ProvinciaVO provinciaNascita = null;
//	private ComuneVO comuneNascita = null;
//	private String telefono = null;
//	private String cellulare = null;
//	private String mail = null;
//	private String pec = null;
//	private String statoEstero = null;
	private Boolean backofficeUser = null;

	private ContattoVO contatto = new ContattoVO();
	
	private List<SelectVO> ruoliUtente = new ArrayList<>();
	
	private List<FunzioneUtenteVO> funzioniUtente = new ArrayList<>();

    public UtenteVO() {}

//    public UtenteVO(String nome, String cognome, String codiceFiscale, String sesso, LocalDate dataNascita,
//			NazioneVO statoNascita, ProvinciaVO provinciaNascita, ComuneVO comuneNascita, String telefono,
//			String cellulare, String mail, String pec, String statoEstero) {
//		super();
//		this.nome = nome;
//		this.cognome = cognome;
//		this.codiceFiscale = codiceFiscale;
//		this.sesso = sesso;
//		this.dataNascita = dataNascita;
//		this.statoNascita = statoNascita;
//		this.provinciaNascita = provinciaNascita;
//		this.comuneNascita = comuneNascita;
//		this.telefono = telefono;
//		this.cellulare = cellulare;
//		this.mail = mail;
//		this.pec = pec;
//		this.statoEstero = statoEstero;
//	}

    public Long getId() {
		return id;
	}

    public void setId(Long id) {
		this.id = id;
	}

//    public String getNome() {
//		return nome;
//	}
//
//    public void setNome(String nome) {
//		this.nome = nome;
//	}
//
//    public String getCognome() {
//		return cognome;
//	}
//
//    public void setCognome(String cognome) {
//		this.cognome = cognome;
//	}
//
//    public String getCodiceFiscale() {
//		return codiceFiscale;
//	}
//
//    public void setCodiceFiscale(String codiceFiscale) {
//		this.codiceFiscale = codiceFiscale;
//	}
//
//    public String getSesso() {
//		return sesso;
//	}
//
//    public void setSesso(String sesso) {
//		this.sesso = sesso;
//	}
//
//    public LocalDate getDataNascita() {
//		return dataNascita;
//	}
//
//    public void setDataNascita(LocalDate dataNascita) {
//		this.dataNascita = dataNascita;
//	}
//
//    public NazioneVO getStatoNascita() {
//		return statoNascita;
//	}
//
//    public void setStatoNascita(NazioneVO statoNascita) {
//		this.statoNascita = statoNascita;
//	}
//
//    public ProvinciaVO getProvinciaNascita() {
//		return provinciaNascita;
//	}
//
//    public void setProvinciaNascita(ProvinciaVO provinciaNascita) {
//		this.provinciaNascita = provinciaNascita;
//	}
//
//    public ComuneVO getComuneNascita() {
//		return comuneNascita;
//	}
//
//    public void setComuneNascita(ComuneVO comuneNascita) {
//		this.comuneNascita = comuneNascita;
//	}
//
//    public String getTelefono() {
//		return telefono;
//	}
//
//    public void setTelefono(String telefono) {
//		this.telefono = telefono;
//	}
//
//    public String getMail() {
//		return mail;
//	}
//
//    public void setMail(String mail) {
//		this.mail = mail;
//	}
//
//    public String getCellulare() {
//		return cellulare;
//	}
//
//    public void setCellulare(String cellulare) {
//		this.cellulare = cellulare;
//	}
//
//    public String getPec() {
//		return pec;
//	}
//
//    public void setPec(String pec) {
//		this.pec = pec;
//	}
//
//    public String getStatoEstero() {
//		return statoEstero;
//	}
//
//    public void setStatoEstero(String statoEstero) {
//		this.statoEstero = statoEstero;
//	}

	public Boolean isBackofficeUser() {
		return backofficeUser;
	}

	public void setBackofficeUser(Boolean isBackofficeUser) {
		this.backofficeUser = isBackofficeUser;
	}

	public ContattoVO getContatto() {
		return contatto;
	}

	public void setContatto(ContattoVO contatto) {
		this.contatto = contatto;
	}
	
	public List<SelectVO> getRuoliUtente() {
		return ruoliUtente;
	}

	public void setRuoliUtente(List<SelectVO> ruoliUtente) {
		this.ruoliUtente = ruoliUtente;
	}

	public List<FunzioneUtenteVO> getFunzioniUtente() {
		return funzioniUtente;
	}

	public void setFunzioniUtente(List<FunzioneUtenteVO> funzioniUtente) {
		this.funzioniUtente = funzioniUtente;
	}

	public Boolean getBackofficeUser() {
		return backofficeUser;
	}
	
	
}