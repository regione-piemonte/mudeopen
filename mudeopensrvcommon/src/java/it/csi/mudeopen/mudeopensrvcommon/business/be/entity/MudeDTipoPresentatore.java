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
@Table(name = "mudeopen_d_tipo_presentatore")
public class MudeDTipoPresentatore extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo")
	private long idTipo;

	@Column(name="denominazione")
	private String denominazione;

    public MudeDTipoPresentatore() {
	}

    public long getIdTipo() {
		return idTipo;
	}

    public void setIdTipo(long idTipo) {
		this.idTipo = idTipo;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
}