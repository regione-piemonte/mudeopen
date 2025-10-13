/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

import java.util.ArrayList;
import java.util.List;

public class TabLocal1 {
    String relativo_all_immobile;
    List<DatiUbicazione> dataGrid;

    public TabLocal1() {
        dataGrid = new ArrayList<>();
    }

    public String getRelativo_all_immobile() {
        return relativo_all_immobile;
    }

    public void setRelativo_all_immobile(String relativo_all_immobile) {
        this.relativo_all_immobile = relativo_all_immobile;
    }

    public List<DatiUbicazione> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(List<DatiUbicazione> dataGrid) {
        this.dataGrid = dataGrid;
    }

    public void addToDataGrid(DatiUbicazione data){
        dataGrid.add(data);
    }
    public void addAllToDataGrid(List<DatiUbicazione> data){
        dataGrid.addAll(data);
    }

    @Override
    public String toString() {
        return "TabLocal1{" +
                "relativo_all_immobile='" + relativo_all_immobile + '\'' +
                ", dataGrid=" + dataGrid +
                '}';
    }
}
