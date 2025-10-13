/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mudeopen_d_destinazione_d_uso")
public class MudeDDestinazioneUso  extends BaseDictionaryEntity implements Serializable {
    private static final long serialVersionUID = -1806855674791096234L;
}