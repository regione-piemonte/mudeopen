/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import java.util.Arrays;

public enum TipoRuoloUtente {
	ENTE_TERZO("ET"),
	CONSULTATORE("CN"),
	AMMINISTRATORE("AM"),
	OPERATORE_DI_SPORTELLO("OS"),
	ISTRUTTORE("IS"),
	RESPONSABILE_PROCEDIMENTO("RP"),
	RESPONSABILE_RILASCIO_PARERE("RR"),
	UTENTE_LETTORE_OPERAZIONI("OL"),
	UTENTE_GESTORE_OPERAZIONI("OG");
    String value;

    TipoRuoloUtente(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TipoRuoloUtente fromString(String code) {
        return Arrays.stream(values()).filter(s -> s.value.equals(code)).findFirst().orElse(null);
    }

    public boolean equals(String to) {
    	return value.equalsIgnoreCase(to);
    }

    public String toName() {
    	return toString().replaceAll("_", " ");
    }

    public static String toString(String from) {
    	TipoRuoloUtente _fromString = TipoRuoloUtente.fromString(from);
    	return _fromString.toName();
    }
}