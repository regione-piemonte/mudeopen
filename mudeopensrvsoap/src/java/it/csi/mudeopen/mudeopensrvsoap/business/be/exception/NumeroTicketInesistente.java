/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.exception;
public class NumeroTicketInesistente extends Exception {
	public NumeroTicketInesistente(String error) {
		super(error);
	}
	
	public NumeroTicketInesistente(String error, Throwable t) {
		super(error, t);
	}
}