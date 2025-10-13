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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mudeopen_r_istanza_tracciato")
public class MudeRIstanzaTracciato extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -3400244675341004133L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_istanza_tracciato")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tipo_tracciato")
    private MudeDTipoTracciato mudeDTipoTracciato;

    @ManyToOne
    @JoinColumn(name = "id_istanza")
    private MudeTIstanza mudeTIstanza;

    @Column(name = "xml_tracciato")
    private String xmlTracciato;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MudeTUser mudeTUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeDTipoTracciato getMudeDTipoTracciato() {
        return mudeDTipoTracciato;
    }

    public void setMudeDTipoTracciato(MudeDTipoTracciato mudeDTipoTracciato) {
        this.mudeDTipoTracciato = mudeDTipoTracciato;
    }

    public MudeTIstanza getMudeTIstanza() {
        return mudeTIstanza;
    }

    public void setMudeTIstanza(MudeTIstanza mudeTIstanza) {
        this.mudeTIstanza = mudeTIstanza;
    }

    public String getXmlTracciato() {
        return xmlTracciato;
    }

    public void setXmlTracciato(String xmlTracciato) {
        this.xmlTracciato = xmlTracciato;
    }

      public MudeTUser getMudeTUser() {
        return mudeTUser;
    }

    public void setMudeTUser(MudeTUser mudeTUser) {
        this.mudeTUser = mudeTUser;
    }
}