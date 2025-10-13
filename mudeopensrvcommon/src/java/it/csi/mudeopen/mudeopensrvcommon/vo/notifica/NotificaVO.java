/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.notifica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ente.EnteVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.UtenteVO;

public class NotificaVO extends ParentVO {

    private static final long serialVersionUID = -3961724086179323695L;

    @JsonProperty("id_user_mittente")
    private UtenteVO mittente;

    @JsonProperty("id_tipo_notifica")
    private MudeDTipoNotifica tipoNotifica;

    @JsonProperty("oggetto_notifica")
    private String oggettoNotifica;

    @JsonProperty("dt_ins")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNotifica;

    @JsonProperty("id_user_destinatari")
    private List<UtenteVO> destinatarioList;

    @JsonProperty("ruolo_mittente")
    private String ruoloMittente;

    @JsonProperty("ente_mittente")
    private EnteVO enteMittente;

    @JsonProperty("istanza")
    private IstanzaVO istanza;

    @JsonProperty("testo_notifica")
    private String testoNotifica;

    @JsonProperty("dettaglio_notifica")
    private String dettaglioNotifica;

    @JsonProperty("letto")
    private Boolean letto;

    @JsonProperty("rif_documenti")
    private Boolean rifDocumenti;

    @JsonProperty("id_notifica")
    private Long id_notifica;

    private List<DocumentoVO> documentiRif = new ArrayList<>();

    @JsonProperty("destinatari")
    private List<SelectVO> destinatari;

	public UtenteVO getMittente() {
		return mittente;
	}

	public void setMittente(UtenteVO mittente) {
		this.mittente = mittente;
	}

	public MudeDTipoNotifica getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(MudeDTipoNotifica tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

	public String getOggettoNotifica() {
		return oggettoNotifica;
	}

	public void setOggettoNotifica(String oggettoNotifica) {
		this.oggettoNotifica = oggettoNotifica;
	}

	public Date getDataNotifica() {
		return dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public List<UtenteVO> getDestinatarioList() {
		return destinatarioList;
	}

	public void setDestinatarioList(List<UtenteVO> destinatarioList) {
		this.destinatarioList = destinatarioList;
	}

	public String getRuoloMittente() {
		return ruoloMittente;
	}

	public void setRuoloMittente(String ruoloMittente) {
		this.ruoloMittente = ruoloMittente;
	}

	public EnteVO getEnteMittente() {
		return enteMittente;
	}

	public void setEnteMittente(EnteVO enteMittente) {
		this.enteMittente = enteMittente;
	}

	public IstanzaVO getIstanza() {
		return istanza;
	}

	public void setIstanza(IstanzaVO istanza) {
		this.istanza = istanza;
	}

	public String getTestoNotifica() {
		return testoNotifica;
	}

	public void setTestoNotifica(String testoNotifica) {
		this.testoNotifica = testoNotifica;
	}

	public Boolean getLetto() {
		return letto;
	}

	public void setLetto(Boolean letto) {
		this.letto = letto;
	}

	public Long getId_notifica() {
		return id_notifica;
	}

	public void setId_notifica(Long id_notifica) {
		this.id_notifica = id_notifica;
	}

	/**
	 * @return the dettaglioNotifica
	 */
	public String getDettaglioNotifica() {
		return dettaglioNotifica;
	}

	/**
	 * @param dettaglioNotifica the dettaglioNotifica to set
	 */
	public void setDettaglioNotifica(String dettaglioNotifica) {
		this.dettaglioNotifica = dettaglioNotifica;
	}

	/**
	 * @return the rifDocumenti
	 */
	public Boolean getRifDocumenti() {
		return rifDocumenti;
	}

	/**
	 * @param rifDocumenti the rifDocumenti to set
	 */
	public void setRifDocumenti(Boolean rifDocumenti) {
		this.rifDocumenti = rifDocumenti;
	}

	/**
	 * @return the documentiRif
	 */
	public List<DocumentoVO> getDocumentiRif() {
		return documentiRif;
	}

	/**
	 * @param documentiRif the documentiRif to set
	 */
	public void setDocumentiRif(List<DocumentoVO> documentiRif) {
		this.documentiRif = documentiRif;
	}

	/**
	 * @return the destinatari
	 */
	public List<SelectVO> getDestinatari() {
		return destinatari;
	}

	/**
	 * @param destinatari the destinatari to set
	 */
	public void setDestinatari(List<SelectVO> destinatari) {
		this.destinatari = destinatari;
	}

	

}