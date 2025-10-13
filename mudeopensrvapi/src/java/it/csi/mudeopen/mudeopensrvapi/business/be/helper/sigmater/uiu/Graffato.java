/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per Graffato complex type.
 * 
 * 
 * &lt;complexType name="Graffato"&gt;
 *         &lt;element name="identificativiCatastali" type="{urn:sigalfsrvUiu}ArrayOfIdentificativoCatastaleFabbricato"/&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Graffato", propOrder = {
    "identificativiCatastali"
})
public class Graffato {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfIdentificativoCatastaleFabbricato identificativiCatastali;

    /**
     * Recupera il valore della proprieta identificativiCatastali.
     * 
     *     {@link ArrayOfIdentificativoCatastaleFabbricato }
     *     
     */
    public ArrayOfIdentificativoCatastaleFabbricato getIdentificativiCatastali() {
        return identificativiCatastali;
    }

    /**
     * Imposta il valore della proprieta identificativiCatastali.
     * 
     *     {@link ArrayOfIdentificativoCatastaleFabbricato }
     *     
     */
    public void setIdentificativiCatastali(ArrayOfIdentificativoCatastaleFabbricato value) {
        this.identificativiCatastali = value;
    }

}
