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

import org.apache.log4j.Logger;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeecoSavedDataVO {

	@JsonProperty("type")
	private String type;

	@JsonProperty("properties")
	private CustomPropertiesVO properties;	
	
	@JsonProperty("geometry")
	/*
	@JsonTypeInfo(use=Id.NAME, include=As.PROPERTY, property="type")
	@JsonSubTypes({
	    @JsonSubTypes.Type(value=TestCustomGWSDeserializer.class, name="Polygon"),
	    @JsonSubTypes.Type(value=TestCustomGWSDeserializer.class, name="MultiPoint")
	})
	*/ 	
	@JsonDeserialize(using = TestCustomGWSDeserializer.class)
	private GeecoGeometryVO geometry;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CustomPropertiesVO getProperties() {
		return properties;
	}

	public void setProperties(CustomPropertiesVO properties) {
		this.properties = properties;
	}

	public GeecoGeometryVO getGeometry() {
		return geometry;
	}

	public void setGeometry(GeecoGeometryVO geometry) {
		this.geometry = geometry;
	}
		
}

class TestCustomGWSDeserializer  extends StdDeserializer<GeecoGeometryVO>{
	   
	   private static Logger logger = Logger.getLogger(TestCustomGWSDeserializer.class.getCanonicalName());	
		
	   public TestCustomGWSDeserializer(Class<?> vc) { 
		        super(vc); 
		}
		  
		public TestCustomGWSDeserializer() {		
			this(null); 
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
					 //--------------------------------------------------------
					 MultiPoint multypoint =null;
					 multypoint = geometryJSON.readMultiPoint(geometry);
					 geecoGeometryVO.setType("Multipoint");
					 geecoGeometryVO.setCoordinates(multypoint);
					 //--------------------------------------------------------
				}
				
			}catch (Exception e) {
				e.printStackTrace();			
			}
			
			return geecoGeometryVO;
		}
	
}
