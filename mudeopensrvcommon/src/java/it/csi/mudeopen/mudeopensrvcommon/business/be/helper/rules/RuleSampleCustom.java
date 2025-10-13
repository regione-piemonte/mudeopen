/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.rules;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class RuleSampleCustom implements RuleCustomInterface{

	private static Logger logger = Logger.getLogger(RuleSampleCustom.class.getCanonicalName());
	
	private String qdrName;
	String property;
	String json;
	
	public RuleSampleCustom(String qdrName,String property,String json) {
		this.json=json;
		this.property=property;
		this.qdrName=qdrName;
	}
	
	@Override
	public boolean isValid() {
		boolean isValidPropertie=false;
        JSONParser parser = new JSONParser();
        JSONObject obj=null;
		try {
			obj = (org.json.simple.JSONObject) parser.parse(this.json);
	        String tabNAme=this.qdrName;
	        logger.info("veifyRules : tabNAme: "+tabNAme );
	        JSONObject qdrObj = (JSONObject) obj.get(tabNAme);	        
	        String sQuadrObj=qdrObj.toJSONString();
	        String proprietaToVerify=this.property;
	        int lastInt=sQuadrObj.lastIndexOf(proprietaToVerify);
	        if(lastInt<0)
	        	logger.info("veifyRules : joProprieta assente");
	        else {
	        	logger.info("veifyRules : joProprieta presente in "+lastInt);
	        	isValidPropertie=true;
	        }	
		} catch (ParseException e) { }

		return isValidPropertie;
	}

}
