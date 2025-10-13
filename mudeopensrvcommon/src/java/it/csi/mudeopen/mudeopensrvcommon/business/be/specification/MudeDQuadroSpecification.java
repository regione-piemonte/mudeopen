/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;

public class MudeDQuadroSpecification {

    public MudeDQuadroSpecification() {
    }

    public static Specification<MudeDQuadro> hasTipoQuadroLike(final String token){
        if(StringUtils.isBlank(token)) return null;

        return new Specification<MudeDQuadro>() {
            public Predicate toPredicate(Root<MudeDQuadro> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(
            			builder.lower(root.get("mudeDTipoQuadro").get("codTipoQuadro")), builder.lower(builder.literal("%" + token.replaceAll("[*]", "%") + "%")) 
            		);
            }
        };
    }

    public static Specification<MudeDQuadro> hasTipoGestioneLike(final String token){
        if(StringUtils.isBlank(token)) return null;

        return new Specification<MudeDQuadro>() {
            public Predicate toPredicate(Root<MudeDQuadro> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(
            			builder.lower(root.get("flgTipoGestione")), builder.lower(builder.literal("%" + token.replaceAll("[*]", "%") + "%")) 
            		);
            }
        };
    }

    public static Specification<MudeDQuadro> hasVersione(final Long token){
        if(token == null) return null;

        return new Specification<MudeDQuadro>() {
            public Predicate toPredicate(Root<MudeDQuadro> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	if(token == -1) { // -1 means all the last versions
            		Subquery<Long> sub = query.subquery(Long.class);     
            		Root<MudeDQuadro> subRoot = sub.from(MudeDQuadro.class);     
            		sub.select(builder.max(subRoot.get("numVersione")))
            		   .where(builder.equal(root.get("mudeDTipoQuadro").get("idTipoQuadro"), subRoot.get("mudeDTipoQuadro").get("idTipoQuadro")));
            		return builder.equal(root.get("numVersione"), sub);
            	}
            	
                return builder.equal(root.get("numVersione"), token);
            }
        };
    }

}