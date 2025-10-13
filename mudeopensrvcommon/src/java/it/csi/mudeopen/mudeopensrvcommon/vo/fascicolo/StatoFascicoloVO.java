/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

import java.io.Serializable;

public class StatoFascicoloVO extends DizionarioVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -6834125077941869030L;
}