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

import org.springframework.data.jpa.domain.Specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDSpeciePratica;

public class MudeDSpeciePraticaSpecification extends BaseSpecification {

    public MudeDSpeciePraticaSpecification() {
    }

    public static Specification<MudeDSpeciePratica> isNotDeleted() {
        return new Specification<MudeDSpeciePratica>() {
            @Override
            public Predicate toPredicate(Root<MudeDSpeciePratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.isNull(root.get("dataFine"));
                return predicate;
            }
        };
    }

    public static Specification<MudeDSpeciePratica> hasCodice(final Map map) {
        return new Specification<MudeDSpeciePratica>() {
            @Override
            public Predicate toPredicate(Root<MudeDSpeciePratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("codice"), map);
            }
        };
    }

    public static Specification<MudeDSpeciePratica> hasDescrizione(final Map map) {
        return new Specification<MudeDSpeciePratica>() {
            @Override
            public Predicate toPredicate(Root<MudeDSpeciePratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("descrizione"), map);
            }
        };
    }

    public static Specification<MudeDSpeciePratica> hasDescrizioneEstesa(final Map map) {
        return new Specification<MudeDSpeciePratica>() {
            @Override
            public Predicate toPredicate(Root<MudeDSpeciePratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("descrizioneEstesa"), map);
            }
        };
    }

    public static Specification<MudeDSpeciePratica> hasVisibilita(final Map map) {
        return new Specification<MudeDSpeciePratica>() {
            @Override
            public Predicate toPredicate(Root<MudeDSpeciePratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("visibilita"), map);
            }
        };
    }
    
}