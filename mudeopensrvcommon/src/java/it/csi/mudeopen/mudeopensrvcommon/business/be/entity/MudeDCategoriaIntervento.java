/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegimeGiuridico;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_categoria")
public class MudeDCategoriaIntervento extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private long idCategoria;

	@Column(name = "denominazione")
	private String denominazione;

	@ManyToOne
	@JoinColumn(name = "id_regime")
	private MudeDRegimeGiuridico mudeDRegimeGiuridico;

    public long getIdCategoria() {
		return idCategoria;
	}

    public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public MudeDRegimeGiuridico getMudeDRegimeGiuridico() {
		return mudeDRegimeGiuridico;
	}

    public void setMudeDRegimeGiuridico(MudeDRegimeGiuridico mudeDRegimeGiuridico) {
		this.mudeDRegimeGiuridico = mudeDRegimeGiuridico;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MudeDCategoriaIntervento [idCategoria=").append(idCategoria).append(", denominazione=")
				.append(denominazione).append(", mudeDRegimeGiuridico=").append(mudeDRegimeGiuridico).append("]");
		return builder.toString();
	}

	
	
}