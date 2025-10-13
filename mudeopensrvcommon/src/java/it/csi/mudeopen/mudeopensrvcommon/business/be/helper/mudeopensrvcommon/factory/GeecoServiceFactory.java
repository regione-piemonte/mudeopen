/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.factory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.geotools.geojson.geom.GeometryJSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.ecogis.geeco_java_client.api.ConfigurationBuilder;
import it.csi.ecogis.geeco_java_client.build.AttributeSchemaFactory;
import it.csi.ecogis.geeco_java_client.build.ConfigurationFactory;
import it.csi.ecogis.geeco_java_client.dto.Configuration ;
import it.csi.ecogis.geeco_java_client.dto.internal.Feature;
import it.csi.ecogis.geeco_java_client.dto.internal.Features;
import it.csi.ecogis.geeco_java_client.dto.internal.Geometry;
import it.csi.ecogis.geeco_java_client.dto.internal.Layer;
import it.csi.ecogis.geeco_java_client.dto.internal.MultipointGeometry;
import it.csi.ecogis.geeco_java_client.dto.internal.PolygonGeometry;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.AttributeSchema;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.PolygonSchema;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.TextSchema;
import it.csi.ecogis.geeco_java_client.util.Constants;
import it.csi.ecogis.geeco_java_client.util.ConversionUtils;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.AbstractServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.vo.geeco.GeecoGeometryVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.geeco.SavedDataVO;
public class GeecoServiceFactory {

	private static Logger logger = Logger.getLogger(GeecoServiceFactory.class.getCanonicalName());

	private String codiceBelfiore;
	private GeecoConfiguration geecoConfiguration;
	
	public GeecoServiceFactory() {
		geecoConfiguration=new GeeocConfigurationExtraCOTO();	
	}

	public GeecoServiceFactory(String pCodiceBelfiore) {
		if("L219".equals(pCodiceBelfiore))
			geecoConfiguration=new GeecoConfigurationCOTO();
		else
			geecoConfiguration=new GeeocConfigurationExtraCOTO();						
	}
	
