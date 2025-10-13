/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.cosmo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadatiControlloCampioneVO extends BaseMetadatiVO {

	@JsonProperty("numero_Pratica_MUDE")
	private String numeroPraticaMUDE;

	@JsonProperty("anno_Pratica_MUDE")
	private String annoPraticaMUDE;

	@JsonProperty("titolo_intervento")
	private String titoloIntervento;

	@JsonProperty("responsabile_procedimento")
	private String responsabileProcedimento;
	
	@JsonProperty("protocollo_denuncia")
	private String protocolloDenuncia;
	
	@JsonProperty("data_protocollo_denuncia")
	private String dataProtocolloDenuncia;
	
	@JsonProperty("protocollo_integrazione")
	private String protocolloIntegrazione;
	
	@JsonProperty("data_protocollo_integrazione")
	private String dataProtocolloIntegrazione;
	
	public String getNumeroPraticaMUDE() {
		return numeroPraticaMUDE;
	}

	public void setNumeroPraticaMUDE(String numeroPraticaMUDE) {
		this.numeroPraticaMUDE = numeroPraticaMUDE;
	}

	public String getAnnoPraticaMUDE() {
		return annoPraticaMUDE;
	}

	public void setAnnoPraticaMUDE(String annoPraticaMUDE) {
		this.annoPraticaMUDE = annoPraticaMUDE;
	}

	public String getTitoloIntervento() {
		return titoloIntervento;
	}

	public void setTitoloIntervento(String titoloIntervento) {
		this.titoloIntervento = titoloIntervento;
	}

	public String getResponsabileProcedimento() {
		return responsabileProcedimento;
	}

	public void setResponsabileProcedimento(String responsabileProcedimento) {
		this.responsabileProcedimento = responsabileProcedimento;
	}

	public String getProtocolloDenuncia() {
		return protocolloDenuncia;
	}

	public void setProtocolloDenuncia(String protocolloDenuncia) {
		this.protocolloDenuncia = protocolloDenuncia;
	}

	public String getDataProtocolloDenuncia() {
		return dataProtocolloDenuncia;
	}

	public void setDataProtocolloDenuncia(String dataProtocolloDenuncia) {
		this.dataProtocolloDenuncia = dataProtocolloDenuncia;
	}

	public String getProtocolloIntegrazione() {
		return protocolloIntegrazione;
	}

	public void setProtocolloIntegrazione(String protocolloIntegrazione) {
		this.protocolloIntegrazione = protocolloIntegrazione;
	}

	public String getDataProtocolloIntegrazione() {
		return dataProtocolloIntegrazione;
	}

	public void setDataProtocolloIntegrazione(String dataProtocolloIntegrazione) {
		this.dataProtocolloIntegrazione = dataProtocolloIntegrazione;
	}

}