/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class MudeDRegioneSpecification extends BaseSpecification {
    public MudeDRegioneSpecification() {
    }

    public static Specification<MudeDRegione> isNotDeleted() {
        return new Specification<MudeDRegione>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDRegione> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("fineValidita");
                Predicate predicate = builder.isNull(path);
                return predicate;
            }
        };
    }

    public static Specification<MudeDRegione> hasDenominazione(final Map map) {
        return new Specification<MudeDRegione>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDRegione> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("denomRegione");
                return getPredicateString(builder, path, map);
            }
        };
    }
}