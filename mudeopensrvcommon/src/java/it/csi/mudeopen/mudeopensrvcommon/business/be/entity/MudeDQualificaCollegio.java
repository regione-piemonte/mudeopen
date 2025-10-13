/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_d_qualifica_collegio")
public class MudeDQualificaCollegio extends BaseDictionaryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
}