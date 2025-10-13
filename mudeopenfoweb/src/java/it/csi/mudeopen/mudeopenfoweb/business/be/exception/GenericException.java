/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business.be.exception;
/**
 * The type Generic exception.
 */
public class GenericException extends Exception{
	private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Generic exception.
     *
     * @param msg the msg
     */
    public GenericException(String msg) {
		super(msg);
	}

    /**
     * Instantiates a new Generic exception.
     *
     * @param arg0 the arg 0
     */
    public GenericException(Throwable arg0) {
		super(arg0);
	}
}