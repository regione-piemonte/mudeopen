/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;

public class MudeDTemplateSpecification {

    public MudeDTemplateSpecification() {
    }

    public static Specification<MudeDTemplate> hasTipologiaistanzaLike(final String token){
        if(StringUtils.isBlank(token)) return null;

        return new Specification<MudeDTemplate>() {
            public Predicate toPredicate(Root<MudeDTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(
            			builder.lower(root.get("mudeTipoIstanza").get("codice")), builder.lower(builder.literal(token.replaceAll("[*]", "%"))) 
            		);
            }
        };
    }

    public static Specification<MudeDTemplate> hasCodiceLike(final String token){
        if(StringUtils.isBlank(token)) return null;

        return new Specification<MudeDTemplate>() {
            public Predicate toPredicate(Root<MudeDTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(
            			builder.lower(root.get("codTemplate")), builder.lower(builder.literal("%" + token.replaceAll("[*]", "%") + "%")) 
            		);
            }
        };
    }

    public static Specification<MudeDTemplate> hasDescrizioneLike(final String token){
        if(StringUtils.isBlank(token)) return null;

        return new Specification<MudeDTemplate>() {
            public Predicate toPredicate(Root<MudeDTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(
            			builder.lower(root.get("desTemplate")), builder.lower(builder.literal("%" + token.replaceAll("[*]", "%") + "%")) 
            		);
            }
        };
    }

    public static Specification<MudeDTemplate> hasDataInizioValiditaLike(final LocalDate token){
        if(token == null) return null;

        return new Specification<MudeDTemplate>() {
            public Predicate toPredicate(Root<MudeDTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.greaterThanOrEqualTo(root.get("dataInizioValidita"), Date.from(token.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
        };
    }

    public static Specification<MudeDTemplate> hasDataCessazioneLike(final LocalDate token){
        if(token == null) return null;

        return new Specification<MudeDTemplate>() {
            public Predicate toPredicate(Root<MudeDTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.lessThanOrEqualTo(root.get("dataCessazione"), Date.from(token.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
        };
    }

    public static Specification<MudeDTemplate> hasVersione(final Long token){
        if(token == null) return null;

        return new Specification<MudeDTemplate>() {
            public Predicate toPredicate(Root<MudeDTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get("numVersione"), token);
            }
        };
    }

    public static Specification<MudeDTemplate> hasStato(final Long token){
        if(token == null) return null;

        return new Specification<MudeDTemplate>() {
            public Predicate toPredicate(Root<MudeDTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get("flgAttivo"), token);
            }
        };
    }

}