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
@XmlType(name = "coords", propOrder = {
    "csiCoordinates"
})
public class Coords {
    @XmlElement(name = "it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate", required = true, nillable = false)
    protected List<CSICoordinate> csiCoordinates;
	public List<CSICoordinate> getCSICoordinate() {
		return csiCoordinates;
	}
	public void setCSICoordinate(List<CSICoordinate> cSICoordinate) {
		csiCoordinates = cSICoordinate;
	}
}
