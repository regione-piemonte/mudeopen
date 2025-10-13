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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_r_tipo_istanza_regime_g_regime_a")
public class MudeRTipoIstanzaRegimeGiuRegimeAgg extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RegimeGiuRegimeAggSeqGenerator")
	@SequenceGenerator(name = "RegimeGiuRegimeAggSeqGenerator", sequenceName = "mudeopen_r_tipo_istanza_regime_g_regime_a_id_seq", allocationSize = 1)
	@Column(name="id_tipo_istanza_regime_g_regime_a")
	private long id;

	@ManyToOne
	@JoinColumn(name="id_tipo_istanza")
	private MudeDTipoIstanza mudeDTipoIstanza;

	@ManyToOne
	@JoinColumn(name="id_regime_giuridico")
	private MudeDRegimeGiuridico mudeDRegimeGiuridico;

	@ManyToOne
	@JoinColumn(name="id_regime_aggiuntivo")
	private MudeDRegimeAggiuntivo mudeDRegimeAggiuntivo;

    public MudeRTipoIstanzaRegimeGiuRegimeAgg() {
	}

    public long getId() {
		return id;
	}

    public void setId(long id) {
		this.id = id;
	}

    public MudeDTipoIstanza getMudeDTipoIstanza() {
		return mudeDTipoIstanza;
	}

    public void setMudeDTipoIstanza(MudeDTipoIstanza mudeDTipoIstanza) {
		this.mudeDTipoIstanza = mudeDTipoIstanza;
	}

    public MudeDRegimeGiuridico getMudeDRegimeGiuridico() {
		return mudeDRegimeGiuridico;
	}

    public void setMudeDRegimeGiuridico(MudeDRegimeGiuridico mudeDRegimeGiuridico) {
		this.mudeDRegimeGiuridico = mudeDRegimeGiuridico;
	}

    public MudeDRegimeAggiuntivo getMudeDRegimeAggiuntivo() {
		return mudeDRegimeAggiuntivo;
	}

    public void setMudeDRegimeAggiuntivo(MudeDRegimeAggiuntivo mudeDRegimeAggiuntivo) {
		this.mudeDRegimeAggiuntivo = mudeDRegimeAggiuntivo;
	}
}