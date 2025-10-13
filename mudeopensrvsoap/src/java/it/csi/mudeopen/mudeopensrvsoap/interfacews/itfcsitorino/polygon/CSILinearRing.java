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
<it.csi.splugesf.jtsutil.dto.geometry.CSILinearRing>
	<coords>
		<it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
			<x>1395782.545</x>
			<y>4992327.848</y>
			<z>NaN</z>
		</it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
		<it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
			<x>1395782.871</x>
			<y>4992326.236</y>
			<z>NaN</z>
		</it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
	</coords>
	<SRID>-1</SRID>
</it.csi.splugesf.jtsutil.dto.geometry.CSILinearRing>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSILinearRing", propOrder = {
    "coords",
    "SRID"
})
public class CSILinearRing {
    @XmlElement(required = true, nillable = false)
    protected Coords coords;
    @XmlElement(required = true)
    protected Integer SRID = -1;
	public Coords getCoords() {
		return coords;
	}
	public void setCoords(Coords coords) {
		this.coords = coords;
	}
	public Integer getSRID() {
		return SRID;
	}
	public void setSRID(Integer sRID) {
		SRID = sRID;
	}
}
