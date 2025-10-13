/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaIdf;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.AbstractServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model.CreaIstanzaVincoloBody;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model.IstanzaVincoloResponse;

@Component
public class MudeIdfManager extends AbstractServiceHelper {

	protected String getScope() { return "IDF"; }
	
	
    public IstanzaVincoloResponse insertNewPractice(MudeTPraticaIdf mudeTPraticaIdf, CreaIstanzaVincoloBody payloadObj) throws Exception {
    	Response response = null;
    	
		try {
			//payloadObj.setIdPratica(codiceIstanza);

        	doLog(true, mudeTPraticaIdf, "CreaIstanzaVincoloBody: " + new ObjectMapper().writeValueAsString(payloadObj));
			
	        Entity<CreaIstanzaVincoloBody> entity = Entity.json(payloadObj);
	        response = getBuilder("/istanza/vincoli")
	                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
	                .post(entity);
	        if (response.getStatus() == 200) {
	        	IstanzaVincoloResponse res = null;
//	        	CreaPraticaFruitoreResponse res = response.readEntity(new GenericType<CreaPraticaFruitoreResponse>() {});
	        	
	        	try {
					String json = response.readEntity(String.class);
					
					res = new ObjectMapper().readValue(json , new TypeReference<IstanzaVincoloResponse>() {});
					saveLogResponse(mudeTPraticaIdf, res, "CreaPraticaFruitoreResponse");
				} catch (Exception e) {
					res = new IstanzaVincoloResponse();
				}
	        	
				return res;
	        }
	        
			handleError(response, null);
		} catch (Exception e) { 
			handleError(response, e);
		}
		
		return null;
    }

}