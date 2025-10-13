/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaTipoOpera;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class MudeRTipoIstanzaTipoOperaDirettaSpecification extends BaseSpecification {

    public MudeRTipoIstanzaTipoOperaDirettaSpecification() {
    }

    public static Specification<MudeRTipoIstanzaTipoOpera> isAbilitato() {
        return new Specification<MudeRTipoIstanzaTipoOpera>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeRTipoIstanzaTipoOpera> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Boolean> path = root.get("abilitato");
                Predicate predicate = builder.equal(path, Boolean.TRUE);
                return predicate;
            }
        };
    }

    public static Specification<MudeRTipoIstanzaTipoOpera> hasTipoIstanza(final Map map) {
        return new Specification<MudeRTipoIstanzaTipoOpera>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeRTipoIstanzaTipoOpera> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("mudeDTipoIstanza").get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeRTipoIstanzaTipoOpera> hasTipoOpera(final Map map) {
        return new Specification<MudeRTipoIstanzaTipoOpera>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeRTipoIstanzaTipoOpera> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("mudeDTipoOpera").get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }
}