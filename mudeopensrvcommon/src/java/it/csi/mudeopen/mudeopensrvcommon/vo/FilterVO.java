/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashMap;

public class FilterVO implements Serializable {

    @JsonIgnore
	private static final long serialVersionUID = 7112515901288409629L;

    HashMap<String, HashMap<String, String>> filter;

    public HashMap<String, HashMap<String, String>> getFilter() {
		return filter;
	}

    public void setFilter(HashMap<String, HashMap<String, String>> filter) {
		this.filter = filter;
	}
	
	
	
}