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
import it.csi.ecogis.geeco_java_client.dto.internal.Geometry;
import it.csi.ecogis.geeco_java_client.dto.internal.Layer;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.AttributeSchema;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.PolygonSchema;
import it.csi.ecogis.geeco_java_client.dto.internal.schema.TextSchema;
import it.csi.ecogis.geeco_java_client.util.Constants;

public class GeecoConfigurationCOTO  implements GeecoConfiguration {

	Configuration confDto ;
	String uuid ="TVVERV9NVURFT1BFTl9NVURFT1BFTlRP";

	@Override
	public Configuration getGeecoConfiguration(String env, String urlQuit, Geometry geometry) throws Exception {
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
		String idLayer=new String ("50");
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
		return confDto;

	}
	
	
	
}
