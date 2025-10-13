/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.tracciato;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class LogTracciatoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 5038016813073651066L;

    @JsonProperty("id_istanza_tracciato")
    private Long id;

    @JsonProperty("istanza")
    private IstanzaSlimLogTracciatoVO istanza;

    @JsonProperty("tipoTracciato")
    private TipoTracciatoVO tipoTracciato;

    @JsonProperty("errore")
    private String errore;

    @JsonProperty("tipo_errore")
    private String tipoErrore;

    @JsonProperty("error_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date errorTime;

    @JsonProperty("utente")
    private UtenteSlim utente;

    @JsonProperty("mail_inviata")
    private Boolean mailInviata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IstanzaSlimLogTracciatoVO getIstanza() {
        return istanza;
    }

    public void setIstanza(IstanzaSlimLogTracciatoVO istanza) {
        this.istanza = istanza;
    }

    public TipoTracciatoVO getTipoTracciato() {
        return tipoTracciato;
    }

    public void setTipoTracciato(TipoTracciatoVO tipoTracciato) {
        this.tipoTracciato = tipoTracciato;
    }

    public String getErrore() {
        return errore;
    }

    public void setErrore(String errore) {
        this.errore = errore;
    }

    public String getTipoErrore() {
        return tipoErrore;
    }

    public void setTipoErrore(String tipoErrore) {
        this.tipoErrore = tipoErrore;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public UtenteSlim getUtente() {
        return utente;
    }

    public void setUtente(UtenteSlim utente) {
        this.utente = utente;
    }

    public Boolean getMailInviata() {
        return mailInviata;
    }

    public void setMailInviata(Boolean mailInviata) {
        this.mailInviata = mailInviata;
    }
}