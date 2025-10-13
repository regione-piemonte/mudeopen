/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.filter.iride.exception;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CSIException extends Exception
{
    static final long serialVersionUID = 8485894515601068477L;
    public static final String NO_TRACE_AVAILABLE = "<<no stack trace available>>";
    public static final String NO_NESTED_EXC = "";
    private String nestedExcClassName;
    private String stackTraceMessage;
    private String nestedExcMsg;

    public CSIException(final String msg) {
        super(msg);
        this.nestedExcClassName = "";
        this.nestedExcMsg = null;
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        this.fillInStackTrace();
        this.printStackTrace(pw);
        this.stackTraceMessage = sw.toString();
    }

    public CSIException(final String msg, final String nestedExcClassName, final String nestedExcMsg) {
        super(msg);
        this.nestedExcClassName = nestedExcClassName;
        this.nestedExcMsg = nestedExcMsg;
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        this.fillInStackTrace();
        this.printStackTrace(pw);
        this.stackTraceMessage = sw.toString();
    }

    public CSIException(final String msg, final String nestedExcClassName, final String nestedExcMsg, final String stackTrace) {
        super(msg);
        this.nestedExcClassName = nestedExcClassName;
        this.nestedExcMsg = nestedExcMsg;
        this.stackTraceMessage = stackTrace;
    }

    public CSIException(final String msg, final Throwable nestedExc) {
        super(msg);
        if (nestedExc != null) {
            this.nestedExcClassName = nestedExc.getClass().getName();
            this.nestedExcMsg = nestedExc.getMessage();
        }
        else {
            this.nestedExcClassName = "";
            this.nestedExcMsg = null;
        }
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        this.fillInStackTrace();
        this.printStackTrace(pw);
        this.stackTraceMessage = sw.toString();
    }

    public String getNestedExcClassName() {
        return this.nestedExcClassName;
    }

    public void setNestedExcClassName(final String nestedExcClassName) {
        this.nestedExcClassName = nestedExcClassName;
    }

    public void setStackTraceMessage(final String stackTraceMessage) {
        this.stackTraceMessage = stackTraceMessage;
    }

    public String getStackTraceMessage() {
        return this.stackTraceMessage;
    }

    @Override
    public String toString() {
        String s = this.getClass().getName() + ":" + this.getMessage();
        if (this.getNestedExcClassName() != null && !this.getNestedExcClassName().equals("")) {
            s = s + " (nested: " + this.getNestedExcClassName() + ", " + this.nestedExcMsg + ")";
        }
        return s;
    }

    public void setNestedExcMsg(final String nestedExcMsg) {
        this.nestedExcMsg = nestedExcMsg;
    }

    public String getNestedExcMsg() {
        return this.nestedExcMsg;
    }
}