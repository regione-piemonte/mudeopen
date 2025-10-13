/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "mudeopen_t_documento")
public class MudeTDocumento extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8582516358289116592L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_tipo_documento")
    private MudeDTipoDocPA tipoDocumento;

    @ManyToOne
    @JoinColumn(name="id_pratica")
    private MudeTPratica pratica;

    @Column(name = "nome_file_documento")
    private String nomeFileDocumento;

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

    @Column(name = "data_annullamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAnnullamento;
    
    @Column(name = "numero_protocollo")
    private String numeroProtocollo;

	@Column(name = "data_protocollo")
    private String dataProtocollo;
    

    public MudeDTipoDocPA getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(MudeDTipoDocPA tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeTPratica getPratica() {
		return pratica;
	}

	public void setPratica(MudeTPratica pratica) {
		this.pratica = pratica;
	}

	public String getNomeFileDocumento() {
		return nomeFileDocumento;
	}

	public void setNomeFileDocumento(String nomeFileDocumento) {
		this.nomeFileDocumento = nomeFileDocumento;
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

	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
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

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

}