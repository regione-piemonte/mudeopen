/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo.polygon;
public class Shell {
    protected Coords coords = null;
    protected Integer SRID = -1;
	public Coords getCoords() {
		return coords = coords != null? coords : new Coords();
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
