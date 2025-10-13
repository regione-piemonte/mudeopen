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
@Table(name = "mudeopen_r_contatto_qualifica")
public class MudeRContattoQualifica extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_contatto_qualifica")
	private long idContattoQualifica;

	@ManyToOne
	@JoinColumn(name="id_qualifica")
	private MudeDQualifica mudeDQualifica;

	@ManyToOne
	@JoinColumn(name="id_contatto")
	private MudeTContatto mudeTContatto;

	@Column(name="numero_iscrizione_ordine")
	private String numeroIscrizioneOrdine;

	@ManyToOne
	@JoinColumn(name="id_provincia")
	private MudeDQualificaCollegio provinciaOrdine;
	
	@ManyToOne
	@JoinColumn(name="id_ordine")
	private MudeDOrdine mudeDOrdine;

	@Column(name="specifica_qualifica")
	private String specificaQualifica;

	@Column(name="no_obbligo_iscrizione_ordine")
	private Boolean senzaObbligoIscrizioneOrdine;

	@Column(name="motivazione")
	private String motivazione;

	public MudeRContattoQualifica() {
	}

	public long getIdContattoQualifica() {
		return idContattoQualifica;
	}

	public void setIdContattoQualifica(long idContattoQualifica) {
		this.idContattoQualifica = idContattoQualifica;
	}

	public MudeDQualifica getMudeDQualifica() {
		return mudeDQualifica;
	}

	public void setMudeDQualifica(MudeDQualifica mudeDQualifica) {
		this.mudeDQualifica = mudeDQualifica;
	}

	public MudeTContatto getMudeTContatto() {
		return mudeTContatto;
	}

	public void setMudeTContatto(MudeTContatto mudeTContatto) {
		this.mudeTContatto = mudeTContatto;
	}

	public String getNumeroIscrizioneOrdine() {
		return numeroIscrizioneOrdine;
	}

	public void setNumeroIscrizioneOrdine(String numeroIscrizioneOrdine) {
		this.numeroIscrizioneOrdine = numeroIscrizioneOrdine;
	}

	public MudeDQualificaCollegio getProvinciaOrdine() {
		return provinciaOrdine;
	}

	public void setProvinciaOrdine(MudeDQualificaCollegio provinciaOrdine) {
		this.provinciaOrdine = provinciaOrdine;
	}

	public MudeDOrdine getMudeDOrdine() {
		return mudeDOrdine;
	}

	public void setMudeDOrdine(MudeDOrdine mudeDOrdine) {
		this.mudeDOrdine = mudeDOrdine;
	}

	public String getSpecificaQualifica() {
		return specificaQualifica;
	}

	public void setSpecificaQualifica(String specificaQualifica) {
		this.specificaQualifica = specificaQualifica;
	}

	public Boolean getSenzaObbligoIscrizioneOrdine() {
		return senzaObbligoIscrizioneOrdine;
	}

	public void setSenzaObbligoIscrizioneOrdine(Boolean senzaObbligoIscrizioneOrdine) {
		this.senzaObbligoIscrizioneOrdine = senzaObbligoIscrizioneOrdine;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

}