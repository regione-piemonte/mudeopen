/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.tracciato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class IstanzaSlimLogTracciatoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -446239407758081994L;

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("codice_istanza")
    private String codiceIstanza;

    @JsonProperty("codice_tipo_istanza")
    private String codiceTipoIstanza;

    @JsonProperty("codice_tipo_tracciato")
    private String codiceTipoTracciato;

    @JsonProperty("versione_tracciato")
    private String versioneTracciato;

    @JsonProperty("data_generazione_tracciato")
    private Date dataGenerazioneTracciato;

    @JsonProperty("utente")
    private UtenteSlim utenteSlim;

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getCodiceIstanza() {
        return codiceIstanza;
    }

    public void setCodiceIstanza(String codiceIstanza) {
        this.codiceIstanza = codiceIstanza;
    }

    public String getCodiceTipoIstanza() {
        return codiceTipoIstanza;
    }

    public void setCodiceTipoIstanza(String codiceTipoIstanza) {
        this.codiceTipoIstanza = codiceTipoIstanza;
    }

    public String getCodiceTipoTracciato() {
        return codiceTipoTracciato;
    }

    public void setCodiceTipoTracciato(String codiceTipoTracciato) {
        this.codiceTipoTracciato = codiceTipoTracciato;
    }

    public String getVersioneTracciato() {
        return versioneTracciato;
    }

    public void setVersioneTracciato(String versioneTracciato) {
        this.versioneTracciato = versioneTracciato;
    }

    public Date getDataGenerazioneTracciato() {
        return dataGenerazioneTracciato;
    }

    public void setDataGenerazioneTracciato(Date dataGenerazioneTracciato) {
        this.dataGenerazioneTracciato = dataGenerazioneTracciato;
    }

    public UtenteSlim getUtenteSlim() {
        return utenteSlim;
    }

    public void setUtenteSlim(UtenteSlim utenteSlim) {
        this.utenteSlim = utenteSlim;
    }
}