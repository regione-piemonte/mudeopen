/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.filter.iride.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The type Csi exception.
 */
public class CSIException extends Exception
{
    /**
     * The Serial version uid.
     */
    static final long serialVersionUID = 8485894515601068477L;
    /**
     * The constant NO_TRACE_AVAILABLE.
     */
    public static final String NO_TRACE_AVAILABLE = "<<no stack trace available>>";
    /**
     * The constant NO_NESTED_EXC.
     */
    public static final String NO_NESTED_EXC = "";
    private String nestedExcClassName;
    private String stackTraceMessage;
    private String nestedExcMsg;

    /**
     * Instantiates a new Csi exception.
     *
     * @param msg the msg
     */
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

    /**
     * Instantiates a new Csi exception.
     *
     * @param msg                the msg
     * @param nestedExcClassName the nested exc class name
     * @param nestedExcMsg       the nested exc msg
     */
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

    /**
     * Instantiates a new Csi exception.
     *
     * @param msg                the msg
     * @param nestedExcClassName the nested exc class name
     * @param nestedExcMsg       the nested exc msg
     * @param stackTrace         the stack trace
     */
    public CSIException(final String msg, final String nestedExcClassName, final String nestedExcMsg, final String stackTrace) {
        super(msg);
        this.nestedExcClassName = nestedExcClassName;
        this.nestedExcMsg = nestedExcMsg;
        this.stackTraceMessage = stackTrace;
    }

    /**
     * Instantiates a new Csi exception.
     *
     * @param msg       the msg
     * @param nestedExc the nested exc
     */
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

    /**
     * Gets nested exc class name.
     *
      the nested exc class name
     */
    public String getNestedExcClassName() {
        return this.nestedExcClassName;
    }

    /**
     * Sets nested exc class name.
     *
     * @param nestedExcClassName the nested exc class name
     */
    public void setNestedExcClassName(final String nestedExcClassName) {
        this.nestedExcClassName = nestedExcClassName;
    }

    /**
     * Sets stack trace message.
     *
     * @param stackTraceMessage the stack trace message
     */
    public void setStackTraceMessage(final String stackTraceMessage) {
        this.stackTraceMessage = stackTraceMessage;
    }

    /**
     * Gets stack trace message.
     *
      the stack trace message
     */
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

    /**
     * Sets nested exc msg.
     *
     * @param nestedExcMsg the nested exc msg
     */
    public void setNestedExcMsg(final String nestedExcMsg) {
        this.nestedExcMsg = nestedExcMsg;
    }

    /**
     * Gets nested exc msg.
     *
      the nested exc msg
     */
    public String getNestedExcMsg() {
        return this.nestedExcMsg;
    }
}