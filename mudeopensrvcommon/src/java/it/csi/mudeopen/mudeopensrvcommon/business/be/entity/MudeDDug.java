/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_dug")
public class MudeDDug extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_dug")
	private long idDug;

	@Column(name="denominazione")
	private String denominazione;

    public MudeDDug() {
	}

    public long getIdDug() {
		return idDug;
	}

    public void setIdDug(long idDug) {
		this.idDug = idDug;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
}