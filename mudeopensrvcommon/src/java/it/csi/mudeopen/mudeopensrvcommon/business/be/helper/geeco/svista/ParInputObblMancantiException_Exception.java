/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.ws.WebFault;
@WebFault(name = "fault4", targetNamespace = "ente")
public class ParInputObblMancantiException_Exception extends Exception {

    private it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.ParInputObblMancantiException faultInfo;

    public ParInputObblMancantiException_Exception() {
        super();
    }

    public ParInputObblMancantiException_Exception(String message) {
        super(message);
    }

    public ParInputObblMancantiException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public ParInputObblMancantiException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.ParInputObblMancantiException fault4) {
        super(message);
        this.faultInfo = fault4;
    }

    public ParInputObblMancantiException_Exception(String message, it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.ParInputObblMancantiException fault4, java.lang.Throwable cause) {
        super(message, cause);
        this.faultInfo = fault4;
    }

    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.ParInputObblMancantiException getFaultInfo() {
        return this.faultInfo;
    }
}