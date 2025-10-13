/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "mudeopen_r_tipo_istanza")
public class MudeRTipoIstanza extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4861105860109750382L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_tipo_istanza")
	private long id;

	@ManyToOne
	@JoinColumn(name = "cod_tipo_istanza_padre")
	private MudeDTipoIstanza mudeDTipoIstanzaPadre;

	@ManyToOne
	@JoinColumn(name = "cod_tipo_istanza_figlia")
	private MudeDTipoIstanza mudeDTipoIstanzaFiglia;
	
	@Column(name="campo_json_padre")
	private String campoJsonPadre;

	@ManyToOne
	@JoinColumn(name = "cod_tipo_istanza_padre2")
	private MudeDTipoIstanza mudeDTipoIstanzaPadre2;

	@Column(name="ripetibile")
	private Integer ripetibile;
	
    public long getId() {
		return id;
	}

    public void setId(long id) {
		this.id = id;
	}

    public MudeDTipoIstanza getMudeDTipoIstanzaPadre() {
		return mudeDTipoIstanzaPadre;
	}

    public void setMudeDTipoIstanzaPadre(MudeDTipoIstanza mudeDTipoIstanzaPadre) {
		this.mudeDTipoIstanzaPadre = mudeDTipoIstanzaPadre;
	}

    public MudeDTipoIstanza getMudeDTipoIstanzaFiglia() {
		return mudeDTipoIstanzaFiglia;
	}

    public void setMudeDTipoIstanzaFiglia(MudeDTipoIstanza mudeDTipoIstanzaFiglia) {
		this.mudeDTipoIstanzaFiglia = mudeDTipoIstanzaFiglia;
	}

	public String getCampoJsonPadre() {
		return campoJsonPadre;
	}

	public void setCampoJsonPadre(String campoJsonPadre) {
		this.campoJsonPadre = campoJsonPadre;
	}

	public MudeDTipoIstanza getMudeDTipoIstanzaPadre2() {
		return mudeDTipoIstanzaPadre2;
	}

	public void setMudeDTipoIstanzaPadre2(MudeDTipoIstanza mudeDTipoIstanzaPadre2) {
		this.mudeDTipoIstanzaPadre2 = mudeDTipoIstanzaPadre2;
	}
	
	
}