/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

import fr.opensagres.xdocreport.document.json.JSONArray;

import java.util.Map;

import it.csi.mudeopen.mudeopensrvapi.vo.localizzazione.TabLocal1VO;
import it.csi.mudeopen.mudeopensrvapi.vo.localizzazione.TabLocal2VO;
import org.json.simple.*;

/**
 * 
 * @author guideluc
 *
 */
public class JsonFormUtil {
	
	public static JSONObject TabLocalIninitialize(String nomeTab) {
		if("TAB_LOCAL_1".equals(nomeTab))
			return TabLocal1Ininitialize(nomeTab);
		else  if("TAB_LOCAL_2".equals(nomeTab))
			return TabLocal2Ininitialize(nomeTab);
		
		return null;
	}
	
	private static JSONObject TabLocal1Ininitialize(String nomeTab) {
		
		TabLocal1VO tab1VO=new TabLocal1VO();
		JSONObject tabObject = new JSONObject();
		tabObject.put(nomeTab, tab1VO);		
		return tabObject;
	}

	private static JSONObject TabLocal2Ininitialize(String nomeTab) {
		TabLocal2VO tab1VO=new TabLocal2VO();
		JSONObject tabObject = new JSONObject();
		tabObject.put(nomeTab, tab1VO);		
		return tabObject;
	} 
	
	

}
