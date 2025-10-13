/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2022 | CSI Piemonte
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
@Table(name = "mudeopen_r_ente_fruitore")
public class MudeREnteFruitore extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 3335182118513426050L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_ente_fruitore")
	private Long id;

	@ManyToOne
    @JoinColumn(name = "id_ente")
    private MudeTEnte ente;

	@ManyToOne
    @JoinColumn(name = "id_fruitore")
    private MudeDFruitore fruitore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MudeTEnte getEnte() {
		return ente;
	}

	public void setEnte(MudeTEnte ente) {
		this.ente = ente;
	}

	public MudeDFruitore getFruitore() {
		return fruitore;
	}

	public void setFruitore(MudeDFruitore fruitore) {
		this.fruitore = fruitore;
	}
}