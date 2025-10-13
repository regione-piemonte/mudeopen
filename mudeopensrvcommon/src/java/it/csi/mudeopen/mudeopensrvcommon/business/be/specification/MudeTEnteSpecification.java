/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTitolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MudeTEnteSpecification {

    public MudeTEnteSpecification() {
    }

    public static Specification<MudeTEnte> isNotDeleted() {
        return new Specification<MudeTEnte>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTEnte> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("dataFine");
                Predicate predicate = builder.isNull(path);
                return predicate;
            }
        };
    }

    public static Specification<MudeTEnte> hasCodiceLike(final String token) {
        if (StringUtils.isBlank(token))
            return null;
        return new Specification<MudeTEnte>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTEnte> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("codice");
                return builder.like(path, "%" + token.trim().toUpperCase() + "%");
            }
        };
    }

    public static Specification<MudeTEnte> hasDescrizioneLike(final String token) {
        if (StringUtils.isBlank(token))
            return null;
        return new Specification<MudeTEnte>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTEnte> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizione");
                return builder.like(path, "%" + token.trim().toUpperCase() + "%");
            }
        };
    }

    public static Specification<MudeTEnte> hasDescrizioneEstesaLike(final String token) {
        if (StringUtils.isBlank(token))
            return null;
        return new Specification<MudeTEnte>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTEnte> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizioneEstesa");
                return builder.like(path, "%" + token.trim().toUpperCase() + "%");
            }
        };
    }
}