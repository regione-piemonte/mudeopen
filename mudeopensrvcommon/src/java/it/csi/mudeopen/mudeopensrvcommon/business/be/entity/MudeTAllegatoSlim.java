/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "mudeopen_t_allegato")
public class MudeTAllegatoSlim extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8582516358289116592L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_allegato")
    private Long id;

    @Column(name="id_tipo_allegato")
    private String idTipoAllegato;

    @Column(name = "nome_file_allegato")
    private String nomeFileAllegato;

    @Column(name = "id_istanza")
    private Long idIstanza;

    @Column(name = "index_node")
    private String fileUID;

    @Column(name = "mimetype")
    private String mimeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdTipoAllegato() {
        return idTipoAllegato;
    }

    public void setIdTipoAllegato(String idTipoAllegato) {
        this.idTipoAllegato = idTipoAllegato;
    }

    public String getNomeFileAllegato() {
        return nomeFileAllegato;
    }

    public void setNomeFileAllegato(String nomeFileAllegato) {
        this.nomeFileAllegato = nomeFileAllegato;
    }

    public Long getIdIstanza() {
        return idIstanza;
    }

    public void setIdIstanza(Long idIstanza) {
        this.idIstanza = idIstanza;
    }

    public String getFileUID() {
        return fileUID;
    }

    public void setFileUID(String fileUID) {
        this.fileUID = fileUID;
    }

    public String getMimeType() {
		if(nomeFileAllegato != null && nomeFileAllegato.toLowerCase().endsWith(".p7m"))
			return "application/pkcs7-mime";

		return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}