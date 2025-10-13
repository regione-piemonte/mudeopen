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
 *         &lt;element name="cercaElencoSubalterniTerreniConBelfioreReturn" type="{urn:sigalfsrvTerreni}IdentificativoCatastaleTerreno" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaElencoSubalterniTerreniConBelfioreReturn"
})
@XmlRootElement(name = "cercaElencoSubalterniTerreniConBelfioreResponse")
public class CercaElencoSubalterniTerreniConBelfioreResponse {

    protected List<IdentificativoCatastaleTerreno> cercaElencoSubalterniTerreniConBelfioreReturn;

    /**
     * Gets the value of the cercaElencoSubalterniTerreniConBelfioreReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cercaElencoSubalterniTerreniConBelfioreReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getCercaElencoSubalterniTerreniConBelfioreReturn().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdentificativoCatastaleTerreno }
     * 
     * 
     */
    public List<IdentificativoCatastaleTerreno> getCercaElencoSubalterniTerreniConBelfioreReturn() {
        if (cercaElencoSubalterniTerreniConBelfioreReturn == null) {
            cercaElencoSubalterniTerreniConBelfioreReturn = new ArrayList<IdentificativoCatastaleTerreno>();
        }
        return this.cercaElencoSubalterniTerreniConBelfioreReturn;
    }

}
