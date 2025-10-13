/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.bean;

import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

public class DatiProcuraBean {

	private IstanzaVO istanza;

    public IstanzaVO getIstanza() {
		return istanza;
	}

    public void setIstanza(IstanzaVO istanza) {
		this.istanza = istanza;
	}
	
}