/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoInterventoPaesaggisticaVO extends DizionarioVO{
    private static final long serialVersionUID = 3758622830668679916L;
    @JsonProperty("gruppo_tipo_intervento_paesaggistica")
    private DizionarioVO tipologiaTipoInterventoPaesaggistica;

    @JsonProperty("riferimento_normativa")
    private String riferimentoNormativa;

    public DizionarioVO getTipologiaTipoInterventoPaesaggistica() {
        return tipologiaTipoInterventoPaesaggistica;
    }

    public void setTipologiaTipoInterventoPaesaggistica(DizionarioVO tipologiaTipoInterventoPaesaggistica) {
        this.tipologiaTipoInterventoPaesaggistica = tipologiaTipoInterventoPaesaggistica;
    }

    public String getRiferimentoNormativa() {
        return riferimentoNormativa;
    }

    public void setRiferimentoNormativa(String riferimentoNormativa) {
        this.riferimentoNormativa = riferimentoNormativa;
    }
}