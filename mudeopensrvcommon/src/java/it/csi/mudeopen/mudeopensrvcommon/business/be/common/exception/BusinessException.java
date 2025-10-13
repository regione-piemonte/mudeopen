/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -2631969419398877860L;

    private String message;
    private Exception exception;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BusinessException(String message) {
        super();
        this.message = message;
    }

    public BusinessException(String message, Exception exception) {
        super();
        this.message = message;
        this.exception = exception;
    }

    public BusinessException(String message, String httpStatusCode, String severity, Object details) {
        super();
        this.message = message;
    }

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}