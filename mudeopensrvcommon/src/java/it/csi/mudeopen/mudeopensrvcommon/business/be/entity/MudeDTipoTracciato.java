/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_d_tipo_tracciato")
public class MudeDTipoTracciato extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4939418286660804201L;

    @Id
    @Column(name = "id_tipo_tracciato")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoTracciatoSeqGenerator")
    //    @SequenceGenerator(name = "TipoTracciatoSeqGenerator", sequenceName = "mudeopen_d_tipo_tracciato_id_tipo_tracciato_seq", allocationSize = 1)
    private Long id;

    @Column(name = "codice")
    private String codice;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "versione")
    private String versione;

    @Column(name = "xsd_validazione")
    private String xsdValidazione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
    }

    public String getXsdValidazione() {
        return xsdValidazione;
    }

    public void setXsdValidazione(String xsdValidazione) {
        this.xsdValidazione = xsdValidazione;
    }
}