/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.ws.WebFault;
@WebFault(name = "fault", targetNamespace = "ente")
public class CSIException_Exception extends Exception {

    private it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CSIException faultInfo;

    public CSIException_Exception() {
        super();
    }

    public CSIException_Exception(String message) {
        super(message);
    }

    public CSIException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public CSIException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CSIException fault) {
        super(message);
        this.faultInfo = fault;
    }

    public CSIException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CSIException fault, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = fault;
    }

    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CSIException getFaultInfo() {
        return this.faultInfo;
    }
}