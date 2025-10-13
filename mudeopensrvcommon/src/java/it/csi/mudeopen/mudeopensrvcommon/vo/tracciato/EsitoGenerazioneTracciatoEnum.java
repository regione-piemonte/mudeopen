/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.tracciato;

import java.util.Arrays;

public enum EsitoGenerazioneTracciatoEnum {

    OK("Tracciati generati"),
    PARTIAL("Tracciati generati parzialmente"),
    KO("Traciati non generati");

    private final String value;

    EsitoGenerazioneTracciatoEnum(String value) {
        this.value = value;
    }

    public static EsitoGenerazioneTracciatoEnum findByValue(final String value) {
        return Arrays.stream(values()).filter(elem -> elem.getValue().toLowerCase().equals(value.toLowerCase())).findFirst().orElse(null);
    }

    public String getValue() {
        return value;
    }
}