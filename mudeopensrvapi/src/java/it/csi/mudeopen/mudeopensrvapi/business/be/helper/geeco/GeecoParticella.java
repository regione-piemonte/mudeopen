/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.mudeopen.mudeopensrvapi.vo.geeco.yaml.EditedFeature;
import it.csi.mudeopen.mudeopensrvapi.vo.geeco.yaml.Geometry.TypeEnum;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.GeecoServiceHelper;
import org.apache.log4j.Logger;
import java.util.Arrays;
import java.util.Map;
public abstract class  GeecoParticella {
    private static Logger logger = Logger.getLogger(GeecoParticella.class.getCanonicalName());

    String uid = null;
    String origin = null;
    double[] firstPoint = null;
    String belfiore = null;
    double[] firstPointConverted = null;
    
    protected static ObjectMapper mapper = new ObjectMapper();
    
    public abstract JsonNode toJson();
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public static GeecoParticella fromEditedFeature(EditedFeature feature, String defaultBelfiore) {
    	String featureJson = null;
        GeecoParticella res = null;
    	
    	try {
    		featureJson = new ObjectMapper().writeValueAsString(feature);
    		logger.info("[GeecoParticella::fromEditedFeature] "+defaultBelfiore+": " + featureJson);
    	
	        Map propertiesMap=feature.getProperties();
	        
	        if(feature.getGeometry().getType() == TypeEnum.MULTIPOINT) { // for DS in TO/extraTO
	            GeecoParticellaExtraTorino particella = new GeecoParticellaExtraTorino();
	            particella.setOrigin("L219".equals(defaultBelfiore)? "ds_torino" :  "ds_extra_torino");
	            particella.setBelfiore(defaultBelfiore);
	            res = particella;
	        } else if(propertiesMap.containsKey("original-properties")) {
	            String originalProps = (String)propertiesMap.get("original-properties");
	            JsonNode props = mapper.readTree(originalProps);
	            
	            if(props.has("cod_com_belfiore") &&
		                    //props.has("geometria") &&
		                    props.has("sezione") &&
		                    props.has("foglio") &&
		                    props.has("particella"))
	                res = fromExtraTorino(props);
	            else if(//props.has("msGeometry") &&
	                          props.has("cit_part") &&
	                          props.has("foglio") &&
	                          props.has("n_part"))
	                res = fromTorinoCatastoTerreni(props);
	            else if(//props.has("msGeometry") &&
	                        props.has("ID_FABBRICATO"))
	                res = fromTorinoCatastoFabbricati(props);
	        }
	        
	        res.setFirstPoint(GeecoServiceHelper.getFirsGeometryPointFromJson(featureJson));
    	} catch (Exception e) {
    		logger.error("[GeecoParticella::fromEditedFeature] " + featureJson, e);
    	} 

        return res;
    }

    public static GeecoParticella fromExtraTorino(JsonNode node){
        GeecoParticellaExtraTorino particella = new GeecoParticellaExtraTorino();
        particella.setOrigin("extra_torino");
        particella.setBelfiore(node.get("cod_com_belfiore").textValue());
        particella.setSezione(node.get("sezione").textValue());
        particella.setFoglio(padLeftZeros(node.get("foglio").textValue(), 4));
        particella.setMappale(padLeftZeros(node.get("particella").textValue(), 4));

        return particella;
    }

    public static GeecoParticella fromTorinoCatastoTerreni(JsonNode node){
        GeecoParticellaTorinoCatastoTerreni particella = new GeecoParticellaTorinoCatastoTerreni();
        particella.setOrigin("torino_catasto_terreni");
        particella.setBelfiore("L219");
        particella.setFoglio(node.get("foglio").textValue());
        particella.setCitPart(node.get("cit_part").textValue());
        particella.setnPart(node.get("n_part").textValue());

        return particella;
    }

    public static GeecoParticella fromTorinoCatastoFabbricati(JsonNode node){
        GeecoParticellaTorinoCatastoFabbricati particella = new GeecoParticellaTorinoCatastoFabbricati();
        particella.setOrigin("torino_catasto");
        particella.setBelfiore("L219");
        particella.setIdFabbricato(node.get("ID_FABBRICATO").textValue());
        if(node.has("PARTICELLA_CT")){
            particella.setParticellaCt(node.get("PARTICELLA_CT").textValue());
        }

        if(node.has("PARTICELLA_CU")){
            particella.setParticellaCu(node.get("PARTICELLA_CU").textValue());
        }

        if(node.has("FOGLIO_CT")){
            particella.setFoglioCt(node.get("FOGLIO_CT").textValue());
        }

        if(node.has("FOGLIO_CU")){
            particella.setFoglioCu(node.get("FOGLIO_CU").textValue());
        }

        return particella;
    }

    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }

        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }

        sb.append(inputString);
        return sb.toString();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getBelfiore() {
        return belfiore;
    }

    public void setBelfiore(String belfiore) {
        this.belfiore = belfiore;
    }

    public double[] getFirstPoint() {
        return firstPoint;
    }

    public void setFirstPoint(double[] firstPoint) {
        this.firstPoint = firstPoint;
    }
	public double[] getFirstPointConverted() {
		return firstPointConverted;
	}
	public void setFirstPointConverted(double[] firstPointConverted) {
		this.firstPointConverted = firstPointConverted;
	}
}
