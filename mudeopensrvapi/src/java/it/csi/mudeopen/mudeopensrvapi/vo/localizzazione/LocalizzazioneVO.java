/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.localizzazione;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalizzazioneVO {

	@JsonProperty("geecosessionid")
	String geecoSessionId;
	@JsonProperty("belfiore")
	String codiceBelfiore;
	public String getGeecoSessionId() {
		return geecoSessionId;
	}
	public void setGeecoSessionId(String geecoSessionId) {
		this.geecoSessionId = geecoSessionId;
	}
	public String getCodiceBelfiore() {
		return codiceBelfiore;
	}
	public void setCodiceBelfiore(String codiceBelfiore) {
		this.codiceBelfiore = codiceBelfiore;
	}
		
}
