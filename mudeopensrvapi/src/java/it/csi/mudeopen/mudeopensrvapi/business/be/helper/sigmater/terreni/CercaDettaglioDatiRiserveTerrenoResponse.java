/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per anonymous complex type.
 * 
 * 
 * &lt;complexType&gt;
 *         &lt;element name="cercaDettaglioDatiRiserveTerrenoReturn" type="{urn:sigalfsrvTerreni}DettaglioDatiRiservaTerreno" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaDettaglioDatiRiserveTerrenoReturn"
})
@XmlRootElement(name = "cercaDettaglioDatiRiserveTerrenoResponse")
public class CercaDettaglioDatiRiserveTerrenoResponse {

    protected List<DettaglioDatiRiservaTerreno> cercaDettaglioDatiRiserveTerrenoReturn;

    /**
     * Gets the value of the cercaDettaglioDatiRiserveTerrenoReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cercaDettaglioDatiRiserveTerrenoReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getCercaDettaglioDatiRiserveTerrenoReturn().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DettaglioDatiRiservaTerreno }
     * 
     * 
     */
    public List<DettaglioDatiRiservaTerreno> getCercaDettaglioDatiRiserveTerrenoReturn() {
        if (cercaDettaglioDatiRiserveTerrenoReturn == null) {
            cercaDettaglioDatiRiserveTerrenoReturn = new ArrayList<DettaglioDatiRiservaTerreno>();
        }
        return this.cercaDettaglioDatiRiserveTerrenoReturn;
    }

}
