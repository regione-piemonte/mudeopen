/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//import javax.xml.rpc.ServiceException;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;
import org.geotools.geojson.geom.GeometryJSON;
//import org.geotools.gml3.GML;
//import org.geotools.util.Version;
//import org.geotools.wfs.GML;
//import org.geotools.wfs.GML.Version;
//import org.geotools.xsd.Parser;
import org.geotools.gml2.GMLConfiguration;
import org.geotools.xsd.Configuration;
import org.geotools.xsd.Parser;
import org.json.JSONException;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.csi.ecogis.geeco_java_client.dto.internal.Feature;
import it.csi.ecogis.geeco_java_client.dto.internal.GeoJsonFeature;
import it.csi.ecogis.geeco_java_client.util.ConversionUtils;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.LimammEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.LimammEnteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoComuneRepository;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;
@Component
public class SvistaServiceHelper {

	static{
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
    }
	
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();	
	
	
	@Autowired
	MudeTGeecoComuneRepository geecoComuneRepository;
	

	@Autowired
	MudeCProprietaRepository mudeCProprietaRepository;

	
    private static Logger logger = Logger.getLogger(SvistaServiceHelper.class.getCanonicalName());

    public String getGeoJSONComune(String pCodiceBelfiore) {
		URL url = null;
    	try {
    		url = new URL(urlWsdl());
		} catch (MalformedURLException e) {
    		logger.error("[SvistaServiceHelper::getGeoJSONComune] Exception", e);
		}	
    	
		LimammEnte limammEntePort = (LimammEnte)new LimammEnteService(url).getLimammEnte();
		((BindingProvider)limammEntePort).getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY,true);
		
		TokenRetryManager trm = new TokenRetryManager();
		trm.setWsProvider(new CxfImpl());
		trm.setOauthHelper(oauth());

		GenericWrapperFactoryBean genericWrapperFactoryBean = new GenericWrapperFactoryBean();
		genericWrapperFactoryBean.setEndPoint(wsEndpoint());		
		genericWrapperFactoryBean.setWrappedInterface(LimammEnte.class);
				
		genericWrapperFactoryBean.setPort(limammEntePort);
		genericWrapperFactoryBean.setTokenRetryManager(trm);
		
    	String geoJson=null;
		Comune comuneSvista=null;
		try {
			String estensione=null;
			long idComune=-1;
			LimammEnte limammEnteWrapper = (LimammEnte) genericWrapperFactoryBean.create();			

	    	comuneSvista = (Comune)limammEnteWrapper.cercaComunePerCodiceBelfiore(pCodiceBelfiore);	
	    	idComune=comuneSvista.getId();
			logger.info("[SvistaServiceHelper::getGeoJSONComune] ID Comune: "+idComune);
		    
			/**
			 * Recuperla le coordinate GML da Svista 
			 * 
			 */
			if(idComune!=-1) {
				estensione = limammEnteWrapper.cercaEstensioneComune(idComune);	
				if(estensione != null) {
					//Salva l'estensione su database..
					storeGMLComune(pCodiceBelfiore,estensione,comuneSvista); 

					estensione=estensione.replaceAll("&lt;", "<");
					estensione=estensione.replaceAll("&gt;", ">");
					estensione=estensione.replaceAll("&quot;", "'");

					geoJson=gmlToGeoJsonConvert(estensione);
				}
			}
		} catch (Exception e) {
    		logger.error("[SvistaServiceHelper::getGeoJSONComune] Exception", e);
		} 
		
