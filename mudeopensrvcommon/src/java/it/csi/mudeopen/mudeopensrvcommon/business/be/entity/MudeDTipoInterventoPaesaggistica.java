/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_d_tipo_intervento_paesaggistica")
public class MudeDTipoInterventoPaesaggistica extends BaseDictionaryEntity implements Serializable {
    private static final long serialVersionUID = 7457895183309384207L;

    @ManyToOne
    @JoinColumn(name = "id_tipologia_tipo_intervento_paesaggistica")
    private MudeDTipologiaTipoInterventoPaesaggistica mudeDTipologiaTipoInterventoPaesaggistica;
    @Column(name = "riferimento_normativa")
    private String riferimentoNormativa;

    public MudeDTipologiaTipoInterventoPaesaggistica getMudeDTipologiaTipoInterventoPaesaggistica() {
        return mudeDTipologiaTipoInterventoPaesaggistica;
    }

    public void setMudeDTipologiaTipoInterventoPaesaggistica(MudeDTipologiaTipoInterventoPaesaggistica mudeDTipologiaTipoInterventoPaesaggistica) {
        this.mudeDTipologiaTipoInterventoPaesaggistica = mudeDTipologiaTipoInterventoPaesaggistica;
    }

    public String getRiferimentoNormativa() {
        return riferimentoNormativa;
    }

    public void setRiferimentoNormativa(String riferimentoNormativa) {
        this.riferimentoNormativa = riferimentoNormativa;
    }
}