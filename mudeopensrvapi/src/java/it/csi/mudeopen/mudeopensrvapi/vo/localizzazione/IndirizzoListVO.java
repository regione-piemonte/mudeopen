/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.localizzazione;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.sigmater.uiu.Indirizzo;
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndirizzoListVO {

	@JsonProperty("indirizzi")
	List<IndirizzoVO> indirizzi;

	public List<IndirizzoVO> getIndirizzi() {
		return indirizzi;
	}

	public void setIndirizzi(List<IndirizzoVO> indirizzi) {
		this.indirizzi = indirizzi;
	}
	
	
	public  static List<IndirizzoVO> buildIndirizzoList(List<Indirizzo> pExtList) {
		List<IndirizzoVO> map = new ArrayList<IndirizzoVO>();
		for(Indirizzo pExt: pExtList) {
			IndirizzoVO vo=IndirizzoVO.buildIndirizzo(pExt);
			map.add(vo);
		}
		return map;
	}		
}
