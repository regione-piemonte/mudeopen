/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_d_tipo_istanza")
public class MudeDTipoIstanza extends BaseDictionaryEntity implements Serializable {

	private static final long serialVersionUID = 7603296243637032000L;

	@Column(name="almeno_un_ruolo")
	private Boolean almenoUnRuolo;

	// indica se e' creabile solo se legata a altra istanza
	@Column(name="legata")
	private Boolean legata;
	
	@ManyToOne
	@JoinColumn(name = "id_ambito")
	private MudeDAmbito mudeDAmbito;

	@OneToMany(mappedBy = "mudeTipoIstanza")
	private List<MudeDTemplate> templates;

//	@OneToMany(mappedBy="mudeDTipoIstanzaPadre", cascade = CascadeType.ALL)
//	private List<MudeRTipoIstanza> mudeRTipoIstanzas;

	@Column(name="cambio_intestatario")
	private Boolean cambioIntestatario = false;
	
	@Column(name="override_ente_padre")
	private Boolean overrideEntePadre = false;
	
	@ManyToOne
	@JoinColumn(name = "id_template_ricevuta_istanza")
	private MudeDTemplateRicevutaIstanza mudeDTemplateRicevutaIstanza;

	@Column(name="soggetti_bloccati")
	Boolean soggettiBloccati;
	
	@Column(name="escludi_branch")
	String escludiBranch;

    public Boolean getLegata() {
		return legata;
	}

    public void setLegata(Boolean legata) {
		this.legata = legata;
	}

    public Boolean getAlmenoUnRuolo() {
		return almenoUnRuolo;
	}

    public void setAlmenoUnRuolo(Boolean almenoUnRuolo) {
		this.almenoUnRuolo = almenoUnRuolo;
	}

    public MudeDAmbito getMudeDAmbito() {
		return mudeDAmbito;
	}

    public void setMudeDAmbito(MudeDAmbito mudeDAmbito) {
		this.mudeDAmbito = mudeDAmbito;
	}

    public List<MudeDTemplate> getTemplates() {
		return templates;
	}

    public void setTemplates(List<MudeDTemplate> templates) {
		this.templates = templates;
	}

	public Boolean getCambioIntestatario() {
		return cambioIntestatario == null ? false : cambioIntestatario;
	}

	public void setCambioIntestatario(Boolean cambioIntestatario) {
		this.cambioIntestatario = cambioIntestatario;
	}

	public MudeDTemplateRicevutaIstanza getMudeDTemplateRicevutaIstanza() {
		return mudeDTemplateRicevutaIstanza;
	}

	public void setMudeDTemplateRicevutaIstanza(MudeDTemplateRicevutaIstanza mudeDTemplateRicevutaIstanza) {
		this.mudeDTemplateRicevutaIstanza = mudeDTemplateRicevutaIstanza;
	}

	public Boolean getSoggettiBloccati() {
		return soggettiBloccati == null? false : soggettiBloccati;
	}

	public void setSoggettiBloccati(Boolean soggettiBloccati) {
		this.soggettiBloccati = soggettiBloccati;
	}

	public String getEscludiBranch() {
		return escludiBranch;
	}

	public void setEscludiBranch(String escludiBranch) {
		this.escludiBranch = escludiBranch;
	}

	public Boolean getOverrideEntePadre() {
		return overrideEntePadre;
	}

	public void setOverrideEntePadre(Boolean overrideEntePadre) {
		this.overrideEntePadre = overrideEntePadre;
	}
	
	

}