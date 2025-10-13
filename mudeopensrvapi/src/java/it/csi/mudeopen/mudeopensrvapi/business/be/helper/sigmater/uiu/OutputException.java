/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per OutputException complex type.
 * 
 * 
 * &lt;complexType name="OutputException"&gt;

 *     &lt;extension base="{urn:sigalfsrvUiu}UserException"&gt;
 *     &lt;/extension&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputException")
public class OutputException
    extends UserException
{
}
