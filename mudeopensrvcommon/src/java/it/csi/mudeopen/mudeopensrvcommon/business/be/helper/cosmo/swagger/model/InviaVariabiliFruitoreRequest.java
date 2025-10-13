/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class InviaVariabiliFruitoreRequest   {

  private List<InviaSegnaleFruitoreVariabileArrayRequest> variabili = new ArrayList<InviaSegnaleFruitoreVariabileArrayRequest>();

  @ApiModelProperty(value = "")
  @JsonProperty("variabili")
  public List<InviaSegnaleFruitoreVariabileArrayRequest> getVariabili() {
    return variabili;
  }
  public void setVariabili(List<InviaSegnaleFruitoreVariabileArrayRequest> variabili) {
    this.variabili = variabili;
  }

}

