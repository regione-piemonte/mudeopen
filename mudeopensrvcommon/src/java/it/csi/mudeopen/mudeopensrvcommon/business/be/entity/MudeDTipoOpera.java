/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.BaseDictionaryEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_d_tipo_opera")
public class MudeDTipoOpera extends BaseDictionaryEntity implements Serializable {
    private static final long serialVersionUID = 7457895183309384207L;
}