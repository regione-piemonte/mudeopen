/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public abstract class ParentVO implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -2238681230786404845L;

}