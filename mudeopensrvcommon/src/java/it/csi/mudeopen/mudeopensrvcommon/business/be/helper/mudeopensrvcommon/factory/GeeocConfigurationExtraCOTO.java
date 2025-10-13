/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import it.csi.ecogis.geeco_java_client.api.ConfigurationBuilder;
import it.csi.ecogis.geeco_java_client.build.AttributeSchemaFactory;
import it.csi.ecogis.geeco_java_client.build.ConfigurationFactory;
import it.csi.ecogis.geeco_java_client.dto.Configuration;
import it.csi.ecogis.geeco_java_client.dto.internal.Feature;
import it.csi.ecogis.geeco_java_client.dto.internal.Features;
import it.csi.ecogis.geeco_java_client.dto.internal.Layer;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.AttributeSchema;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.PolygonSchema;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.TextSchema;
import it.csi.ecogis.geeco_java_client.util.Constants;
import  it.csi.ecogis.geeco_java_client.dto.internal.Geometry;

public class GeeocConfigurationExtraCOTO implements GeecoConfiguration {
	
	Configuration confDto ;
	String codiceBelfiore;
	String uuid="TVVERV9NVURFT1BFTl9NVURFT1BFTg";
	
	//------------------------------------------
	AttributeSchemaFactory asf=null;
	List<AttributeSchema> attributeSchemas=null;	
	HashMap<String, Object> defaultValues=null;
	
	boolean readOnly=false;
	boolean isHttps=true;
	boolean showInputFeatures=false;
	boolean showLabelOnFeatures=true;
	boolean required=true;
	int maxlenght=1000;
	boolean ireadOnly=false;
	boolean canInsertNewFeatures=true;
	boolean canDeleteFeatures=false;
	String defaultStyles=null;
	String alias=null;
	
	ConfigurationBuilder config;
	
	GeeocConfigurationExtraCOTO(){
		
	}
	
	public Layer addLayerParticelle() {
		String idLayer=new String ("40");
    	Layer layer=new Layer();
    	layer.setIdLayer(idLayer);
    	layer.setDefaultStyles(defaultStyles);
    	
		AttributeSchemaFactory asf=null;
		List<AttributeSchema> attributeSchemas=null;	
		asf = new AttributeSchemaFactory();		
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
    	layer.setSchemas(attributeSchemas);
		//-----------------------------------------------------------    	
    	layer.setCanInsertNewFeatures(true);
    	layer.setCanDeleteFeatures(true);
    	config.createEditingLayerWithoutFeature(idLayer, attributeSchemas, defaultValues, defaultStyles, canInsertNewFeatures,  canDeleteFeatures, alias);
		return layer;				
	}
	
	public Layer addLayerLimitiComunali(Geometry geometry) {
		//-------------------------------------------------------
		//SCHEMA - Schema Factory...		
		//-------------------------------------------------------
		asf = new AttributeSchemaFactory();						
		//
		boolean isRequired=true;
		ireadOnly=true;
		TextSchema attrSchema2 =new TextSchema("Comune","Comune",ireadOnly,isRequired);
		asf.addTextAttributeSchema(attrSchema2 );
		//
		ireadOnly=true;		
		TextSchema attrSchema3 =new TextSchema("CODBELFIORE",null,ireadOnly,isRequired);
		asf.addTextAttributeSchema(attrSchema3 );
		//
		PolygonSchema polySchema=new PolygonSchema("geom",Constants.GEOMETRY_TYPE_POLYGON);
		asf=asf.addPolygonAttributeSchema(polySchema);			
		//
		//-----------------------------------------------------------
		attributeSchemas=asf.create();					
		//-------------------------------------------------------
		//SCHEMA - END
		//-------------------------------------------------------
		
		String idLayer=new String ("41");
		config.createEditingLayerWithoutFeature(idLayer, attributeSchemas, defaultValues, defaultStyles, canInsertNewFeatures,  canDeleteFeatures, alias);
		Layer layer=new Layer();
    	
    	layer.setIdLayer("41");
    	Features features=new Features();
    	List<Feature> featureList=new ArrayList<Feature>();
    	Feature feature=new Feature();
	    feature.setId(Long.valueOf("5709"));
	    feature.setType("FeatureCollection");
	    	//-------------------------------------
	    	//Popola la geometria..
	    	//-------------------------------------
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
			config.addFeaturesToEditingLayer(idLayer,features);
			return layer;
	}
	

	
	@Override
	public Configuration getGeecoConfiguration(String env, String urlQuit, Geometry geometry) 
			throws Exception {
		
    	//-------------------------------------------------------
		this.config = new ConfigurationFactory();
		config = config.createAppInfo(uuid, env);			
		config = config.createStartupInfo(readOnly, null, showInputFeatures, true, null, null, isHttps, showLabelOnFeatures, null);
		config = config.createQuitInfo(urlQuit);
				
		//------------------------------------------------------------------
		//LAYERS		
		//------------------------------------------------------------------
    	ArrayList<Layer> layers=new ArrayList<Layer>();
    	//--Layer 40
    	Layer layerParticelle=addLayerParticelle();    	
    	//--Layer 41
    	Layer layerLimitiComunali=addLayerLimitiComunali(geometry);    	
    	
    	layers.add(layerParticelle);
    	layers.add(layerLimitiComunali);
		//------------------------------------------------------------------
		//LAYERS -- END		
		//------------------------------------------------------------------						
		confDto = config.build();				
		return confDto;
	}
}