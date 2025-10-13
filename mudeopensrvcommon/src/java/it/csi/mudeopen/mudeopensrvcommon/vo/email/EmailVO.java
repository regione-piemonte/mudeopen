/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.email;

import java.io.Serializable;

public class EmailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idNotifica;

    private String from;
    private String to;

    private String object;
    private String body;

    public EmailVO(Long idNotifica, String from, String to, String object, String body) {
    	this.idNotifica = idNotifica;
    	this.from = from;
		this.to = to;
		this.object = object;
		this.body = body;
	}

    public Long getIdNotifica() {
		return idNotifica;
	}
	public void setIdNotifica(Long idNotifica) {
		this.idNotifica = idNotifica;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}