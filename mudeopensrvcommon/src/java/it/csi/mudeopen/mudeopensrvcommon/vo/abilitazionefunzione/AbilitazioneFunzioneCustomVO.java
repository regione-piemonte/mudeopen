/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;

import java.io.Serializable;
import java.util.List;

public class AbilitazioneFunzioneCustomVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 4231200505794020238L;

    @JsonProperty("abilitazione")
    private AbilitazioneVO abilitazione;

    @JsonProperty("funzioni")
    private List<FunzioneVO> funzioni;

    @JsonProperty("id_utente")
    private Long idUtente = null;

    @JsonProperty("quadri")
    List<QuadroVO> quadri;

    public AbilitazioneVO getAbilitazione() {
        return abilitazione;
    }

    public void setAbilitazione(AbilitazioneVO abilitazione) {
        this.abilitazione = abilitazione;
    }

    public List<FunzioneVO> getFunzioni() {
        return funzioni;
    }

    public void setFunzioni(List<FunzioneVO> funzioni) {
        this.funzioni = funzioni;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public List<QuadroVO> getQuadri() {
        return quadri;
    }

    public void setQuadri(List<QuadroVO> quadri) {
        this.quadri = quadri;
    }
}