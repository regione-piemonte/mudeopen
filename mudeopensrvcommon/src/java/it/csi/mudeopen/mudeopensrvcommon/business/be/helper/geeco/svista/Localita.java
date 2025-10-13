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
@XmlType(name = "Localita", propOrder = {
    "id",
    "idComune",
    "nome"
})
public class Localita {

    protected long id;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long idComune;
    @XmlElement(required = true, nillable = true)
    protected String nome;

    public long getId() {
        return id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public Long getIdComune() {
        return idComune;
    }

    public void setIdComune(Long value) {
        this.idComune = value;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String value) {
        this.nome = value;
    }

}