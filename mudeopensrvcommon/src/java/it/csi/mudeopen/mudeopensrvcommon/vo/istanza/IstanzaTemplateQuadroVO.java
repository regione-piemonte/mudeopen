/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;

import java.io.Serializable;
public class IstanzaTemplateQuadroVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1094432622212150201L;

    private MudeTIstanza mudeTIstanza;

    private MudeRTemplateQuadro mudeRTemplateQuadro;

    public MudeTIstanza getMudeTIstanza() {
        return mudeTIstanza;
    }

    public void setMudeTIstanza(MudeTIstanza mudeTIstanza) {
        this.mudeTIstanza = mudeTIstanza;
    }

    public MudeRTemplateQuadro getMudeRTemplateQuadro() {
        return mudeRTemplateQuadro;
    }

    public void setMudeRTemplateQuadro(MudeRTemplateQuadro mudeRTemplateQuadro) {
        this.mudeRTemplateQuadro = mudeRTemplateQuadro;
    }
}