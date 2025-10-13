/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.modello;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class ModelloVO implements Serializable {

    private static final long serialVersionUID = -8897267946166416410L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("denominazione")
    private String denominazione;

    @JsonProperty("path_modello")
    private String pathModello;

    @JsonProperty("dimensione_file")
    private Long dimensioneFile;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ModelloVO modelloVO = (ModelloVO) o;
        return Objects.equals(id, modelloVO.id) && Objects.equals(denominazione, modelloVO.denominazione) && Objects.equals(pathModello, modelloVO.pathModello);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, denominazione, pathModello);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ModelloVO {");
        sb.append("         id:").append(id);
        sb.append(",         denominazione:'").append(denominazione).append("'");
        sb.append(",         pathModello:'").append(pathModello).append("'");
        sb.append("}");
        return sb.toString();
    }
}