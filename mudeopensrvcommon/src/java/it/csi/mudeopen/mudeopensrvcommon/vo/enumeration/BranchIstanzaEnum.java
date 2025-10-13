/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.enumeration;

import java.util.Arrays;

public enum BranchIstanzaEnum {
    BRANCH_1(1),
    BRANCH_2(2),
    BRANCH_3(3),
    BRANCH_4(4);

    private final Integer value;

    BranchIstanzaEnum(Integer value) {
        this.value = value;
    }

    public Integer getvalue() {
        return value;
    }

    public static BranchIstanzaEnum findByValue(final Integer value) {
        return Arrays.stream(values()).filter(v -> v.getvalue() == value).findFirst().orElse(null);
    }
}