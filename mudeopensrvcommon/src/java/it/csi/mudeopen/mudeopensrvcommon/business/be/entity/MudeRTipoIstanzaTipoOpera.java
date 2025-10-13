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
@Table(name = "mudeopen_r_tipo_istanza_tipo_opera_diretta")
public class MudeRTipoIstanzaTipoOpera extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -5384853007263236614L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoIstanzaTipoOperaDirettaSeqGenerator")
    @SequenceGenerator(name = "TipoIstanzaTipoOperaDirettaSeqGenerator", sequenceName = "mudeopen_r_tipo_istanza_tipo_opera_diretta_id_seq", allocationSize = 1)
    @Column(name="id_tipo_istanza_tipo_opera_diretta")
    private long id;

    @ManyToOne
    @JoinColumn(name="id_tipo_istanza")
    private MudeDTipoIstanza mudeDTipoIstanza;

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

    public MudeDTipoIstanza getMudeDTipoIstanza() {
        return mudeDTipoIstanza;
    }

    public void setMudeDTipoIstanza(MudeDTipoIstanza mudeDTipoIstanza) {
        this.mudeDTipoIstanza = mudeDTipoIstanza;
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