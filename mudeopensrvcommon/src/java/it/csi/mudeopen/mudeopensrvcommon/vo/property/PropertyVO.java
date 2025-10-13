/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.property;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;

public class PropertyVO   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 

  private String name = null;
  private String value = null;
  private String source = null;

    @JsonProperty("name")

  public String getName() {
    return name;
  }

    public void setName(String name) {
    this.name = name;
  }

    @JsonProperty("value")

  public String getValue() {
    return value;
  }

    public void setValue(String value) {
    this.value = value;
  }

    @JsonProperty("source")

  public String getSource() {
    return source;
  }

    public void setSource(String source) {
    this.source = source;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PropertyVO property = (PropertyVO) o;
    return Objects.equals(name, property.name) &&
        Objects.equals(value, property.value) &&
        Objects.equals(source, property.source);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value, source);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Property {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}