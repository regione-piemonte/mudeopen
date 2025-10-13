/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
/**
 * <p>Classe Java per UserException complex type.
 * 
 * 
 * &lt;complexType name="UserException"&gt;

 *     &lt;extension base="{urn:sigalfsrvUiu}CSIException"&gt;
 *     &lt;/extension&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserException")
@XmlSeeAlso({
    AutorizzMancanteEnteException.class,
    OutputException.class,
    ParInputObblMancantiException.class,
    ParInputValNonCorrettoException.class,
    PermissionException.class
})
public class UserException
    extends CSIException
{
}
