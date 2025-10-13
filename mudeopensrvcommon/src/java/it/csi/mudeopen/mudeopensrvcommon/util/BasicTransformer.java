/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;

import java.lang.reflect.Method;
import java.util.HashMap;

/*
 * replaces mergefields of the form: {{ pointed path object }}
 */
public class BasicTransformer {
	
	public static String fillInMergeFields(String body, HashMap<String, Object> context) {
		for(int startIndex=body.indexOf("{{"); startIndex>-1; startIndex = body.indexOf("{{", startIndex)) {
			int endIndex = body.indexOf("}}", startIndex+2);
			if(endIndex == -1) break;
				
			String exprRes = "";
			try {
				String expr = body.substring(startIndex+2, endIndex);

				Object obj = null;
				for(String exprToken : expr.split("[.]")) {
					exprToken = exprToken.trim();

					if(obj == null)
						obj = context.get(exprToken);
					else {
			            Method method = obj.getClass().getMethod("get" + exprToken.substring(0, 1).toUpperCase() + exprToken.substring(1));
			            method.setAccessible(true);
			            obj = method.invoke(obj);
			            if(obj == null) break;
					}
				}
				
	            if(obj != null)
	            	exprRes = obj.toString();
			}
			catch(Exception ignore) { 
				// ignore.printStackTrace();
			}
			
			body = body.substring(0, startIndex) + exprRes + body.substring(endIndex + 2);
		}

        return body;
	}

}