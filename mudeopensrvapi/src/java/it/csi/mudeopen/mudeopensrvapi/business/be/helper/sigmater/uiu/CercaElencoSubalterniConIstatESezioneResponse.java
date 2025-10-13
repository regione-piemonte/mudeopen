/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu;

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
 *         &lt;element name="cercaElencoSubalterniConIstatESezioneReturn" type="{urn:sigalfsrvUiu}IdentificativoCatastaleFabbricato" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaElencoSubalterniConIstatESezioneReturn"
})
@XmlRootElement(name = "cercaElencoSubalterniConIstatESezioneResponse")
public class CercaElencoSubalterniConIstatESezioneResponse {

    protected List<IdentificativoCatastaleFabbricato> cercaElencoSubalterniConIstatESezioneReturn;

    /**
     * Gets the value of the cercaElencoSubalterniConIstatESezioneReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cercaElencoSubalterniConIstatESezioneReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getCercaElencoSubalterniConIstatESezioneReturn().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdentificativoCatastaleFabbricato }
     * 
     * 
     */
    public List<IdentificativoCatastaleFabbricato> getCercaElencoSubalterniConIstatESezioneReturn() {
        if (cercaElencoSubalterniConIstatESezioneReturn == null) {
            cercaElencoSubalterniConIstatESezioneReturn = new ArrayList<IdentificativoCatastaleFabbricato>();
        }
        return this.cercaElencoSubalterniConIstatESezioneReturn;
    }

}
