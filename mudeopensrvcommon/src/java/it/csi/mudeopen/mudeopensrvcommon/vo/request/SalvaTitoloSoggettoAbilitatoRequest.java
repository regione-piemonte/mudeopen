/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;

public class SalvaTitoloSoggettoAbilitatoRequest  implements Serializable{

	private static final long serialVersionUID = 940131279672490001L;

	@JsonProperty("id_istanza")
	private Long idIstanza;

	@JsonProperty("titolo_soggetto_abilitato")
	private SelectVO titoloSoggettoAbilitato;

	@JsonProperty("titolo_soggetto_abilitato_rt")
	private SelectVO titoloSoggettoAbilitatoRT;

	@JsonProperty("soggetto_rappresentato")
	private ContattoVO soggettoRappresentato;

    public Long getIdIstanza() {
		return idIstanza;
	}

    public void setIdIstanza(Long idIstanza) {
		this.idIstanza = idIstanza;
	}

    public SelectVO getTitoloSoggettoAbilitato() {
		return titoloSoggettoAbilitato;
	}

    public void setTitoloSoggettoAbilitato(SelectVO titoloSoggettoAbilitato) {
		this.titoloSoggettoAbilitato = titoloSoggettoAbilitato;
	}

    public ContattoVO getSoggettoRappresentato() {
		return soggettoRappresentato;
	}

    public void setSoggettoRappresentato(ContattoVO soggettoRappresentato) {
		this.soggettoRappresentato = soggettoRappresentato;
	}

	public SelectVO getTitoloSoggettoAbilitatoRT() {
		return titoloSoggettoAbilitatoRT;
	}

	public void setTitoloSoggettoAbilitatoRT(SelectVO titoloSoggettoAbilitatoRT) {
		this.titoloSoggettoAbilitatoRT = titoloSoggettoAbilitatoRT;
	}
	
	
	
}