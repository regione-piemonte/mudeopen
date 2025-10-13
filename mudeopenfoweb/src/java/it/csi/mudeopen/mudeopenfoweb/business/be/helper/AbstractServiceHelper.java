/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenfoweb.business.be.helper;
import org.apache.log4j.Logger;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
public abstract class AbstractServiceHelper {
    public static final Logger logger = Logger.getLogger(Constants.COMPONENT_NAME + ".service");
    protected String hostname = null;
    protected String endpointBase = null;
    public String getHostname() {
		return hostname;
	}

    public void setHostname(String hostname) {
		this.hostname = hostname;
	}

    public String getEndpointBase() {
		return endpointBase;
	}

    public void setEndpointBase(String endpointBase) {
		this.endpointBase = endpointBase;
	}
}