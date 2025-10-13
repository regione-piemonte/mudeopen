/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAdempimento.TipoAdempimento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

@Entity
@Table(name = "mudeopen_d_ambito")
public class MudeDAmbito extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4456539782642327010L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_ambito")
	private Long idAmbito;

	@Column(name = "cod_ambito")
	private String codAmbito;

	@Column(name = "des_ambito")
	private String desAmbito;

	@Column(name = "des_estesa_ambito")
	private String desEstesaAmbito;

	@Column(name = "flg_attivo")
	private long flgAttivo;

    public String getCodAmbito() {
		return codAmbito;
	}

    public void setCodAmbito(String codAmbito) {
		this.codAmbito = codAmbito;
	}

    public String getDesAmbito() {
		return desAmbito;
	}

    public void setDesAmbito(String desAmbito) {
		this.desAmbito = desAmbito;
	}

    public String getDesEstesaAmbito() {
		return desEstesaAmbito;
	}

    public void setDesEstesaAmbito(String desEstesaAmbito) {
		this.desEstesaAmbito = desEstesaAmbito;
	}

    public long getFlgAttivo() {
		return flgAttivo;
	}

    public void setFlgAttivo(long flgAttivo) {
		this.flgAttivo = flgAttivo;
	}

    public Long getIdAmbito() {
		return idAmbito;
	}

    public void setIdAmbito(Long idAmbito) {
		this.idAmbito = idAmbito;
	}
}