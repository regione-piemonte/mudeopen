/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SelectVO {
    @JsonIgnore
    private static final long serialVersionUID = -2641198993382229145L;

    private String id;

    @JsonProperty(value = "value")
    private String descrizione;

    public SelectVO() {
    }

    public SelectVO(String id, String descrizione) {
        super();
        this.id = id;
        this.descrizione = descrizione;
    }

    public SelectVO(Long id, String descrizione) {
        super();
        this.id = String.valueOf(id);
        this.descrizione = descrizione;
    }

    public String getId() {
        return id;
    }

    @JsonIgnore
    public Long loadIdAsLong(){
        return Long.parseLong(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId(Long id){
        this.id = String.valueOf(id);
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SelectVO other = (SelectVO) obj;
        if (descrizione == null) {
            if (other.descrizione != null)
                return false;
        } else if (!descrizione.equals(other.descrizione))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

//    @Override
//    public void validate(String parent) throws ValidationException {
//        boolean valid = true;
//        HashMap<String, String> detail = new HashMap<>();
//        //todo non si capisce a cosa serva... verificare
////        if (id == null || id == 0l) {
////            detail.put(parent + ".id", "not valid");
////            valid = false;
////        }
//
//        if (!valid) {
//            throw new ValidationException(detail);
//        }
//    }

}