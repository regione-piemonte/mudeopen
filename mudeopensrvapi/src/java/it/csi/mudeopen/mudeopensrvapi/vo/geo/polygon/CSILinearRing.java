/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo.polygon;
public class CSILinearRing {
    protected Coords coords = null/* FSQ */;
    protected Integer SRID = -1/* FSQ */;
	public Coords getCoords(/* FSQ */) {
		return coords = coords != null? coords : new Coords()/* FSQ */;
	}

	public void setCoords(Coords coords/* FSQ */) {
		this.coords = coords/* FSQ */;
	}

	public Integer getSRID(/* FSQ */) {
		return SRID/* FSQ */;
	}

	public void setSRID(Integer sRID/* FSQ */) {
		SRID = sRID/* FSQ */;
	}
}
