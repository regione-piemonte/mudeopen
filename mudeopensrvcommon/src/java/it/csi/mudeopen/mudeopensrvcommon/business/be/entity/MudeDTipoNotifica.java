/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_tipo_notifica")
public class MudeDTipoNotifica extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2404039273488839223L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo_notifica")
	private Long idTipoNotifica ;

	@Column(name = "cod_tipo_notifica")
	private String codTipoNotifica;

	@Column(name = "des_tipo_notifica")
	private String desTipoNotifica;
	
	@Column(name = "oggetto_proposto")
	private String oggettoProposto;
	
	@Column(name="invio_email")
	private Boolean invioEmail ;
	
	@Column(name="sub_cod_tipo_notifica")
	private String subCodTipoNotifica;
	
	public Long getIdTipoNotifica() {
		return idTipoNotifica;
	}

	public void setIdTipoNotifica(Long idTipoNotifica) {
		this.idTipoNotifica = idTipoNotifica;
	}

	public String getCodTipoNotifica() {
		return codTipoNotifica;
	}

	public void setCodTipoNotifica(String codTipoNotifica) {
		this.codTipoNotifica = codTipoNotifica;
	}

	public String getDesTipoNotifica() {
		return desTipoNotifica;
	}

	public void setDesTipoNotifica(String desTipoNotifica) {
		this.desTipoNotifica = desTipoNotifica;
	}

	public Boolean getInvioEmail() {
		return invioEmail;
	}

	public void setInvioEmail(Boolean invioEmail) {
		this.invioEmail = invioEmail;
	}

	public String getSubCodTipoNotifica() {
		return subCodTipoNotifica;
	}

	public void setSubCodTipoNotifica(String subCodTipoNotifica) {
		this.subCodTipoNotifica = subCodTipoNotifica;
	}
	/**
	 * @return the oggettoProposto
	 */
	public String getOggettoProposto() {
		return oggettoProposto;
	}

	/**
	 * @param oggettoProposto the oggettoProposto to set
	 */
	public void setOggettoProposto(String oggettoProposto) {
		this.oggettoProposto = oggettoProposto;
	}
	
	

	
}