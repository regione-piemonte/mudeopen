/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
//import io.swagger.annotations.*;
//@javax.annotation.Generated(value = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.codegen.languages.JavaResteasyEapServerCodegen", date = "2023-11-30T13:15:35.752Z")
public class InviaSegnaleFruitoreResponse   {

  private String codiceInvio = null;

/**
   **/

	@ApiModelProperty(required = true, value = "")
	@JsonProperty("codiceInvio")
	@NotNull
	public String getCodiceInvio() {
		  return codiceInvio;
	}
	public void setCodiceInvio(String codiceInvio) {
		this.codiceInvio = codiceInvio;
	}
  

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InviaSegnaleFruitoreResponse inviaSegnaleFruitoreResponse = (InviaSegnaleFruitoreResponse) o;
    return Objects.equals(codiceInvio, inviaSegnaleFruitoreResponse.codiceInvio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceInvio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InviaSegnaleFruitoreResponse {\n");

    sb.append("    codiceInvio: ").append(toIndentedString(codiceInvio)).append("\n");
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

