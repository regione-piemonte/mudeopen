/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
@Entity
@Table(name = "mudeopen_t_indirizzo")
public class MudeTIndirizzo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_indirizzo")
    private Long id;
    @Column(name = "cap")
    private String cap;
    @Column(name = "denom_comune_estero")
    private String denomComuneEstero;
    @Column(name = "indirizzo")
    private String indirizzo;
    @Column(name = "localita")
    private String localita;
    @Column(name = "id_dug")
    private Long idDug;
    @Column(name = "numero_civico")
    private String numeroCivico;
    @Column(name = "indirizzo_estero")
    private Boolean indirizzoEstero;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_indirizzo")
    private TipoIndirizzo tipoIndirizzo;
    //bi-directional many-to-one association to MudeDComune
    @ManyToOne
    @JoinColumn(name = "id_comune")
    private MudeDComune mudeDComune;
    //bi-directional many-to-one association to MudeDComune
    @ManyToOne
    @JoinColumn(name = "id_nazione")
    private MudeDNazione mudeDNazione;
    //bi-directional many-to-one association to MudeTSoggetto
    @ManyToOne
    @JoinColumn(name = "id_contatto")
    private MudeTContatto mudeTContatto;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "cellulare")
    private String cellulare;
    @Column(name = "mail")
    private String mail;
    @Column(name = "pec")
    private String pec;

    public MudeTIndirizzo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCap() {
        return this.cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getDenomComuneEstero() {
        return this.denomComuneEstero;
    }

    public void setDenomComuneEstero(String denomComuneEstero) {
        this.denomComuneEstero = denomComuneEstero;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNumeroCivico() {
        return this.numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public Boolean getIndirizzoEstero() {
        return this.indirizzoEstero;
    }

    public void setIndirizzoEstero(Boolean indirizzoEstero) {
        this.indirizzoEstero = indirizzoEstero;
    }

    public MudeDComune getMudeDComune() {
        return mudeDComune;
    }

    public void setMudeDComune(MudeDComune mudeDComune) {
        this.mudeDComune = mudeDComune;
    }

    public MudeTContatto getMudeTContatto() {
        return mudeTContatto;
    }

    public void setMudeTContatto(MudeTContatto mudeTContatto) {
        this.mudeTContatto = mudeTContatto;
    }

    public TipoIndirizzo getTipoIndirizzo() {
        return tipoIndirizzo;
    }

    public void setTipoIndirizzo(TipoIndirizzo tipoIndirizzo) {
        this.tipoIndirizzo = tipoIndirizzo;
    }

    public MudeDNazione getMudeDNazione() {
        return mudeDNazione;
    }

    public void setMudeDNazione(MudeDNazione mudeDNazione) {
        this.mudeDNazione = mudeDNazione;
    }

    public String getLocalita() {
        return localita;
    }

    public void setLocalita(String localita) {
        this.localita = localita;
    }

    public Long getIdDug() {
        return idDug;
    }

    public void setIdDug(Long idDug) {
        this.idDug = idDug;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public enum TipoIndirizzo {
        DOMICILIO("Domicilio", 1, TipoContatto.PF),
        RESIDENZA("Residenza", 2, TipoContatto.PF),
        STUDIO("Studio", 3, TipoContatto.PF),

        SEDE_LEGALE("Sede legale", 1, TipoContatto.PG),
        SEDE_OPERATIVA("Sede operativa", 2, TipoContatto.PG);

        String label;
        long id;
        TipoContatto tipoContatto;

        TipoIndirizzo(String label, long id, TipoContatto tipoContatto) {
            this.label = label;
            this.id = id;
            this.tipoContatto = tipoContatto;
        }

        public static TipoIndirizzo fromLabel(long id, TipoContatto tipoContatto) {
            for (TipoIndirizzo tipo : TipoIndirizzo.values()) {
                if (tipo.id == id && tipo.tipoContatto == tipoContatto) {
                    return tipo;
                }
            }
            return null;
        }

        public String getLabel() {
            return label;
        }

        public long getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TipoContatto getTipoContatto() {
            return tipoContatto;
        }
    }
}