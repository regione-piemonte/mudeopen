/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.enumeration;

import java.util.Arrays;

public enum TipoLogNumeriMude {
	ISTANZA("I"), 
	FASCICOLO("F")
	;

    private final String code;

	TipoLogNumeriMude(String code) {
        this.code = code;
    }

    /**
     * Gets description.
     *
      the description
     */
    public String getCode() {
        return code;
    }

    /**
     * Find by description funzioni abilitazioni enum.
     *
     * @param descrizione the descrizione
      the funzioni abilitazioni enum
     */
    public static TipoLogNumeriMude findByDescription(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getCode().equals(descrizione)).findFirst().orElse(null);
    }
}
