/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.dto;
import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
public class BaseDTO {
    @JsonIgnore
	protected Date gestDataIns;
    @JsonIgnore
	protected String gestAttoreIns;
    @JsonIgnore
	protected Date gestDataUpd;
    @JsonIgnore
	protected String gestAttoreUpd;
    public Date getGestDataIns() {
		return gestDataIns;
	}

    public void setGestDataIns(Date gestDataIns) {
		this.gestDataIns = gestDataIns;
	}

    public String getGestAttoreIns() {
		return gestAttoreIns;
	}

    public void setGestAttoreIns(String gestAttoreIns) {
		this.gestAttoreIns = gestAttoreIns;
	}

    public Date getGestDataUpd() {
		return gestDataUpd;
	}

    public void setGestDataUpd(Date gestDataUpd) {
		this.gestDataUpd = gestDataUpd;
	}

    public String getGestAttoreUpd() {
		return gestAttoreUpd;
	}

    public void setGestAttoreUpd(String gestAttoreUpd) {
		this.gestAttoreUpd = gestAttoreUpd;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this);
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}

	@Override
	public String toString() {
		return "class BaseDTO ";
	}

    protected String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}

		return o.toString().replace("\n", "\n    ");
	}
}