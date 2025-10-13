/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "mudeopen_d_ordine")
public class MudeDOrdine extends BaseDictionaryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to MudeTIndirizzo
	@OneToMany(mappedBy="mudeDOrdine")
	private List<MudeRContattoQualifica> mudeRContattoQualifica;

	public List<MudeRContattoQualifica> getMudeRContattoQualifica() {
		return mudeRContattoQualifica;
	}

	public void setMudeRContattoQualifica(List<MudeRContattoQualifica> mudeRContattoQualifica) {
		this.mudeRContattoQualifica = mudeRContattoQualifica;
	}
}