	public String initializeGeecoConfiguration(String env,String urlQuit,Geometry geometry) {
			Configuration confDto = null;
			String json = null;
			try {
				confDto = geecoConfiguration.getGeecoConfiguration(env,urlQuit,geometry);
				json = ConversionUtils.configurationBeanToJson(confDto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return json;
	}
	
	public static String initializeGeecoConfigurationBean(String env,String uuid,String urlQuit
	,Geometry geometry		
	) 
	throws Exception {
    	//-------------------------------------------------------
		//PArametri default
    	//-------------------------------------------------------
		Configuration confDto = null;
		boolean canInsertNewFeatures=true;
		boolean canDeleteFeatures=false;
		String defaultStyles=null;
		HashMap<String, Object> defaultValues=null;					
		boolean required=true;
		int maxlenght=1000;
		boolean ireadOnly=false;
		String alias=null;
    	//-------------------------------------------------------
		ConfigurationBuilder config = new ConfigurationFactory();
		config = config.createAppInfo(uuid, env);			
		boolean readOnly=false;
		boolean isHttps=true;
		boolean showInputFeatures=false;
		boolean showLabelOnFeatures=true;
		config = config.createStartupInfo(readOnly, null, showInputFeatures, true, null, null, isHttps, showLabelOnFeatures, null);
		config = config.createQuitInfo(urlQuit);
    	//-------------------------------------------------------
		//SCHEMA - Schema Factory...
    	//-------------------------------------------------------
		AttributeSchemaFactory asf=null;
		List<AttributeSchema> attributeSchemas=null;	
		asf = new AttributeSchemaFactory();		
		
		//-------------------------------------------------------
		//Esempio di tipologie di schema,,
		/*
		//Integer Schema
		IntegerSchema integerSchema=new IntegerSchema("TEST_1",alias,required,ireadOnly,maxlenght);
		asf=asf.addIntegerAttributeSchema(integerSchema);		
		//Text Schema
		TextSchema attrSchema =new TextSchema("TEST_2",null,ireadOnly,false);
		asf.addTextAttributeSchema(attrSchema );
		//Polygon Schema
		PolygonSchema polySchema=new PolygonSchema("TEST_3",Constants.GEOMETRY_TYPE_POLYGON);
		asf=asf.addPolygonAttributeSchema(polySchema);			
		*/
		//-------------------------------------------------------		
		//
		TextSchema attrSchema2 =new TextSchema("Istat Comune","Codice Istat",ireadOnly,false);
		asf.addTextAttributeSchema(attrSchema2 );
		//
		TextSchema attrSchema3 =new TextSchema("Sezione",null,ireadOnly,true);
		asf.addTextAttributeSchema(attrSchema3 );
		//
		TextSchema attrSchema4 =new TextSchema("Foglio",null,ireadOnly,true);
		asf.addTextAttributeSchema(attrSchema3 );
		//
		TextSchema attrSchema5 =new TextSchema("Particella",null,false,false);
		asf.addTextAttributeSchema(attrSchema5 );		
		//
		PolygonSchema polySchema=new PolygonSchema("geom",Constants.GEOMETRY_TYPE_POLYGON);
		asf=asf.addPolygonAttributeSchema(polySchema);			
		
		//-----------------------------------------------------------
		attributeSchemas=asf.create();			
		String idLayer=new String ("40");
		config.createEditingLayerWithoutFeature(idLayer, attributeSchemas, defaultValues, defaultStyles, canInsertNewFeatures,  canDeleteFeatures, alias);
		
		//-------------------------------------------------------
		//SCHEMA - END
		//-------------------------------------------------------
				
		//------------------------------------------------------------------
		//LAYERS		
		//------------------------------------------------------------------
    	ArrayList<Layer> layers=new ArrayList<Layer>();
    	//--Layer 40
    	Layer layer=new Layer();
    	layer.setIdLayer("40");
    	Features features=new Features();
    	List<Feature> featureList=new ArrayList<Feature>();
    	Feature feature=new Feature();
	    	feature.setId(Long.valueOf("1"));
	    	feature.setType("FeatureCollection");
	    	//-------------------------------------
	    	//Popola la geometria..
	    	//-------------------------------------
	    	//
	    	//-------------------------------------
			feature.setGeometry(geometry);			    		    	
			//---------------------------------------------------------
			//End Set Geometry
			//---------------------------------------------------------
			//Set Properties
			//---------------------------------------------------------
			Map<String, Object> proprerties=new HashedMap();
				proprerties.put("Identificativo", uuid);
				//proprerties.put("TEST_1", "AVIGLIANA");
				//proprerties.put("TEST_2", "A518");
			//feature.setProperties(proprerties);
			//---------------------------------------------------------
			//Set Features Properties - END
			//---------------------------------------------------------
				
				
			//---------------------------------------------------------
			featureList.add(feature);
			//---------------------------------------------------------
			features.setFeaturesList(featureList);			
			//---------------------------------------------------------
			layer.setFeatures(features);		
		//------------------------------------------------------------------
		layers.add(layer);		
		//------------------------------------------------------------------
		//LAYERS -- END		
		//------------------------------------------------------------------		
		
		config.addFeaturesToEditingLayer(idLayer,features);
		//config.setLayers(layers);
		confDto = config.build();
		String json = ConversionUtils.configurationBeanToJson(confDto);
		return json;
    }

	public static String initializeTestGeecoConfigurationBean(String env,String uuid,String urlQuit)
	throws Exception {
    	//-------------------------------------------------------
		//PArametri default
    	//-------------------------------------------------------
		Configuration confDto = null;
		boolean canInsertNewFeatures=true;
		boolean canDeleteFeatures=false;
		String defaultStyles=null;
		HashMap<String, Object> defaultValues=null;					
		boolean required=true;
		int maxlenght=1000;
		boolean ireadOnly=false;
		String alias=null;
    	//-------------------------------------------------------
		ConfigurationBuilder config = new ConfigurationFactory();
		config = config.createAppInfo(uuid, env);			
		boolean readOnly=false;
		boolean isHttps=true;
		config = config.createStartupInfo(readOnly, null, true, true, null, null, isHttps, true, null);
		config = config.createQuitInfo(urlQuit);
    	//-------------------------------------------------------
		//SCHEMA - Schema Factory...
    	//-------------------------------------------------------
		AttributeSchemaFactory asf=null;
		List<AttributeSchema> attributeSchemas=null;	
		asf = new AttributeSchemaFactory();		
		
		//-------------------------------------------------------
		//Esempio di tipologie di schema,,
		/*
		//Integer Schema
		IntegerSchema integerSchema=new IntegerSchema("TEST_1",alias,required,ireadOnly,maxlenght);
		asf=asf.addIntegerAttributeSchema(integerSchema);		
		//Text Schema
		TextSchema attrSchema =new TextSchema("TEST_2",null,ireadOnly,false);
		asf.addTextAttributeSchema(attrSchema );
		//Polygon Schema
		PolygonSchema polySchema=new PolygonSchema("TEST_3",Constants.GEOMETRY_TYPE_POLYGON);
		asf=asf.addPolygonAttributeSchema(polySchema);			
		*/
		//-------------------------------------------------------;
		TextSchema attrSchema2 =new TextSchema("Istat Comune","Codice Istat",false,false);
		asf.addTextAttributeSchema(attrSchema2 );
		//
		TextSchema attrSchema3 =new TextSchema("Sezione",null,ireadOnly,true);
		asf.addTextAttributeSchema(attrSchema3 );
		//
		TextSchema attrSchema4 =new TextSchema("Foglio",null,ireadOnly,true);
		asf.addTextAttributeSchema(attrSchema3 );
		//
		TextSchema attrSchema5 =new TextSchema("Particella",null,false,true);
		asf.addTextAttributeSchema(attrSchema5 );		
		//
		PolygonSchema polySchema=new PolygonSchema("geom",Constants.GEOMETRY_TYPE_POLYGON);
		asf=asf.addPolygonAttributeSchema(polySchema);	
		
		//-------------------------------------------------------
		attributeSchemas=asf.create();			
		String idLayer=new String ("40");
		config.createEditingLayerWithoutFeature(idLayer, attributeSchemas, defaultValues, defaultStyles, canInsertNewFeatures,  canDeleteFeatures, alias);
		
		//-------------------------------------------------------
		//SCHEMA - END
		//-------------------------------------------------------
				
		//------------------------------------------------------------------
		//LAYERS		
		//------------------------------------------------------------------
    	ArrayList<Layer> layers=new ArrayList<Layer>();
    	//--Layer 40
    	Layer layer=new Layer();
    	layer.setIdLayer("40");
    	Features features=new Features();
    	List<Feature> featureList=new ArrayList<Feature>();
    	Feature feature=new Feature();
	    	feature.setId(Long.valueOf(idLayer));
	    	feature.setType("FeatureCollection");
	    	//-------------------------------------
	    	//Popola la geometria..
	    	//-------------------------------------
			Geometry geometry = new MultipointGeometry();
			List<List<BigDecimal>> coordinates = new ArrayList<List<BigDecimal>>();
			List<BigDecimal> c1 = new ArrayList<BigDecimal>();
			List<BigDecimal> c2 = new ArrayList<BigDecimal>();
			List<BigDecimal> c3 = new ArrayList<BigDecimal>();
			List<BigDecimal> c4 = new ArrayList<BigDecimal>();
			c1.add(new BigDecimal("471549.0295"));
			c1.add(new BigDecimal("4972530.052"));
			
			c2.add(new BigDecimal("471549.0000"));
			c2.add(new BigDecimal("4972530.052"));
			
			c3.add(new BigDecimal("471549.0295"));
			c3.add(new BigDecimal("4972530.000"));
			
			c4.add(new BigDecimal("471549.0000"));
			c4.add(new BigDecimal("4972530.000"));
			
			coordinates.add(c1);
			coordinates.add(c2);
			coordinates.add(c3);
			coordinates.add(c4);
			((MultipointGeometry) geometry).setCoordinates(coordinates);
			((MultipointGeometry) geometry).setType(Constants.GEOMETRY_TYPE_MULTIPOINT);			
			feature.setGeometry(geometry);			    	
	    	
			//---------------------------------------------------------
			//End Set Geometry
			//---------------------------------------------------------
			Map<String, Object> proprerties=new HashedMap();
				proprerties.put("Identificativo", uuid);
				proprerties.put("TEST_1", "AVIGLIANA");
				proprerties.put("TEST_2", "A518");
			//feature.setProperties(proprerties);
			features.setFeaturesList(featureList);
			
		layer.setFeatures(features);		
		layers.add(layer);		
		//------------------------------------------------------------------
		//LAYERS -- END		
		//------------------------------------------------------------------		
		
		config.addFeaturesToEditingLayer(idLayer,features);
		//config.setLayers(layers);
		confDto = config.build();
		String json = ConversionUtils.configurationBeanToJson(confDto);
		return json;
    }

	public static Geometry setGeometry(String tipoGeometria,Object coordinates ) {
		Geometry geometry = null;
		if(tipoGeometria.equals("Polygon") ) {
			geometry = new PolygonGeometry();
    		((PolygonGeometry) geometry).setType(Constants.GEOMETRY_TYPE_POLYGON);
    		List<List<List<BigDecimal>>> lista=(List<List<List<BigDecimal>>>) coordinates;
    			((PolygonGeometry) geometry).setCoordinates(lista);
		}	
		if(tipoGeometria.equals("Multipoint") ) {			
			geometry = new MultipointGeometry();
			((MultipointGeometry) geometry).setType(Constants.GEOMETRY_TYPE_MULTIPOINT);	
			List<List<BigDecimal>> multipunti=(List<List<BigDecimal>>) coordinates;
			((MultipointGeometry) geometry).setCoordinates(multipunti);
		}
		return geometry;
	}
	
	private static Feature setFeature(Long idFeature,Geometry geometry) {
			if(geometry==null)
				return null;					
			Feature feature=new Feature();
	    	feature.setId(idFeature);
	    	feature.setType("FeatureCollection");	    	
	    	feature.setGeometry(geometry);		    	
			return feature;
	}		
	
	
    public  static String getGeecoSessionId(String urlConfigruration) {
    	if(urlConfigruration==null)
    		return null;
    	//------------------------------
    	String result=null;       	
    	String sl="&isc=";
    	int el=urlConfigruration.lastIndexOf(sl)+5;
    	int bl=urlConfigruration.length()-el;
    	result=urlConfigruration.substring(el);
    	
    	return result;    	
    }

    public static SavedDataVO deserializeSavedData(String json) {
    	SavedDataVO bean=null;
		try {
			bean = new ObjectMapper()
				      .readerFor(SavedDataVO.class)
				      .readValue(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return bean;
    }

    public static String  serializeSavedData(SavedDataVO jsonObj) 
    	throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();
	    //Object to JSON in file
	    String jsonInString = mapper.writeValueAsString(jsonObj.getGeometry());
	    return jsonInString;
    }

    public static String getGeecoJsonFromGeoJSON(GeecoGeometryVO geoJson) 
    		throws IOException {
    	GeometryJSON geometryJSON = new GeometryJSON();
    	ByteArrayOutputStream bOutput = new ByteArrayOutputStream(2000);
		OutputStreamWriter os=new OutputStreamWriter(bOutput);
		org.locationtech.jts.geom.Geometry gg=geoJson.getCoordinates();		
		geometryJSON.write(gg, os);
		String converted=geometryJSON.toString(gg);
		return converted; 
    }
    public static String getGeecoJsonFromGeoJSON(SavedDataVO geoJson)
    		throws IOException {
		return  getGeecoJsonFromGeoJSON(geoJson.getGeometry()) ;     	    	
    }

	public String getCodiceBelfiore() {
		return codiceBelfiore;
	}

	public void setCodiceBelfiore(String codiceBelfiore) {
		this.codiceBelfiore = codiceBelfiore;
	}    

}