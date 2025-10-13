/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_t_dati_istanza")
public class MudeTDatiIstanza extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    public enum DatiIstanza{
        TITOLO_SOGGETTO_ABILITATO,
        TITOLO_SOGGETTO_ABILITATO_RT
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_dati_istanza")
	private long idDato;

	@Enumerated(EnumType.STRING)
	@Column(name="dato")
	private DatiIstanza dato;

	@Column(name="valore")
	private String valore;
	
	@ManyToOne
	@JoinColumn(name="id_istanza")
	private MudeTIstanza mudeTIstanza;

	@ManyToOne
	@JoinColumn(name="id_soggetto")
	private MudeTContatto mudeTContatto;

    public MudeTDatiIstanza() {
	}

    public long getIdDato() {
		return idDato;
	}

    public void setIdDato(long idDato) {
		this.idDato = idDato;
	}

    public DatiIstanza getDato() {
		return dato;
	}

    public void setDato(DatiIstanza dato) {
		this.dato = dato;
	}

    public String getValore() {
		return valore;
	}

    public void setValore(String valore) {
		this.valore = valore;
	}

    public MudeTIstanza getMudeTIstanza() {
		return mudeTIstanza;
	}

    public void setMudeTIstanza(MudeTIstanza mudeTIstanza) {
		this.mudeTIstanza = mudeTIstanza;
	}

    public MudeTContatto getMudeTContatto() {
		return mudeTContatto;
	}

    public void setMudeTContatto(MudeTContatto mudeTContatto) {
		this.mudeTContatto = mudeTContatto;
	}
}