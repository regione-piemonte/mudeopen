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
 *         &lt;element name="cercaElencoSubalterniGraffatiConIstatESezioneReturn" type="{urn:sigalfsrvUiu}Graffato" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaElencoSubalterniGraffatiConIstatESezioneReturn"
})
@XmlRootElement(name = "cercaElencoSubalterniGraffatiConIstatESezioneResponse")
public class CercaElencoSubalterniGraffatiConIstatESezioneResponse {

    protected List<Graffato> cercaElencoSubalterniGraffatiConIstatESezioneReturn;

    /**
     * Gets the value of the cercaElencoSubalterniGraffatiConIstatESezioneReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cercaElencoSubalterniGraffatiConIstatESezioneReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getCercaElencoSubalterniGraffatiConIstatESezioneReturn().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Graffato }
     * 
     * 
     */
    public List<Graffato> getCercaElencoSubalterniGraffatiConIstatESezioneReturn() {
        if (cercaElencoSubalterniGraffatiConIstatESezioneReturn == null) {
            cercaElencoSubalterniGraffatiConIstatESezioneReturn = new ArrayList<Graffato>();
        }
        return this.cercaElencoSubalterniGraffatiConIstatESezioneReturn;
    }

}
