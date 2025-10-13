/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.common;

import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;

public class MessageVO {

	private static final long serialVersionUID = -2973985886556102874L;
	
	private String message;
	private String type;

    public MessageVO(String message, String type) {
		super();
		this.message = message;
		this.type = type;
	}

    public String getMessage() {
		return message;
	}

    public void setMessage(String message) {
		this.message = message;
	}

    public String getType() {
		return type;
	}

    public void setType(String type) {
		this.type = type;
	}
	
	
	@Override
	public String toString() {
		return "MessageVO [message=" + message + ", type=" + type + "]";
	}

}