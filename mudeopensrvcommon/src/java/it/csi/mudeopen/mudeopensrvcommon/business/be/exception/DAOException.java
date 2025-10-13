/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.exception;
public class DAOException extends GenericException {
	private static final long serialVersionUID = 1L;

    public DAOException(String msg) {
		super(msg);
	}

    public DAOException(Throwable arg0) {
		super(arg0);
	}
}