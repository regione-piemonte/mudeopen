/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.QueryOpEnum;

import java.util.Arrays;
import java.util.Locale;

public enum TipoContatto {

    @JsonProperty("PG")
    PG("pg"),
    @JsonProperty("PF")
    PF("pf");

    private final String value;

    TipoContatto(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TipoContatto findByValue(final String value) {
        return Arrays.stream(values()).filter(elem -> elem.getValue().toLowerCase().equals(value.toLowerCase())).findFirst().orElse(null);
    }
}