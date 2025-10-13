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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_fruitore")
public class MudeDFruitore  extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -1810166669495571584L;

	@Id
    @Column(name = "id_fruitore")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFruitore;

    @Column(name = "codice_fruitore")
    private String codiceFruitore;

    @Column(name ="note")
	private String note;
/*    
    @ManyToOne
    @JoinColumn(name = "id_user")
    private MudeTUser mudeTUser;
*/
	public Long getIdFruitore() {
		return idFruitore;
	}

	public void setIdFruitore(Long idFruitore) {
		this.idFruitore = idFruitore;
	}

	public String getCodiceFruitore() {
		return codiceFruitore;
	}

	public void setCodiceFruitore(String codiceFruitore) {
		this.codiceFruitore = codiceFruitore;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
/*
	public MudeTUser getMudeTUser() {
		return mudeTUser;
	}

	public void setMudeTUser(MudeTUser mudeTUser) {
		this.mudeTUser = mudeTUser;
	}
*/
 }