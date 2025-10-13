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
@Table(name = "mudeopen_r_istanza_pratica")
public class MudeRIstanzaPratica extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6942252871333308128L;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_istanza_pratica")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_istanza")
    private MudeTIstanza istanza;

    @ManyToOne
    @JoinColumn(name="id_pratica")
    private MudeTPratica pratica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MudeTIstanza getIstanza() {
		return istanza;
	}

	public void setIstanza(MudeTIstanza istanza) {
		this.istanza = istanza;
	}

	public MudeTPratica getPratica() {
		return pratica;
	}

	public void setPratica(MudeTPratica pratica) {
		this.pratica = pratica;
	}

}