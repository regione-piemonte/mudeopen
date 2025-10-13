/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.geeco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeecoConfigurationVO {

	@JsonProperty("url")
	String configurationUrl;

	@JsonProperty("geecoSessionId")
	String geecoSessionId;

	public String getConfigurationUrl() {
		return configurationUrl;
	}

	
	public void setConfigurationUrl(String configurationUrl) {
		this.configurationUrl = configurationUrl;
	}

	public String getGeecoSessionId() {
		return geecoSessionId;
	}

	public void setGeecoSessionId(String geecoSessionId) {
		this.geecoSessionId = geecoSessionId;
	}

	
}
