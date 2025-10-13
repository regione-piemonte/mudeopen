/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.exception;

import java.util.HashMap;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -2631969419398877860L;

    public ValidationException(HashMap<String, String> detail) {
		super();
		this.detail = detail;
	}
	private HashMap<String, String> detail;

    public HashMap<String, String> getDetail() {
		return detail;
	}

    public void setDetail(HashMap<String, String> detail) {
		this.detail = detail;
	}
	
	

}