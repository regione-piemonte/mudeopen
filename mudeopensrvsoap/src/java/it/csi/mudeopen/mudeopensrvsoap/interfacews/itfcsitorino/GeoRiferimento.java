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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
/**
 * <p>Java class for geoRiferimento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="geoRiferimento"&gt;
 *         &lt;element name="idLivelloPoligono" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="descLivelloPoligono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="servizioFonte" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="servizioLivello" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="dataGeoriferimento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="codIstatComune" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="geoCellulas" type="{http://itfcsitorino.interfacews.mudesrvextsic.mude.csi.it/}geoCellula" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="geoCatastos" type="{http://itfcsitorino.interfacews.mudesrvextsic.mude.csi.it/}geoCatasto" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="geoDatocarotas" type="{http://itfcsitorino.interfacews.mudesrvextsic.mude.csi.it/}geoDatocarota" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="geoUbicaziones" type="{http://itfcsitorino.interfacews.mudesrvextsic.mude.csi.it/}geoUbicazione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="csiGeometry" type="{http://itfcsitorino.interfacews.mudesrvextsic.mude.csi.it/}cSIGeometry" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geoRiferimento", propOrder = {
    "idLivelloPoligono",
    "descLivelloPoligono",
    "servizioFonte",
    "servizioLivello",
    "dataGeoriferimento",
    "codIstatComune",
    "geoCellulas",
    "geoCatastos",
    "geoDatocarotas",
    "geoUbicaziones",
    "csiGeometry"
})
public class GeoRiferimento {
    protected int idLivelloPoligono;
    @XmlElement(required = true)
    protected String descLivelloPoligono;
    protected int servizioFonte;
    protected int servizioLivello;
    @XmlSchemaType(name = "dateTime")
    protected String dataGeoriferimento;
    @XmlElement(required = true)
    protected String codIstatComune;
    @XmlElement(nillable = true)
    protected List<GeoCellula> geoCellulas;
    @XmlElement(nillable = true)
    protected List<GeoCatasto> geoCatastos;
    @XmlElement(nillable = true)
    protected List<GeoDatocarota> geoDatocarotas;
    @XmlElement(nillable = true)
    protected List<GeoUbicazione> geoUbicaziones;
    @XmlElement(name = "CsiGeometry", required = true, nillable = false)
    protected String csiGeometry;
    /**
     * Gets the value of the idLivelloPoligono property.
     * 
     */
    public int getIdLivelloPoligono() {
        return idLivelloPoligono;
    }
    /**
     * Sets the value of the idLivelloPoligono property.
     * 
     */
    public void setIdLivelloPoligono(int value) {
        this.idLivelloPoligono = value;
    }
    /**
     * Gets the value of the descLivelloPoligono property.
     * 
     *     
     */
    public String getDescLivelloPoligono() {
        return descLivelloPoligono;
    }
    /**
     * Sets the value of the descLivelloPoligono property.
     * 
     *     
     */
    public void setDescLivelloPoligono(String value) {
        this.descLivelloPoligono = value;
    }
    /**
     * Gets the value of the servizioFonte property.
     * 
     */
    public int getServizioFonte() {
        return servizioFonte;
    }
    /**
     * Sets the value of the servizioFonte property.
     * 
     */
    public void setServizioFonte(int value) {
        this.servizioFonte = value;
    }
    /**
     * Gets the value of the servizioLivello property.
     * 
     */
    public int getServizioLivello() {
        return servizioLivello;
    }
    /**
     * Sets the value of the servizioLivello property.
     * 
     */
    public void setServizioLivello(int value) {
        this.servizioLivello = value;
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
     * Gets the value of the codIstatComune property.
     * 
     *     
     */
    public String getCodIstatComune() {
        return codIstatComune;
    }
    /**
     * Sets the value of the codIstatComune property.
     * 
     *     
     */
    public void setCodIstatComune(String value) {
        this.codIstatComune = value;
    }
    /**
     * Gets the value of the geoCellulas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoCellulas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getGeoCellulas().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoCellula }
     * 
     * 
     */
    public List<GeoCellula> getGeoCellulas() {
        if (geoCellulas == null) {
            geoCellulas = new ArrayList<GeoCellula>();
        }
        return this.geoCellulas;
    }
    public void setGeoCellulas(List<GeoCellula> value) {
        this.geoCellulas = value;
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
    public void setGeoCatastos(List<GeoCatasto> value) {
        this.geoCatastos = value;
    }
    /**
     * Gets the value of the geoDatocarotas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the geoDatocarotas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getGeoDatocarotas().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GeoDatocarota }
     * 
     * 
     */
    public List<GeoDatocarota> getGeoDatocarotas() {
        if (geoDatocarotas == null) {
            geoDatocarotas = new ArrayList<GeoDatocarota>();
        }
        return this.geoDatocarotas;
    }
    public void setGeoDatocarotas(List<GeoDatocarota> value) {
        this.geoDatocarotas = value;
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
    public void setGeoUbicaziones(List<GeoUbicazione> value) {
        this.geoUbicaziones = value;
    }
    /**
     * Gets the value of the csiGeometry property.
     * 
     *     {@link CsiGeometry }
     *     
     */
    public String getCsiGeometry() {
        return csiGeometry;
    }
    /**
     * Sets the value of the csiGeometry property.
     * 
     *     {@link CsiGeometry }
     *     
     */
    public void setCsiGeometry(String value) {
        this.csiGeometry = value;
    }
}
