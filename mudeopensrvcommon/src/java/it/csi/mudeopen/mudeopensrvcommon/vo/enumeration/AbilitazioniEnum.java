/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.enumeration;

import java.util.Arrays;

public enum AbilitazioniEnum {
    CREATORE_IST_PM_OBB("CREATORE_IST_PM_OBB"),
    CREATORE_IST_PM_OPZ("CREATORE_IST_PM_OPZ"),
    PM_RUP_PM_OBB("PM_RUP_PM_OBB"),
    PM_RUP_PM_OPZ("PM_RUP_PM_OPZ"),
    COLLAB_PM_RUP_PM_OBB("COLLAB_PM_RUP_PM_OBB"),
    COLLAB_PM_RUP_PM_OPZ("COLLAB_PM_RUP_PM_OPZ"),
    PROF_SPEC("PROF_SPEC"),
    CONSULTATORE("CONSULTATORE"),
    FASCIC_CREATORE("FASCIC_CREATORE"),
    FASCIC_CONSULTATORE("FASCIC_CONSULTATORE"),
    FASCIC_CREATORE_IST("FASCIC_CREATORE_IST");

    private final String description;

    AbilitazioniEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static AbilitazioniEnum findByDescription(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescription().equals(descrizione)).findFirst().orElse(null);
    }

    public boolean equals(String to) {
    	return description.equalsIgnoreCase(to);
    }

}