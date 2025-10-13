/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.report;

import java.io.Serializable;

public class ReportPdfVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String descrizioneAllegato;

	public String getDescrizioneAllegato() {
		return descrizioneAllegato;
	}

	public void setDescrizioneAllegato(String descrizioneAllegato) {
		this.descrizioneAllegato = descrizioneAllegato;
	}
}