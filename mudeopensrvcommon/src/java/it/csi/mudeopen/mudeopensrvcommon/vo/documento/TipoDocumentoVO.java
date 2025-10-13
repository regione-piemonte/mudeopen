/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.documento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
public class TipoDocumentoVO  implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 5106760606195241272L;

    @JsonProperty("id_tipo_docpa")
    private Long id;

    @JsonProperty("codice_tipo_docpa")
    private String codice;

    @JsonProperty("descrizione_tipo_docpa")
    private String descrizione;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}