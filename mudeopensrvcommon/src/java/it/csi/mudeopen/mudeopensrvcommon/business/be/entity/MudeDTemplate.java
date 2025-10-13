/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "mudeopen_d_template")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MudeDTemplate implements Serializable {

	private static final long serialVersionUID = 8197060945226208442L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_template")
	private Long idTemplate;

	@ManyToOne
	@JoinColumn(name="id_tipo_istanza")
	private MudeDTipoIstanza mudeTipoIstanza;

	@Column(name = "cod_template")
	private String codTemplate;

	@Column(name = "des_template")
	private String desTemplate;

	@Temporal(TemporalType.DATE)
	@Column(name="data_inizio_validita")
	private Date dataInizioValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="data_cessazione")
	private Date dataCessazione;

	@Column(name = "flg_attivo")
	private Long flgAttivo;

	@Type(type = "jsonb")
	@Column(name="json_configura_template", columnDefinition = "jsonb")
	private String jsonConfiguraTemplate;

	@ManyToOne
	@JoinColumn(name="id_modello_documentale")
	private MudeTModello modello;
	
	@ManyToOne
	@JoinColumn(name="id_modello_intestazione")
	private MudeTModello modelloIntestazione;

	@Column(name = "num_versione")
	private Integer numeroVersione;
	
	@Column(name = "obbligatoria_nomina_pm")
	private Boolean obbligatoriaNominaPM;
	
	public Long getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}

	public MudeDTipoIstanza getMudeTipoIstanza() {
		return mudeTipoIstanza;
	}

	public void setMudeTipoIstanza(MudeDTipoIstanza mudeTipoIstanza) {
		this.mudeTipoIstanza = mudeTipoIstanza;
	}

	public String getCodTemplate() {
		return codTemplate;
	}

	public void setCodTemplate(String codTemplate) {
		this.codTemplate = codTemplate;
	}

	public String getDesTemplate() {
		return desTemplate;
	}

	public void setDesTemplate(String desTemplate) {
		this.desTemplate = desTemplate;
	}

	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Date getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(Date dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	public Long getFlgAttivo() {
		return flgAttivo;
	}

	public void setFlgAttivo(Long flgAttivo) {
		this.flgAttivo = flgAttivo;
	}

	public String getJsonConfiguraTemplate() {
		return jsonConfiguraTemplate;
	}

	public void setJsonConfiguraTemplate(String jsonConfiguraTemplate) {
		this.jsonConfiguraTemplate = jsonConfiguraTemplate;
	}

	public MudeTModello getModello() {
		return modello;
	}

	public void setModello(MudeTModello modello) {
		this.modello = modello;
	}

	public Integer getNumeroVersione() {
		return numeroVersione;
	}

	public void setNumeroVersione(Integer numeroVersione) {
		this.numeroVersione = numeroVersione;
	}

	public MudeTModello getModelloIntestazione() {
		return modelloIntestazione;
	}

	public void setModelloIntestazione(MudeTModello modelloIntestazione) {
		this.modelloIntestazione = modelloIntestazione;
	}

	public Boolean getObbligatoriaNominaPM() {
		return obbligatoriaNominaPM;
	}

	public void setObbligatoriaNominaPM(Boolean obbligatoriaNominaPM) {
		this.obbligatoriaNominaPM = obbligatoriaNominaPM;
	}
}