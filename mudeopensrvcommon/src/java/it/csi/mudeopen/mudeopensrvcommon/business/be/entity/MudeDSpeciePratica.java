/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_d_specie_pratica")
public class MudeDSpeciePratica  extends BaseDictionaryEntity implements Serializable {
    private static final long serialVersionUID = -6688770605022717721L;

	@Column(name = "flag_sanatoria")
	private Boolean flagSanatoria;
	
	@Column(name = "flag_variante")
	private Boolean flagVariante;
	
	@Column(name = "visibilita")
	private String visibilita;

	public Boolean getFlagSanatoria() {
		return flagSanatoria == null? Boolean.FALSE : flagSanatoria;
	}

	public void setFlagSanatoria(Boolean flagSanatoria) {
		this.flagSanatoria = flagSanatoria;
	}

	public Boolean getFlagVariante() {
		return flagVariante == null? Boolean.FALSE : flagVariante;
	}

	public void setFlagVariante(Boolean flagVariante) {
		this.flagVariante = flagVariante;
	}

	public String getVisibilita() {
		return visibilita;
	}

	public void setVisibilita(String visibilita) {
		this.visibilita = visibilita;
	}

}