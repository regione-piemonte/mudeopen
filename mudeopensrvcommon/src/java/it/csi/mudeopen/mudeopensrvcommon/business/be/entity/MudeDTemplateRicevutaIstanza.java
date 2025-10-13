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
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_template_ricevuta_istanza")
public class MudeDTemplateRicevutaIstanza implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_template_ricevuta_istanza")
	private Long idTemplate;

	@Column(name = "des_template")
	private String desTemplate;

	@Column(name = "content_template")
	byte[] contentTemplate;
	
	@Column(name = "testo_oggetto")
	private String testoOggetto;

	@Column(name = "testo_body_1")
	private String testoBody1;

	@Column(name = "testo_body_2")
	private String testoBody2;
	
	@Column(name = "testo_body_3")
	private String testoBody3;
	
	@Column(name = "testo_footer")
	private String testoFooter;
	
	
	public Long getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getDesTemplate() {
		return desTemplate;
	}

	public void setDesTemplate(String desTemplate) {
		this.desTemplate = desTemplate;
	}

	public byte[] getContentTemplate() {
		return contentTemplate;
	}

	public void setContentTemplate(byte[] contentTemplate) {
		this.contentTemplate = contentTemplate;
	}

	public String getTestoOggetto() {
		return testoOggetto;
	}

	public void setTestoOggetto(String testoOggetto) {
		this.testoOggetto = testoOggetto;
	}

	public String getTestoBody1() {
		return testoBody1;
	}

	public void setTestoBody1(String testoBody1) {
		this.testoBody1 = testoBody1;
	}

	public String getTestoBody2() {
		return testoBody2;
	}

	public void setTestoBody2(String testoBody2) {
		this.testoBody2 = testoBody2;
	}

	public String getTestoBody3() {
		return testoBody3;
	}

	public void setTestoBody3(String testoBody3) {
		this.testoBody3 = testoBody3;
	}

	public String getTestoFooter() {
		return testoFooter;
	}

	public void setTestoFooter(String testoFooter) {
		this.testoFooter = testoFooter;
	}

	

}