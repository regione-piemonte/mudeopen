/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.exception;
public class NotAuthorizedException extends Exception {
	public NotAuthorizedException(String error) {
		super(error);
	}
	
	public NotAuthorizedException(String error, Throwable t) {
		super(error, t);
	}
}