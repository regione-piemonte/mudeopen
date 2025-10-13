/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_d_tipologia_committente")
public class MudeDTipologiaCommittente extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id_tipologia_committente")
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipologiaCommittenteSeqGenerator")
    //    @SequenceGenerator(name = "TipologiaCommittenteSeqGenerator", sequenceName = "mudeopen_d_tipologia_committente_id_tipologia_committente_seq", allocationSize = 1)
    private Long id;
    @Column(name = "denominazione")
    private String denominazione;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }
}