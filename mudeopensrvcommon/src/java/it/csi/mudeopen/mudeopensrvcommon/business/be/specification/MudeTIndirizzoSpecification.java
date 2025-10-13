/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;

public class MudeTIndirizzoSpecification {

    public static Specification<MudeTIndirizzo> findBy(List<Long> mudeDComuneList,
													   Long idDug,
													   String duf) {
		return new Specification<MudeTIndirizzo>() {
			@Override
			public Predicate toPredicate(Root<MudeTIndirizzo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();

				if (mudeDComuneList != null) {
					final Path<Long> group = root.get("mudeDComune").get("idComune");
					predicates.add(group.in(mudeDComuneList));
				}
				if (idDug != null)
					predicates.add(builder.equal(root.get("idDug"), idDug));
				
				if (duf != null)
					predicates.add(builder.equal(root.get("localita"), duf));

				return builder.and(predicates.toArray(new Predicate[0]));
			}
		};
	}

}