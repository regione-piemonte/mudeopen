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
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class CompleteUploadSessionRequest   {

  private String hashes = null;

  /**
   **/

  @ApiModelProperty(value = "")
  @JsonProperty("hashes")
  public String getHashes() {
    return hashes;
  }
  public void setHashes(String hashes) {
    this.hashes = hashes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompleteUploadSessionRequest completeUploadSessionRequest = (CompleteUploadSessionRequest) o;
    return Objects.equals(hashes, completeUploadSessionRequest.hashes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hashes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompleteUploadSessionRequest {\n");

    sb.append("    hashes: ").append(toIndentedString(hashes)).append("\n");
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

