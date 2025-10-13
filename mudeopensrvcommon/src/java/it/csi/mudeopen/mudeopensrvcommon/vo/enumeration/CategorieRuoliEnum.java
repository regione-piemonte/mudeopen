/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.enumeration;

import java.util.Arrays;

public enum CategorieRuoliEnum {
    AVENTI_TITOLO_DELEGATI("ATD"),
    TECNICI("TEC"),
    IMPRESE_LAVORI("IMP");

    private final String description;

    CategorieRuoliEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static CategorieRuoliEnum findByDescription(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescription().equals(descrizione)).findFirst().orElse(null);
    }
}