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
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_r_comune_fruitore")
public class MudeRComuneFruitore extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_comune_fruitore")
	private long idComuneFruitore;

	@ManyToOne
	@JoinColumn(name="id_comune")
	private MudeDComune comune;

	@ManyToOne
	@JoinColumn(name="id_fruitore")
	private MudeDFruitore fruitore;

	public long getIdComuneFruitore() {
		return idComuneFruitore;
	}

	public void setIdComuneFruitore(long idComuneFruitore) {
		this.idComuneFruitore = idComuneFruitore;
	}

	public MudeDComune getComune() {
		return comune;
	}

	public void setComune(MudeDComune comune) {
		this.comune = comune;
	}

	public MudeDFruitore getFruitore() {
		return fruitore;
	}

	public void setFruitore(MudeDFruitore fruitore) {
		this.fruitore = fruitore;
	}

}