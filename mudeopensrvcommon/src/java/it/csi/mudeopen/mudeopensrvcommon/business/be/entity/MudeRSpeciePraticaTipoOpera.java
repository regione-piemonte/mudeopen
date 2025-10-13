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
@Table(name = "mudeopen_r_specie_pratica_tipo_opera")
public class MudeRSpeciePraticaTipoOpera extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8770998035002976214L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SpeciePraticaTipoOperaSeqGenerator")
    @SequenceGenerator(name = "SpeciePraticaTipoOperaSeqGenerator", sequenceName = "mudeopen_r_specie_pratica_tipo_opera_id_seq", allocationSize = 1)
    @Column(name="id_specie_pratica_tipo_opera")
    private long id;

    @ManyToOne
    @JoinColumn(name="id_specie_pratica")
    private MudeDSpeciePratica mudeDSpeciePratica;

    @ManyToOne
    @JoinColumn(name="id_tipo_opera")
    private MudeDTipoOpera mudeDTipoOpera;

    @Column(name = "abilitato")
    private Boolean abilitato;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MudeDSpeciePratica getMudeDSpeciePratica() {
        return mudeDSpeciePratica;
    }

    public void setMudeDSpeciePratica(MudeDSpeciePratica mudeDSpeciePratica) {
        this.mudeDSpeciePratica = mudeDSpeciePratica;
    }

    public MudeDTipoOpera getMudeDTipoOpera() {
        return mudeDTipoOpera;
    }

    public void setMudeDTipoOpera(MudeDTipoOpera mudeDTipoOpera) {
        this.mudeDTipoOpera = mudeDTipoOpera;
    }

    public Boolean getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {
        this.abilitato = abilitato;
    }
}