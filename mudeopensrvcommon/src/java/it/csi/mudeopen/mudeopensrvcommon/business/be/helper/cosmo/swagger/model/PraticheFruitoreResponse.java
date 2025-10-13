/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.PageInfo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.PraticheFruitore;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class PraticheFruitoreResponse   {

  private List<PraticheFruitore> praticheFruitore = new ArrayList<PraticheFruitore>();
  private PageInfo pageInfo = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("praticheFruitore")
  public List<PraticheFruitore> getPraticheFruitore() {
    return praticheFruitore;
  }
  public void setPraticheFruitore(List<PraticheFruitore> praticheFruitore) {
    this.praticheFruitore = praticheFruitore;
  }

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("pageInfo")
  public PageInfo getPageInfo() {
    return pageInfo;
  }
  public void setPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PraticheFruitoreResponse praticheFruitoreResponse = (PraticheFruitoreResponse) o;
    return Objects.equals(praticheFruitore, praticheFruitoreResponse.praticheFruitore) &&
        Objects.equals(pageInfo, praticheFruitoreResponse.pageInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(praticheFruitore, pageInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PraticheFruitoreResponse {\n");

    sb.append("    praticheFruitore: ").append(toIndentedString(praticheFruitore)).append("\n");
    sb.append("    pageInfo: ").append(toIndentedString(pageInfo)).append("\n");
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

