/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

import java.util.ArrayList;
import java.util.List;

public class TabLocal2 {
    List<DatiCatastali> dataGrid;

    public TabLocal2() {
        dataGrid = new ArrayList<>();
    }

    public List<DatiCatastali> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(List<DatiCatastali> dataGrid) {
        this.dataGrid = dataGrid;
    }

    public void addToDataGrid(DatiCatastali data){
        dataGrid.add(data);
    }
    public void addAllToDataGrid(List<DatiCatastali> data){
        dataGrid.addAll(data);
    }

    @Override
    public String toString() {
        return "TabLocal2{" +
                "dataGrid=" + dataGrid +
                '}';
    }
}
