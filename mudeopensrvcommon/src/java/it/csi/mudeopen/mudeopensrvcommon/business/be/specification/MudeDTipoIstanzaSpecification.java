/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class MudeDTipoIstanzaSpecification extends BaseSpecification {

    public MudeDTipoIstanzaSpecification() {
    }

    public static Specification<MudeDTipoIstanza> isNotDeleted() {
        return new Specification<MudeDTipoIstanza>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipoIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("dataFine");
                Predicate predicate = builder.isNull(path);
                return predicate;
            }
        };
    }

    public static Specification<MudeDTipoIstanza> hasCodice(final Map map) {
        return new Specification<MudeDTipoIstanza>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipoIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeDTipoIstanza> hasDescrizione(final Map map) {
        return new Specification<MudeDTipoIstanza>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipoIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizione");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeDTipoIstanza> hasDescrizioneEstesa(final Map map) {
        return new Specification<MudeDTipoIstanza>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipoIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizioneEstesa");
                return getPredicateString(builder, path, map);
            }
        };
    }

}