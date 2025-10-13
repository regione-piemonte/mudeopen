/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.ws.WebFault;
@WebFault(name = "fault3", targetNamespace = "ente")
public class OutputException_Exception extends Exception {

    private it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.OutputException faultInfo;

    public OutputException_Exception() {
        super();
    }

    public OutputException_Exception(String message) {
        super(message);
    }

    public OutputException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public OutputException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.OutputException fault3) {
        super(message);
        this.faultInfo = fault3;
    }

    public OutputException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.OutputException fault3, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = fault3;
    }

    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.OutputException getFaultInfo() {
        return this.faultInfo;
    }
}