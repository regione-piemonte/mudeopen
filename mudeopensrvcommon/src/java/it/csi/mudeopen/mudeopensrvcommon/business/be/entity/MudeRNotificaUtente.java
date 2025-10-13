/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "mudeopen_r_notifica_utente")
public class MudeRNotificaUtente extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_notifica_utente")
	private long idNotificaUtente;

	@Column(name="letto")
	private Boolean letto;
	
	@ManyToOne
	@JoinColumn(name="id_notifica")
	private MudeTNotifica mudeTNotifica;

	@ManyToOne
	@JoinColumn(name="id_utente")
	private MudeTUser mudeTUser;
	
    @Column(name = "data_lettura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLettura;

	public long getIdNotificaUtente() {
		return idNotificaUtente;
	}

	public void setIdNotificaUtente(long idNotificaUtente) {
		this.idNotificaUtente = idNotificaUtente;
	}

	public MudeTNotifica getMudeTNotifica() {
		return mudeTNotifica;
	}

	public void setMudeTNotifica(MudeTNotifica mudeTNotifica) {
		this.mudeTNotifica = mudeTNotifica;
	}

	public MudeTUser getMudeTUser() {
		return mudeTUser;
	}

	public void setMudeTUser(MudeTUser mudeTUser) {
		this.mudeTUser = mudeTUser;
	}

	public Boolean getLetto() {
		return letto;
	}

	public void setLetto(Boolean letto) {
		this.letto = letto;
	}

	/**
	 * @return the dataLettura
	 */
	public Date getDataLettura() {
		return dataLettura;
	}

	/**
	 * @param dataLettura the dataLettura to set
	 */
	public void setDataLettura(Date dataLettura) {
		this.dataLettura = dataLettura;
	}

	

}