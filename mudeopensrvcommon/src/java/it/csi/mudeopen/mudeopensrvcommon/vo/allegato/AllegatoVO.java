/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.allegato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AllegatoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 9177368031048572486L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("tipo_allegato")
    private TipoAllegatoVO tipoAllegato;

    @JsonProperty("istanza")
    private IstanzaVO istanza;

    @JsonProperty("fascicolo")
    private FascicoloVO fascicolo;

    @JsonProperty("nome_file_allegato")
    private String nomeFileAllegato;

    @JsonProperty("desc_breve_allegato")
    private String descBreveAllegato;

    @JsonProperty("firmato")
    private Boolean firmato;

    @JsonProperty("index_node")
    private String fileUID;

    @JsonProperty("dimensione_file")
    private Long dimensioneFile;

    @JsonProperty("data_caricamento")
    private Date dataCaricamento;

    @JsonProperty("utente")
    private UtenteVO utente;

    @JsonProperty("mimetype")
    private String mimetype;

    @JsonProperty("protocollo")
    private String protocollo;
    
	@JsonProperty("data_protocollo")
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

	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAllegatoVO getTipoAllegato() {
        return tipoAllegato;
    }

    public void setTipoAllegato(TipoAllegatoVO tipoAllegato) {
        this.tipoAllegato = tipoAllegato;
    }

    public IstanzaVO getIstanza() {
        return istanza;
    }

    public void setIstanza(IstanzaVO istanza) {
        this.istanza = istanza;
    }

    public FascicoloVO getFascicolo() {
        return fascicolo;
    }

    public void setFascicolo(FascicoloVO fascicolo) {
        this.fascicolo = fascicolo;
    }

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

	public String getMimetype() {
		return mimetype == null ? "application/octet-stream" : mimetype;
	}

	public void setMimetype(String mimetype) {
		if(mimetype == null && nomeFileAllegato != null) {
			switch(nomeFileAllegato.toLowerCase().replaceAll("^.*[.](.*)$", "$1")) {
			case "p7m":
				mimetype = "application/pkcs7-mime";
				break;
			case "docx":
				mimetype = "officedocument.wordprocessingml.document";
				break;
			default:
				mimetype = "application/octet-stream";
			}
		}
		
		this.mimetype = mimetype;
	}

    //END TO BE REMOVED
}