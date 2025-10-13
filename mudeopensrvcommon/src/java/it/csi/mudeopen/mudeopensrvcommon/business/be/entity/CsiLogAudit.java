/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="csi_log_audit")
@NamedQuery(name="CsiLogAudit.findAll", query="SELECT a FROM CsiLogAudit a")
public class CsiLogAudit implements Serializable{
	
	private static final long serialVersionUID = 1L;

    public enum TraceOperation{
        INSERIMENTO_TIPO_QUADRO("INSERIMENTO TIPO QUADRO"),
        ELIMINA_TIPO_QUADRO("ELIMINA TIPO QUADRO"),
        MODIFICA_TIPO_QUADRO("MODIFICA TIPO QUADRO"),
        INSERIMENTO_QUADRO("INSERIMENTO QUADRO"),
        ELIMINA_QUADRO("ELIMINA QUADRO"),
        MODIFICA_QUADRO("MODIFICA QUADRO"),
        INSERIMENTO_TEMPLATE("INSERIMENTO TEMPLATE"),
        ELIMINA_TEMPLATE("ELIMINA TEMPLATE"),
        MODIFICA_TEMPLATE("MODIFICA TEMPLATE"),
        INSERIMENTO_QUADRO_TEMPLATE("INSERIMENTO QUADRO TEMPLATE"),
        ELIMINA_QUADRO_TEMPLATE("ELIMINA QUADRO TEMPLATE"),
        MODIFICA_QUADRO_TEMPLATE("MODIFICA QUADRO TEMPLATE"),
        INSERIMENTO_ALLEGATO_TEMPLATE("INSERIMENTO QUADRO TEMPLATE"),
        ELIMINA_ALLEGATO_TEMPLATE("ELIMINA QUADRO TEMPLATE"),
        MODIFICA_ALLEGATO_TEMPLATE("MODIFICA QUADRO TEMPLATE"),
        INSERIMENTO_CONTATTO_RUBRICA("INSERIMENTO CONTATTO RUBRICA"),
        ELIMINA_CONTATTO_RUBRICA("ELIMINA CONTATTO RUBRICA"),
        MODIFICA_CONTATTO_RUBRICA("MODIFICA CONTATTO RUBRICA"),
        INSERIMENTO_ISTANZA("INSERIMENTO ISTANZA"),
        MODIFICA_ISTANZA("MODIFICA ISTANZA"),
        DEPOSITA_ISTANZA("DEPOSITA ISTANZA"),
        ELIMINA_ISTANZA("ELIMINA ISTANZA"),
        ASSOCIA_PERSONA_FISICA_RUOLO_ISTANZA("ASSOCIA PERSONA FISICA/RUOLO ISTANZA"),
        ASSOCIA_PERSONA_GIURIDICA_RUOLO_ISTANZA("ASSOCIA PERSONA GIURIDICA/RUOLO ISTANZA"),
        DISASSOCIA_RUOLO_ISTANZA("ASSOCIA RUOLO ISTANZA"),
        SALVA_TIPO_ISTANZA("SALVA TIPO ISTANZA"),
        SALVA_TIPOLOGIA_PRESENTATORE_ISTANZA("SALVA TIPOLOGIA PRESENTATORE ISTANZA"),
        SALVA_TITOLO_PRESENTATORE_ISTANZA("SALVA TITOLO PRESENTATORE ISTANZA"),
        INSERIMENTO_FASCICOLO("INSERIMENTO FASCICOLO"),
        ASSOCIA_INTESTATARIO_FASCICOLO("ASSOCIA_INTESTATARIO_FASCICOLO"),
        ELIMINA_INTESTATARIO_FASCICOLO("ELIMINA_INTESTATARIO_FASCICOLO"),
        MODIFICA_FASCICOLO("MODIFICA FASCICOLO"),
        ELIMINA_FASCICOLO("ELIMINA FASCICOLO");
    	
		private String operation;

		private TraceOperation(String operation) {
			this.operation = operation;
		}

        public String getOperation() {
			return operation;
		}

        public void setOperation(String operation) {
			this.operation = operation;
		}
		
	}

	
	// --

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_log")
	private Integer idLog;
	
	@Column(name="data_ora")
	private Timestamp dataOra;

	@Column(name="id_app")
	private String idApp;

	@Column(name="ip_address")
	private String ipAddress;

	@Column(name="utente")
	private String utente;

	@Column(name="operazione")
	private String operazione;

	@Column(name="ogg_oper")
	private String oggOper;

	@Column(name="key_oper")
	private String keyOper;
	
	
	// --
    public Integer getIdLog() {
		return idLog;
	}

    public void setIdLog(Integer idLog) {
		this.idLog = idLog;
	}

    public Timestamp getDataOra() {
		return dataOra;
	}

    public void setDataOra(Timestamp dataOra) {
		this.dataOra = dataOra;
	}

    public String getIdApp() {
		return idApp;
	}

    public void setIdApp(String idApp) {
		this.idApp = idApp;
	}

    public String getIpAddress() {
		return ipAddress;
	}

    public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

    public String getUtente() {
		return utente;
	}

    public void setUtente(String utente) {
		this.utente = utente;
	}

    public String getOperazione() {
		return operazione;
	}

    public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

    public String getOggOper() {
		return oggOper;
	}

    public void setOggOper(String oggOper) {
		this.oggOper = oggOper;
	}

    public String getKeyOper() {
		return keyOper;
	}

    public void setKeyOper(String keyOper) {
		this.keyOper = keyOper;
	}

	
	// --
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CsiLogAudit [idLog=");
		builder.append(idLog);
		builder.append(", dataOra=");
		builder.append(dataOra);
		builder.append(", idApp=");
		builder.append(idApp);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append(", utente=");
		builder.append(utente);
		builder.append(", operazione=");
		builder.append(operazione);
		builder.append(", oggOper=");
		builder.append(oggOper);
		builder.append(", key_oper=");
		builder.append(keyOper);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}