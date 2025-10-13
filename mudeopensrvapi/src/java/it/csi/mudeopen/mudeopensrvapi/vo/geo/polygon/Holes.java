/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geo.polygon;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
public class Holes {
    protected List<CSILinearRing> csiLinearRing = null;
    @JsonIgnore
    public CSILinearRing addNewCSILinearRing() {
    	CSILinearRing newEl = new CSILinearRing();
    	
    	if(csiLinearRing == null)
    		csiLinearRing = new ArrayList<CSILinearRing>();
    	
    	csiLinearRing.add(newEl);
    	return newEl;
    }

	public List<CSILinearRing> getCsiLinearRing() {
		return csiLinearRing;
	}

	public void setCsiLinearRing(List<CSILinearRing> csiLinearRing) {
		this.csiLinearRing = csiLinearRing;
	}
}
