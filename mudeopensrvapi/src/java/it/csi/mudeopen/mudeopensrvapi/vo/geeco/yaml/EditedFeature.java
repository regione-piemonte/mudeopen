/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geeco.yaml;

import java.math.BigDecimal;
import java.util.*;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.log4j.Logger;

import javax.validation.*;
import javax.validation.constraints.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2022-07-08T14:11:43.829Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class EditedFeature   {

  private static Logger logger = Logger.getLogger(EditedFeature.class.getCanonicalName());

  public static ObjectMapper mapper = new ObjectMapper();

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("id")
  private BigDecimal id = null;

  @JsonProperty("geometry")
  private Geometry geometry = null;

  @JsonProperty("properties")
  private Map<String, String> properties = null;

  public EditedFeature type(String type) {
    this.type = type;
    return this;
  }

    public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public EditedFeature id(BigDecimal id) {
    this.id = id;
    return this;
  }

  @Valid
  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public EditedFeature geometry(Geometry geometry) {
    this.geometry = geometry;
    return this;
  }

  @Valid
  public Geometry getGeometry() {
    return geometry;
  }

  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }

  public EditedFeature properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public EditedFeature putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }


    public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

@Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EditedFeature editedFeature = (EditedFeature) o;
    return Objects.equals(type, editedFeature.type) &&
        Objects.equals(id, editedFeature.id) &&
        Objects.equals(geometry, editedFeature.geometry) &&
        Objects.equals(properties, editedFeature.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, id, geometry, properties);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EditedFeature {\n");

    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    geometry: ").append(toIndentedString(geometry)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

