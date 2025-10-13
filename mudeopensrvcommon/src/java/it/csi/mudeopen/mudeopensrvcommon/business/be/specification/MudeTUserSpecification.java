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

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;

public class MudeTUserSpecification extends BaseSpecification  {

    public MudeTUserSpecification() {
    }

    public static Specification<MudeTUser> hasNomeLike(final Map map){
        return new Specification<MudeTUser>() {
            @Override
            public Predicate toPredicate(Root<MudeTUser> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("nome");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeTUser> searchFO(){
        return new Specification<MudeTUser>() {
            @Override
            public Predicate toPredicate(Root<MudeTUser> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get("utenteFo"), true);
            }
        };
    }

    public static Specification<MudeTUser> hasCognomeLike(final Map map){
        return new Specification<MudeTUser>() {
            @Override
            public Predicate toPredicate(Root<MudeTUser> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("cognome");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeTUser> hasCFEquals(final String token){
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTUser>() {
            @Override
            public Predicate toPredicate(Root<MudeTUser> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("cf");
                return builder.equal(path, token.trim().toUpperCase());
            }
        };
    }

    public static Specification<MudeTUser> hasCFLike(final String token){
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTUser>() {
            @Override
            public Predicate toPredicate(Root<MudeTUser> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("cf");
                return builder.like(path, "%"+token.trim().toUpperCase()+"%");
            }
        };
    }

    public static Specification<MudeTUser> hasExcludeIDs(final String token){
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTUser>() {
            @Override
            public Predicate toPredicate(Root<MudeTUser> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> idUser = root.get("cf");
                return builder.not(idUser.in((Object[])token.split(",")));

            }
        };
    }

    public static Specification<MudeTUser> hasCF(final Map map) {
        return new Specification<MudeTUser>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTUser> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("cf");
                return getPredicateString(builder, path, map);
            }
        };
    }

}