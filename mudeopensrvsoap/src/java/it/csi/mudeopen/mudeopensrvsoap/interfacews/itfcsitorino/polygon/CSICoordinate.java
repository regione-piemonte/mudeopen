/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.polygon;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
/*
<it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
	<x>1395782.545</x>
	<y>4992327.848</y>
	<z>NaN</z>
</it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSICoordinate", propOrder = {
    "x",
    "y",
    "z"
})
public class CSICoordinate {
    @XmlElement(required = true, nillable = false)
    protected Double x = Double.NaN;
    @XmlElement(required = true, nillable = false)
    protected Double y = Double.NaN;
    @XmlElement(required = true, nillable = false)
    protected Double z = Double.NaN;
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public Double getZ() {
		return z;
	}
	public void setZ(Double z) {
		this.z = z;
	}
}
