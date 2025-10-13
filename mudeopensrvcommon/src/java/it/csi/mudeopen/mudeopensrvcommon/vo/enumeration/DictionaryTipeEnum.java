/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.enumeration;

import java.util.Arrays;

public enum DictionaryTipeEnum {
    RUOLO_UTENTE("ruolo_utente"),
    SPECIE_PRATICA("specie_pratica"),
    STATO_FASCICOLO("stato_fascicolo"),
    STATO_ISTANZA("stato_istanza"),
    TIPO_DEROGA("tipo_deroga"),
    DESTINAZIONE_USO("destinazione_d_uso"),
    TIPO_ENTE("tipo_ente"),
    TIPO_OPERA("tipo_opera"),
    TIPO_RAPPRESENTANZA("tipo_rappresentanza"),
    ORDINE("ordine"),
    TITOLO("titolo"),
    RUOLO_SOGGETTO("ruolo_soggetto"),
    TIPO_INTERVENTO_PAESAGGISTICA("tipo_intervento_paesaggistica"),
    TIPOLOGIA_TIPO_INTERVENTO_PAESAGGISTICA("tipologia_tipo_intervento_paesaggistica");

    private final String description;

    DictionaryTipeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static DictionaryTipeEnum findByDescription(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescription().equals(descrizione)).findFirst().orElse(null);
    }
}