/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.quadri;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloVO;

import java.io.Serializable;

@JsonInclude(Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 2371923718553042186L;

    @JsonProperty("id_template")
    private Long idTemplate;

    @JsonProperty("tipo_istanza")
    private TipoIstanzaVO tipoIstanza;

    @JsonProperty("cod_template")
    private String codTemplate;

    @JsonProperty("des_template")
    private String desTemplate;

    @JsonProperty("data_inizio_validita")
    private String dataInizioValidita;

    @JsonProperty("data_cessazione")
    private String dataCessazione;

    @JsonProperty("flg_attivo")
    private long flgAttivo;

    @JsonProperty("json_configura_template")
    private String jsonConfiguraTemplate;

    @JsonProperty("modello_documentale")
    private ModelloVO modello;

    @JsonProperty("modello_intestazione")
    private ModelloVO modelloIntestazione;

    @JsonProperty("num_versione")
    private Integer numeroVersione;

    @JsonProperty("modificabile")
    private boolean modificabile;

    @JsonProperty("ultima_versione")
    private boolean ultimaVersione;

    @JsonProperty("obbligatoria_nomina_pm")
    private Boolean obbligatoriaNominaPM;

    public boolean isModificabile() {
        return modificabile;
    }

    public void setModificabile(boolean modificabile) {
        this.modificabile = modificabile;
    }

    public Long getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Long idTemplate) {
        this.idTemplate = idTemplate;
    }

    public TipoIstanzaVO getTipoIstanza() {
        return tipoIstanza;
    }

    public void setTipoIstanza(TipoIstanzaVO tipoIstanza) {
        this.tipoIstanza = tipoIstanza;
    }

    public String getCodTemplate() {
        return codTemplate;
    }

    public void setCodTemplate(String codTemplate) {
        this.codTemplate = codTemplate;
    }

    public String getDesTemplate() {
        return desTemplate;
    }

    public void setDesTemplate(String desTemplate) {
        this.desTemplate = desTemplate;
    }

    public String getDataInizioValidita() {
        return dataInizioValidita;
    }

    public void setDataInizioValidita(String dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }

    public String getDataCessazione() {
        return dataCessazione;
    }

    public void setDataCessazione(String dataCessazione) {
        this.dataCessazione = dataCessazione;
    }

    public long getFlgAttivo() {
        return flgAttivo;
    }

    public void setFlgAttivo(long flgAttivo) {
        this.flgAttivo = flgAttivo;
    }

    public String getJsonConfiguraTemplate() {
        return jsonConfiguraTemplate;
    }

    public void setJsonConfiguraTemplate(String jsonConfiguraTemplate) {
        this.jsonConfiguraTemplate = jsonConfiguraTemplate;
    }

    public ModelloVO getModello() {
        return modello;
    }

    public void setModello(ModelloVO modello) {
        this.modello = modello;
    }

    public Integer getNumeroVersione() {
        return numeroVersione;
    }

    public void setNumeroVersione(Integer numeroVersione) {
        this.numeroVersione = numeroVersione;
    }

    public boolean isUltimaVersione() {
        return ultimaVersione;
    }

    public void setUltimaVersione(boolean ultimaVersione) {
        this.ultimaVersione = ultimaVersione;
    }

    public ModelloVO getModelloIntestazione() {
        return modelloIntestazione;
    }

    public void setModelloIntestazione(ModelloVO modelloIntestazione) {
        this.modelloIntestazione = modelloIntestazione;
    }

    public Boolean getObbligatoriaNominaPM() {
        return obbligatoriaNominaPM;
    }

    public void setObbligatoriaNominaPM(Boolean obbligatoriaNominaPM) {
        this.obbligatoriaNominaPM = obbligatoriaNominaPM;
    }

}