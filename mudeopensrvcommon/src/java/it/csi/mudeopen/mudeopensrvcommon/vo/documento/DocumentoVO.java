/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.documento;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;
public class DocumentoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 9177368031048572486L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("tipo_documento")
    private TipoDocumentoVO tipoDocumento;

    @JsonProperty("idPratica")
    private Long idPratica;

    @JsonProperty("nome_file_documento")
    private String nomeFileDocumento;

    @JsonProperty("index_node")
    private String fileUID;

    @JsonProperty("dimensione_file")
    private Long dimensioneFile;

    @JsonProperty("data_caricamento")
    private Date dataCaricamento;

    @JsonProperty("utente")
    private UtenteVO utente;

    @JsonProperty("notificato")
    private Boolean notificato;

    @JsonProperty("numero_protocollo")
    private String numeroProtocollo;

	@JsonProperty("data_protocollo")
    private String dataProtocollo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UtenteVO getUtente() {
        return utente;
    }

    public void setUtente(UtenteVO utente) {
        this.utente = utente;
    }

    //TO BE REMOVED
    byte[] fileContent;
    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

	public TipoDocumentoVO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoVO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}

	/**
	 * @return the notificato
	 */
	public Boolean getNotificato() {
		return notificato;
	}

	/**
	 * @param notificato the notificato to set
	 */
	public void setNotificato(Boolean notificato) {
		this.notificato = notificato;
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