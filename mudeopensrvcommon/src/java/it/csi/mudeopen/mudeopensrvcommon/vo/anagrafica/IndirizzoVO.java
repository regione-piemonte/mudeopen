/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica;

import it.csi.mudeopen.mudeopensrvcommon.vo.exception.ValidationException;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.NazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;

public class IndirizzoVO extends ParentVO {

	private Long id;
	private String cap;
	private ComuneVO comune;
	private ProvinciaVO provincia;
	private String localita;
	private SelectVO dug;
	private String duf;
	private String numero;
	private NazioneVO stato;
	private String comuneIndirizzoEstero;
	private SelectVO tipologiaIndirizzo;
	private String telefono = null;
	private String cellulare = null;
	private String mail = null;
	private String pec = null;

	public IndirizzoVO(String cap, ComuneVO comune, ProvinciaVO provincia, String localita, String civico, NazioneVO stato, String comuneIndirizzoEstero, SelectVO tipo, SelectVO dug, String duf, String mail, String pec, String cellulare, String telefono) {
		super();
		this.cap = cap;
		this.comune = comune;
		this.provincia = provincia;
		this.localita = localita;
		this.numero = civico;
		this.stato = stato;
		this.comuneIndirizzoEstero = comuneIndirizzoEstero;
		this.tipologiaIndirizzo = tipo;
		this.dug = dug;
		this.duf = duf;
		this.mail = mail;
		this.pec = pec;
		this.cellulare = cellulare;
		this.telefono = telefono;
	}

	public IndirizzoVO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public SelectVO getDug() {
		return dug;
	}

	public void setDug(SelectVO dug) {
		this.dug = dug;
	}

	public String getDuf() {
		return duf;
	}

	public void setDuf(String duf) {
		this.duf = duf;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public SelectVO getTipologiaIndirizzo() {
		return tipologiaIndirizzo;
	}

	public void setTipologiaIndirizzo(SelectVO tipologiaIndirizzo) {
		this.tipologiaIndirizzo = tipologiaIndirizzo;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	public NazioneVO getStato() {
		return stato;
	}

	public void setStato(NazioneVO stato) {
		this.stato = stato;
	}

	public ComuneVO getComune() {
		return comune;
	}

	public void setComune(ComuneVO comune) {
		this.comune = comune;
	}

	public String getComuneIndirizzoEstero() {
		return comuneIndirizzoEstero;
	}

	public void setComuneIndirizzoEstero(String comuneIndirizzoEstero) {
		this.comuneIndirizzoEstero = comuneIndirizzoEstero;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

}