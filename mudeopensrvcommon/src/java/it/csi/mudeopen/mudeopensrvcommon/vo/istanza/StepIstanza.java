/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;

public enum StepIstanza {
    CREATA(1),
    DEFINITO_TIPO_PRESENTATORE(2),
    DEFINITO_TITOLO_PRESENTATORE(3),
    AGGIUNTI_SOGGETTI(4);

    int id;

    StepIstanza(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}