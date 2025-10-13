/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;

@Entity
@Table(name = "mudeopen_r_loc_titolare")
public class MudeopenRLocTitolare extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_titolare")
    private Long idTitolare;

    @Column(name="id_catasto")
    private Long idCatasto;

    @Column(name="cf_titolare")
    private String cfTitolare;

    @Column(name="cognome")
    private String cognome;

    @Column(name="nome")
    private String nome;

    @Column(name="partita_iva")
    private String partitaIva;

    @Column(name="denominazione")
    private String denominazione;

    public Long getIdTitolare() {
        return idTitolare;
    }

    public void setIdTitolare(Long idTitolare) {
        this.idTitolare = idTitolare;
    }

    public Long getIdCatasto() {
        return idCatasto;
    }

    public void setIdCatasto(Long idCatasto) {
        this.idCatasto = idCatasto;
    }

    public String getCfTitolare() {
        return cfTitolare;
    }

    public void setCfTitolare(String cfTitolare) {
        this.cfTitolare = cfTitolare;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }
}
