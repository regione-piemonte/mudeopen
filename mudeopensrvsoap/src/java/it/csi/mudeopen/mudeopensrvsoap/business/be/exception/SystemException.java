/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.exception;
public class SystemException extends Exception {
	public SystemException(String error) {
		super(error);
	}
	
	public SystemException(String error, Throwable t) {
		super(error, t);
	}
}