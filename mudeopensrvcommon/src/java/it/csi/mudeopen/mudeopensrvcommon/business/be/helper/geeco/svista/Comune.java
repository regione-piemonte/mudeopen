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
@XmlType(name = "Comune", propOrder = {
    "aslDiRiferimento",
    "cap",
    "codCatasto",
    "codIstat",
    "id",
    "idProvincia",
    "nome"
})
public class Comune {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfAsl aslDiRiferimento;
    @XmlElement(required = true, nillable = true)
    protected String cap;
    @XmlElement(required = true, nillable = true)
    protected String codCatasto;
    @XmlElement(required = true, nillable = true)
    protected String codIstat;
    protected long id;
    protected long idProvincia;
    @XmlElement(required = true, nillable = true)
    protected String nome;

    public ArrayOfAsl getAslDiRiferimento() {
        return aslDiRiferimento;
    }

    public void setAslDiRiferimento(ArrayOfAsl value) {
        this.aslDiRiferimento = value;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String value) {
        this.cap = value;
    }

    public String getCodCatasto() {
        return codCatasto;
    }

    public void setCodCatasto(String value) {
        this.codCatasto = value;
    }

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

    public long getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(long value) {
        this.idProvincia = value;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String value) {
        this.nome = value;
    }

}