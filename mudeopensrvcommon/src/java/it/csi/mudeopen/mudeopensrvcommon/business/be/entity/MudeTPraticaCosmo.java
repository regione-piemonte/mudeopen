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
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "mudeopen_t_pratica_cosmo")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MudeTPraticaCosmo extends BaseLoggerEntity implements Serializable {

    private static final long serialVersionUID = -9110433609297411435L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pratica_cosmo")
    private Long id;

    @Column(name = "id_istanza")
    private long idIstanza;

    @Column(name = "codice_stato_cosmo")
    private String codiceStatoCosmo;

    @Column(name = "numero_pratica")
    private String numeroPratica;

    @Column(name = "id_doc_cosmo")
    private String idDocCosmo;

    @Column(name = "retries")
    private Integer retries;
    
    @Column(name = "tipo_controllo")
    private String tipoControllo;
    
    @Column(name = "controllo_campione")
    private Boolean controlloCampione;
    
    @Column(name = "cc_selezionato")
    private Boolean ccSelezionato;
    
    @Column(name = "trasmiss_doc")
    private Boolean trasmissDoc = false;

    @Type(type = "jsonb")
    @Column(name = "json_assegnatari", columnDefinition = "jsonb")
    private String jsonAssegnatari;
    
	public String getJsonAssegnatari() {
		return jsonAssegnatari;
	}

	public void setJsonAssegnatari(String jsonAssegnatari) {
		this.jsonAssegnatari = jsonAssegnatari;
	}

	public Boolean getTrasmissDoc() {
		return trasmissDoc;
	}

	public void setTrasmissDoc(Boolean trasmissDoc) {
		this.trasmissDoc = trasmissDoc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(Long idIstanza) {
		this.idIstanza = idIstanza;
	}

	public String getCodiceStatoCosmo() {
		return codiceStatoCosmo;
	}

	public void setCodiceStatoCosmo(String codiceStatoCosmo) {
		this.codiceStatoCosmo = codiceStatoCosmo;
	}

	public String getNumeroPratica() {
		return numeroPratica;
	}

	public void setNumeroPratica(String numeroPratica) {
		this.numeroPratica = numeroPratica;
	}

	public Integer getRetries() {
		return retries;
	}

	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	public void setIdIstanza(long idIstanza) {
		this.idIstanza = idIstanza;
	}

	public String getTipoControllo() {
		return tipoControllo;
	}

	public void setTipoControllo(String tipoControllo) {
		this.tipoControllo = tipoControllo;
	}

	public String getIdDocCosmo() {
		return idDocCosmo;
	}

	public void setIdDocCosmo(String idDocCosmo) {
		this.idDocCosmo = idDocCosmo;
	}

	public Boolean getControlloCampione() {
		return controlloCampione == null? false : controlloCampione;
	}

	public void setControlloCampione(Boolean controlloCampione) {
		this.controlloCampione = controlloCampione;
	}

	public Boolean getCcSelezionato() {
		return ccSelezionato == null? false : ccSelezionato;
	}

	public void setCcSelezionato(Boolean ccSelezionato) {
		this.ccSelezionato = ccSelezionato;
	}

}