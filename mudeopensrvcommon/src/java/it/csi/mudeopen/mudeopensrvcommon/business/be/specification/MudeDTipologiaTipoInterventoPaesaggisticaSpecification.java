/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipologiaTipoInterventoPaesaggistica;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class MudeDTipologiaTipoInterventoPaesaggisticaSpecification extends BaseSpecification {

    public MudeDTipologiaTipoInterventoPaesaggisticaSpecification() {
    }

    public static Specification<MudeDTipologiaTipoInterventoPaesaggistica> isNotDeleted() {
        return new Specification<MudeDTipologiaTipoInterventoPaesaggistica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipologiaTipoInterventoPaesaggistica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("dataFine");
                Predicate predicate = builder.isNull(path);
                return predicate;
            }
        };
    }

    public static Specification<MudeDTipologiaTipoInterventoPaesaggistica> hasCodice(final Map map) {
        return new Specification<MudeDTipologiaTipoInterventoPaesaggistica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipologiaTipoInterventoPaesaggistica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("codice");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeDTipologiaTipoInterventoPaesaggistica> hasDescrizione(final Map map) {
        return new Specification<MudeDTipologiaTipoInterventoPaesaggistica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipologiaTipoInterventoPaesaggistica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizione");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeDTipologiaTipoInterventoPaesaggistica> hasDescrizioneEstesa(final Map map) {
        return new Specification<MudeDTipologiaTipoInterventoPaesaggistica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDTipologiaTipoInterventoPaesaggistica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("descrizioneEstesa");
                return getPredicateString(builder, path, map);
            }
        };
    }

}