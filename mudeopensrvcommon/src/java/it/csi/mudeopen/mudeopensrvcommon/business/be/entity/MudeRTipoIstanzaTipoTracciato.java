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
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_r_tipo_istanza_tipo_tracciato")
public class MudeRTipoIstanzaTipoTracciato extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -3400244675341004133L;

    @Id
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoIstanzaTipoTracciatoSeqGenerator")
    //    @SequenceGenerator(name = "TipoIstanzaTipoTracciatoSeqGenerator", sequenceName = "mudeopen_r_tipo_istanza_tipo_tracciato_id_seq", allocationSize = 1)
    @Column(name = "id_tipo_istanza_tipo_tracciato")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tipo_tracciato")
    private MudeDTipoTracciato mudeDTipoTracciato;

    @ManyToOne
    @JoinColumn(name = "id_tipo_istanza")
    private MudeDTipoIstanza mudeDTipoIstanza;

    @Column(name = "xslt_template_principale")
    private String xsltTemplatePrincipale;
    @Column(name = "attiva")
    private Boolean attiva;

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

    public MudeDTipoIstanza getMudeDTipoIstanza() {
        return mudeDTipoIstanza;
    }

    public void setMudeDTipoIstanza(MudeDTipoIstanza mudeDTipoIstanza) {
        this.mudeDTipoIstanza = mudeDTipoIstanza;
    }

    public String getXsltTemplatePrincipale() {
        return xsltTemplatePrincipale;
    }

    public void setXsltTemplatePrincipale(String xsltTemplatePrincipale) {
        this.xsltTemplatePrincipale = xsltTemplatePrincipale;
    }

    public Boolean getAttiva() {
        return attiva;
    }

    public void setAttiva(Boolean attiva) {
        this.attiva = attiva;
    }
}