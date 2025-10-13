/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.tracciato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

import java.io.Serializable;
import java.util.Date;

public class IstanzaTracciatoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 290549643279958010L;

    @JsonProperty("id_istanza_tracciato")
    private Long id;

    @JsonProperty("tipo_tracciato")
    private TipoTracciatoVO tipoTracciato;

    @JsonProperty("istanza")
    private Long idIstanza;

    @JsonProperty("xml_tracciato")
    private String xmlTracciato;

    @JsonProperty("data_generazione_tracciato")
    private Date dataGenerazioneTracciato;

    @JsonProperty("utente")
    private UtenteSlim utenteSlim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTracciatoVO getTipoTracciato() {
        return tipoTracciato;
    }

    public void setTipoTracciato(TipoTracciatoVO tipoTracciato) {
        this.tipoTracciato = tipoTracciato;
    }

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getXmlTracciato() {
        return xmlTracciato;
    }

    public void setXmlTracciato(String xmlTracciato) {
        this.xmlTracciato = xmlTracciato;
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