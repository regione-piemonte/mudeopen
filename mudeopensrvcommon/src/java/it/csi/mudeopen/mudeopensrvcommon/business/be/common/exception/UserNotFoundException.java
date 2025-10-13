/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2631969419398877860L;

    public UserNotFoundException(String mesasge) {
		super(mesasge);
	}

}