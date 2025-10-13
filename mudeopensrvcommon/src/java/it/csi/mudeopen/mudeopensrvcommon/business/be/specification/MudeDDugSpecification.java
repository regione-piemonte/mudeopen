/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDitta;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class MudeDDugSpecification extends BaseSpecification {
    public MudeDDugSpecification() {
    }

    public static Specification<MudeDDug> hasDenominazione(final Map map) {
        return new Specification<MudeDDug>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDDug> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("denominazione");
                return getPredicateString(builder, path, map);
            }
        };
    }
}