/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopensrvapi.business.be.helper;

import org.apache.log4j.Logger;

/**
 * The type Abstract service helper.
 */
public abstract class AbstractServiceHelper {

    /**
     * The constant CSILOGGER.
     */
    public static final Logger LOGGER = Logger.getLogger(AbstractServiceHelper.class.getCanonicalName());

    /**
     * The Hostname.
     */
    protected String hostname = null;
    /**
     * The Endpoint base.
     */
    protected String endpointBase = null;

    /**
     * Gets hostname.
     *
      the hostname
     */
    public String getHostname() {
		return hostname;
	}

    /**
     * Sets hostname.
     *
     * @param hostname the hostname
     */
    public void setHostname(String hostname) {
		this.hostname = hostname;
	}

    /**
     * Gets endpoint base.
     *
      the endpoint base
     */
    public String getEndpointBase() {
		return endpointBase;
	}

    /**
     * Sets endpoint base.
     *
     * @param endpointBase the endpoint base
     */
    public void setEndpointBase(String endpointBase) {
		this.endpointBase = endpointBase;
	}

}