/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_adempimento")
public class MudeDAdempimento extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    public enum TipoAdempimento {
        TUTELA,
        PRESTAZIONI;

        public static TipoAdempimento fromString(String param) {
	        String toUpper = param.toUpperCase();
	        try {
	            return valueOf(toUpper);
	        } catch (Exception e) {
	            return null;
	        }
	    }		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_adempimento")
	private long idAdempimento;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_adempimento")
	private TipoAdempimento tipoAdempimento;

	@Column(name = "ambito")
	private String ambito;

	@Column(name = "denominazione")
	private String denominazione;

	@Column(name = "posizione")
	private long posizione;
	
	@ManyToOne
	@JoinColumn(name = "id_regime")
	private MudeDRegimeAggiuntivo mudeDRegimeAggiuntivo;

    public long getIdAdempimento() {
		return idAdempimento;
	}

    public void setIdAdempimento(long idAdempimento) {
		this.idAdempimento = idAdempimento;
	}

    public TipoAdempimento getTipoAdempimento() {
		return tipoAdempimento;
	}

    public void setTipoAdempimento(TipoAdempimento tipoAdempimento) {
		this.tipoAdempimento = tipoAdempimento;
	}

    public String getAmbito() {
		return ambito;
	}

    public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

    public String getDenominazione() {
		return denominazione;
	}

    public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

    public MudeDRegimeAggiuntivo getMudeDRegimeAggiuntivo() {
		return mudeDRegimeAggiuntivo;
	}

    public void setMudeDRegimeAggiuntivo(MudeDRegimeAggiuntivo mudeDRegimeAggiuntivo) {
		this.mudeDRegimeAggiuntivo = mudeDRegimeAggiuntivo;
	}

    public long getPosizione() {
		return posizione;
	}

    public void setPosizione(long posizione) {
		this.posizione = posizione;
	}

	
	
}