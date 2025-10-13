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
@Table(name = "mudeopen_r_ruolo_fruitore")
public class MudeRRuoloFruitore extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -9070331799084777433L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_ruolo_fruitore")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "codice_ruolo")
	private MudeDRuoloUtente mudeDRuoloUtente;
	
	@ManyToOne
    @JoinColumn(name = "id_fruitore")
	private MudeDFruitore mudeDFruitore;

    @Column(name = "id_tipo_istanza")
	private String idTipoIstanza;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MudeDRuoloUtente getMudeDRuoloUtente() {
		return mudeDRuoloUtente;
	}

	public void setMudeDRuoloUtente(MudeDRuoloUtente mudeDRuoloUtente) {
		this.mudeDRuoloUtente = mudeDRuoloUtente;
	}

	public MudeDFruitore getMudeDFruitore() {
		return mudeDFruitore;
	}

	public void setMudeDFruitore(MudeDFruitore mudeDFruitore) {
		this.mudeDFruitore = mudeDFruitore;
	}

	public String getIdTipoIstanza() {
		return idTipoIstanza;
	}

	public void setIdTipoIstanza(String idTipoIstanza) {
		this.idTipoIstanza = idTipoIstanza;
	}

	
	
	
}