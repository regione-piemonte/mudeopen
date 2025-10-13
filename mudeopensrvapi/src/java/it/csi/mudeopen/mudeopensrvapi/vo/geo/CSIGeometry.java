/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo;

import it.csi.mudeopen.mudeopensrvapi.vo.geo.polygon.CSIPolygon;

public class CSIGeometry {

    protected CSIPolygon csiPolygon = null;

	public CSIPolygon getCsiPolygon() {
		return csiPolygon;
	}

	public void setCsiPolygon(CSIPolygon csiPolygon) {
		this.csiPolygon = csiPolygon;
	}

}
