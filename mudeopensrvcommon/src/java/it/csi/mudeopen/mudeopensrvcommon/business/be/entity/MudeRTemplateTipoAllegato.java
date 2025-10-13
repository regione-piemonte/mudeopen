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
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_r_template_tipo_allegato")
public class MudeRTemplateTipoAllegato extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4397451815880607508L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_template_tipo_allegato")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_template")
    private MudeDTemplate template;

    @ManyToOne
    @JoinColumn(name="id_tipo_allegato")
    private MudeDTipoAllegato tipoAllegato;

    @Column(name = "ordinamento")
    private Integer ordinamento;

    @Column(name = "obbligatorio")
    private Boolean obbligatorio;

    @Column(name = "ripetibile")
    private Boolean ripetibile;

    @Column(name = "espressione_obbligatorieta")
    private String espressioneObbligatorieta;

    @Column(name = "espressione_ripetibilita")
    private String espressioneRipetibilita;

    @ManyToOne
    @JoinColumn(name="id_modello_documentale")
    private MudeTModello modello;

    @Column(name = "flag_ricorrente")
    private Boolean flagRicorrente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeDTemplate getTemplate() {
        return template;
    }

    public void setTemplate(MudeDTemplate template) {
        this.template = template;
    }

    public MudeDTipoAllegato getTipoAllegato() {
        return tipoAllegato;
    }

    public void setTipoAllegato(MudeDTipoAllegato tipoAllegato) {
        this.tipoAllegato = tipoAllegato;
    }

    public Integer getOrdinamento() {
        return ordinamento;
    }

    public void setOrdinamento(Integer ordinamento) {
        this.ordinamento = ordinamento;
    }

    public Boolean getObbligatorio() {
        return obbligatorio;
    }

    public void setObbligatorio(Boolean obbligatorio) {
        this.obbligatorio = obbligatorio;
    }

    public Boolean getRipetibile() {
        return ripetibile;
    }

    public void setRipetibile(Boolean ripetibile) {
        this.ripetibile = ripetibile;
    }

    public String getEspressioneObbligatorieta() {
        return espressioneObbligatorieta;
    }

    public void setEspressioneObbligatorieta(String espressioneObbligatorieta) {
        this.espressioneObbligatorieta = espressioneObbligatorieta;
    }

    public String getEspressioneRipetibilita() {
        return espressioneRipetibilita;
    }

    public void setEspressioneRipetibilita(String espressioneRipetibilita) {
        this.espressioneRipetibilita = espressioneRipetibilita;
    }

    public MudeTModello getModello() {
        return modello;
    }

    public void setModello(MudeTModello modello) {
        this.modello = modello;
    }

    public Boolean getFlagRicorrente() {
        return flagRicorrente;
    }

    public void setFlagRicorrente(Boolean flagRicorrente) {
        this.flagRicorrente = flagRicorrente;
    }

}