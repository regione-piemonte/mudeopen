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
public class GeecoVO {
	
	@JsonProperty("ApiInfo")
	private ApiInfoVO apiInfo;
	
	@JsonProperty("StartupInfo")
	private StartupInfoVO startupInfo;

	@JsonProperty("QuitInfo")
	private QuitInfoVO quitInfoVO;

	
	public ApiInfoVO getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(ApiInfoVO apiInfo) {
		this.apiInfo = apiInfo;
	}

	public StartupInfoVO getStartupInfo() {
		return startupInfo;
	}

	public void setStartupInfo(StartupInfoVO startupInfo) {
		this.startupInfo = startupInfo;
	}

	public QuitInfoVO getQuitInfoVO() {
		return quitInfoVO;
	}

	public void setQuitInfoVO(QuitInfoVO quitInfoVO) {
		this.quitInfoVO = quitInfoVO;
	}

	
	
}
