/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
@Entity
@Table(name = "mudeopen_t_notifica")
public class MudeTNotifica extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8582516358289116592L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notifica")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_tipo_notifica")
    private MudeDTipoNotifica tipoNotifica;

    @ManyToOne
    @JoinColumn(name="id_istanza")
    private MudeTIstanza istanza;

    @Column(name = "oggetto_notifica")
    private String oggettoNotifica;

    @Column(name = "testo_notifica")
    private String testoNotifica;

    @Column(name = "dettaglio")
    private String dettaglio;

    @Type(type = "jsonb")
    @Column(name = "json_data", columnDefinition = "jsonb")
    private String jsonData;

    @ManyToOne
    @JoinColumn(name = "id_user_mittente")
    private MudeTUser mudeTUser;

    @Column(name = "ruolo_mittente")
    private String ruoloMittente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MudeDTipoNotifica getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(MudeDTipoNotifica tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

	public MudeTIstanza getIstanza() {
		return istanza;
	}

	public void setIstanza(MudeTIstanza istanza) {
		this.istanza = istanza;
	}

	public String getOggettoNotifica() {
		return oggettoNotifica;
	}

	public void setOggettoNotifica(String oggettoNotifica) {
		this.oggettoNotifica = oggettoNotifica;
	}

	public String getTestoNotifica() {
		return testoNotifica;
	}

	public void setTestoNotifica(String testoNotifica) {
		this.testoNotifica = testoNotifica;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public MudeTUser getMudeTUser() {
		return mudeTUser;
	}

	public void setMudeTUser(MudeTUser mudeTUser) {
		this.mudeTUser = mudeTUser;
	}

	public String getRuoloMittente() {
		return ruoloMittente;
	}

	public void setRuoloMittente(String ruoloMittente) {
		this.ruoloMittente = ruoloMittente;
	}

	/**
	 * @return the dettaglio
	 */
	public String getDettaglio() {
		return dettaglio;
	}

	/**
	 * @param dettaglio the dettaglio to set
	 */
	public void setDettaglio(String dettaglio) {
		this.dettaglio = dettaglio;
	}

	

}