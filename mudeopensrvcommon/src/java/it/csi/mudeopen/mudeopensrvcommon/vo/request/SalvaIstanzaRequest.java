/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;

public class SalvaIstanzaRequest  implements Serializable{

	private static final long serialVersionUID = 940131279672490001L;

	@JsonProperty("id_istanza")
	private Long idIstanza;

	@JsonProperty("id_istanza_riferimento")
	private Long idIstanzaRiferimento;
	
	@JsonProperty("id_fascicolo")
	private Long idFascicolo;
	private ProvinciaVO provincia;
	private ComuneVO comune;

	@JsonProperty("tipologia_istanza")
	private SelectVO tipologiaIstanza;
	
	@JsonProperty("tipologia_presentatore")
	private SelectVO tipologiaPresentatore;

	@JsonProperty("bo_template_override")
	private String boTemplateOverride;

	@JsonProperty("ruoli")
	private List<RuoliFascicoloRequest> ruoli;
	
	@JsonProperty("keywords")
	private String keywords;
	
    public Long getIdIstanza() {
		return idIstanza;
	}

    public void setIdIstanza(Long idIstanza) {
		this.idIstanza = idIstanza;
	}

    public ProvinciaVO getProvincia() {
		return provincia;
	}

    public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

    public ComuneVO getComune() {
		return comune;
	}

    public void setComune(ComuneVO comune) {
		this.comune = comune;
	}

    public SelectVO getTipologiaIstanza() {
		return tipologiaIstanza;
	}

    public void setTipologiaIstanza(SelectVO tipologiaIstanza) {
		this.tipologiaIstanza = tipologiaIstanza;
	}

    public SelectVO getTipologiaPresentatore() {
		return tipologiaPresentatore;
	}

    public void setTipologiaPresentatore(SelectVO tipologiaPresentatore) {
		this.tipologiaPresentatore = tipologiaPresentatore;
	}

    public Long getIdFascicolo() {
		return idFascicolo;
	}

    public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

    public Long getIdIstanzaRiferimento() {
		return idIstanzaRiferimento;
	}

    public void setIdIstanzaRiferimento(Long idIstanzaRiferimento) {
		this.idIstanzaRiferimento = idIstanzaRiferimento;
	}

	public String getBoTemplateOverride() {
		return boTemplateOverride;
	}

	public void setBoTemplateOverride(String boTemplateOverride) {
		this.boTemplateOverride = boTemplateOverride;
	}

	public List<RuoliFascicoloRequest> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<RuoliFascicoloRequest> ruoli) {
		this.ruoli = ruoli;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
}