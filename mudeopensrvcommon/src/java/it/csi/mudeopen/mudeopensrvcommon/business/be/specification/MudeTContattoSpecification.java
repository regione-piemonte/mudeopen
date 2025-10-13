/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class MudeTContattoSpecification extends BaseSpecification {

    public MudeTContattoSpecification() {
    }

    public static Specification<MudeTContatto> isNotDeleted() {
        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Long> path = root.get("dataCessazione");
                Predicate predicate = builder.isNull(path);
                return predicate;
            }
        };
    }

    public static Specification<MudeTContatto> hasNome(final Map map) {
        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("nome");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeTContatto> hasCognome(final Map map) {
        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("cognome");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeTContatto> hasRagioneSociale(final Map map) {

        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("ragioneSociale");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeTContatto> hasCF(final Map map) {
        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("cf");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeTContatto> hasPiva(final Map map) {

        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("piva");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeTContatto> hasBooleanField(final Map map, final String fieldName) {
        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Boolean> path = root.get(fieldName);
                return getPredicateBoolean(builder, path, map);
            }
        };
    }

    public static Specification<MudeTContatto> hasPivaComunitaria(final Map map) {
        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("pivaComunitaria");
                return getPredicateString(builder, path, map);
            }
        };
    }

    public static Specification<MudeTContatto> hasStringList(final Map map) {
        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<List<String>> path = root.get("cf");
                return getPredicateListString(builder, path, map);
            }
        };
    }

    public static Specification<MudeTContatto> hasUserEquals(final String token) {
        if (token == null)
            return null;
        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> path = root.get("mudeTUser").get("cf");
                return builder.equal(path, token.trim().toUpperCase());
            }
        };
    }

    public static Specification<MudeTContatto> hasTipoContattoEquals(final TipoContatto token) {
        return new Specification<MudeTContatto>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<TipoContatto> path = root.get("tipoContatto");
                return builder.equal(path, token);
            }
        };
    }

    public static Specification<MudeTContatto> hasExcludeIDs(final String token){
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTContatto>() {
            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<String> idUser = root.get("id");
                return builder.not(idUser.in((Object[])token.split(",")));
            }
        };
    }

    /*
     * IMPLEMENTS:
		select
			distinct mudetconta0_.*
		from
			mudeopen_t_contatto mudetconta0_
		where
			(mudetconta0_.data_cessazione is null)
			and mudetconta0_.tipo_contatto = 'PF'
			and (mudetconta0_.id in (
							select
								mudetconta0_.id
							from
								mudeopen_r_istanza_utente muderistan2_
								inner join mudeopen_t_user mudetuser3_ on muderistan2_.id_utente = mudetuser3_.id_user
							where
								mudetuser3_.cf = mudetconta0_.cf
								and muderistan2_.id_istanza = 2548
								and muderistan2_.id_utente = mudetconta0_.id_user
								and (muderistan2_.data_fine is null)
								and (mudetconta0_.data_cessazione is null)
								AND NOT(mudetuser3_.id_user=5)
				)
				or (mudetconta0_.id IN (
						SELECT c2.id 
						FROM 
							mudeopen_t_contatto c2
							inner join mudeopen_t_user u2 ON u2.id_user = c2.id_user
						WHERE c2.cf = u2.cf 
							AND c2.cf IN (SELECT cf FROM mudeopen_t_contatto WHERE id_user=5)  -- ONLY Cfs FROM PRIVATE ADDRESS BOOK
							-- AND NOT(u2.id_user=5) -- INCLUDE ALL ACCREDITED
					)
				)
				or (mudetconta0_.id_user=5 AND mudetconta0_.cf NOT IN (
						SELECT c2.cf 
						FROM 
							mudeopen_t_contatto c2
							inner join mudeopen_t_user u2 ON u2.id_user = c2.id_user
						WHERE c2.cf = u2.cf 
					)
		
				)
			)

	 */
    public static Specification<MudeTContatto> includePermittedContacts(final Long idIstanza, Long idUser) {
        if (idIstanza == null || idUser == null)
            return null;

        return new Specification<MudeTContatto>() {
            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                // create joins "mudeopen_t_istanza_user/mudeopen_t_user" to include contacts with permissions
                Subquery<Long> subQueryIdContatto = query.subquery(Long.class);
                Root<MudeRIstanzaUtente> iuRoot = subQueryIdContatto.from(MudeRIstanzaUtente.class);
                Join<MudeRIstanzaUtente, MudeTUser> tUser_Join = iuRoot.join("utente", JoinType.INNER);

                // create joins "mudeopen_t_contatto/mudeopen_t_user" to include accredited users
                Subquery<Long> subQueryIdUser = query.subquery(Long.class);
                Root<MudeTContatto> iContactUserRoot = subQueryIdUser.from(MudeTContatto.class);
                Join<MudeTContatto, MudeTUser> tContactUser_Join = iContactUserRoot.join("mudeTUser", JoinType.INNER);

                // create joins "mudeopen_t_contatto/mudeopen_t_user" to esclude accredited users
                Subquery<Long> subQueryAccredited = query.subquery(Long.class);
                Root<MudeTContatto> iAccreditedRoot = subQueryAccredited.from(MudeTContatto.class);
                Join<MudeTContatto, MudeTUser> tAccredited_Join = iAccreditedRoot.join("mudeTUser", JoinType.INNER);

                // create subquery "mudeopen_t_contatto" to include contact fron personal address book
                Subquery<String> subqueryFromPersonalBook = query.subquery(String.class);
                Root<MudeTContatto> sFromPersonalBookRoot = subqueryFromPersonalBook.from(MudeTContatto.class);
                subqueryFromPersonalBook.select(sFromPersonalBookRoot.get("cf")).where(builder.equal(sFromPersonalBookRoot.get("mudeTUser").get("idUser"), idUser));

                // search only mudeopen_t_contatto.id externally linked to istanzaUtente or those directly linked to the current user
                return builder.or(
                        // subquery that includes contacts with permissions
                        builder.in(root.get("id")).value(subQueryIdContatto.select(root.get("id")).where(builder.and(builder.equal(tUser_Join.get("cf"), root.get("cf")), builder.equal(iuRoot.get("istanza").get("id"), idIstanza), builder.equal(iuRoot.get("utente").get("idUser"), root.get("mudeTUser").get("idUser")), builder.isNull(iuRoot.get("dataFine")), builder.isNull(root.get("dataCessazione")), builder.equal(iuRoot.get("utente").get("idUser"), idUser).not() // esclude current user
                        ))),
                        // subquery that includes accredited contacts with permissions
                        builder.in(root.get("id")).value(subQueryIdUser.select(iContactUserRoot.get("id")).where(builder.and(builder.equal(tContactUser_Join.get("cf"), iContactUserRoot.get("cf")), builder.isNull(iContactUserRoot.get("dataCessazione")), builder.in(iContactUserRoot.get("cf")).value(subqueryFromPersonalBook) // ONLY CFs FROM PRIVATE ADDRESS BOOK
                                // builder.equal(tContactUser_Join.get("mudeTUser").get("idUser"), idUser).not() // INCLUDE ALL ACCREDITED
                        ))),
                        // subquery that includes contacts from user's address book
                        builder.and(builder.equal(root.get("mudeTUser").get("idUser"), idUser), builder.in(root.get("cf")).value(subQueryAccredited.select(iAccreditedRoot.get("cf")).where(builder.and(builder.equal(tAccredited_Join.get("cf"), iAccreditedRoot.get("cf"))))).not()));

            }
        };
    }

    public static Specification<MudeTContatto> includeAllMUDEAccreditedContacts(final Long idIstanza, Long idUser) {
        if (idIstanza == null || idUser == null)
            return null;

        return new Specification<MudeTContatto>() {
            @Override
            public Predicate toPredicate(Root<MudeTContatto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                // create joins"mudeopen_t_user/mudeopen_t_contatto" to include externalContacts
                Subquery<Long> subQueryIdContatto = query.subquery(Long.class);
                Root<MudeTContatto> externalContact_Root = query.from(MudeTContatto.class);

                Root<MudeTUser> iuRoot = subQueryIdContatto.from(MudeTUser.class);

                Subquery joinExternalContactsFromIstanzaUtente = subQueryIdContatto.select(externalContact_Root.get("id")).where(builder.equal(iuRoot.get("cf"), externalContact_Root.get("cf")));

                query.distinct(true);

                // search only mudeopen_t_contatto.id externally linked to istanzaUtente or those directly linked to the current user
                return builder.or(builder.in(root.get("id")).value(joinExternalContactsFromIstanzaUtente), builder.equal(root.get("mudeTUser").get("idUser"), idUser));
            }
        };
    }

    public static Specification<MudeTContatto> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return null;
        };
    }
}