/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRSpeciePraticaTipoIntervento;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class MudeRSpeciePraticaTipoInterventoSpecification extends BaseSpecification {
    public MudeRSpeciePraticaTipoInterventoSpecification() {
    }

    public static Specification<MudeRSpeciePraticaTipoIntervento> isAbilitato() {
        return new Specification<MudeRSpeciePraticaTipoIntervento>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeRSpeciePraticaTipoIntervento> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Boolean> path = root.get("abilitato");
                Predicate predicate = builder.equal(path, Boolean.TRUE);
                return predicate;
            }
        };
    }

    public static Specification<MudeRSpeciePraticaTipoIntervento> hasTipoIntervento(final Map map) {
        return new Specification<MudeRSpeciePraticaTipoIntervento>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeRSpeciePraticaTipoIntervento> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("mudeDTipoIntervento").get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeRSpeciePraticaTipoIntervento> hasSpeciePratica(final Map map) {
        return new Specification<MudeRSpeciePraticaTipoIntervento>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeRSpeciePraticaTipoIntervento> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("mudeDSpeciePratica").get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }
}