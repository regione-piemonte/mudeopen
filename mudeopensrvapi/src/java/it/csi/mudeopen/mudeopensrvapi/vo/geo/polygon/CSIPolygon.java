/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo.polygon;
public class CSIPolygon {
    protected Holes holes = null;
    protected Shell shell = null;
    protected Integer SRID = -1;
	public Holes getHoles() {
		return holes = holes != null? holes : new Holes();
	}

	public void setHoles(Holes holes) {
		this.holes = holes;
	}

	public Shell getShell() {
		return shell = shell != null? shell : new Shell();
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
