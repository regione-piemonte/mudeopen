/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.ppay;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PPayImportoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5626332407976983528L;

    @JsonProperty("id_importo")
    private Long idImporto;

    @JsonProperty("id_ente")
    private Long idEnte;

    @JsonProperty("dati_spec_riscossione")
    private String dati_spec_riscossione;

    @JsonProperty("cod_tipo_istanza")
    private String idTipoIstanza;

    @JsonProperty("cod_specie_pratica")
    private String idSpeciePratica;

    @JsonProperty("tipo_importo")
    private String tipoImporto;

    @JsonProperty("descrizione")
    private String descrizione;

    @JsonProperty("importo")
    private Number importo;

	public Long getIdImporto() {
		return idImporto;
	}

	public void setIdImporto(Long idImporto) {
		this.idImporto = idImporto;
	}

	public Long getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Long idEnte) {
		this.idEnte = idEnte;
	}

	public String getDatiSpecRiscossione() {
		return dati_spec_riscossione;
	}

	public void setDatiSpecRiscossione(String codicePpay) {
		this.dati_spec_riscossione = codicePpay;
	}

	public String getIdTipoIstanza() {
		return idTipoIstanza;
	}

	public void setIdTipoIstanza(String idTipoIstanza) {
		this.idTipoIstanza = idTipoIstanza;
	}

	public String getIdSpeciePratica() {
		return idSpeciePratica;
	}

	public void setIdSpeciePratica(String idSpeciePratica) {
		this.idSpeciePratica = idSpeciePratica;
	}

	public String getTipoImporto() {
		return tipoImporto;
	}

	public void setTipoImporto(String tipoImporto) {
		this.tipoImporto = tipoImporto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Number getImporto() {
		return importo;
	}

	public void setImporto(Number importo) {
		this.importo = importo;
	}

}