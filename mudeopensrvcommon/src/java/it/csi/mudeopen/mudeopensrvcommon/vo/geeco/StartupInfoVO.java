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
public class StartupInfoVO {

	@JsonProperty("readonly")
	private boolean readonly;
	@JsonProperty("zoomEnabled")
	private boolean zoomEnabled;
	@JsonProperty("showInputFeatures")
	private boolean showInputFeatures;
	@JsonProperty("showLabelOnFeatures")
	private boolean showLabelOnFeatures;
	@JsonProperty("https")
	private boolean https;
	
	//########################################
	public boolean isReadonly() {
		return readonly;
	}
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}
	public boolean isZoomEnabled() {
		return zoomEnabled;
	}
	public void setZoomEnabled(boolean zoomEnabled) {
		this.zoomEnabled = zoomEnabled;
	}
	public boolean isShowInputFeatures() {
		return showInputFeatures;
	}
	public void setShowInputFeatures(boolean showInputFeatures) {
		this.showInputFeatures = showInputFeatures;
	}
	public boolean isShowLabelOnFeatures() {
		return showLabelOnFeatures;
	}
	public void setShowLabelOnFeatures(boolean showLabelOnFeatures) {
		this.showLabelOnFeatures = showLabelOnFeatures;
	}
	public boolean isHttps() {
		return https;
	}
	public void setHttps(boolean https) {
		this.https = https;
	}
	
	
}