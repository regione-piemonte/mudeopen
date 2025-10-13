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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_r_tipo_istanza_specie_pratica")
public class MudeRTipoIstanzaSpeciePratica extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 7202493721637463842L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoIstanzaSpeciePraticaSeqGenerator")
    @SequenceGenerator(name = "TipoIstanzaSpeciePraticaSeqGenerator", sequenceName = "mudeopen_r_tipo_istanza_specie_pratica_id_seq", allocationSize = 1)
    @Column(name="id_tipo_istanza_specie_pratica")
    private long id;

    @ManyToOne
    @JoinColumn(name="id_tipo_istanza")
    private MudeDTipoIstanza mudeDTipoIstanza;

    @ManyToOne
    @JoinColumn(name="id_specie_pratica")
    private MudeDSpeciePratica mudeDSpeciePratica;

    @Column(name = "abilitato")
    private Boolean abilitato;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MudeDTipoIstanza getMudeDTipoIstanza() {
        return mudeDTipoIstanza;
    }

    public void setMudeDTipoIstanza(MudeDTipoIstanza mudeDTipoIstanza) {
        this.mudeDTipoIstanza = mudeDTipoIstanza;
    }

    public MudeDSpeciePratica getMudeDSpeciePratica() {
        return mudeDSpeciePratica;
    }

    public void setMudeDSpeciePratica(MudeDSpeciePratica mudeDSpeciePratica) {
        this.mudeDSpeciePratica = mudeDSpeciePratica;
    }

    public Boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }

}