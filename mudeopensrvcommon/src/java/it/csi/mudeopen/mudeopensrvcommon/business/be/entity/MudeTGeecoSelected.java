/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "mudeopen_t_geeco_selected")
public class MudeTGeecoSelected extends BaseEntity {

    @Id
    @GeneratedValue(
    		strategy = GenerationType.IDENTITY,
    		generator="seq_geeco_selected"
    )
    @SequenceGenerator(name="seq_geeco_selected",sequenceName="seq_mudeopen_t_geeco_selected", allocationSize=1)    
    @Column(name = "id_selected")
    private Long id;

    @Column(name = "sessione_geeco")
    private String sessioneGeeco;

    @Type(type = "jsonb")
    @Column(name = "selected_data", columnDefinition = "jsonb")       
    private String selectedData;

    @Type(type = "jsonb")
    @Column(name = "selected_position", columnDefinition = "jsonb")       
    private String selectedPosition;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdSession() {
		return sessioneGeeco;
	}

	public void setIdSession(String idSession) {
		this.sessioneGeeco = idSession;
	}

	public String getSelectedData() {
		return selectedData;
	}

	public void setSelectedData(String selectedData) {
		this.selectedData = selectedData;
	}

	public String getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(String selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

}