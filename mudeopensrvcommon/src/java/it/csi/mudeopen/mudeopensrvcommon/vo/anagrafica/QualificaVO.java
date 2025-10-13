/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;

public class QualificaVO extends ParentVO {

	private Long id;
	private SelectVO tipologiaQualificaProfessionale;

	@JsonProperty("numero_iscrizione_ordine")
	private String numeroIscrizioneOrdine;

	private ProvinciaVO provincia;
	private SelectVO ordineProfessionale;

	@JsonProperty("specifica_qualifica")
	private String specificaQualifica;

	@JsonProperty("no_obbligo_iscrizione_ordine")
	private Boolean senzaObbligoIscrizioneOrdine;

	@JsonProperty("motivazione")
	private String motivazione;

	public QualificaVO() {}

	public QualificaVO(SelectVO tipologiaQualificaProfessionale, String numeroIscrizioneOrdine, ProvinciaVO provincia,
			SelectVO ordineProfessionale) {
		super();
		this.tipologiaQualificaProfessionale = tipologiaQualificaProfessionale;
		this.numeroIscrizioneOrdine = numeroIscrizioneOrdine;
		this.provincia = provincia;
		this.ordineProfessionale = ordineProfessionale;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SelectVO getTipologiaQualificaProfessionale() {
		return tipologiaQualificaProfessionale;
	}

	public void setTipologiaQualificaProfessionale(SelectVO tipologiaQualificaProfessionale) {
		this.tipologiaQualificaProfessionale = tipologiaQualificaProfessionale;
	}

	public String getNumeroIscrizioneOrdine() {
		return numeroIscrizioneOrdine;
	}

	public void setNumeroIscrizioneOrdine(String numeroIscrizioneOrdine) {
		this.numeroIscrizioneOrdine = numeroIscrizioneOrdine;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	public SelectVO getOrdineProfessionale() {
		return ordineProfessionale;
	}

	public void setOrdineProfessionale(SelectVO ordineProfessionale) {
		this.ordineProfessionale = ordineProfessionale;
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