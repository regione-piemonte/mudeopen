/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.exception;
public class FruitoreNonAbilitatoComuneException extends Exception {
	public FruitoreNonAbilitatoComuneException(String err) {
		super(err);
	}
	
	public FruitoreNonAbilitatoComuneException(String error, Throwable t) {
		super(error, t);
	}
	
}