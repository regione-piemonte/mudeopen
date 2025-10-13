/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geeco.yaml;

import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2022-07-08T14:11:43.829Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
@JsonIgnoreProperties
public class Geometry   {

  public enum TypeEnum {
    LINESTRING("LineString"),

    POLYGON("Polygon"),

    MULTIPOLYGON("MultiPolygon"),    
    MULTIPOINT("MultiPoint");

    private final String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("coordinates")
  private Object coordinates = null;

  @JsonProperty("type")
  private TypeEnum type = null;

  public Geometry type(TypeEnum type) {
    this.type = type;
    return this;
  }

  @NotNull
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Geometry geometry = (Geometry) o;
    return Objects.equals(type, geometry.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Geometry {\n");

    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

public Object getCoordinates() {
	return coordinates;
}

public void setCoordinates(Object coordinates) {
	this.coordinates = coordinates;
}
}
