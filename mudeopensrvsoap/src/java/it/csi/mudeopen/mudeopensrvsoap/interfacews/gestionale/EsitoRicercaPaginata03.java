/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Java class for esitoRicercaPaginata03 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;complexType name="esitoRicercaPaginata03"&gt;
 *         &lt;element name="listaCompleta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numIstanzeRestituite" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="numIstanzeTotali" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="numPagina" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="numPagineTotali" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="elencoIstanze" type="{http://gestionale.interfacews.mudesrvextsic.mude.csi.it/}istanzaEstesa03" maxOccurs="unbounded" minOccurs="0"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esitoRicercaPaginata03", propOrder = {
    "listaCompleta",
    "numIstanzeRestituite",
    "numIstanzeTotali",
    "numPagina",
    "numPagineTotali",
    "elencoIstanze"
})
public class EsitoRicercaPaginata03 {
    @XmlElement(required = true)
    protected String listaCompleta;
    protected int numIstanzeRestituite;
    protected int numIstanzeTotali;
    protected int numPagina;
    protected int numPagineTotali;
    @XmlElement(nillable = true)
    protected List<IstanzaEstesa03> elencoIstanze;
    /**
     * Gets the value of the listaCompleta property.
     * 
     *     
     */
    public String getListaCompleta() {
        return listaCompleta;
    }
    /**
     * Sets the value of the listaCompleta property.
     * 
     *     
     */
    public void setListaCompleta(String value) {
        this.listaCompleta = value;
    }
    /**
     * Gets the value of the numIstanzeRestituite property.
     * 
     */
    public int getNumIstanzeRestituite() {
        return numIstanzeRestituite;
    }
    /**
     * Sets the value of the numIstanzeRestituite property.
     * 
     */
    public void setNumIstanzeRestituite(int value) {
        this.numIstanzeRestituite = value;
    }
    /**
     * Gets the value of the numIstanzeTotali property.
     * 
     */
    public int getNumIstanzeTotali() {
        return numIstanzeTotali;
    }
    /**
     * Sets the value of the numIstanzeTotali property.
     * 
     */
    public void setNumIstanzeTotali(int value) {
        this.numIstanzeTotali = value;
    }
    /**
     * Gets the value of the numPagina property.
     * 
     */
    public int getNumPagina() {
        return numPagina;
    }
    /**
     * Sets the value of the numPagina property.
     * 
     */
    public void setNumPagina(int value) {
        this.numPagina = value;
    }
    /**
     * Gets the value of the numPagineTotali property.
     * 
     */
    public int getNumPagineTotali() {
        return numPagineTotali;
    }
    /**
     * Sets the value of the numPagineTotali property.
     * 
     */
    public void setNumPagineTotali(int value) {
        this.numPagineTotali = value;
    }
    /**
     * Gets the value of the elencoIstanze property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elencoIstanze property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     *    getElencoIstanze().add(newItem);
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IstanzaEstesa03 }
     * 
     * 
     */
    public List<IstanzaEstesa03> getElencoIstanze() {
        if (elencoIstanze == null) {
            elencoIstanze = new ArrayList<IstanzaEstesa03>();
        }
        return this.elencoIstanze;
    }
}
