/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

import java.io.Serializable;

@Entity
@Table(name = "mudeopen_t_ente")
public class MudeTEnte extends BaseEntity  implements Serializable{

    private static final long serialVersionUID = 118280650307419093L;

	@Id
    @Column(name = "id_ente")
    private Long id;

    @Column(name = "codice")
    private String codice;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "descrizione_estesa")
    private String descrizioneEstesa;

    @Column(name = "indirizzo_ente")
    private String indirizzoEnte;

    @Column(name = "cap_ente")
    private String capEnte;

    @Column(name = "responsabile_ente")
    private String responsabileEnte;

    @Column(name = "pec_ente")
    private String pecEnte;

    @ManyToOne
    @JoinColumn(name = "id_comune")
    private MudeDComune comune;

    @ManyToOne
    @JoinColumn(name = "id_tipo_ente")
    private MudeDTipoEnte tipoEnte;

    @Column(name = "logo_content")
    @Type(type = "org.hibernate.type.BinaryType")
    byte[] logoContent;

    @Column(name = "email_gruppo")
    private String emailGruppoEnte;

    @Column(name = "intestazione")
    private String intestazione;

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

    public String getDescrizioneEstesa() {
        return descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {
        this.descrizioneEstesa = descrizioneEstesa;
    }

    public String getIndirizzoEnte() {
        return indirizzoEnte;
    }

    public void setIndirizzoEnte(String indirizzoEnte) {
        this.indirizzoEnte = indirizzoEnte;
    }

    public String getCapEnte() {
        return capEnte;
    }

    public void setCapEnte(String capEnte) {
        this.capEnte = capEnte;
    }

    public String getResponsabileEnte() {
        return responsabileEnte;
    }

    public void setResponsabileEnte(String responsabileEnte) {
        this.responsabileEnte = responsabileEnte;
    }

    public String getPecEnte() {
        return pecEnte;
    }

    public void setPecEnte(String pecEnte) {
        this.pecEnte = pecEnte;
    }

    public MudeDComune getComune() {
        return comune;
    }

    public void setComune(MudeDComune comune) {
        this.comune = comune;
    }

    public MudeDTipoEnte getTipoEnte() {
        return tipoEnte;
    }

    public void setTipoEnte(MudeDTipoEnte tipoEnte) {
        this.tipoEnte = tipoEnte;
    }

    public byte[] getLogoContent() {
        return logoContent;
    }

    public void setLogoContent(byte[] logoContent) {
        this.logoContent = logoContent;
    }

	public String getEmailGruppoEnte() {
		return emailGruppoEnte;
	}

	public void setEmailGruppoEnte(String emailGruppoEnte) {
		this.emailGruppoEnte = emailGruppoEnte;
	}

	public String getIntestazione() {
		return intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}
}