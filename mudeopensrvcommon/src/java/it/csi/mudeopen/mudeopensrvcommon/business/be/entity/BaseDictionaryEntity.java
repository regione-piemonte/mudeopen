/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseDictionaryEntity extends BaseEntity{
    @Id
    @Column(name = "codice")
    private String codice;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "descrizione_estesa")
    private String descrizioneEstesa;

//    @Column(name = "data_inizio")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dataInizio;
//
//    @Column(name = "data_fine")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dataFine;

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

//    /**
//     * Gets data inizio.
//     *
//     * @return the data inizio
//     */
//    public Date getDataInizio() {
//        return dataInizio;
//    }
//
//    /**
//     * Sets data inizio.
//     *
//     * @param dataInizio the data inizio
//     */
//    public void setDataInizio(Date dataInizio) {
//        this.dataInizio = dataInizio;
//    }
//
//    /**
//     * Gets data fine.
//     *
//     * @return the data fine
//     */
//    public Date getDataFine() {
//        return dataFine;
//    }
//
//    /**
//     * Sets data fine.
//     *
//     * @param dataFine the data fine
//     */
//    public void setDataFine(Date dataFine) {
//        this.dataFine = dataFine;
//    }
}