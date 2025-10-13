/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.filter.iride.exception;

public class UserException extends CSIException
{
    public UserException(final String msg) {
        super(msg);
    }

    public UserException(final String msg, final String nestedClass, final String nestedMsg) {
        super(msg, nestedClass, nestedMsg);
    }

    public UserException(final String msg, final String nestedClass, final String nestedMsg, final String stackTrace) {
        super(msg, nestedClass, nestedMsg, stackTrace);
    }

    public UserException(final String msg, final Throwable nested) {
        super(msg, nested);
    }
}