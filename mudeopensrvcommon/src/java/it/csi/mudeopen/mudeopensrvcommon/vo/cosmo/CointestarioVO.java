/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.cosmo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CointestarioVO extends IntestatarioVO {
	@JsonProperty("tipo_cointestatario")
	private String tipo;

	@JsonProperty("cognome_cointestatario")
	private String cognome;

	@JsonProperty("nome_cointestatario")
	private String nome;

	@JsonProperty("email_cointestatario")
	private String email;

	@JsonProperty("ragione_sociale_coint")
	private String ragioneSociale;

	@JsonProperty("pec_coint")
	private String pec;

	@JsonProperty("cognome_rappresentante_coint")
	private String cognomeRappresentante;

	@JsonProperty("nome_rappresentante_coint")
	private String nomeRappresentante;

	@JsonProperty("email_rappresentante_coint")
	private String emailRappresentante;

}