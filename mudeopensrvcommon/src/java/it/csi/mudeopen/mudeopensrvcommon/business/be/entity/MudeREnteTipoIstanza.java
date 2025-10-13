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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_r_ente_comune_tipo_istanza")
public class MudeREnteTipoIstanza extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 7202493721637463842L;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_ente_comune_tipo_istanza")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_ente")
    private MudeTEnte ente;

    @ManyToOne
    @JoinColumn(name="id_comune")
    private MudeDComune comune;

    @ManyToOne
    @JoinColumn(name="codice_tipo_istanza")
    private MudeDTipoIstanza tipoIstanza;

    @Column(name = "codice_specie_pratica")
    private String codiceSpeciePratica;

    @Column(name = "competenza_principale")
    private Boolean competenzaPrincipale;

    /*@ManyToOne
    @JoinColumn(name="id_responsabile_procedimento")
    private MudeTUser responsabileProcedimento;*/

    @Column(name="responsabile_procedimento")
    private String respProcedimento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MudeTEnte getEnte() {
		return ente;
	}

	public void setEnte(MudeTEnte ente) {
		this.ente = ente;
	}

	public MudeDTipoIstanza getTipoIstanza() {
		return tipoIstanza;
	}

	public void setTipoIstanza(MudeDTipoIstanza tipoIstanza) {
		this.tipoIstanza = tipoIstanza;
	}

	public String getCodiceSpeciePratica() {
		return codiceSpeciePratica;
	}

	public void setCodiceSpeciePratica(String codiceSpeciePratica) {
		this.codiceSpeciePratica = codiceSpeciePratica;
	}

	public Boolean getCompetenzaPrincipale() {
		return competenzaPrincipale;
	}

	public void setCompetenzaPrincipale(Boolean competenzaPrincipale) {
		this.competenzaPrincipale = competenzaPrincipale;
	}
/*
	public MudeTUser getResponsabileProcedimento() {
		return responsabileProcedimento;
	}

	public void setResponsabileProcedimento(MudeTUser responsabileProcedimento) {
		this.responsabileProcedimento = responsabileProcedimento;
	}
*/
	/**
	 * @return the respProcedimento
	 */
	public String getRespProcedimento() {
		return respProcedimento;
	}

	/**
	 * @param respProcedimento the respProcedimento to set
	 */
	public void setRespProcedimento(String respProcedimento) {
		this.respProcedimento = respProcedimento;
	}

	public MudeDComune getComune() {
		return comune;
	}

	public void setComune(MudeDComune comune) {
		this.comune = comune;
	}
	
	

}