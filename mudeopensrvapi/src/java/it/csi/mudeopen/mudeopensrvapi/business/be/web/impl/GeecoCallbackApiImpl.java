/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import it.csi.ecogis.geeco_integration_api.interfaces.SaveFeatureApiMonitoringAware;
import it.csi.ecogis.util.dto.GeoJSONFeature;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco.GeecoCallbakHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco.SaveFeatureApiMonitoringAwareImpl;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.GeecoCallbackApi;
import it.csi.mudeopen.mudeopensrvapi.vo.TestResourcesResult;
import it.csi.mudeopen.mudeopensrvapi.vo.geeco.yaml.EditedFeature;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;

@Component
public class GeecoCallbackApiImpl implements GeecoCallbackApi {

	private static final String ERRORE_INTERNO = "errore_interno";

	private static Logger logger = Logger.getLogger(GeecoCallbackApiImpl.class.getCanonicalName());
	
	@Autowired
	GeecoCallbakHelper geecoCallbackHelper;
	@Override
	public Response deleteFeatureForEditingLayer(
			final String environment, 
			final String layerId,
			final String idEditingSession, 
			final String  featureId, 
			GeoJSONFeature feature,
			SecurityContext securityContext, 
			HttpHeaders httpHeaders
		) {

		logger.info("GeecoCallbackApiImpl.deleteFeatureForEditingLayer");
		logger.info("environment = " + environment + ", layerId = " + layerId + ", idEditingSession = " + idEditingSession + ", featureId = " + featureId + ", feature = " + feature + ", securityContext = " + securityContext );
		SaveFeatureApiMonitoringAware callback=new SaveFeatureApiMonitoringAwareImpl();
		
		String apiVersion="1.0";
		String arg0=apiVersion;
		String arg1= environment;
		String arg2= layerId;
		String arg3= idEditingSession;		
		String arg4= featureId;
		
		SecurityContext arg6=null;		
		try {
			return callback.deleteFeatureForEditingLayer(arg0, arg1, arg2, arg3, arg4,arg6, httpHeaders);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		//c'è un errore.................................................
		ErrorResponse error = new ErrorResponse("500", ERRORE_INTERNO, ":: insertFeatureForEditingLayer:: ", new HashMap<>(), null);
		return Response.serverError().entity(error).status(500).build();
	}

	@Override
	public Response updateFeatureForEditingLayer(
			//String apiVersion, 
			String environment, 
			String layerId,
			String idEditingSession, String featureId,
			GeoJSONFeature feature,SecurityContext securityContext, HttpHeaders httpHeaders) {
		logger.info("GeecoCallbackApiImpl.updateFeatureForEditingLayer");
		logger.info("environment = " + environment + ", layerId = " + layerId + ", idEditingSession = " + idEditingSession + ", featureId = " + featureId + ", feature = " + feature + ", securityContext = " + securityContext );
		SaveFeatureApiMonitoringAware callback=new SaveFeatureApiMonitoringAwareImpl();
		String apiVersion="1.0";		

		return callback.updateFeatureForEditingLayer(apiVersion, environment, layerId, featureId,idEditingSession,feature, securityContext, httpHeaders);
	}
	
	@Override
	public Response insertFeatureForEditingLayer(
							      //String apiVersion, 
							      String environment,
							      String layerId,
							      String idEditingSession, EditedFeature feature, SecurityContext securityContext, HttpHeaders httpHeaders) {
	
		logger.info("[GeecoCallbackApiImpl::insertFeatureForEditingLayer] "+idEditingSession);
	
		Long idSelectedData=null;
		try {              
	      idSelectedData=geecoCallbackHelper.handleFeatureSelected(idEditingSession, feature);
	      long val = 0L;
	      try{
	         idSelectedData.longValue();
	      } catch(Exception e){
	  		logger.error("[insertFeatureForEditingLayer::insertFeatureForEditingLayer] ", e);
	      }
	
	      feature.setId(new BigDecimal(val));
	      Map<String, String> props = feature.getProperties();
	      props.put("id",String.valueOf(val));
	      props.put("Identificativo",String.valueOf(val));
	      feature.setProperties(props);
	      //2- ATTIVITA' VERSO GEECO - vengono gestite le attività a valle della Callback di Geeco
	
	      return Response.ok(feature).build();
	   }catch (Exception e) {
			logger.error("[insertFeatureForEditingLayer::insertFeatureForEditingLayer] Exception handling "+idEditingSession, e);
	   }
	
	   ErrorResponse error = new ErrorResponse("500", ERRORE_INTERNO, ":: insertFeatureForEditingLayer:: ", new HashMap<>(), null);
	   return Response.serverError().entity(error).status(500).build();
	
	}
		
	@Override
	public Response testEditingLayer(
			final String environment, 
			final String layerId,
			final String idEditingSession, 
			SecurityContext securityContext, 
			HttpHeaders httpHeaders) {
		
		/*
		  TestResourcesResult:
		    type: object
		    properties:
		      db-available:
		        type: boolean
		        description: True if the database is available
		        default: false
		      api-available:
		        type: boolean
		        description: True if api are available
		        default: false
		      response-time:
		        type: number
		        description: Response time in milliseconds for test resources invocation
		 */

		logger.info("[GeecoCallbackApiImpl::testEditingLayer] "+idEditingSession);
		
		long startTime = System.currentTimeMillis();
		boolean isEnabled = geecoCallbackHelper.isGeecoEnabled(idEditingSession);
      
		TestResourcesResult res = new TestResourcesResult();
		res.setApiAvailable(isEnabled);
		res.setDbAvailable(isEnabled);
		res.setResponseTime(System.currentTimeMillis() - startTime);
		
		return Response.ok(res).build();
	}

}
