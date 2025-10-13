/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "mudeopen_t_allegato")
public class MudeTAllegato extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8582516358289116592L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_allegato")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_tipo_allegato")
    private MudeDTipoAllegato tipoAllegato;

    @ManyToOne
    @JoinColumn(name="id_istanza")
    private MudeTIstanza istanza;

//    @ManyToOne
//    @JoinColumn(name="id_fascicolo")
//    private MudeTFascicolo fascicolo;

    @Column(name = "nome_file_allegato")
    private String nomeFileAllegato;

    @Column(name = "desc_breve_allegato")
    private String descBreveAllegato;

    @Column(name = "firmato")
    private Boolean firmato = false;

    @Column(name = "index_node")
    private String fileUID;

    @Column(name = "dimensione_file")
    private Long dimensioneFile;

    @Column(name = "data_caricamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCaricamento;

    @ManyToOne
    @JoinColumn(name="id_user")
    private MudeTUser user;

    @Column(name = "mimetype")
    private String mimetype;

    @Column(name = "protocollo")
    private String protocollo;

	@Column(name = "data_protocollo")
    private String dataProtocollo;

    public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(String data_protocollo) {
		this.dataProtocollo = data_protocollo;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeDTipoAllegato getTipoAllegato() {
        return tipoAllegato;
    }

    public void setTipoAllegato(MudeDTipoAllegato tipoAllegato) {
        this.tipoAllegato = tipoAllegato;
    }

    public MudeTIstanza getIstanza() {
        return istanza;
    }

    public void setIstanza(MudeTIstanza istanza) {
        this.istanza = istanza;
    }

//    /**
//     * Gets fascicolo.
//     *
//     * @return the fascicolo
//     */
//    public MudeTFascicolo getFascicolo() {
//        return fascicolo;
//    }
//
//    /**
//     * Sets fascicolo.
//     *
//     * @param fascicolo the fascicolo
//     */
//    public void setFascicolo(MudeTFascicolo fascicolo) {
//        this.fascicolo = fascicolo;
//    }

    public String getNomeFileAllegato() {
        return nomeFileAllegato;
    }

    public void setNomeFileAllegato(String nomeFileAllegato) {
        this.nomeFileAllegato = nomeFileAllegato;
    }

    public String getDescBreveAllegato() {
        return descBreveAllegato;
    }

    public void setDescBreveAllegato(String descBreveAllegato) {
        this.descBreveAllegato = descBreveAllegato;
    }

    public Boolean getFirmato() {
        return firmato;
    }

    public void setFirmato(Boolean firmato) {
        this.firmato = firmato;
    }

    public String getFileUID() {
        return fileUID;
    }

    public void setFileUID(String fileUID) {
        this.fileUID = fileUID;
    }

    public Long getDimensioneFile() {
        return dimensioneFile;
    }

    public void setDimensioneFile(Long dimensioneFile) {
        this.dimensioneFile = dimensioneFile;
    }

    public Date getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(Date dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    public MudeTUser getUser() {
        return user;
    }

    public void setUser(MudeTUser user) {
        this.user = user;
    }

    //TO BE REMOVED
    @Column(name = "file_content")
    byte[] fileContent;

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

	public String getMimetype() {
		if(nomeFileAllegato != null && nomeFileAllegato.toLowerCase().endsWith(".p7m"))
			return "application/pkcs7-mime";
		
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

    //END TO BE REMOVED
}