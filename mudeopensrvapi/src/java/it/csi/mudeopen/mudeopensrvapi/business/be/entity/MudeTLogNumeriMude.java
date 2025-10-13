/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;

@Entity
@Table(name = "mudeopen_t_log_numeri_mude")
public class MudeTLogNumeriMude {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	protected Long id;
	
	@Column(name = "numero_mude")
	protected String numeroMude;

	@Column(name = "tipo")
	protected String tipo;
	
	@ManyToOne
    @JoinColumn(name = "id_fruitore")
	protected MudeDFruitore mudeDFruitore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroMude() {
		return numeroMude;
	}

	public void setNumeroMude(String numeroMude) {
		this.numeroMude = numeroMude;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public MudeDFruitore getMudeDFruitore() {
		return mudeDFruitore;
	}

	public void setMudeDFruitore(MudeDFruitore mudeDFruitore) {
		this.mudeDFruitore = mudeDFruitore;
	}

	
}
