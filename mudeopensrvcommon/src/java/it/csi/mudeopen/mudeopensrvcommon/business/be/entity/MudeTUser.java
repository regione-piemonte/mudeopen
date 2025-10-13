/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;

import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;

@Entity
@Table(name = "mudeopen_t_user")
public class MudeTUser extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private long idUser;

	@Column(name = "cf")
	private String cf;

	@Temporal(TemporalType.DATE)
	@Column(name = "fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name = "inizio_validita")
	private Date inizioValidita;

	@Column(name="nome")
	private String nome;
	
	@Column(name="cognome")
	private String cognome;
	
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

	@OneToMany(mappedBy="mudeTUser")
	private List<MudeTContatto> mudeTContattos;

	@OneToMany(mappedBy="mudeTUser")
	private List<MudeTFascicolo> mudeTFascicolos;
	
	@Column(name="stato_estero")
	private String statoEstero;
	
	@Column(name = "utente_fo")
    private Boolean utenteFo = false;
	
	@Column(name = "utente_bo")
    private Boolean utenteBo = false;
	
	@Column(name = "utente_api")
    private Boolean utenteApi = false;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fine_validita_bo")
	private Date fineValiditaBo;

	@Column(name = "validato_da_utente")
	private Boolean validatoDaUtente;

    public long getIdUser() {
		return idUser;
	}

    public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

    public String getCf() {
		return cf;
	}

    public void setCf(String cf) {
		this.cf = cf;
	}

    public Date getFineValidita() {
		return fineValidita;
	}

    public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

    public Date getInizioValidita() {
		return inizioValidita;
	}

    public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
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

    public String getTelefono() {
		return telefono;
	}

    public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

    public String getMail() {
		return mail;
	}

    public void setMail(String mail) {
		this.mail = mail;
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

    public String getCellulare() {
		return cellulare;
	}

    public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

    public String getPec() {
		return pec;
	}

    public void setPec(String pec) {
		this.pec = pec;
	}

    public List<MudeTContatto> getMudeTContattos() {
		return mudeTContattos;
	}

    public void setMudeTContattos(List<MudeTContatto> mudeTContattos) {
		this.mudeTContattos = mudeTContattos;
	}

    public List<MudeTFascicolo> getMudeTFascicolos() {
		return mudeTFascicolos;
	}

    public void setMudeTFascicolos(List<MudeTFascicolo> mudeTFascicolos) {
		this.mudeTFascicolos = mudeTFascicolos;
	}

    public String getStatoEstero() {
		return statoEstero;
	}

    public void setStatoEstero(String statoEstero) {
		this.statoEstero = statoEstero;
	}

	public boolean isBackofficeAdminUser() {
	    // TODO: THIS MUST BE REPLACED WITH REAL BO USER AUTHEMTOCATION
		return cf != null && "AAAAAA00A11B000J".equals(cf);
	}

	public Boolean getUtenteFo() {
		return utenteFo;
	}

	public void setUtenteFo(Boolean utenteFo) {
		this.utenteFo = utenteFo;
	}

	public Boolean getUtenteBo() {
		return utenteBo;
	}

	public void setUtenteBo(Boolean utenteBo) {
		this.utenteBo = utenteBo;
	}

	

	public Boolean getUtenteApi() {
		return utenteApi;
	}

	public void setUtenteApi(Boolean utenteApi) {
		this.utenteApi = utenteApi;
	}

	public Date getFineValiditaBo() {
		return fineValiditaBo;
	}

	public void setFineValiditaBo(Date fineValiditaBo) {
		this.fineValiditaBo = fineValiditaBo;
	}

	public Boolean getValidatoDaUtente() {
		return validatoDaUtente == null ? false : validatoDaUtente;
	}

	public void setValidatoDaUtente(Boolean validatoDaUtente) {
		this.validatoDaUtente = validatoDaUtente;
	}
	
	

}