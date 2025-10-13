/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for estraiFileIstanzaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="estraiFileIstanzaResponse"&gt;
 *         &lt;element name="return" type="{http://documenti.interfacews.mudesrvextsic.mude.csi.it/}fileOutput" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estraiFileIstanzaResponse", propOrder = {
    "_return"
})
public class EstraiFileIstanzaResponse {
    @XmlElement(name = "return")
    protected FileOutput _return;
    /**
     * Gets the value of the return property.
     * 
     *     {@link FileOutput }
     *     
     */
    public FileOutput getReturn() {
        return _return;
    }
    /**
     * Sets the value of the return property.
     * 
     *     {@link FileOutput }
     *     
     */
    public void setReturn(FileOutput value) {
        this._return = value;
    }
}
