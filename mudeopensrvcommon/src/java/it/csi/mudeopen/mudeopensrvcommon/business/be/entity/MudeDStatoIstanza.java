/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_stato_istanza")
public class MudeDStatoIstanza extends BaseDictionaryEntity implements Serializable {
	
    private static final long serialVersionUID = 1091157378212614158L;

    @Column(name="cardinal_pos")
    private Integer cardinalPos;
    
    @Column(name="livello")
    private Integer livello;

	public Integer getLivello() {
		return livello;
	}

	public void setLivello(Integer livello) {
		this.livello = livello;
	}

	public Integer getCardinalPos() {
		return cardinalPos;
	}

	public void setCardinalPos(Integer cardinalPos) {
		this.cardinalPos = cardinalPos;
	}

    
}