/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
@Entity
@Table(name = "mudeopen_d_ppay_importi")
public class MudeDPPayImporto extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6942222871333308128L;

    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_importo")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_importo_default")
    private MudeDPPayImporto importoDefault;

    @ManyToOne
    @JoinColumn(name = "id_ente")
    private MudeTEnte ente;

    @ManyToOne
    @JoinColumn(name = "id_tipo_istanza")
    private MudeDTipoIstanza tipoIstanza;

    @ManyToOne
    @JoinColumn(name = "id_specie_pratica")
    private MudeDSpeciePratica speciePratica;

    @Column(name="tipo_importo")
    private String tipoImporto;

    @Column(name="descrizione")
    private String descrizione;

    @Column(name="importo")
    private BigDecimal importo;

    @Column(name="dati_spec_riscossione")
    private String datiSpecificiRiscossione;

    @Column(name="causale")
    private String causale;

    @Column(name="anno_accertamento")
    private String annoAccertamento;

    @Column(name="numero_accertamento")
    private String numeroAccertamento;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MudeDPPayImporto getImportoDefault() {
		return importoDefault;
	}

	public void setImportoDefault(MudeDPPayImporto importoDefault) {
		this.importoDefault = importoDefault;
	}

	public MudeTEnte getEnte() {
		return ente;
	}

	public void setEnte(MudeTEnte ente) {
		this.ente = ente;
	}

	public String getDatiSpecificiRiscossione() {
		return datiSpecificiRiscossione;
	}

	public void setDatiSpecificiRiscossione(String codicePpay) {
		this.datiSpecificiRiscossione = codicePpay;
	}

	public MudeDTipoIstanza getTipoIstanza() {
		return tipoIstanza;
	}

	public void setTipoIstanza(MudeDTipoIstanza tipoIstanza) {
		this.tipoIstanza = tipoIstanza;
	}

	public MudeDSpeciePratica getSpeciePratica() {
		return speciePratica;
	}

	public void setSpeciePratica(MudeDSpeciePratica speciePratica) {
		this.speciePratica = speciePratica;
	}

	public String getTipoImporto() {
		return tipoImporto;
	}

	public void setTipoImporto(String tipoImporto) {
		this.tipoImporto = tipoImporto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getNumeroAccertamento() {
		return numeroAccertamento;
	}

	public void setNumeroAccertamento(String numeroAccertamento) {
		this.numeroAccertamento = numeroAccertamento;
	}

	public String getAnnoAccertamento() {
		return annoAccertamento;
	}

	public void setAnnoAccertamento(String annoAccertamento) {
		this.annoAccertamento = annoAccertamento;
	}
}