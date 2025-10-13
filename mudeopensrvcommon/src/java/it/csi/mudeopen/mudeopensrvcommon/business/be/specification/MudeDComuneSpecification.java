/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class MudeDComuneSpecification extends BaseSpecification {
    public MudeDComuneSpecification() {
    }

    public static Specification<MudeDComune> isNotDeleted() {
        return new Specification<MudeDComune>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDComune> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("fineValidita");
                Predicate predicate = builder.isNull(path);
                return predicate;
            }
        };
    }

    public static Specification<MudeDComune> hasDenominazione(final Map map) {
        return new Specification<MudeDComune>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDComune> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("denomComune");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeDComune> hasProvincia(final Map map) {
        return new Specification<MudeDComune>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDComune> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("mudeDProvincia").get("idProvincia");
                return getPredicateLong(builder, path, map);
            }
        };
    }
}