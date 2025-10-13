/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.exception;
public class GenericException extends Exception{
	private static final long serialVersionUID = 1L;

    public GenericException(String msg) {
		super(msg);
	}

    public GenericException(Throwable arg0) {
		super(arg0);
	}
}