/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_t_ppay_pagamento")
public class MudeTPpayPagamento extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pagamento")
	private long id;

    @ManyToOne
    @JoinColumn(name="id_istanza")
	private MudeTIstanza istanza;

	@Column(name = "identificativo_pagamento")
	private String identificativoPagamento;

	@Column(name = "url_wisp")
	private String urlWisp;
	
	@Column(name = "iuv")
	private String IUV;

	@Column(name = "importo")
	private String importo;

	@Column(name = "codice_avviso")
	private String codiceAvviso;
	
	@Column(name = "codice_esito")
	private String codiceEsito;

	@Column(name = "descrizione_esito")
	private String descrizioneEsito;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MudeTIstanza getIstanza() {
		return istanza;
	}

	public void setIstanza(MudeTIstanza istanza) {
		this.istanza = istanza;
	}

	public String getIdentificativoPagamento() {
		return identificativoPagamento;
	}

	public void setIdentificativoPagamento(String identificativoPagamento) {
		this.identificativoPagamento = identificativoPagamento;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getCodiceEsito() {
		return codiceEsito;
	}

	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}

	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}

	public void setDescrizioneEsito(String descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}

	public String getIUV() {
		return IUV;
	}

	public void setIUV(String iUV) {
		IUV = iUV;
	}

	public String getCodiceAvviso() {
		return codiceAvviso;
	}

	public void setCodiceAvviso(String codiceAvviso) {
		this.codiceAvviso = codiceAvviso;
	}

	public String getUrlWisp() {
		return urlWisp;
	}

	public void setUrlWisp(String urlWisp) {
		this.urlWisp = urlWisp;
	}

	
}