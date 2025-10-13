/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeDeserializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class IstanzaStatoVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -3786766068645723984L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("istanza")
    private IstanzaVO istanza;

    @JsonProperty("stato_istanza")
    private DizionarioVO statoIstanza;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("data_inizio")
    private LocalDateTime dataInizio = null;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @JsonProperty("data_fine")
    private LocalDateTime dataFine = null;

    @JsonProperty("effettuato_da")
    private String effettuato_da;

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

    public DizionarioVO getStatoIstanza() {
        return statoIstanza;
    }

    public void setStatoIstanza(DizionarioVO statoIstanza) {
        this.statoIstanza = statoIstanza;
    }

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}

	public String getEffettuato_da() {
		return effettuato_da;
	}

	public void setEffettuato_da(String effettuato_da) {
		this.effettuato_da = effettuato_da;
	}
}