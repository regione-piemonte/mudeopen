/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per PermissionException complex type.
 * 
 * 
 * &lt;complexType name="PermissionException"&gt;

 *     &lt;extension base="{urn:sigalfsrvUiu}UserException"&gt;
 *     &lt;/extension&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PermissionException")
public class PermissionException
    extends UserException
{
}
