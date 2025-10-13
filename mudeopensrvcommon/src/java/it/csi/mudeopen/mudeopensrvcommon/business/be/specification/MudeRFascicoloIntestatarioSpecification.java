/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIntestatario;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MudeRFascicoloIntestatarioSpecification {

    public MudeRFascicoloIntestatarioSpecification() {
    }

    public static Specification<MudeRFascicoloIntestatario> isNotDeleted() {
        return new Specification<MudeRFascicoloIntestatario>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<MudeRFascicoloIntestatario> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("dataFine");
                return builder.isNull(path);
            }
        };
    }

    public static Specification<MudeRFascicoloIntestatario> isDeleted() {
        return new Specification<MudeRFascicoloIntestatario>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<MudeRFascicoloIntestatario> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("dataFine");
                return builder.isNotNull(path);
            }
        };
    }

    public static Specification<MudeRFascicoloIntestatario> hasFascicoloEquals(final Long token){
        if(null == token) return null;
        return new Specification<MudeRFascicoloIntestatario>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<MudeRFascicoloIntestatario> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("fascicolo").get("id");
                return builder.equal(path, token);
            }
        };
    }

    public static Specification<MudeRFascicoloIntestatario> hasIntestatarioEquals(final Long token){
        if(null == token) return null;
        return new Specification<MudeRFascicoloIntestatario>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<MudeRFascicoloIntestatario> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("intestatario").get("id");
                return builder.equal(path, token);
            }
        };
    }
}