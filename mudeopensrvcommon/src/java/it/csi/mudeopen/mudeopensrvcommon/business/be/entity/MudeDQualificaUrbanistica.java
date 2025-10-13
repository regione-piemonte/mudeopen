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
@Table(name = "mudeopen_d_qualifica_urbanistica")
public class MudeDQualificaUrbanistica extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6851507088163605776L;

    @Id
    @Column(name = "id_qualifica_urbanistica")
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QualificaUrbanisticaSeqGenerator")
    //    @SequenceGenerator(name = "QualificaUrbanisticaSeqGenerator", sequenceName = "mudeopen_d_qualifica_urbanistica_id_qualifica_urbanistica_seq", allocationSize = 1)
    private Long id;

    @Column(name = "codice")
    private String codice;

    @Column(name = "descrizione")
    private String descrizione;

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }
}