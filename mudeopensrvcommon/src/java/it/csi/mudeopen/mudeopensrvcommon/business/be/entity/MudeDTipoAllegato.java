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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mudeopen_d_tipo_allegato")
public class MudeDTipoAllegato extends BaseDictionaryEntity implements Serializable {
    private static final long serialVersionUID = 2404039273488839223L;

    @ManyToOne
    @JoinColumn(name="id_gruppo_allegato")
    private MudeDCategoriaAllegato categoriaAllegato;

    @Column(name="sub_cod_tipo_allegato")
    private String subCodeTipoAllegato;

    @Column(name="valida_firma")
    private Boolean validaFirma = Boolean.TRUE;

    @Column(name="firma_obbligatoria")
    private Boolean firmaObbligatoria;

    @Column(name="id")
    private Long id;

    public MudeDCategoriaAllegato getCategoriaAllegato() {
        return categoriaAllegato;
    }

    public void setCategoriaAllegato(MudeDCategoriaAllegato categoriaAllegato) {
        this.categoriaAllegato = categoriaAllegato;
    }

    public String getSubCodeTipoAllegato() {
        return subCodeTipoAllegato;
    }

    public void setSubCodeTipoAllegato(String subCodeTipoAllegato) {
        this.subCodeTipoAllegato = subCodeTipoAllegato;
    }

    public Boolean getValidaFirma() {
        return validaFirma;
    }

    public void setValidaFirma(Boolean validaFirma) {
        this.validaFirma = validaFirma;
    }

    public Boolean getFirmaObbligatoria() {
        return firmaObbligatoria;
    }

    public void setFirmaObbligatoria(Boolean firmaObbligatoria) {
        this.firmaObbligatoria = firmaObbligatoria;
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}