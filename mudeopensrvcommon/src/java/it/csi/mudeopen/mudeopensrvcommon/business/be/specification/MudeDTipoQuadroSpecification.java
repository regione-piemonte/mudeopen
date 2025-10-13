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

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoQuadro;

public class MudeDTipoQuadroSpecification {
    public static Specification<MudeDTipoQuadro> hasCodeLike(final String token){
        if(StringUtils.isBlank(token)) return null;

        return new Specification<MudeDTipoQuadro>() {
            @Override
            public Predicate toPredicate(Root<MudeDTipoQuadro> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("codTipoQuadro");
                return builder.like(
                			builder.lower(root.get("codTipoQuadro")), builder.lower(builder.literal("%" + token.replaceAll("[*]", "%") + "%")) 
                		);
            }
        };
    }

    public static Specification<MudeDTipoQuadro> hasDescrLike(final String token){
        if(StringUtils.isBlank(token)) return null;

        return new Specification<MudeDTipoQuadro>() {
            @Override
            public Predicate toPredicate(Root<MudeDTipoQuadro> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(
            			builder.lower(root.get("desTipoQuadro")), builder.lower(builder.literal("%" + token.replaceAll("[*]", "%") + "%")) 
            		);
            }
        };
    }

    public static Specification<MudeDTipoQuadro> hasIdCategoriaQuadro(final Long token){
        if(token == null) return null;

        return new Specification<MudeDTipoQuadro>() {
            @Override
            public Predicate toPredicate(Root<MudeDTipoQuadro> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get("idCategoriaQuadro"), token);
            }
        };
    }

}