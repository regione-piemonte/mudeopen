/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.tracciato;

import java.util.Arrays;

public enum TipoErroreTracciatoEnum {

    VALID("Validation"),

    SYS("System");

    private final String value;

    TipoErroreTracciatoEnum(String value) {
        this.value = value;
    }

    public static TipoErroreTracciatoEnum findByValue(final String value) {
        return Arrays.stream(values()).filter(elem -> elem.getValue().toLowerCase().equals(value.toLowerCase())).findFirst().orElse(null);
    }

    public String getValue() {
        return value;
    }
}