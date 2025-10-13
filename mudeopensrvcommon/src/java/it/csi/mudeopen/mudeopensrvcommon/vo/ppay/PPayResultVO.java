/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.ppay;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PPayResultVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -5626332407976983528L;

    @JsonProperty("totale")
    private String totale;

    @JsonProperty("url")
    private String url;

    @JsonProperty("json_importi")
    private String jsonImporti;

	public String getTotale() {
		return totale;
	}
	
	public double getTotaleAsDobule() {
		return Double.parseDouble(totale.replace(',', '.'));
	}

	public void setTotale(String totale) {
		this.totale = totale;
	}

	public String getJsonImporti() {
		return jsonImporti;
	}

	public void setJsonImporti(String jsonImporti) {
		this.jsonImporti = jsonImporti;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}