/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.ws.WebFault;
@WebFault(name = "fault5", targetNamespace = "ente")
public class ParInputValNonCorrettoException_Exception extends Exception {

    private it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.ParInputValNonCorrettoException faultInfo;

    public ParInputValNonCorrettoException_Exception() {
        super();
    }

    public ParInputValNonCorrettoException_Exception(String message) {
        super(message);
    }

    public ParInputValNonCorrettoException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public ParInputValNonCorrettoException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.ParInputValNonCorrettoException fault5) {
        super(message);
        this.faultInfo = fault5;
    }

    public ParInputValNonCorrettoException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.ParInputValNonCorrettoException fault5, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = fault5;
    }

    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.ParInputValNonCorrettoException getFaultInfo() {
        return this.faultInfo;
    }
}