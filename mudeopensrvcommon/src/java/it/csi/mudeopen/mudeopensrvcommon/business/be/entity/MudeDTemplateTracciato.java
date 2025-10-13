/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_d_template_tracciato")
public class MudeDTemplateTracciato extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id_template_tracciato")
    //    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TemplateTracciatoSeqGenerator")
    //    @SequenceGenerator(name = "TemplateTracciatoSeqGenerator", sequenceName = "mudeopen_d_template_tracciato_id_template_tracciato_seq", allocationSize = 1)
    private Long id;

    @Column(name = "codice")
    private String codice;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "xslt_template")
    private String xsltTemplate;

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

    public String getXsltTemplate() {
        return xsltTemplate;
    }

    public void setXsltTemplate(String xsltTemplate) {
        this.xsltTemplate = xsltTemplate;
    }

}