/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.geeco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

class CustomGWSDeserializer  extends StdDeserializer<GeecoGeometryVO>{
	   
	
	private static final long serialVersionUID = 1L;

	public CustomGWSDeserializer() {		
		super(GeecoGeometryVO.class); 
	}

   public CustomGWSDeserializer(Class<?> vc) { 
	        super(vc); 
	}
	  

	@Override
	public GeecoGeometryVO deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		GeecoGeometryVO geecoGeometryVO=new GeecoGeometryVO();

		try {
			
			Collection collezione= new ArrayList();
			StringBuffer sb1=new StringBuffer();

			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getFactory();			
			JsonNode mainNode = mapper.readTree(jsonparser);
			
			sb1.append(mainNode);
			JsonNode geometryNode = mainNode.get("coordinates");
						
			StringBuffer sb=new StringBuffer();

			if (geometryNode.isArray()) {
				List<Point> lst =null;
			    for (final JsonNode objNode : geometryNode) {
			        sb.append(objNode);
			        Iterator<JsonNode> ele= objNode.elements();
			        while(ele.hasNext()) {			    
			        	collezione.add(ele.next());			        				        	
			        }			        			      
			    }
			}

			String coordinates=sb.toString();	
			String geometry=sb1.toString();

			GeometryJSON geometryJSON = new GeometryJSON();
			 //org.locationtech.jts.geom.
			 Geometry geometryJSN = geometryJSON.read(geometry);
			 Coordinate[] coordinate=  geometryJSN.getCoordinates();		 
			 			 
			 if(geometryJSN instanceof Polygon) {
				 //--------------------------------------------------------
				 Polygon polygon =null;
				 polygon = geometryJSON.readPolygon(geometry);
				 geecoGeometryVO.setType("Poligon");
				 geecoGeometryVO.setCoordinates(polygon);
			 }else if ( geometryJSN instanceof  org.locationtech.jts.geom.MultiPoint) {
				 //MultiPoint multipoint =null;
				 MultiPoint multipoint = geometryJSON.readMultiPoint(geometry);
				 geecoGeometryVO.setType("MultiPoint");
				 geecoGeometryVO.setCoordinates(multipoint);				 
			 }
			
		}catch (Exception e) {
			e.printStackTrace();			
		}
		
		return geecoGeometryVO;
	}
	
}	