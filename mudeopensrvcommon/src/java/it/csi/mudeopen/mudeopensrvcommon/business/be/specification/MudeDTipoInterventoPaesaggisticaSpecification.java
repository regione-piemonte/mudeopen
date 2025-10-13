/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoInterventoPaesaggistica;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class MudeDTipoInterventoPaesaggisticaSpecification extends BaseSpecification {

    public MudeDTipoInterventoPaesaggisticaSpecification() {
    }

    public static Specification<MudeDTipoInterventoPaesaggistica> isNotDeleted() {
        return new Specification<MudeDTipoInterventoPaesaggistica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipoInterventoPaesaggistica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("dataFine");
                Predicate predicate = builder.isNull(path);
                return predicate;
            }
        };
    }

    public static Specification<MudeDTipoInterventoPaesaggistica> hasCodice(final Map map) {
        return new Specification<MudeDTipoInterventoPaesaggistica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipoInterventoPaesaggistica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeDTipoInterventoPaesaggistica> hasDescrizione(final Map map) {
        return new Specification<MudeDTipoInterventoPaesaggistica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipoInterventoPaesaggistica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizione");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeDTipoInterventoPaesaggistica> hasDescrizioneEstesa(final Map map) {
        return new Specification<MudeDTipoInterventoPaesaggistica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipoInterventoPaesaggistica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizioneEstesa");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeDTipoInterventoPaesaggistica> hasTipologiaTipoInterventoPaesaggistica(final Map map) {
        return new Specification<MudeDTipoInterventoPaesaggistica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipoInterventoPaesaggistica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("mudeDTipologiaTipoInterventoPaesaggistica").get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }

}