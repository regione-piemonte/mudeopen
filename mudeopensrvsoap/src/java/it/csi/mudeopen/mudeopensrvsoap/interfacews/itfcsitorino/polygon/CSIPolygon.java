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
<it.csi.splugesf.jtsutil.dto.geometry.CSIPolygon>
	<holes>
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
	</holes>
	<shell>
		<coords>
			<it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
				<x>1395765.824</x>
				<y>4992321.667</y>
				<z>NaN</z>
			</it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
			<it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
				<x>1395765.965</x>
				<y>4992322.237</y>
				<z>NaN</z>
			</it.csi.splugesf.jtsutil.dto.geometry.CSICoordinate>
		</coords>
		<SRID>-1</SRID>
	</shell>
	<SRID>-1</SRID>
</it.csi.splugesf.jtsutil.dto.geometry.CSIPolygon>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CSIPolygon", propOrder = {
    "holes",
    "shell",
    "SRID"
})
public class CSIPolygon {
    @XmlElement(required = false)
    protected Holes holes;
    @XmlElement(required = true)
    protected Shell shell;
    @XmlElement(required = true)
    protected Integer SRID = -1;
	public Holes getHoles() {
		return holes;
	}
	public void setHoles(Holes holes) {
		this.holes = holes;
	}
	public Shell getShell() {
		return shell;
	}
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	public Integer getSRID() {
		return SRID;
	}
	public void setSRID(Integer sRID) {
		SRID = sRID;
	}
}
