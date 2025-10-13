/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.enumeration;

import java.util.Arrays;

public enum ValidationResultEnum {

    VALID("Valido"),

    INVALID("Non valido"),

    MANDATORY("Campo obbligatorio"),

    INVALID_LENGTH("Lunghezza non valida"),

    INVALID_CHARS("Caratteri non validi"),

    INVALID_CONTROL_CODE("Codice di controllo non valido"),

    INVALID_ALPHA_STRING("Può contenere solo caratteri alfabetici"),

    INVALID_ALPHA_NUMERIC_STRING("Può contenere solo caratteri alfabetici e numeri"),

    INVALID_NUMBER("Non è un numero valido"),

    INVALID_DATE("Data non valida"),

    INVALID_EMAIL("Email non valida"),

    DUPLICATE("Contenuto duplicato");

    private final String description;

    ValidationResultEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ValidationResultEnum findByDescr(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescription().equals(descrizione)).findFirst().orElse(null);
    }

}