/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_r_tipo_istanza_tipo_intervento")
public class MudeRTipoIstanzaTipoIntervento extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5108856708750403945L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoIstanzaTipoInterventoSeqGenerator")
    @SequenceGenerator(name = "TipoIstanzaTipoInterventoSeqGenerator", sequenceName = "mudeopen_r_tipo_istanza_tipo_intervento_id_seq", allocationSize = 1)
    @Column(name="id_tipo_istanza_tipo_intervento")
    private long id;

    @ManyToOne
    @JoinColumn(name="codice_tipo_intervento")
    private MudeDTipoIntervento mudeDTipoIntervento;

    @ManyToOne
    @JoinColumn(name="codice_tipo_istanza")
    private MudeDTipoIstanza mudeDTipoIstanza;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MudeDTipoIntervento getMudeDTipoIntervento() {
		return mudeDTipoIntervento;
	}

	public void setMudeDTipoIntervento(MudeDTipoIntervento mudeDTipoIntervento) {
		this.mudeDTipoIntervento = mudeDTipoIntervento;
	}

	public MudeDTipoIstanza getMudeDTipoIstanza() {
		return mudeDTipoIstanza;
	}

	public void setMudeDTipoIstanza(MudeDTipoIstanza mudeDTipoIstanza) {
		this.mudeDTipoIstanza = mudeDTipoIstanza;
	}
}