/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Provincia", propOrder = {
    "codIstat",
    "id",
    "idRegione",
    "nome",
    "sigla"
})
public class Provincia {

    @XmlElement(required = true, nillable = true)
    protected String codIstat;
    protected long id;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long idRegione;
    @XmlElement(required = true, nillable = true)
    protected String nome;
    @XmlElement(required = true, nillable = true)
    protected String sigla;

    public String getCodIstat() {
        return codIstat;
    }

    public void setCodIstat(String value) {
        this.codIstat = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public Long getIdRegione() {
        return idRegione;
    }

    public void setIdRegione(Long value) {
        this.idRegione = value;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String value) {
        this.nome = value;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String value) {
        this.sigla = value;
    }

}