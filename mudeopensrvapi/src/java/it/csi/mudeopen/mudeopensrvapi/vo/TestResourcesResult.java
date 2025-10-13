/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestResourcesResult {
	
    @JsonProperty("db-available")
	private boolean dbAvailable;
    
    @JsonProperty("api-available")
	private boolean apiAvailable;
    
    @JsonProperty("response-time")
	private long responseTime;

	public boolean isDbAvailable() {
		return dbAvailable;
	}

	public void setDbAvailable(boolean dbAvailable) {
		this.dbAvailable = dbAvailable;
	}

	public boolean isApiAvailable() {
		return apiAvailable;
	}

	public void setApiAvailable(boolean apiAvailable) {
		this.apiAvailable = apiAvailable;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
    
}
