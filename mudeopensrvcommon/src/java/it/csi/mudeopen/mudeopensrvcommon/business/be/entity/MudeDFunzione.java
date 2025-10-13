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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mudeopen_d_funzione")
public class MudeDFunzione implements Serializable {
    private static final long serialVersionUID = -3532218377483356107L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funzione")
    private Long id;

    @Column(name = "codice_funzione")
    private String codice;

    @Column(name = "desc_funzione")
    private String descrizione;

    @Column(name = "tipo_funzione")
    private Character tipo;

    @ManyToOne
    @JoinColumn(name = "id_categoria_quadro")
    private MudeDCategoriaQuadro categoriaQuadro;

    @Column(name = "previsto_pm")
    private Boolean previstoPM;

    @Column(name = "data_inizio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInizio;

    @Column(name = "data_fine")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public MudeDCategoriaQuadro getCategoriaQuadro() {
        return categoriaQuadro;
    }

    public void setCategoriaQuadro(MudeDCategoriaQuadro categoriaQuadro) {
        this.categoriaQuadro = categoriaQuadro;
    }

    public Boolean getPrevistoPM() {
        return previstoPM;
    }

    public void setPrevistoPM(Boolean previstoPM) {
        this.previstoPM = previstoPM;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }
}