/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.vo;

import java.io.Serializable;

import it.csi.mudeopen.mudeopensrvapi.vo.VisualizzaDatiProtocollazioneIstanzaVo;

public class VisualizzaDatiProtocollazioneIstanzaSoapResponse extends SoapResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private VisualizzaDatiProtocollazioneIstanzaVo visualizzaDatiProtocollazioneIstanzaVo;

	public VisualizzaDatiProtocollazioneIstanzaVo getVisualizzaDatiProtocollazioneIstanzaVo() {
		return visualizzaDatiProtocollazioneIstanzaVo;
	}

	public void setVisualizzaDatiProtocollazioneIstanzaVo(VisualizzaDatiProtocollazioneIstanzaVo visualizzaDatiProtocollazioneIstanzaVo) {
		this.visualizzaDatiProtocollazioneIstanzaVo = visualizzaDatiProtocollazioneIstanzaVo;
	}
	
	
}
