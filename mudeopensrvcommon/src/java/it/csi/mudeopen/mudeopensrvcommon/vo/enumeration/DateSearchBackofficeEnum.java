/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.enumeration;

import java.util.Arrays;

public enum DateSearchBackofficeEnum {
	DATA_PROTOCOLLO        (1),
	DATA_DI_PROVVEDIMENTO  (2),
	DATA_INIZIO_LAVORI     (3),
	DATA_FINE_LAVORI       (4),
	DATA_DINIEGO           (5),
	DATA_NOTIFICA          (6);

    private final Integer value;

    DateSearchBackofficeEnum(Integer value) {
        this.value = value;
    }

    public Integer getvalue() {
        return value;
    }

    public static DateSearchBackofficeEnum findByValue(final Integer value) {
        return Arrays.stream(values()).filter(v -> v.getvalue() == value).findFirst().orElse(null);
    }
}