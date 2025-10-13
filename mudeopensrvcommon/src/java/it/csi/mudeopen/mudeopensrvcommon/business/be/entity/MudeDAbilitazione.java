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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mudeopen_d_abilitazione")
public class MudeDAbilitazione implements Serializable {
    private static final long serialVersionUID = 832576564204398689L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abilitazione")
    private Long id;

    @Column(name = "codice_abilitazione")
    private String codice;

    @Column(name = "desc_abilitazione")
    private String descrizione;

    @Column(name = "tipo_abilitazione")
    private Character tipo;

    @Column(name = "necessaria_iscrizione_albo")
    private Boolean necessariaIscrizioneAlbo;

    @Column(name = "necessaria_presenza_in_istanza")
    private Boolean necessariaPresenzaInIstanza;

    @Column(name = "data_inizio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInizio;

    @Column(name = "data_fine")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFine;

    @Column(name = "necessaria_selezione_quadro")
    private Boolean necessariaSelezioneQuadro;

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

    public Boolean getNecessariaIscrizioneAlbo() {
        return necessariaIscrizioneAlbo;
    }

    public void setNecessariaIscrizioneAlbo(Boolean necessariaIscrizioneAlbo) {
        this.necessariaIscrizioneAlbo = necessariaIscrizioneAlbo;
    }

    public Boolean getNecessariaPresenzaInIstanza() {
        return necessariaPresenzaInIstanza;
    }

    public void setNecessariaPresenzaInIstanza(Boolean necessariaPresenzaInIstanza) {
        this.necessariaPresenzaInIstanza = necessariaPresenzaInIstanza;
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

	public Boolean getNecessariaSelezioneQuadro() {
		return necessariaSelezioneQuadro;
	}

	public void setNecessariaSelezioneQuadro(Boolean necessariaSelezioneQuadro) {
		this.necessariaSelezioneQuadro = necessariaSelezioneQuadro;
	}
}