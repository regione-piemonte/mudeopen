/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.ws.WebFault;
@WebFault(name = "fault2", targetNamespace = "ente")
public class UnrecoverableException_Exception extends Exception {

    private it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.UnrecoverableException faultInfo;

    public UnrecoverableException_Exception() {
        super();
    }

    public UnrecoverableException_Exception(String message) {
        super(message);
    }

    public UnrecoverableException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public UnrecoverableException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.UnrecoverableException fault2) {
        super(message);
        this.faultInfo = fault2;
    }

    public UnrecoverableException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.UnrecoverableException fault2, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = fault2;
    }

    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.UnrecoverableException getFaultInfo() {
        return this.faultInfo;
    }
}