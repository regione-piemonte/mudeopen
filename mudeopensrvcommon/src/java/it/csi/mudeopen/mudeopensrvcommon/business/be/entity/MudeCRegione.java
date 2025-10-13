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
@Table(name = "mudeopen_c_regione")
public class MudeCRegione implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name="id_regione")
	private MudeDRegione mudeDRegione;

	@Column(name="progressivo_anno")
	private long progressivoAnno;

	@Temporal(TemporalType.DATE)
	@Column(name="data_modifica")
	private Date dataModifica;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MudeDRegione getMudeDRegione() {
		return mudeDRegione;
	}

	public void setMudeDRegione(MudeDRegione mudeDRegione) {
		this.mudeDRegione = mudeDRegione;
	}

	public long getProgressivoAnno() {
		return progressivoAnno;
	}

	public void setProgressivoAnno(long progressivoAnno) {
		this.progressivoAnno = progressivoAnno;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}
	
	
}