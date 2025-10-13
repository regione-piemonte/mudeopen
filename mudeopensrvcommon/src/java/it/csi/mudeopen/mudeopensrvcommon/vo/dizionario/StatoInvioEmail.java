/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import java.util.Arrays;

public enum StatoInvioEmail {
    TO_SEND("TO_SEND"),
    SENDING("SENDING"),
    SENT("SENT"),
    FAILED("FAILED"),
    IN_RETRY("IN_RETRY");
    String value;

    StatoInvioEmail(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StatoInvioEmail fromString(String code) {
        return Arrays.stream(values()).filter(s -> s.value.equals(code)).findFirst().orElse(null);
    }

    public boolean equals(String to) {
    	return value.equalsIgnoreCase(to);
    }

    public String toName() {
    	return toString().replaceAll("_", " ");
    }

    public static String toString(String from) {
    	StatoInvioEmail _fromString = StatoInvioEmail.fromString(from);
    	return _fromString.toName();
    }
}