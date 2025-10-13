/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo;

public enum StatoFascicolo {
    APERTO(1),
    CHIUSO(2);

    int id;
		StatoFascicolo(int id) {
			this.id = id;
		}

    int getId() {
			return id;
		}
}