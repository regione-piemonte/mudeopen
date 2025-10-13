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
 *         &lt;element name="cercaDettaglioDatiDeduzioniPorzioneReturn" type="{urn:sigalfsrvTerreni}DettaglioDatiDeduzione" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cercaDettaglioDatiDeduzioniPorzioneReturn"
})
@XmlRootElement(name = "cercaDettaglioDatiDeduzioniPorzioneResponse")
public class CercaDettaglioDatiDeduzioniPorzioneResponse {

    protected List<DettaglioDatiDeduzione> cercaDettaglioDatiDeduzioniPorzioneReturn;

    /**
     * Gets the value of the cercaDettaglioDatiDeduzioniPorzioneReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cercaDettaglioDatiDeduzioniPorzioneReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     *    getCercaDettaglioDatiDeduzioniPorzioneReturn().add(newItem);

     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DettaglioDatiDeduzione }
     * 
     * 
     */
    public List<DettaglioDatiDeduzione> getCercaDettaglioDatiDeduzioniPorzioneReturn() {
        if (cercaDettaglioDatiDeduzioniPorzioneReturn == null) {
            cercaDettaglioDatiDeduzioniPorzioneReturn = new ArrayList<DettaglioDatiDeduzione>();
        }
        return this.cercaDettaglioDatiDeduzioniPorzioneReturn;
    }

}
