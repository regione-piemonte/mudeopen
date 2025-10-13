/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.ws.WebFault;
@WebFault(name = "fault1", targetNamespace = "ente")
public class SystemException_Exception extends Exception {

    private it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.SystemException faultInfo;

    public SystemException_Exception() {
        super();
    }

    public SystemException_Exception(String message) {
        super(message);
    }

    public SystemException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public SystemException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.SystemException fault1) {
        super(message);
        this.faultInfo = fault1;
    }

    public SystemException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.SystemException fault1, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = fault1;
    }

    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.SystemException getFaultInfo() {
        return this.faultInfo;
    }
}