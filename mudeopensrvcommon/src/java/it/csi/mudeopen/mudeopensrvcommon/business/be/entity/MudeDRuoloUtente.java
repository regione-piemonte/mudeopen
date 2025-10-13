/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_d_ruolo_utente")
public class MudeDRuoloUtente extends BaseDictionaryEntity implements Serializable {
    private static final long serialVersionUID = -261050313328162861L;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "mudeopen_r_ruolo_funzione", 
        joinColumns = { @JoinColumn(name = "codice_ruolo_utente", nullable = false, updatable = false) }, 
        inverseJoinColumns = { @JoinColumn(name = "id_funzione", nullable = false, updatable = false) }
    )
    private Set<MudeDFunzione> funzioni = new HashSet<>();
}