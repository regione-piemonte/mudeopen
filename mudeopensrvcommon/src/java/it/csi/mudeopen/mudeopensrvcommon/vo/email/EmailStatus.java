/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.mudeopen.mudeopensrvcommon.vo.email;

public enum EmailStatus {
	TO_SEND("TO_SEND"),
	SENDING("SENDING"),
	IN_RETRY("IN_RETRY"),
	SENT("SENT"),
	FAILED("FAILED");
	
	private final String status;

	EmailStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
