/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for inserisciNotifica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="inserisciNotifica"&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notificaDaInserire" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}inserimentoNotifica" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inserisciNotifica", propOrder = {
    "token",
    "notificaDaInserire"
})
public class InserisciNotifica {
    protected String token;
    protected InserimentoNotifica notificaDaInserire;
    /**
     * Gets the value of the token property.
     * 
     *     
     */
    public String getToken() {
        return token;
    }
    /**
     * Sets the value of the token property.
     * 
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }
    /**
     * Gets the value of the notificaDaInserire property.
     * 
     *     {@link InserimentoNotifica }
     *     
     */
    public InserimentoNotifica getNotificaDaInserire() {
        return notificaDaInserire;
    }
    /**
     * Sets the value of the notificaDaInserire property.
     * 
     *     {@link InserimentoNotifica }
     *     
     */
    public void setNotificaDaInserire(InserimentoNotifica value) {
        this.notificaDaInserire = value;
    }
}
