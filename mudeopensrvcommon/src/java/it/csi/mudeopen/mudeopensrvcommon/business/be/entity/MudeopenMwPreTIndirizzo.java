/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_mw_pre_t_indirizzo")
public class MudeopenMwPreTIndirizzo {
    @Override
    public String toString() {
        return "MudeopenMwPreTIndirizzo{" +
                "idIndirizzo=" + idIndirizzo +
                ", idComune=" + idComune +
                ", idCivicoTopon=" + idCivicoTopon +
                ", idViaTopon=" + idViaTopon +
                ", sedime='" + sedime + '\'' +
                ", descVia='" + descVia + '\'' +
                ", numCivico=" + numCivico +
                ", bis='" + bis + '\'' +
                ", bisinterno='" + bisinterno + '\'' +
                ", interno='" + interno + '\'' +
                ", interno2='" + interno2 + '\'' +
                ", indirizzoCompleto='" + indirizzoCompleto + '\'' +
                ", cap='" + cap + '\'' +
                ", scala='" + scala + '\'' +
                ", flProvvisorio='" + flProvvisorio + '\'' +
                ", secondario='" + secondario + '\'' +
                '}';
    }

    @Id
    @Column(name="id_indirizzo")
    private Long idIndirizzo;

    @Column(name="id_comune")
    private Long idComune;

    @Column(name="id_civico_topon")
    private Integer idCivicoTopon;

    @Column(name="id_via_topon")
    private Long idViaTopon;

    @Column(name="sedime")
    private String sedime;

    @Column(name="desc_via")
    private String descVia;

    @Column(name="num_civico")
    private String numCivico;

    @Column(name="bis")
    private String bis;

    @Column(name="bisinterno")
    private String bisinterno;

    @Column(name="interno")
    private String interno;

    @Column(name="interno2")
    private String interno2;

    @Column(name="indirizzo_completo")
    private String indirizzoCompleto;

    @Column(name="cap")
    private String cap;

    @Column(name="scala")
    private String scala;

    @Column(name="fl_provvisorio")
    private String flProvvisorio;

    @Column(name="secondario")
    private String secondario;

    public Long getIdIndirizzo() {
        return idIndirizzo;
    }

    public void setIdIndirizzo(Long idIndirizzo) {
        this.idIndirizzo = idIndirizzo;
    }

    public Long getIdComune() {
        return idComune;
    }

    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    public Integer getIdCivicoTopon() {
        return idCivicoTopon;
    }

    public void setIdCivicoTopon(Integer idCivicoTopon) {
        this.idCivicoTopon = idCivicoTopon;
    }

    public Long getIdViaTopon() {
        return idViaTopon;
    }

    public void setIdViaTopon(Long idViaTopon) {
        this.idViaTopon = idViaTopon;
    }

    public String getSedime() {
        return sedime;
    }

    public void setSedime(String sedime) {
        this.sedime = sedime;
    }

    public String getDescVia() {
        return descVia;
    }

    public void setDescVia(String descVia) {
        this.descVia = descVia;
    }

    public String getNumCivico() {
        return numCivico;
    }

    public void setNumCivico(String numCivico) {
        this.numCivico = numCivico;
    }

    public String getBis() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public String getBisDecoded(){
        if(bis==null){
            return "";
        }
        switch(bis){
            case "1" : return "BIS";
            case "2" : return "TER";
            case "3" : return "QUATER";
            default: return "";
        }
    }

    public String getBisinterno() {
        return bisinterno;
    }

    public void setBisinterno(String bisinterno) {
        this.bisinterno = bisinterno;
    }

    public String getBisinternoDecoded(){
        if(bisinterno==null){
            return "";
        }
        switch(bisinterno){
            case "1" : return "BIS";
            case "2" : return "TER";
            case "3" : return "QUATER";
            default: return "";
        }
    }

    public String getInterno() {
        return interno;
    }

    public void setInterno(String interno) {
        this.interno = interno;
    }

    public String getInterno2() {
        return interno2;
    }

    public void setInterno2(String interno2) {
        this.interno2 = interno2;
    }

    public String getIndirizzoCompleto() {
        return indirizzoCompleto;
    }

    public void setIndirizzoCompleto(String indirizzoCompleto) {
        this.indirizzoCompleto = indirizzoCompleto;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getScala() {
        return scala;
    }

    public void setScala(String scala) {
        this.scala = scala;
    }

    public String getFlProvvisorio() {
        return flProvvisorio;
    }

    public void setFlProvvisorio(String flProvvisorio) {
        this.flProvvisorio = flProvvisorio;
    }

    public String getSecondario() {
        return secondario;
    }

    public void setSecondario(String secondario) {
        this.secondario = secondario;
    }
}