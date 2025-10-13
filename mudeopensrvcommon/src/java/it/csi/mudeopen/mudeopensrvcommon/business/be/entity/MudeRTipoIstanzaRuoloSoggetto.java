/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_r_tipo_istanza_ruolo_sog")
public class MudeRTipoIstanzaRuoloSoggetto extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_istanza_ruolo_sog")
	private long id;

	@ManyToOne
	@JoinColumn(name="id_ruolo_soggetto")
	private MudeDRuoloSoggetto mudeDRuoloSoggetto;

	@ManyToOne
	@JoinColumn(name="id_tipo_istanza")
	private MudeDTipoIstanza mudeDTipoIstanza;

	@Column(name="obbligatorio")
	private Boolean obbligatorio;

    public MudeRTipoIstanzaRuoloSoggetto() {
	}

    public long getId() {
		return id;
	}

    public void setId(long id) {
		this.id = id;
	}

    public MudeDRuoloSoggetto getMudeDRuoloSoggetto() {
		return mudeDRuoloSoggetto;
	}

    public void setMudeDRuoloSoggetto(MudeDRuoloSoggetto mudeDRuoloSoggetto) {
		this.mudeDRuoloSoggetto = mudeDRuoloSoggetto;
	}

    public MudeDTipoIstanza getMudeDTipoIstanza() {
		return mudeDTipoIstanza;
	}

    public void setMudeDTipoIstanza(MudeDTipoIstanza mudeDTipoIstanza) {
		this.mudeDTipoIstanza = mudeDTipoIstanza;
	}

    public Boolean getObbligatorio() {
		return obbligatorio;
	}

    public void setObbligatorio(Boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	}

}