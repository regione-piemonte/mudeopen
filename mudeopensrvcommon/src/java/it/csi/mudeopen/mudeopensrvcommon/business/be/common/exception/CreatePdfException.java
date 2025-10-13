/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception;

public class CreatePdfException extends RuntimeException{

	private static final long serialVersionUID = 3856149867881382747L;

    public CreatePdfException(String message) {
		super(message);
	}
	
}