/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.geeco.yaml;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2022-07-08T14:11:43.829Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
@JsonIgnoreProperties
public class MultiPoint extends Geometry  {
  @JsonProperty("coordinates")
  private List<Point2D> coordinates = null;

  public MultiPoint coordinates(List<Point2D> coordinates) {
    this.coordinates = coordinates;
    return this;
  }

  public MultiPoint addCoordinatesItem(Point2D coordinatesItem) {
    if (coordinates == null) {
      coordinates = new ArrayList<>();
    }
    coordinates.add(coordinatesItem);
    return this;
  }

  @Valid
  public List<Point2D> getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(List<Point2D> coordinates) {
    this.coordinates = coordinates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultiPoint multiPoint = (MultiPoint) o;
    return Objects.equals(coordinates, multiPoint.coordinates) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coordinates, super.hashCode());
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MultiPoint {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    coordinates: ").append(toIndentedString(coordinates)).append("\n");
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

