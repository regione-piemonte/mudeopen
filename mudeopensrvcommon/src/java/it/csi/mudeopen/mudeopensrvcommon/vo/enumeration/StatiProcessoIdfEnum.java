/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.enumeration;

import java.util.Arrays;

public enum StatiProcessoIdfEnum {
	IN_CODA("IN_CODA"),
    IN_PROCESSO("IN_PROCESSO");

    private final String description;

    StatiProcessoIdfEnum(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }

    public static StatiProcessoIdfEnum findByDescription(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.toString().equals(descrizione)).findFirst().orElse(null);
    }

    public boolean equals(String to) {
    	return description.equalsIgnoreCase(to);
    }

}