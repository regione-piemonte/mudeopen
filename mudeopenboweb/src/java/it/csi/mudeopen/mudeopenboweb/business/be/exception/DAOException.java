/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.exception;
/**
 * The type Dao exception.
 */
public class DAOException extends GenericException {
	private static final long serialVersionUID = 1L;
    /**
     * Instantiates a new Dao exception.
     *
     * @param msg the msg
     */
    public DAOException(String msg) {
		super(msg);
	}
    /**
     * Instantiates a new Dao exception.
     *
     * @param arg0 the arg 0
     */
    public DAOException(Throwable arg0) {
		super(arg0);
	}
}