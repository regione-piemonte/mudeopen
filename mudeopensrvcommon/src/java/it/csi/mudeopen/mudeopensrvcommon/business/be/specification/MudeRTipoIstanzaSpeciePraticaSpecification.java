/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaSpeciePratica;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class MudeRTipoIstanzaSpeciePraticaSpecification extends BaseSpecification {

    public MudeRTipoIstanzaSpeciePraticaSpecification() {
    }

    public static Specification<MudeRTipoIstanzaSpeciePratica> isAbilitato() {
        return new Specification<MudeRTipoIstanzaSpeciePratica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeRTipoIstanzaSpeciePratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Boolean> path = root.get("abilitato");
                Predicate predicate = builder.equal(path, Boolean.TRUE);
                return predicate;
            }
        };
    }

    public static Specification<MudeRTipoIstanzaSpeciePratica> hasSpeciePratica(final Map map) {
        return new Specification<MudeRTipoIstanzaSpeciePratica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeRTipoIstanzaSpeciePratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("mudeDSpeciePratica").get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeRTipoIstanzaSpeciePratica> hasTipoIstanza(final Map map) {
        return new Specification<MudeRTipoIstanzaSpeciePratica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeRTipoIstanzaSpeciePratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("mudeDTipoIstanza").get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }

}