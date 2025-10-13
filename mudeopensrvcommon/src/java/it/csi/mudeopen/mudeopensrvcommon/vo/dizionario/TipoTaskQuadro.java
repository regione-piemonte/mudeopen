/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import java.util.Arrays;

public enum TipoTaskQuadro {
	CAMBIO_STATO("CAMBIO_STATO"),
	CAMBIO_STATO_APA("CAMBIO_STATO_APA"),
	NOTIF_ISTANZA("NOTIF_ISTANZA"),
	NOTIF_ISTANZA_APA("NOTIF_ISTANZA_APA");
    String value;

    TipoTaskQuadro(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TipoTaskQuadro fromString(String code) {
        return Arrays.stream(values()).filter(s -> s.value.equals(code)).findFirst().orElse(null);
    }

    public boolean equals(String to) {
    	return value.equalsIgnoreCase(to);
    }

    public String toName() {
    	return toString().replaceAll("_", " ");
    }

    public static String toString(String from) {
    	TipoTaskQuadro _fromString = TipoTaskQuadro.fromString(from);
    	return _fromString.toName();
    }
}