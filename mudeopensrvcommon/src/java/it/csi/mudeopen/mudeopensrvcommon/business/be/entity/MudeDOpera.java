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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_opera")
public class MudeDOpera extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_opera")
	private long idOpera;

	@Column(name = "denominazione")
	private String denominazione;

	@ManyToOne
	@JoinColumn(name = "id_elemento")
	private MudeDElemento elemento;

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private MudeDCategoriaIntervento categoria;

    public MudeDOpera() {
	}

    public long getIdOpera() {
		return idOpera;
	}

    public void setIdOpera(long idOpera) {
		this.idOpera = idOpera;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public MudeDElemento getElemento() {
		return elemento;
	}

    public void setElemento(MudeDElemento elemento) {
		this.elemento = elemento;
	}

    public MudeDCategoriaIntervento getCategoria() {
		return categoria;
	}

    public void setCategoria(MudeDCategoriaIntervento categoria) {
		this.categoria = categoria;
	}

}