    	return geoJson;
    }

    public void storeGMLComune(String pCodiceBelfiore,String gmlXML,Comune comuneSvista) {    	
    	Date now=new Date();
    	MudeTGeecoComune geecoComune = geecoComuneRepository.findByCodBelfioreComune(pCodiceBelfiore);
    	if(geecoComune==null)
    		geecoComune = new MudeTGeecoComune(); 
    	
    	Date dataInizio=geecoComune.getDataInizio();
    	if(dataInizio!=null) {
			logger.info("[SvistaServiceHelper::getGeoJSONComune] dt ins not null");
    		 try {	
		   		 long diffInMillies = Math.abs(now.getTime() - dataInizio.getTime());
		   		 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				logger.info("[SvistaServiceHelper::getGeoJSONComune] diff :" +diff);
    		 }
    		 catch(Exception e) {
	    		logger.error("[SvistaServiceHelper::storeGMLComune] Exception", e);
    		 }
    	}else {
			logger.info("[SvistaServiceHelper::getGeoJSONComune] dt ins is null");
    	}
    	
    	geecoComune.setCodBelfioreComune(pCodiceBelfiore);    	    
    	geecoComune.setCoordinateGml(gmlXML);
    	geecoComune.setIdComuneGeeco(comuneSvista.getId());
    	geecoComune.setDenomComune(comuneSvista.getNome());    	
    	geecoComune.setDataInizio(now);    	
		geecoComuneRepository.saveDAO(geecoComune);    	
    }

    public static Feature getFeatureFromGeoJson(String pGeoJson,String pCodiceBeldiore) {
    		Feature feature=null;
    		String converted="{ \"geometry\":" + pGeoJson +",\"type\":\"Features\",\"id\":1 ,\"properties\":{\"Identificativo\": \""+pCodiceBeldiore+"\"}}";
			GeoJsonFeature jf=new GeoJsonFeature();
			jf.setFeatureLabel("MyFeature");
			jf.setGeoJsonFeature(converted);					
			try {
				feature=ConversionUtils.fromFeatureGeoJson(jf);
			} catch (JSONException e) {
	    		logger.error("[SvistaServiceHelper::getFeatureFromGeoJson] Exception", e);
			}	
			return feature;
    }

    public static String  gmlToGeoJsonConvert(String gmlXML) {
		String json =null;
		Object version=null;	    
		Object obj=null;
		GeometryJSON geometryJSON = new GeometryJSON();
		InputStream input =new ByteArrayInputStream(gmlXML.getBytes());
		Configuration configuration = new GMLConfiguration();		
		Parser parser = new Parser(configuration);
		Reader reader = new StringReader(gmlXML);
		Envelope envelope=null;
		Geometry bbox =null;
		try {			
			obj=parser.parse(reader);
			String name=obj.getClass().getCanonicalName();			
			//Envelope geom = (Envelope)parser.parse(input);			
			if(obj instanceof Geometry) {
				// logger.info("TODO: -------- (Geometry)-----------------");				
			}else if (obj instanceof org.locationtech.jts.geom.Envelope) {
				envelope=(Envelope)obj;
				bbox = new GeometryFactory().toGeometry(envelope);
			}	
			
			json = geometryJSON.toString(bbox);
			
		} catch (Exception e) {
    		logger.error("[SvistaServiceHelper::gmlToGeoJsonConvert] Exception", e);
		} 
	      
		//GML gml = new GML(Version.GML3);		
		//Geometry geometry=null;
		//GMLReader gmlReader = new GMLReader();
		//GeometryFactory factory =new GeometryFactory();			

		return json;
	}

    private String urlWsdl() { return mudeCProprietaRepository.getValueByName("SVISTA_URL_WSDL", "http://tst-applogic.reteunitaria.piemonte.it/limammApplEnteWsfad2/services/limammEnte?wsdl"); }
    private String wsEndpoint() { return mudeCProprietaRepository.getValueByName("SVISTA_URL_POLIGONI", "http://tst-api-ent.ecosis.csi.it/api/territorio_svista_limamm_ente/2.0"); }

    private OauthHelper oauth() {  
    	return new OauthHelper(
    				mudeCProprietaRepository.getValueByName("SVISTA_URLTOKEN", "http://tst-api-ent.ecosis.csi.it/api/token"), 
    				mudeCProprietaRepository.getValueByName("SVISTA_CONSUMER_KEY", "ETNTrY8h2fQklpD3sTkmRxqdOCEa"), 
    				mudeCProprietaRepository.getValueByName("SVISTA_CONSUMER_SECRET", "94lbJKJCjKtpHQwihMWpErWOOIMa")
    			);    		
    }

	   
}