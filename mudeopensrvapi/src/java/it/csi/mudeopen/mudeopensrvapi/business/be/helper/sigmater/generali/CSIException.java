/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.generali;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per CSIException complex type.
 * 
 * 
 * &lt;complexType name="CSIException"&gt;
 *         &lt;element name="nestedExcClassName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nestedExcMsg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stackTraceMessage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSIException", propOrder = {
    "nestedExcClassName",
    "nestedExcMsg",
    "stackTraceMessage"
})
@XmlSeeAlso({
    SystemException.class,
    UnrecoverableException.class
})
public class CSIException {

    @XmlElement(required = true, nillable = true)
    protected String nestedExcClassName;
    @XmlElement(required = true, nillable = true)
    protected String nestedExcMsg;
    @XmlElement(required = true, nillable = true)
    protected String stackTraceMessage;

    /**
     * Recupera il valore della proprieta nestedExcClassName.
     * 
     *     
     */
    public String getNestedExcClassName() {
        return nestedExcClassName;
    }

    /**
     * Imposta il valore della proprieta nestedExcClassName.
     * 
     *     
     */
    public void setNestedExcClassName(String value) {
        this.nestedExcClassName = value;
    }

    /**
     * Recupera il valore della proprieta nestedExcMsg.
     * 
     *     
     */
    public String getNestedExcMsg() {
        return nestedExcMsg;
    }

    /**
     * Imposta il valore della proprieta nestedExcMsg.
     * 
     *     
     */
    public void setNestedExcMsg(String value) {
        this.nestedExcMsg = value;
    }

    /**
     * Recupera il valore della proprieta stackTraceMessage.
     * 
     *     
     */
    public String getStackTraceMessage() {
        return stackTraceMessage;
    }

    /**
     * Imposta il valore della proprieta stackTraceMessage.
     * 
     *     
     */
    public void setStackTraceMessage(String value) {
        this.stackTraceMessage = value;
    }

}
