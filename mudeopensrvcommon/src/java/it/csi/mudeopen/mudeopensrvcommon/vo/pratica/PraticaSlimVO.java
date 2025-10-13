/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.pratica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.BaseEntity;
public class PraticaSlimVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6942252871333308128L;

    private Long id;
	private String numeroPratica;
	private Long annoPratica;
	
	public PraticaSlimVO(Long id, String numeroPratica, Long annoPratica) {
		this.id = id;
		this.numeroPratica = numeroPratica;
		this.annoPratica = annoPratica;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPratica() {
		return numeroPratica;
	}

	public void setNumeroPratica(String numeroPratica) {
		this.numeroPratica = numeroPratica;
	}

	public Long getAnnoPratica() {
		return annoPratica;
	}

	public void setAnnoPratica(Long annoPratica) {
		this.annoPratica = annoPratica;
	}

}