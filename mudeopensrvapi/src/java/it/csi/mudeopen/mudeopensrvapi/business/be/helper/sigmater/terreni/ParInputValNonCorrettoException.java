/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.terreni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per ParInputValNonCorrettoException complex type.
 * 
 * 
 * &lt;complexType name="ParInputValNonCorrettoException"&gt;

 *     &lt;extension base="{urn:sigalfsrvTerreni}UserException"&gt;
 *     &lt;/extension&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParInputValNonCorrettoException")
public class ParInputValNonCorrettoException
    extends UserException
{
}
