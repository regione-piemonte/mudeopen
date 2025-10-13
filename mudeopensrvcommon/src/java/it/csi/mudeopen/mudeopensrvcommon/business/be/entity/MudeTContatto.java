/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.converter.LocalDateTimeConverter;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;

@Entity
@Table(name = "mudeopen_t_contatto")
public class MudeTContatto implements LoggableEntity{

//	/**
//	 * The enum Tipo contatto.
//	 */
//	public enum TipoContatto{
//		/**
//		 * Pg tipo contatto.
//		 */
//		PG,
//		/**
//		 * Pf tipo contatto.
//		 */
//		PF
//	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_contatto")
	private Long id;
	
	// ****************** INIZIO DATI PF ****************************
	@Column(name="nome")
	private String nome;
	
	@Column(name="cognome")
	private String cognome;

	@Column(name="cf")
	private String cf;
	
	@Column(name="sesso")
	private String sesso;
	
	@Column(name="data_nascita")
	private Timestamp dataNascita;
	
	@ManyToOne
	@JoinColumn(name="id_stato_nascita")
	private MudeDNazione statoNascita;
	
	@ManyToOne
	@JoinColumn(name="id_provincia_nascita")
	private MudeDProvincia provinciaNascita;
	
	@ManyToOne
	@JoinColumn(name="id_comune_nascita")
	private MudeDComune comuneNascita;

	@Column(name="telefono")
	private String telefono;
	
	@Column(name="cellulare")
	private String cellulare;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="pec")
	private String pec;

	@OneToMany(mappedBy="mudeTContatto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<MudeRContattoQualifica> qualifiche;
	
	@OneToMany(mappedBy="mudeTContatto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MudeTIndirizzo> indirizzi;
	
	// ****************** FINE DATI PF ****************************
	// ****************** INIZIO DATI PG ****************************
	
	@Column(name="ragione_sociale")
	private String ragioneSociale;
	
	@Column(name="piva")
	private String piva;

	@Column(name="nazionalita")
	private String nazionalita;
	
	@Column(name="piva_comunitaria")
	private String pivaComunitaria;

	@ManyToOne
	@JoinColumn(name="id_stato_membro")
	private MudeDNazione statoMembro;

	@Column(name="tipo_attivita")
	private String tipoAttivita;

	// ****************** FINE DATI PG ****************************
	// ****************** DATI COMUNI ****************************
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_contatto")
	private TipoContatto tipoContatto;
	
	@OneToMany(mappedBy="mudeTContatto", cascade = CascadeType.ALL)
	private List<MudeRIstanzaSoggetto> mudeRIstanzaSoggettos;
	
	@ManyToOne
	@JoinColumn(name="id_user")
	private MudeTUser mudeTUser;

	@Column(name = "guid", columnDefinition = "uuid")
	private String guid;

	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "data_creazione")
	private LocalDateTime dataCreazione;

	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "data_cessazione")
	private LocalDateTime dataCessazione;

	@Column(name="citta_estera")
	private String cittaEstera;

	@Column(name="titolare")
	private Boolean titolare;

	@Column(name="impresa_lavori")
	private Boolean impresaLavori;

	@Column(name="professionista")
	private Boolean professionista;

    @Column(name = "data_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataModifica;

    @Column(name = "loguser")
    private String loguser;
	
	public MudeTContatto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Timestamp getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}

	public MudeDNazione getStatoNascita() {
		return statoNascita;
	}

	public void setStatoNascita(MudeDNazione statoNascita) {
		this.statoNascita = statoNascita;
	}

	public MudeDProvincia getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(MudeDProvincia provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public MudeDComune getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(MudeDComune comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public TipoContatto getTipoContatto() {
		return tipoContatto;
	}

	public void setTipoContatto(TipoContatto tipoContatto) {
		this.tipoContatto = tipoContatto;
	}

	public List<MudeRContattoQualifica> getQualifiche() {
		return qualifiche;
	}

	public void setQualifiche(List<MudeRContattoQualifica> qualifiche) {
		this.qualifiche = qualifiche;
	}

	public MudeTUser getMudeTUser() {
		return mudeTUser;
	}

	public void setMudeTUser(MudeTUser mudeTUser) {
		this.mudeTUser = mudeTUser;
	}

	public List<MudeTIndirizzo> getIndirizzi() {
		return indirizzi;
	}

	public void setIndirizzi(List<MudeTIndirizzo> indirizzi) {
		this.indirizzi = indirizzi;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public String getPivaComunitaria() {
		return pivaComunitaria;
	}

	public void setPivaComunitaria(String pivaComunitaria) {
		this.pivaComunitaria = pivaComunitaria;
	}

	public MudeDNazione getStatoMembro() {
		return statoMembro;
	}

	public void setStatoMembro(MudeDNazione statoMembro) {
		this.statoMembro = statoMembro;
	}

	public String getTipoAttivita() {
		return tipoAttivita;
	}

	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}

	public List<MudeRIstanzaSoggetto> getMudeRIstanzaSoggettos() {
		return mudeRIstanzaSoggettos;
	}

	public void setMudeRIstanzaSoggettos(List<MudeRIstanzaSoggetto> mudeRIstanzaSoggettos) {
		this.mudeRIstanzaSoggettos = mudeRIstanzaSoggettos;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public LocalDateTime getDataCessazione() {
		return dataCessazione;
	}

	public void setDataCessazione(LocalDateTime dataCessazione) {
		this.dataCessazione = dataCessazione;
	}

	@Override
	public String getTableName() {
		return "mudeopen_t_contatto";
	}

	public String getCittaEstera() {
		return cittaEstera;
	}

	public void setCittaEstera(String cittaEstera) {
		this.cittaEstera = cittaEstera;
	}

	public Boolean getTitolare() {
		return titolare;
	}

	public void setTitolare(Boolean titolare) {
		this.titolare = titolare;
	}

	public Boolean getImpresaLavori() {
		return impresaLavori;
	}

	public void setImpresaLavori(Boolean impresaLavori) {
		this.impresaLavori = impresaLavori;
	}

	public Boolean getProfessionista() {
		return professionista;
	}

	public void setProfessionista(Boolean professionista) {
		this.professionista = professionista;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public String getLoguser() {
		return loguser;
	}

	public void setLoguser(String loguser) {
		this.loguser = loguser;
	}

}