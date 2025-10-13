/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.polygon;
import java.util.List;
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
@XmlType(name = "shell", propOrder = {
    "coords",
    "SRID"
})
public class Shell {
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
