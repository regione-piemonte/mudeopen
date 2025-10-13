/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
@Entity
@Table(name = "mudeopen_t_geeco_comune")
public class MudeTGeecoComune extends BaseEntity {

	
    @Id
    @GeneratedValue(
    		strategy = GenerationType.IDENTITY,
    		generator="seq_geeco_comune"
    )
    @SequenceGenerator(name="seq_geeco_comune",sequenceName="seq_mudeopen_t_geeco_comune", allocationSize=1)    
    @Column(name = "id_comune")
    private Long id;

    @Column(name = "Fk_comune_geeco")
    private Long idComuneGeeco;

    @Column(name = "cod_belfiore_comune")       
    private String codBelfioreComune;

    @Column(name = "denom_comune")       
    private String denomComune;

    @Column(name = "coordinate_gml")       
    private String coordinateGml;

    @Type(type = "jsonb")
    @Column(name = "selected_position", columnDefinition = "jsonb")       
    private String selectedPosition;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdComuneGeeco() {
		return idComuneGeeco;
	}

	public void setIdComuneGeeco(Long idComuneGeeco) {
		this.idComuneGeeco = idComuneGeeco;
	}

	public String getCodBelfioreComune() {
		return codBelfioreComune;
	}

	public void setCodBelfioreComune(String codBelfioreComune) {
		this.codBelfioreComune = codBelfioreComune;
	}

	public String getDenomComune() {
		return denomComune;
	}

	public void setDenomComune(String denomComune) {
		this.denomComune = denomComune;
	}

	public String getCoordinateGml() {
		return coordinateGml;
	}

	public void setCoordinateGml(String coordinateGml) {
		this.coordinateGml = coordinateGml;
	}

	public String getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(String selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

}