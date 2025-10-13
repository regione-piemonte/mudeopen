/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for elencoGeoRiferimentoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="elencoGeoRiferimentoResponse"&gt;
 *         &lt;element name="result" type="{http://itfcsitorino.interfacews.mudesrvextsic.mude.csi.it/}geoRiferimento" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "elencoGeoRiferimentoResponse", propOrder = {
    "result"
})
public class ElencoGeoRiferimentoResponse {
    protected List<GeoRiferimento> result;
    /**
     * Gets the value of the result property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the result property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getResult().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoRiferimento }
     * 
     * 
     */
    public List<GeoRiferimento> getResult() {
        if (result == null) {
            result = new ArrayList<GeoRiferimento>();
        }
        return this.result;
    }
}
