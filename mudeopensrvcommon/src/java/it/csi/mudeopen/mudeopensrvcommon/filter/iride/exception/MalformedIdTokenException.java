/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.filter.iride.exception;

public class MalformedIdTokenException extends UserException
{
    public MalformedIdTokenException() {
        super("");
    }

    public MalformedIdTokenException(final String msg) {
        super(msg);
    }
}