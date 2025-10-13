/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;

@Entity
@Table(name = "mudeopen_r_fabbr_civici")
public class MudeopenRFabbrCivici extends BaseEntity {
    @Column(name = "fk_civici")
    private Integer fkCivici;

    @Column(name = "fk_fabbricati")
    private Integer fkFabbricati;

    @Column(name = "fk_civici_city")
    private String fkCiviciCity;

    @Column(name = "fk_fabbricati_city")
    private String fkFabbricatiCity;

    @Id
    @Column(name = "pk_r_fabbr_civici")
    private Integer pkRFabbrCivici;

    public Integer getFkCivici() {
        return fkCivici;
    }

    public void setFkCivici(Integer fkCivici) {
        this.fkCivici = fkCivici;
    }

    public Integer getFkFabbricati() {
        return fkFabbricati;
    }

    public void setFkFabbricati(Integer fkFabbricati) {
        this.fkFabbricati = fkFabbricati;
    }

    public String getFkCiviciCity() {
        return fkCiviciCity;
    }

    public void setFkCiviciCity(String fkCiviciCity) {
        this.fkCiviciCity = fkCiviciCity;
    }

    public String getFkFabbricatiCity() {
        return fkFabbricatiCity;
    }

    public void setFkFabbricatiCity(String fkFabbricatiCity) {
        this.fkFabbricatiCity = fkFabbricatiCity;
    }

    public Integer getPkRFabbrCivici() {
        return pkRFabbrCivici;
    }

    public void setPkRFabbrCivici(Integer pkRFabbrCivici) {
        this.pkRFabbrCivici = pkRFabbrCivici;
    }

    @Override
    public String toString() {
        return "MudeopenRFabbrCivici{" +
                "fkCivici=" + fkCivici +
                ", fkFabbricati=" + fkFabbricati +
                ", fkCiviciCity=" + fkCiviciCity +
                ", fkFabbricatiCity=" + fkFabbricatiCity +
                ", pkRFabbrCivici=" + pkRFabbrCivici +
                '}';
    }
}
