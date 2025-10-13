/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.luoghi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;

import java.util.Objects;

public class ComuneVO extends SelectVO implements Comparable<ComuneVO> {

	private static final long serialVersionUID = -6580396484906960147L;
	private String codBelfiore;

	@JsonIgnore
	private String codIstat;

    public String getCodBelfiore() {
		return codBelfiore;
	}

    public void setCodBelfiore(String codBelfiore) {
		this.codBelfiore = codBelfiore;
	}

    public String getCodIstat() {
		return codIstat;
	}

    public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	@Override
	public int compareTo(ComuneVO o) {
		return this.getDescrizione().compareTo(o.getDescrizione());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		ComuneVO comuneVO = (ComuneVO) o;
		return Objects.equals(codBelfiore, comuneVO.codBelfiore) && Objects.equals(codIstat, comuneVO.codIstat);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), codBelfiore, codIstat);
	}
}