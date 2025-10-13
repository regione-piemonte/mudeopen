/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_t_modello")
public class MudeTModello extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -9110433609297411435L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modello")
    private Long id;

    @Column(name = "denominazione")
    private String denominazione;

    @Column(name = "path_modello")
    private String pathModello;

    @Column(name = "dimensione_file")
    private Long dimensioneFile;

    @Column(name = "file_content")
    byte[] fileContent;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getPathModello() {
        return pathModello;
    }

    public void setPathModello(String pathModello) {
        this.pathModello = pathModello;
    }

    public Long getDimensioneFile() {
        return dimensioneFile;
    }

    public void setDimensioneFile(Long dimensioneFile) {
        this.dimensioneFile = dimensioneFile;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

}