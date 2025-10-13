/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.response;

import java.io.Serializable;
import java.util.HashMap;

public class DefaultResponse implements MudeResponse, Serializable{

	private static final long serialVersionUID = -2517870193254779853L;

    public DefaultResponse() {
		this.status = StatusOK;
	}

    public DefaultResponse(String status, String code) {
		super();
		this.status = status;
		this.code = code;
	}
	
	private String status;
	private String code;
	private HashMap<String, String> detail;

    public String getStatus() {
		return status;
	}

    public void setStatus(String status) {
		this.status = status;
	}

    public String getCode() {
		return code;
	}

    public void setCode(String code) {
		this.code = code;
	}

    public HashMap<String, String> getDetail() {
		return detail;
	}

    public void setDetail(HashMap<String, String> detail) {
		this.detail = detail;
	}
}