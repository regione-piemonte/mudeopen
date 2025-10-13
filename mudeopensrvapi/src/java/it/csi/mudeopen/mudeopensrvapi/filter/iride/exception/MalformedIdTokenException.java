/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.filter.iride.exception;

/**
 * The type Malformed id token exception.
 */
public class MalformedIdTokenException extends UserException
{
    /**
     * Instantiates a new Malformed id token exception.
     */
    public MalformedIdTokenException() {
        super("");
    }

    /**
     * Instantiates a new Malformed id token exception.
     *
     * @param msg the msg
     */
    public MalformedIdTokenException(final String msg) {
        super(msg);
    }
}