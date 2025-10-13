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
@Table(name = "mudeopen_d_tipo_ditta")
public class MudeDTipoDitta extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_ditta")
	private long idTipoDitta;

	@Column(name="denominazione")
	private String denominazione;

    public MudeDTipoDitta() {
	}

    public long getIdTipoDitta() {
		return idTipoDitta;
	}

    public void setIdTipoDitta(long idTipoDitta) {
		this.idTipoDitta = idTipoDitta;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
}