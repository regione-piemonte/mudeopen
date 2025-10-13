/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.utente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

import java.io.Serializable;

public class IstanzaUtenteVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -8332295733602645691L;

    @JsonProperty("id_istanza_utente")
    private Long id;

    @JsonProperty("istanza")
    private IstanzaVO istanza;

    @JsonProperty("utente")
    private UtenteVO utente;

    @JsonProperty("abilitazione")
    private AbilitazioneVO abilitazione;

    @JsonProperty("abilitatore")
    private UtenteVO abilitatore;

    @JsonProperty("modificabile")
    private Boolean modificabile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IstanzaVO getIstanza() {
        return istanza;
    }

    public void setIstanza(IstanzaVO istanza) {
        this.istanza = istanza;
    }

    public UtenteVO getUtente() {
        return utente;
    }

    public void setUtente(UtenteVO utente) {
        this.utente = utente;
    }

    public AbilitazioneVO getAbilitazione() {
        return abilitazione;
    }

    public void setAbilitazione(AbilitazioneVO abilitazione) {
        this.abilitazione = abilitazione;
    }

    public UtenteVO getAbilitatore() {
        return abilitatore;
    }

    public void setAbilitatore(UtenteVO abilitatore) {
        this.abilitatore = abilitatore;
    }

	public Boolean getModificabile() {
		return modificabile;
	}

	public void setModificabile(Boolean modificabile) {
		this.modificabile = modificabile;
	}
}