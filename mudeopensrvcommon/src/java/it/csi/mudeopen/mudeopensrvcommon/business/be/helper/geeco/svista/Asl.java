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
@XmlType(name = "Asl", propOrder = {
    "codAsl",
    "nome",
    "nomeBreve"
})
public class Asl {

    @XmlElement(required = true, nillable = true)
    protected String codAsl;
    @XmlElement(required = true, nillable = true)
    protected String nome;
    @XmlElement(required = true, nillable = true)
    protected String nomeBreve;

    public String getCodAsl() {
        return codAsl;
    }

    public void setCodAsl(String value) {
        this.codAsl = value;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String value) {
        this.nome = value;
    }

    public String getNomeBreve() {
        return nomeBreve;
    }

    public void setNomeBreve(String value) {
        this.nomeBreve = value;
    }

}