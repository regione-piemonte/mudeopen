/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.itfcsitorino.polygon.CSIPolygon;
/*
<it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
	<x>1395782.545</x>
	<y>4992327.848</y>
	<z>NaN</z>
</it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSIGeometry", propOrder = {
    "csiPolygon"
})
public class CsiGeometry {
    @XmlElement(name = "it.csi.splugesf.jtsutil.dto.geometry.CSIPolygon", required = true, nillable = false)
    protected CSIPolygon csiPolygon;
	public CSIPolygon getCsiPolygon() {
		return csiPolygon;
	}
	public void setCsiPolygon(CSIPolygon csiPolygon) {
		this.csiPolygon = csiPolygon;
	}
}
