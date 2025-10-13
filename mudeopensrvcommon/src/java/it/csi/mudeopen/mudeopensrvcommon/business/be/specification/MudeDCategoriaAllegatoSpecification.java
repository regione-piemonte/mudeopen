/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDCategoriaAllegato;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MudeDCategoriaAllegatoSpecification {
    public MudeDCategoriaAllegatoSpecification() {
    }

    public static Specification<MudeDCategoriaAllegato> isNotDeleted() {
        return new Specification<MudeDCategoriaAllegato>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDCategoriaAllegato> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("dataFine");
                Predicate predicate = builder.isNull(path);
                return predicate;
            }
        };
    }

    public static Specification<MudeDCategoriaAllegato> hasCodiceLike(final String token) {
        if (StringUtils.isBlank(token))
            return null;
        return new Specification<MudeDCategoriaAllegato>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDCategoriaAllegato> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("codice");
                return builder.like(path, "%" + token.trim().toUpperCase() + "%");
            }
        };
    }

    public static Specification<MudeDCategoriaAllegato> hasDescrizioneLike(final String token) {
        if (StringUtils.isBlank(token))
            return null;
        return new Specification<MudeDCategoriaAllegato>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDCategoriaAllegato> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizione");
                return builder.like(path, "%" + token.trim().toUpperCase() + "%");
            }
        };
    }

    public static Specification<MudeDCategoriaAllegato> hasDescrizioneEstesaLike(final String token) {
        if (StringUtils.isBlank(token))
            return null;
        return new Specification<MudeDCategoriaAllegato>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDCategoriaAllegato> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizioneEstesa");
                return builder.like(path, "%" + token.trim().toUpperCase() + "%");
            }
        };
    }
}