/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.enumeration;

import java.util.Arrays;

public enum StatiProcessoCosmoEnum {
	IN_CODA("IN_CODA"),
	CREATA("CREATA"),
	ALLEGATO("ALLEGATO"),
    INVIO_VARIBILI("INVIO_VARIABILI"),
    IN_PROCESSO("IN_PROCESSO"),
    DA_COLLEGARE("DA_COLLEGARE"),
    SEGNALATA("SEGNALATA"),
    PROPAGA_SEGNALE("PROPAGA_SEGNALE");

    private final String description;

    StatiProcessoCosmoEnum(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }

    public static StatiProcessoCosmoEnum findByDescription(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.toString().equals(descrizione)).findFirst().orElse(null);
    }

    public boolean equals(String to) {
    	return description.equalsIgnoreCase(to);
    }

}