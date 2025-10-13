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
 *         &lt;element name="cercaElencoIndirizziPerIdentificativoCatastaleReturn" type="{urn:sigalfsrvUiu}Indirizzo" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaElencoIndirizziPerIdentificativoCatastaleReturn"
})
@XmlRootElement(name = "cercaElencoIndirizziPerIdentificativoCatastaleResponse")
public class CercaElencoIndirizziPerIdentificativoCatastaleResponse {

    protected List<Indirizzo> cercaElencoIndirizziPerIdentificativoCatastaleReturn;

    /**
     * Gets the value of the cercaElencoIndirizziPerIdentificativoCatastaleReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cercaElencoIndirizziPerIdentificativoCatastaleReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getCercaElencoIndirizziPerIdentificativoCatastaleReturn().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Indirizzo }
     * 
     * 
     */
    public List<Indirizzo> getCercaElencoIndirizziPerIdentificativoCatastaleReturn() {
        if (cercaElencoIndirizziPerIdentificativoCatastaleReturn == null) {
            cercaElencoIndirizziPerIdentificativoCatastaleReturn = new ArrayList<Indirizzo>();
        }
        return this.cercaElencoIndirizziPerIdentificativoCatastaleReturn;
    }

}
