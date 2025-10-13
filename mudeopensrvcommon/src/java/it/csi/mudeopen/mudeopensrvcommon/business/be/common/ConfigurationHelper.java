/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopensrvcommon.business.be.common;

/**
 * The type Configuration helper.
 */
public class ConfigurationHelper {

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
     * @return the hostname
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
     * @return the endpoint base
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

    /**
     * Instantiates a new Configuration helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public ConfigurationHelper(String hostname, String endpointBase) {
		this.hostname = hostname;
		this.endpointBase = hostname + endpointBase;
	}
}