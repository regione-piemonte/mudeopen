/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_r_istanza_soggetto_ruoli")
public class MudeRIstanzaSoggettoRuoli extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_istanza_soggetto")
	private long idIstanzaSoggetto;

	@Column(name="ruoli")
	private String ruoli;

	public long getIdIstanzaSoggetto() {
		return idIstanzaSoggetto;
	}

	public void setIdIstanzaSoggetto(long idIstanzaSoggetto) {
		this.idIstanzaSoggetto = idIstanzaSoggetto;
	}

	public String getRuoli() {
		return ruoli;
	}

	public void setRuoli(String ruoli) {
		this.ruoli = ruoli;
	}

	

}