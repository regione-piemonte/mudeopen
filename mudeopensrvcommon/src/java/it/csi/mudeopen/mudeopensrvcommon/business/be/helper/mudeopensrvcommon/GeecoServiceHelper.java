/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoSelectedCllbk;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoSelectedCllbkRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.PropertyUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.UserUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.oauth2.OauthHelperGeeco;
import it.csi.mudeopen.mudeopensrvcommon.vo.geeco.ConfigurationUrlVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.geeco.GeecoCentralizedDataVO;

@Component
public class GeecoServiceHelper {
	private static Logger logger = Logger.getLogger(GeecoServiceHelper.class.getCanonicalName());
	
	@Autowired
	MudeTGeecoComuneRepository geecoComuneRepository;
	
    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;
	
	@Autowired
	SvistaServiceHelper svistaService;
	
    @Autowired
	private MudeTGeecoSelectedCllbkRepository mudeTGeecoSelectedCllbkRepository;
    
    @Autowired
	private UserUtil userUtil;

	@Autowired
	PropertyUtil propertyUtil;
	
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();
	private Invocation.Builder getBuilder(String url) {

        Client client = ClientBuilder.newBuilder().build();
        String token = getURLToken();
        String uid = getConsumerKey();
        String pwd = getConsumerSecret();
        OauthHelperGeeco oauthHelper = new OauthHelperGeeco(token, uid, pwd, getMaxTimeout(), isApiManagerTokenChacheEnabled());
        String oauth = oauthHelper.getToken();
    	client.register(new ClientRequestFilter() {
			@Override
			public void filter(ClientRequestContext requestContext) throws IOException {
				LoggerUtil.debug(logger, "GEECO-rest CALL ["+url+" - "+token+" - "+uid+" - "+pwd+" - "+oauth+"]: " + requestContext.getHeaders().toString());
			}
		});
		return client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + oauth);
    }

	/**
	 * Dato il codice belfiore prova a vedere se  nel database, 
	 * altrimenti cerca le coordinate su Svista...
	 * 
	 * 
	 * @param codCodiceBelfiore
	 * @return
	 */
	public  String getJsonByCodiceBelfiore(String codCodiceBelfiore) {
		String geoJson=null;
		String gmlCoordinates=null;
		
		MudeTGeecoComune geecoComune=geecoComuneRepository.findByCodBelfioreComune(codCodiceBelfiore);
		if(geecoComune!=null) {
			gmlCoordinates=geecoComune.getCoordinateGml();
			//-----------------------------------------------------
			//TODO: verifica sulla dta di inserimento...
			Date dataInizio=geecoComune.getDataInizio();
			Date now=new Date();
	   		try {	
		   		 long diffInMillies = Math.abs(now.getTime() - dataInizio.getTime());
		   		 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		   		 logger.info("--------diff :" +diff);
		   		MudeCProprieta ggRetryProperty=propertyUtil.getPropertyValue(PropertyUtil.MUDE_GEECO_SVISTA_GG_RETRAY);
		   		long ddRetray=Long.parseLong(ggRetryProperty.getValore());
		   		 //Se diff > MUDE_GEECO_SVISTA_GG_RETRAY
		   		if(ddRetray<diff)
		   			gmlCoordinates=null;//!!!!!!
			}catch(Exception e) {
				e.printStackTrace(); 
			}

			//-----------------------------------------------------	   		
		}	
		
		
		if(gmlCoordinates!=null) {
			gmlCoordinates=gmlCoordinates.replaceAll("&lt;", "<");
			gmlCoordinates=gmlCoordinates.replaceAll("&gt;", ">");
			gmlCoordinates=gmlCoordinates.replaceAll("&quot;", "'");
			logger.info("GeecoServiceFactory:: getJsonByCodiceBelfiore (1): "+gmlCoordinates);
			//ottiene il geojson dalla Estensione GML..
			geoJson=SvistaServiceHelper.gmlToGeoJsonConvert(gmlCoordinates);
		}else {
			return svistaService.getGeoJSONComune(codCodiceBelfiore);			
		}
		
		return geoJson;
	}
		
    public List<GeecoCentralizedDataVO> callGeecoGetMetadataService(String  urlService) {
    	
    	String result=null;
    	List<GeecoCentralizedDataVO> metadata=null;
        Response resp = getBuilder(urlService)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)               
                .get();
        if (resp.getStatus() >= 400) {	        	
        	result = resp.readEntity(String.class);
            logger.error("[GeecoServiceHelper::callGeecoConfigurationService] ERROR : " + (StringUtils.isNotBlank(result) ? result : resp.getStatus()));
        } else {
            //result = resp.readEntity(String.class);
        	GenericType<List<GeecoCentralizedDataVO>> geecoType=new GenericType<List<GeecoCentralizedDataVO>>() {
            };
            metadata =  resp.readEntity(geecoType);            	        		        	
            logger.info("[GeecoServiceHelper::callGeecoConfigurationService] Creato nodo : " + result);
        }

    	return metadata;
    }

    /**
     * 
     * 
     * 
     * 
     * @param jsonNode
     * @return
     */
    public String callGeecoConfigurationService(String jsonNode, String codiceBelfioreComune, Long idIstanza, String featuresGeometry, String featuresPoints) {
		String extraInfo = mudeCProprietaRepository.getValueByNameAndInstanceVisibility("GEECO_JSON_EXTRA_PARAMS", idIstanza, userUtil.isBOContext()? "BO" : "FO", "");
		String overrideDebugJson = mudeCProprietaRepository.getValueByNameAndInstanceSP("GEECO_JSON" + ("L219".equals(codiceBelfioreComune)? "_TO" : ""), idIstanza, "");
		if(overrideDebugJson != null && !overrideDebugJson.equals("")) {
			jsonNode = overrideDebugJson.replace("${QuitInfo.url}", getUrlQuit()+idIstanza)
						.replace("${ApiInfo.env}", getEnv())
						.replace("${ApiInfo.uuid}", getUuid())
						.replace("${extraInfo}", extraInfo)
						.replace("${features.geometry}", featuresGeometry == null? "" : featuresGeometry)
						.replace("${features.pointOrPolygon}", featuresPoints == null? featuresGeometry : "{\"type\":\"MultiPoint\",\"coordinates\":["+featuresPoints+"]}")
						.replace("${codiceBelfioreComune}", codiceBelfioreComune);
		}
    	
    	logger.info("callGeecoConfigurationService:  "+jsonNode);
    	ConfigurationUrlVO geecoUrl=null;
        String result = null;  
		if(jsonNode!=null) {
	        MultipartFormDataOutput multipartForm = new MultipartFormDataOutput();
	        //multipartForm.addFormData("node", jsonNode, MediaType.TEXT_PLAIN_TYPE);
	        multipartForm.addPart(jsonNode, MediaType.TEXT_PLAIN_TYPE);
	        Entity<String> entity = Entity.json(jsonNode);
	        
	        Response resp = getBuilder(getUrlConfigruration())
	                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE)
	                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)               
	                .post(entity);
	        if (resp.getStatus() != 200) {	        	
	        	result = resp.readEntity(String.class);
	            logger.error("[GeecoServiceHelper::callGeecoConfigurationService] ERROR : " + (StringUtils.isNotBlank(result) ? result : resp.getStatus()));
	            
	            throw new BusinessException("Servizio di geolocalizzazione non disponibile al momento. Si prega di riprovare in seguito.");
	        } else {
	            //result = resp.readEntity(String.class);
	        	GenericType<ConfigurationUrlVO> jgeecourlType=new GenericType<ConfigurationUrlVO>() {
                };
                geecoUrl = resp.readEntity(jgeecourlType);
                result=geecoUrl.getGeecourl();	        		        	
	            logger.info("[GeecoServiceHelper::callGeecoConfigurationService] Creato nodo : " + result);
	        }
		}    

    	return result;    	
    }

	static public double[] getFirsGeometryPointFromJson(String json) {
		try {
		    ObjectMapper mapper = new ObjectMapper();
		    mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
	        ObjectNode jsonData = (ObjectNode)mapper.readTree(json);
	        JsonNode jsonPoint = null;
	        String type = jsonData.get("geometry").get("type").toString().replaceAll("\"", "");
	        
	        if("MultiPoint".equals(type) || "LineString".equals(type))
	        	jsonPoint = jsonData.get("geometry").get("coordinates").get(0);
	        else if("Polygon".equals(type))
	        	jsonPoint = jsonData.get("geometry").get("coordinates").get(0).get(0);
	        else if("MultiPolygon".equals(type))
	        	jsonPoint = jsonData.get("geometry").get("coordinates").get(0).get(0).get(0);
	
	    	return new double[] { 
	    			mapper.treeToValue(jsonPoint.get(0), Double.class), 
	    			mapper.treeToValue(jsonPoint.get(1), Double.class)
	    		};
		} catch (Exception ignore) { }
		
		return null;
	}
	
	public String getFirsGeometryPoint(Long idIstanza, String doConvert) {
		MudeTGeecoSelectedCllbk mudeTGeecoSelectedCllbk = mudeTGeecoSelectedCllbkRepository.getLatestSessionPosition(idIstanza);
		if(mudeTGeecoSelectedCllbk != null && mudeTGeecoSelectedCllbk.getSelectedPosition() != null) {
			double[] point = getFirsGeometryPointFromJson(mudeTGeecoSelectedCllbk.getSelectedPosition());
			if(point != null) {
				if(StringUtils.isNotBlank(doConvert))
					point = convertPointFormat(point, doConvert);

				return "[" + point[0] + ", " + point[1] + "]";
			}
		}
	        
		return null;
	}

	public double[] convertPointFormat(double[] point, String postGisConvert) {
		if(!"".equals(postGisConvert)) {
			try {
				String[] convertedPoints = mudeTGeecoSelectedCllbkRepository.convertPoint(point[0], 
																						point[1], 
																						Integer.parseInt(postGisConvert.split(":")[0]),
																						Integer.parseInt(postGisConvert.split(":")[1])).split(",");

				logger.info("[GeecoServiceFactory::convertPointFormat] postgis convert["+postGisConvert+"] OK point ["+point[0] + "," + point[1]+"] ---> ["+convertedPoints[0] + "," + convertedPoints[1]+"]");
				
				double[] res = new double[2];
				res[0] = BigDecimal.valueOf(new Double(convertedPoints[0]))
					    .setScale(4, RoundingMode.HALF_UP).doubleValue();
				res[1] = BigDecimal.valueOf(new Double(convertedPoints[1]))
					    .setScale(4, RoundingMode.HALF_UP).doubleValue();
				
				return res;
			} catch (Exception e) {
				logger.error("[GeecoServiceFactory::convertPointFormat] postgis convert["+postGisConvert+"] error for point ["+point[0] + "," + point[1]+"]", e);
			}
		}
		
		return point;
	}
	
	public String getURLToken() { return mudeCProprietaRepository.getValueByName("GEECO_URL_TOKEN", "http://tst-api-ent.ecosis.csi.it/api/token"); }
	public String getUrlConfigruration() {return mudeCProprietaRepository.getValueByName("GEECO_URL", "http://tst-api-ent.ecosis.csi.it/api/geeco-int-api/1.0") + "/" + getEnv() + "/configuration";}
	public String getUrlQuit() {return mudeCProprietaRepository.getValueByName("GEECO_FE_CALLBACK__URL", "http://localhost:17000/mudeopen/#/geeco-callback/");}
	public String getUuid() {return mudeCProprietaRepository.getValueByName("GEECO_UID", "TVVERV9NVURFT1BFTl9NVURFT1BFTg");}
	public String getConsumerKey() { return mudeCProprietaRepository.getValueByName("GEECO_CUSTOMER_KEY", "ETNTrY8h2fQklpD3sTkmRxqdOCEa"); }
	public String getConsumerSecret() { return mudeCProprietaRepository.getValueByName("GEECO_CONSUMER_SECRET", "94lbJKJCjKtpHQwihMWpErWOOIMa"); }
	public String getEnv() {return mudeCProprietaRepository.getValueByName("GEECO_ENV", "prod");}
	public boolean isApiManagerTokenChacheEnabled() {return "abilitato".equalsIgnoreCase(mudeCProprietaRepository.getValueByName("API_MANAGER_TOKEN_CACHE", "abilitato")) ;}
	private int getMaxTimeout() { return Integer.parseInt(mudeCProprietaRepository.getValueByName("GEECO_MAX_TOKEN_LEASE_MS", "-1")); } // -1 disabled by default, it takes the expires from token
	
}
