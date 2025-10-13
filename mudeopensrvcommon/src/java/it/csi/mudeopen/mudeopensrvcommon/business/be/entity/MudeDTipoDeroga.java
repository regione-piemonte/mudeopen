/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_d_tipo_deroga")
public class MudeDTipoDeroga  extends BaseDictionaryEntity implements Serializable {
    private static final long serialVersionUID = -5709565701192162263L;
}