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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_ruolo_soggetto")
public class MudeDRuoloSoggetto extends BaseDictionaryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="visibile")
	private Boolean visibile;
	
	@Column(name="branch_obbligatorio")
	private String branchObbligatorio;

	@Column(name="categoria_ruolo")
	private String categoriaRuolo;

	@Column(name="operatori")
	private String operatori;

	@Column(name="includi")
	private String includi;
	
	@Column(name="escludi")
	private String escludi;

	@Column(name="obbligatori")
	private String obbligatori;

	public Boolean getVisibile() {
		return visibile;
	}

	public void setVisibile(Boolean visibile) {
		this.visibile = visibile;
	}

	public String getBranchObbligatorio() {
		return branchObbligatorio;
	}

	public void setBranchObbligatorio(String branchObbligatorio) {
		this.branchObbligatorio = branchObbligatorio;
	}

	public String getCategoriaRuolo() {
		return categoriaRuolo;
	}

	public void setCategoriaRuolo(String categoriaRuolo) {
		this.categoriaRuolo = categoriaRuolo;
	}

	public String getOperatori() {
		return operatori;
	}

	public void setOperatori(String operatori) {
		this.operatori = operatori;
	}

	public String getEscludi() {
		return escludi;
	}

	public void setEscludi(String escludi) {
		this.escludi = escludi;
	}

	public String getObbligatori() {
		return obbligatori;
	}

	public void setObbligatori(String obbligatori) {
		this.obbligatori = obbligatori;
	}

	public String getIncludi() {
		return includi;
	}

	public void setIncludi(String includi) {
		this.includi = includi;
	}
}