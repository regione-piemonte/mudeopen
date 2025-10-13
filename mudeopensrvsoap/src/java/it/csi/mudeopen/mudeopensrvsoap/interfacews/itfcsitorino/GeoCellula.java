/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
/**
 * <p>Java class for geoCellula complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="geoCellula"&gt;
 *         &lt;element name="chiaveCarotaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codCellula" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="dataGeoriferimento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="geoFabbricatos" type="{http://itfcsitorino.interfacews.mudesrvextsic.mude.csi.it/}geoFabbricato" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geoCellula", propOrder = {
    "chiaveCarotaggio",
    "codCellula",
    "dataGeoriferimento",
    "geoFabbricatos"
})
public class GeoCellula {
    protected String chiaveCarotaggio;
    protected Integer codCellula;
    @XmlSchemaType(name = "dateTime")
    protected String dataGeoriferimento;
    @XmlElement(nillable = true)
    protected List<GeoFabbricato> geoFabbricatos;
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
     * Gets the value of the codCellula property.
     * 
     *     {@link Integer }
     *     
     */
    public Integer getCodCellula() {
        return codCellula;
    }
    /**
     * Sets the value of the codCellula property.
     * 
     *     {@link Integer }
     *     
     */
    public void setCodCellula(Integer value) {
        this.codCellula = value;
    }
    /**
     * Gets the value of the dataGeoriferimento property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public String getDataGeoriferimento() {
        return dataGeoriferimento;
    }
    /**
     * Sets the value of the dataGeoriferimento property.
     * 
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataGeoriferimento(String value) {
        this.dataGeoriferimento = value;
    }
    /**
     * Gets the value of the geoFabbricatos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoFabbricatos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getGeoFabbricatos().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoFabbricato }
     * 
     * 
     */
    public List<GeoFabbricato> getGeoFabbricatos() {
        if (geoFabbricatos == null) {
            geoFabbricatos = new ArrayList<GeoFabbricato>();
        }
        return this.geoFabbricatos;
    }
}
