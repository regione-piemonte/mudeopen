/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSIException", propOrder = {
    "nestedExcClassName",
    "nestedExcMsg",
    "stackTraceMessage"
})
@XmlSeeAlso({
    SystemException.class,
    UnrecoverableException.class,
    UserException.class
})
public class CSIException {

    @XmlElement(required = true, nillable = true)
    protected String nestedExcClassName;
    @XmlElement(required = true, nillable = true)
    protected String nestedExcMsg;
    @XmlElement(required = true, nillable = true)
    protected String stackTraceMessage;

    public String getNestedExcClassName() {
        return nestedExcClassName;
    }

    public void setNestedExcClassName(String value) {
        this.nestedExcClassName = value;
    }

    public String getNestedExcMsg() {
        return nestedExcMsg;
    }

    public void setNestedExcMsg(String value) {
        this.nestedExcMsg = value;
    }

    public String getStackTraceMessage() {
        return stackTraceMessage;
    }

    public void setStackTraceMessage(String value) {
        this.stackTraceMessage = value;
    }

}