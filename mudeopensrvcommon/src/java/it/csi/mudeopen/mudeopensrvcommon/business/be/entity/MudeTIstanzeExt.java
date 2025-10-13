/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "mudeopen_t_istanze_ext")
public class MudeTIstanzeExt extends BaseEntity implements LoggableEntity {

    @Id
    @Column(name = "id_istanza")
    private Long idIstanza;

    @Column(name = "ricevuta_pdf_content")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] ricevutaPdfContent;

    @Column(name = "data_generazione")
    private Timestamp dataGenerazione;

    @ManyToOne
    @JoinColumn(name = "id_user_ins")
    private MudeTUser user;
    
    @Column(name = "dps_script_stato")
    private String dps_script_stato;
    

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public boolean isRicevutaPdfContent() {
    	return ricevutaPdfContent!=null;
	}

	public byte[] getRicevutaPdfContent() {
		return ricevutaPdfContent;
	}

	public void setRicevutaPdfContent(byte[] ricevutaPdfContent) {
		this.ricevutaPdfContent = ricevutaPdfContent;
	}

	public Timestamp getDataGenerazione() {
        return dataGenerazione;
    }

    public void setDataGenerazione(Timestamp dataGenerazione) {
        this.dataGenerazione = dataGenerazione;
    }

	public MudeTUser getUser() {
        return user;
    }

     public void setUser(MudeTUser user) {
        this.user = user;
    }

 	@Override
 	public String getTableName() {
 		return "mudeopen_t_istanze_ext";
 	}

	public String getDps_script_stato() {
		return dps_script_stato;
	}

	public void setDps_script_stato(String dps_script_stato) {
		this.dps_script_stato = dps_script_stato;
	}

}