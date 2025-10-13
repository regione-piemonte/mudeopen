/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.enumeration;

import java.util.Arrays;

public enum QueryOpEnum {
    EQUALS("eq"),
    NOT_EQUALS("ne"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUALS("lte"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUALS("gte"),
    IN("in"),
    NOT_IN("nin"),
    LIKE("c"),
    LIKE_IGNORE_CASE("ci"),
    START_WITH("s"),
    START_WITH_IGNORE_CASE("si"),
    END_WITH("e"),
    END_WITH_IGNORE_CASE("ei");

    private final String value;

    QueryOpEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QueryOpEnum findByValue(final String value) {
        return Arrays.stream(values()).filter(elem -> elem.getValue().equals(value.trim())).findFirst().orElse(null);
    }
}