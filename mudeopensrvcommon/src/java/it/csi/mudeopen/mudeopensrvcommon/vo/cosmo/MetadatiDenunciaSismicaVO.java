/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.cosmo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadatiDenunciaSismicaVO extends BaseMetadatiVO {

	//@JsonProperty("cointestatari")
	//private List<CointestarioVO> cointestatari;
	
	@JsonProperty("desc_titolo_abilitativo")
	private String descTitoloAbilitativo;

	@JsonProperty("data_reg_titolo_abilitativo")
	private String dataRegTitoloAbilitativo;

	//@JsonProperty("delegati")
	//private List<DelegatoVO> delegati;
	
	@JsonProperty("tipo_istruttoria")
	private Integer tipoIstruttoria;

	@JsonProperty("opere")
	private String opere;

	@JsonProperty("nome_modello")
	private Long nomeModello;

	@JsonProperty("elenco_allegati")
	private List<String> elencoAllegati;

	public String getDescTitoloAbilitativo() {
		return descTitoloAbilitativo;
	}

	public void setDescTitoloAbilitativo(String descTitoloAbilitativo) {
		this.descTitoloAbilitativo = descTitoloAbilitativo;
	}

	public String getDataRegTitoloAbilitativo() {
		return dataRegTitoloAbilitativo;
	}

	public void setDataRegTitoloAbilitativo(String dataRegTitoloAbilitativo) {
		this.dataRegTitoloAbilitativo = dataRegTitoloAbilitativo;
	}

	public Integer getTipoIstruttoria() {
		return tipoIstruttoria;
	}

	public void setTipoIstruttoria(Integer tipoIstruttoria) {
		this.tipoIstruttoria = tipoIstruttoria;
	}

	public String getOpere() {
		return opere;
	}

	public void setOpere(String opere) {
		this.opere = opere;
	}

	public Long getNomeModello() {
		return nomeModello;
	}

	public void setNomeModello(Long nomeModello) {
		this.nomeModello = nomeModello;
	}

	public List<String> getElencoAllegati() {
		return elencoAllegati;
	}

	public void setElencoAllegati(List<String> elencoAllegati) {
		this.elencoAllegati = elencoAllegati;
	}

}