/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for geoFabbricato complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="geoFabbricato"&gt;
 *         &lt;element name="chiaveCarotaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codFabbricato" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="geoUbicaziones" type="{http://itfcsitorino.interfacews.mudesrvextsic.mude.csi.it/}geoUbicazione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="geoCatastos" type="{http://itfcsitorino.interfacews.mudesrvextsic.mude.csi.it/}geoCatasto" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geoFabbricato", propOrder = {
    "chiaveCarotaggio",
    "codFabbricato",
    "geoUbicaziones",
    "geoCatastos"
})
public class GeoFabbricato {
    protected String chiaveCarotaggio;
    protected Integer codFabbricato;
    @XmlElement(nillable = true)
    protected List<GeoUbicazione> geoUbicaziones;
    @XmlElement(nillable = true)
    protected List<GeoCatasto> geoCatastos;
    /**
     * Gets the value of the chiaveCarotaggio property.
     * 
     *     
     */
    public String getChiaveCarotaggio() {
        return chiaveCarotaggio;
    }
    /**
     * Sets the value of the chiaveCarotaggio property.
     * 
     *     
     */
    public void setChiaveCarotaggio(String value) {
        this.chiaveCarotaggio = value;
    }
    /**
     * Gets the value of the codFabbricato property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getCodFabbricato() {
        return codFabbricato;
    }
    /**
     * Sets the value of the codFabbricato property.
     * 
     *     {@link Integer }
     *     
     */
    public void setCodFabbricato(Integer value) {
        this.codFabbricato = value;
    }
    /**
     * Gets the value of the geoUbicaziones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoUbicaziones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getGeoUbicaziones().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoUbicazione }
     * 
     * 
     */
    public List<GeoUbicazione> getGeoUbicaziones() {
        if (geoUbicaziones == null) {
            geoUbicaziones = new ArrayList<GeoUbicazione>();
        }
        return this.geoUbicaziones;
    }
    /**
     * Gets the value of the geoCatastos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoCatastos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getGeoCatastos().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoCatasto }
     * 
     * 
     */
    public List<GeoCatasto> getGeoCatastos() {
        if (geoCatastos == null) {
            geoCatastos = new ArrayList<GeoCatasto>();
        }
        return this.geoCatastos;
    }
}
