/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo.polygon;
import java.util.ArrayList;
import java.util.List;
public class Coords {
    protected List<CSICoordinate> csiCoordinates = null;
	public List<CSICoordinate> getCSICoordinate() {
		return csiCoordinates = csiCoordinates != null? csiCoordinates : new ArrayList<CSICoordinate>();
	}

	public void setCSICoordinate(List<CSICoordinate> cSICoordinate) {
		csiCoordinates = cSICoordinate;
	}
}
