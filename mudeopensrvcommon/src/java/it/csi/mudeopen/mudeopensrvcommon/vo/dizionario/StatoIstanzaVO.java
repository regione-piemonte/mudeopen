/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.dizionario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;

import java.io.Serializable;

public class StatoIstanzaVO extends DizionarioVO implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -9008303301710468777L;
}