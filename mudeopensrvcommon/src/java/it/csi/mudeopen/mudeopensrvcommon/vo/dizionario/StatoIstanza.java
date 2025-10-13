/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import java.util.Arrays;

public enum StatoIstanza {
    BOZZA("BZZ", 10),
    DA_FIRMARE("DFR", 20),
    FIRMATA("FRM", 30),
    RESTITUITA_PER_VERIFICHE("RPA", 40),
    DEPOSITATA("DPS", 50),
    PRESA_IN_CARICO("PRC", 60),
    REGISTRATA_DA_PA("APA", 100),

    ATTESA_INTEGRAZIONE("ATI",110),
    SCANDENZA_ATTESA_INT("SAI",115),
    INTEGRAZIONE_RICEVUTA("INT",120),
    IN_ATTESA_INIZIO_LAVORI("IAI",125),
    ATTESA_FINE_LAVORI("AFL",130),
    CONTROLLO_CAMPIONE("COC",140),
    IN_CONTROLLO("ICO",150),
    ISTRUTTORIA_CONTROLLO_CAMPIONE("ICI",155),
    ATTESA_INT_DOC_CONTROLLO_CAMPIONE("AIC",156),
    SCADENZA_ATTESA_INT_DOC_CONTROLLO_A_CAMPIONE("SIC",157),
    ATTESA_DRE("DRE",160),
    ATTESA_RELAZIONE_RSU("RSU",170),
    ATTESA_COLLAUDO("COL",180),
    RIFIUTATA("RES",200),
    ACCETTATA("ACC",201),
    ANNULLATA("ANN",202),
    ACCETTATA_CONTROLLO_A_CAMPIONE("ACO",202),
    ATTESA_ADEMPIMENTI("ITS",210),
    INTEGRAZIONE_CONTROLLO_A_CAMPIONE("INC",210),
    ADEMPIMENTI_RICEVUTI("ASR",220),
    CONCLUSA("CON",230),
    IN_VIGILANZA("VIG",300),
    SCADENZA_ATTESA_INTEGRAZIONE("SIA",300),
    ARCHIVIATA("ARC",310)
    ;
	
    String value;
    int level;

    StatoIstanza(String value, int level) {
        this.value = value;
        this.level = level;
    }

    public String getValue() {
        return value;
    }

    public int getLevel() {
        return level;
    }

    public static StatoIstanza fromString(String code) {
        return Arrays.stream(values()).filter(s -> s.value.equals(code)).findFirst().orElse(null);
    }

    public boolean equals(String to) {
    	return value.equalsIgnoreCase(to);
    }

    public String toName() {
    	return toString().replaceAll("_", " ");
    }

    public static String toString(String from) {
    	StatoIstanza _fromString = StatoIstanza.fromString(from);
    	return _fromString.toName();
    }
}