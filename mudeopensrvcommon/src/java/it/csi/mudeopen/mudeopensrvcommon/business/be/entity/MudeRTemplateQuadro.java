/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "mudeopen_r_template_quadro")
public class MudeRTemplateQuadro extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -5298060657066743709L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_template_quadro")
	private Long idTemplateQuadro;

	@ManyToOne
	@JoinColumn(name = "id_template")
	private MudeDTemplate mudeDTemplate;

	@ManyToOne
	@JoinColumn(name = "id_quadro")
	private MudeDQuadro mudeDQuadro;

	@Column(name = "ordinamento_template_quadro")
	private Long ordinamentoTemplateQuadro;

	@Column(name = "flg_quadro_obbigatorio")
	private Integer flgQuadroObbigatorio;

	@Column(name = "proprieta")
	private String proprieta;

    public Long getIdTemplateQuadro() {
		return idTemplateQuadro;
	}

    public void setIdTemplateQuadro(Long idTemplateQuadro) {
		this.idTemplateQuadro = idTemplateQuadro;
	}

    public MudeDTemplate getMudeDTemplate() {
		return mudeDTemplate;
	}

    public void setMudeDTemplate(MudeDTemplate mudeDTemplate) {
		this.mudeDTemplate = mudeDTemplate;
	}

    public MudeDQuadro getMudeDQuadro() {
		return mudeDQuadro;
	}

    public void setMudeDQuadro(MudeDQuadro mudeDQuadro) {
		this.mudeDQuadro = mudeDQuadro;
	}

    public Long getOrdinamentoTemplateQuadro() {
		return ordinamentoTemplateQuadro;
	}

    public void setOrdinamentoTemplateQuadro(Long ordinamentoTemplateQuadro) {
		this.ordinamentoTemplateQuadro = ordinamentoTemplateQuadro;
	}

    public Integer getFlgQuadroObbigatorio() {
		return flgQuadroObbigatorio;
	}

    public void setFlgQuadroObbigatorio(Integer flgQuadroObbigatorio) {
		this.flgQuadroObbigatorio = flgQuadroObbigatorio;
	}

	public String getProprieta() {
		return proprieta;
	}

	public void setProprieta(String proprieta) {
		this.proprieta = proprieta;
	}
}