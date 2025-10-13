/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.exception;
public class MudeWSException extends Exception {
	public MudeWSException() {
		super();
	}
	
	public MudeWSException(String error) {
		super(stripMessage(error));
	}
	
	public MudeWSException(String error, Throwable t) {
		super(stripMessage(error), t);
	}
	
	static private String stripMessage(String error) {
		if(error != null && error.startsWith("[") && error.indexOf("]") > -1)
			return error.substring(error.indexOf("]") + 1).trim();
		return error;
	}
}