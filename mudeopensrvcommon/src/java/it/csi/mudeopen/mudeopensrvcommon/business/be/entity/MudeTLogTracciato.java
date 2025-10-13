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
@Table(name = "mudeopen_t_log_tracciato")
public class MudeTLogTracciato extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5200789423819633131L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log_tracciato")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_istanza")
    private MudeTIstanza mudeTIstanza;

    @ManyToOne
    @JoinColumn(name = "id_tipo_tracciato")
    private MudeDTipoTracciato mudeDTipoTracciato;

    @Column(name = "errore")
    private String errore;

    @Column(name = "tipo_errore")
    private String tipoErrore;
    @Column(name = "error_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date errorTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MudeTUser mudeTUser;

    @Column(name = "mail_inviata")
    private Boolean mailInviata;

    @Column(name = "used_xslt")
    private String usedXslt;
    @Column(name = "jsondata_as_xml")
    private String jsondataAsXML;
    @Column(name = "generated_xml")
    private String generatedXML;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeTIstanza getMudeTIstanza() {
        return mudeTIstanza;
    }

    public void setMudeTIstanza(MudeTIstanza mudeTIstanza) {
        this.mudeTIstanza = mudeTIstanza;
    }

    public MudeDTipoTracciato getMudeDTipoTracciato() {
        return mudeDTipoTracciato;
    }

    public void setMudeDTipoTracciato(MudeDTipoTracciato mudeDTipoTracciato) {
        this.mudeDTipoTracciato = mudeDTipoTracciato;
    }

    public String getErrore() {
        return errore;
    }

    public void setErrore(String errore) {
        this.errore = errore;
    }

    public String getTipoErrore() {
        return tipoErrore;
    }

    public void setTipoErrore(String tipoErrore) {
        this.tipoErrore = tipoErrore;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public MudeTUser getMudeTUser() {
        return mudeTUser;
    }

    public void setMudeTUser(MudeTUser mudeTUser) {
        this.mudeTUser = mudeTUser;
    }

    public Boolean getMailInviata() {
        return mailInviata;
    }

    public void setMailInviata(Boolean mailInviata) {
        this.mailInviata = mailInviata;
    }

    public String getUsedXslt() {
        return usedXslt;
    }

    public void setUsedXslt(String usedXslt) {
        this.usedXslt = usedXslt;
    }

    public String getJsondataAsXML() {
        return jsondataAsXML;
    }

    public void setJsondataAsXML(String jsondataAsXML) {
        this.jsondataAsXML = jsondataAsXML;
    }

    public String getGeneratedXML() {
        return generatedXML;
    }

    public void setGeneratedXML(String generatedXML) {
        this.generatedXML = generatedXML;
    }
}