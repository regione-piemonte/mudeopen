/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRAbilitazioneFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloStato;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MudeDAbilitazioneSpecification {

    public MudeDAbilitazioneSpecification() {
    }

    public static Specification<MudeDAbilitazione> isNotDeleted() {
        return new Specification<MudeDAbilitazione>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeDAbilitazione> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("dataFine");
                return builder.isNull(path);
            }
        };
    }

    public static Specification<MudeDAbilitazione> hasCodiceAbilitazioneStartsWith(final String token){
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeDAbilitazione>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<MudeDAbilitazione> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("codice");
                return builder.like(path, token.trim().toUpperCase() + "%");
            }
        };
    }

    public static Specification<MudeDAbilitazione> hasCodiceAbilitazioneNotEndsWith(final String token){
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeDAbilitazione>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<MudeDAbilitazione> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("codice");
                return builder.notLike(path, "%" + token.trim().toUpperCase());
            }
        };
    }

    public static Specification<MudeDAbilitazione> hasCodiceAbilitazioneNotStartsWith(final String token){
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeDAbilitazione>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<MudeDAbilitazione> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("codice");
                return builder.notLike(path, token.trim().toUpperCase() + "%");
            }
        };
    }
}