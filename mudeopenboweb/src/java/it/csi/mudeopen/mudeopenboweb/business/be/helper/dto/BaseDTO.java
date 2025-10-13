/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.helper.dto;
import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * The type Base dto.
 */
public class BaseDTO {
    /**
     * The Gest data ins.
     */
    @JsonIgnore
	protected Date gestDataIns;
    /**
     * The Gest attore ins.
     */
    @JsonIgnore
	protected String gestAttoreIns;
    /**
     * The Gest data upd.
     */
    @JsonIgnore
	protected Date gestDataUpd;
    /**
     * The Gest attore upd.
     */
    @JsonIgnore
	protected String gestAttoreUpd;
    /**
     * Gets gest data ins.
     *
     * @return the gest data ins
     */
    public Date getGestDataIns() {
		return gestDataIns;
	}
    /**
     * Sets gest data ins.
     *
     * @param gestDataIns the gest data ins
     */
    public void setGestDataIns(Date gestDataIns) {
		this.gestDataIns = gestDataIns;
	}
    /**
     * Gets gest attore ins.
     *
     * @return the gest attore ins
     */
    public String getGestAttoreIns() {
		return gestAttoreIns;
	}
    /**
     * Sets gest attore ins.
     *
     * @param gestAttoreIns the gest attore ins
     */
    public void setGestAttoreIns(String gestAttoreIns) {
		this.gestAttoreIns = gestAttoreIns;
	}
    /**
     * Gets gest data upd.
     *
     * @return the gest data upd
     */
    public Date getGestDataUpd() {
		return gestDataUpd;
	}
    /**
     * Sets gest data upd.
     *
     * @param gestDataUpd the gest data upd
     */
    public void setGestDataUpd(Date gestDataUpd) {
		this.gestDataUpd = gestDataUpd;
	}
    /**
     * Gets gest attore upd.
     *
     * @return the gest attore upd
     */
    public String getGestAttoreUpd() {
		return gestAttoreUpd;
	}
    /**
     * Sets gest attore upd.
     *
     * @param gestAttoreUpd the gest attore upd
     */
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
    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     *
     * @param o the o
     * @return the string
     */
    protected String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}

		return o.toString().replace("\n", "\n    ");
	}
}