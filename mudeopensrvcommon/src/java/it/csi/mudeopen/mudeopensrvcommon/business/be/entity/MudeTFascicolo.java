/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mudeopen_t_fascicolo")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MudeTFascicolo implements LoggableEntity {

	/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fascicolo")
    */
	@Id
	@GenericGenerator(name = "FascicoloIdentity", strategy = "it.csi.mudeopen.mudeopensrvcommon.business.be.entity.identity.FascicoloIdentity")
	@GeneratedValue(generator = "FascicoloIdentity")
	@Column(name = "id_fascicolo", unique = true, nullable = false)	
    private Long id;

    @Column(name = "codice_fascicolo")
    private String codiceFascicolo;

    @Column(name = "data_creazione")
    private Timestamp dataCreazione;

    @OneToOne
    @JoinColumn(name = "id_tipo_intervento")
    private MudeDTipoIntervento tipoIntervento;

    @ManyToOne
    @JoinColumn(name = "id_tipo_istanza")
    private MudeDTipoIstanza tipoIstanza;

    @ManyToOne
    @JoinColumn(name = "id_comune")
    private MudeDComune comune;

    /*
    * DISMISSED DUE TOO MUCH OVERHEAD
    @OneToMany(mappedBy = "mudeTFascicolo", cascade = CascadeType.ALL)
    private List<MudeTIstanza> mudeTIstanzas;
     */

    @ManyToOne
    @JoinColumn(name = "id_user")
    private MudeTUser mudeTUser;

    @Type(type = "jsonb")
    @Column(name = "json_data", columnDefinition = "jsonb")
    private String jsonData;

    @Column(name = "index_folder")
    private String uuidIndex;

    @Column(name = "data_modifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataModifica;

    @Column(name = "data_fine")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFine;

    @Column(name = "loguser")
    private String loguser;

//    @OneToMany
//    @Where(clause = "dataFine IS NULL")
//    private MudeRFascicoloIntestatario intestatario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MudeDTipoIstanza getTipoIstanza() {
        return tipoIstanza;
    }

    public void setTipoIstanza(MudeDTipoIstanza tipoIstanza) {
        this.tipoIstanza = tipoIstanza;
    }

    public Timestamp getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Timestamp dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public MudeTUser getMudeTUser() {
        return mudeTUser;
    }

    public void setMudeTUser(MudeTUser mudeTUser) {
        this.mudeTUser = mudeTUser;
    }

    public MudeDComune getComune() {
        return comune;
    }

    public void setComune(MudeDComune comune) {
        this.comune = comune;
    }

    public String getCodiceFascicolo() {
        return codiceFascicolo;
    }

    public void setCodiceFascicolo(String codiceFascicolo) {
        this.codiceFascicolo = codiceFascicolo;
    }

    public String getJsonData() {
        return jsonData;

    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    /**
     * Gets mude t istanzas.
     *
     * @return the mude t istanzas
    public List<MudeTIstanza> getMudeTIstanzas() {
        return mudeTIstanzas;
    }
     */

    public MudeDTipoIntervento getTipoIntervento() {
        return tipoIntervento;
    }

    public void setTipoIntervento(MudeDTipoIntervento tipoIntervento) {
        this.tipoIntervento = tipoIntervento;
    }

    //    /**
    //     * Gets intestatario.
    //     *
    //     * @return the intestatario
    //     */
    //    public MudeTContatto getIntestatario() {
    //		return intestatario;
    //	}
    //
    //    /**
    //     * Sets intestatario.
    //     *
    //     * @param intestatario the intestatario
    //     */
    //    public void setIntestatario(MudeTContatto intestatario) {
    //		this.intestatario = intestatario;
    //	}

    @Override
    public String getTableName() {
        return "mudeopen_t_fascicolo";
    }

    public String getUuidIndex() {
        return uuidIndex;
    }

    public void setUuidIndex(String uuidIndex) {
        this.uuidIndex = uuidIndex;
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

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

}