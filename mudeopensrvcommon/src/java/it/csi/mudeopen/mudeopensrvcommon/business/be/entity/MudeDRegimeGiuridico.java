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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_regime_giuridico")
public class MudeDRegimeGiuridico extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_regime")
	private long idRegime;

	@Column(name = "denominazione")
	private String denominazione;

	@Column(name = "priorita")
	private Long priorita;

    public long getIdRegime() {
		return idRegime;
	}

    public void setIdRegime(long idRegime) {
		this.idRegime = idRegime;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public Long getPriorita() {
		return priorita;
	}

    public void setPriorita(Long priorita) {
		this.priorita = priorita;
	}

}