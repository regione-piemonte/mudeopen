/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;

import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
@Entity
@Table(name = "mudeopen_d_tipo_quadro")
public class MudeDTipoQuadro extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1725077694525250332L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo_quadro")
	private Long idTipoQuadro;

	@Column(name = "cod_tipo_quadro")
	private String codTipoQuadro;

	@Column(name = "des_tipo_quadro")
	private String desTipoQuadro;

	@Column(name = "id_categoria_quadro")
	private Long idCategoriaQuadro;

	@ManyToOne
	@JoinColumn(name = "id_tipo_quadro_padre")
	private MudeDTipoQuadro tipoQuadroPadre;
	
	public String getCodTipoQuadro() {
		return codTipoQuadro;
	}

	public void setCodTipoQuadro(String codTipoQuadro) {
		this.codTipoQuadro = codTipoQuadro;
	}

	public String getDesTipoQuadro() {
		return desTipoQuadro;
	}

	public void setDesTipoQuadro(String desTipoQuadro) {
		this.desTipoQuadro = desTipoQuadro;
	}

	public Long getIdTipoQuadro() {
		return idTipoQuadro;
	}

	public void setIdTipoQuadro(Long idTipoQuadro) {
		this.idTipoQuadro = idTipoQuadro;
	}

	public Long getIdCategoriaQuadro() {
		return idCategoriaQuadro;
	}

	public void setIdCategoriaQuadro(Long idCategoriaQuadro) {
		this.idCategoriaQuadro = idCategoriaQuadro;
	}

	public MudeDTipoQuadro getTipoQuadroPadre() {
		return tipoQuadroPadre;
	}

	public void setTipoQuadroPadre(MudeDTipoQuadro tipoQuadroPadre) {
		this.tipoQuadroPadre = tipoQuadroPadre;
	}

}