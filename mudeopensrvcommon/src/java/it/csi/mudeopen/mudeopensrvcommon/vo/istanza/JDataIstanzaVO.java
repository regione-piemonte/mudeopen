/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.csi.mudeopen.mudeopensrvcommon.vo.ParentVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.abilitazionefunzione.AbilitazioneFunzioneSlimCustomVO;

import java.util.List;

public class JDataIstanzaVO extends ParentVO {
    private static final long serialVersionUID = -3921724086179323695L;

    //////////////////////
    // input

    @JsonProperty("id_istanza")
    private Long idIstanza;

    @JsonProperty("is_obbligatoria_nomina_pm")
    private Boolean isObbligatoriaNominaPM;

    @JsonProperty("id_quadro")
    private Long idQuadro;

    @JsonProperty("cod_tipo_quadro")
    private String codTipoQuadro;

    //////////////////////
    // output

    @JsonProperty("quadro_modificabile")
    private boolean quadroModificabile;

    @JsonProperty("json_data")
    private String jsonData;

    @JsonProperty("abilitazioni")
    private List<AbilitazioneFunzioneSlimCustomVO> abilitazioni;

    @JsonProperty("stato_istanza")
    private String statoIstanza;

    @JsonProperty("nuovo_indirizzo")
    private Boolean nuovoIndirizzo = false;

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public Boolean getIsObbligatoriaNominaPM() {
        return isObbligatoriaNominaPM;
    }

    public void setIsObbligatoriaNominaPM(Boolean isObbligatoriaNominaPM) {
        this.isObbligatoriaNominaPM = isObbligatoriaNominaPM;
    }

    public Long getIdQuadro() {
        return idQuadro;
    }

    public void setIdQuadro(Long idQuadro) {
        this.idQuadro = idQuadro;
    }

    public String getCodTipoQuadro() {
        return codTipoQuadro;
    }

    public void setCodTipoQuadro(String codTipoQuadro) {
        this.codTipoQuadro = codTipoQuadro;
    }

    public boolean isQuadroModificabile() {
        return quadroModificabile;
    }

    public void setQuadroModificabile(boolean quadroModificabile) {
        this.quadroModificabile = quadroModificabile;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public List<AbilitazioneFunzioneSlimCustomVO> getAbilitazioni() {
        return abilitazioni;
    }

    public void setAbilitazioni(List<AbilitazioneFunzioneSlimCustomVO> abilitazioni) {
        this.abilitazioni = abilitazioni;
    }

	public String getStatoIstanza() {
		return statoIstanza;
	}

	public void setStatoIstanza(String statoIstanza) {
		this.statoIstanza = statoIstanza;
	}

	public Boolean getNuovoIndirizzo() {
		return nuovoIndirizzo;
	}

	public void setNuovoIndirizzo(Boolean nuovoIndirizzo) {
		this.nuovoIndirizzo = nuovoIndirizzo;
	}
}