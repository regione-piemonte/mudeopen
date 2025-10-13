/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index;

public class IndexDocumentoMetadataProperty {

    //private String codiceTipologiaDocumentoPA;
	private String codiceTipologia;
    private String numeroPraticaDocumentoPA;
    private String istatComuneDocumentoPA;
    private String annoCreazioneDocumentoPA;
    private String author;

	public String getCodiceTipologia() {
		return codiceTipologia;
	}
	public void setCodiceTipologia(String codiceTipologia) {
		this.codiceTipologia = codiceTipologia;
	}
	public String getNumeroPraticaDocumentoPA() {
		return numeroPraticaDocumentoPA;
	}
	public void setNumeroPraticaDocumentoPA(String numeroPraticaDocumentoPA) {
		this.numeroPraticaDocumentoPA = numeroPraticaDocumentoPA;
	}
	public String getIstatComuneDocumentoPA() {
		return istatComuneDocumentoPA;
	}
	public void setIstatComuneDocumentoPA(String istatComuneDocumentoPA) {
		this.istatComuneDocumentoPA = istatComuneDocumentoPA;
	}
	public String getAnnoCreazioneDocumentoPA() {
		return annoCreazioneDocumentoPA;
	}
	public void setAnnoCreazioneDocumentoPA(String annoCreazioneDocumentoPA) {
		this.annoCreazioneDocumentoPA = annoCreazioneDocumentoPA;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

}