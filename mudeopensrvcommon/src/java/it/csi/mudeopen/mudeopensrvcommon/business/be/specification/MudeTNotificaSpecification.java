/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;

public class MudeTNotificaSpecification extends BaseSpecification {
	
    public static Specification<MudeTIstanza> hasTipoIstanza(final Map map) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("tipoIstanza").get("codice"), map);
            }
        };
    }

}