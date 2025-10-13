/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIntestatario;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
public class MudeTFascicoloSpecification extends BaseSpecification{
    public static Specification<MudeTFascicolo> findBy(MudeTUser mudeTUser, List<Long> mudeDComuneList, Long idDug, String duf,
                                                       String idIntestatarioPf, String idIntestatarioPg, String idPm, String mudeDTipoIntervento, LocalDate dataCreazioneDa,
                                                       LocalDate dataCreazioneA, String codiceFascicolo, String statoFascicolo, Set<Long>fascicoli, String scope) {
        return new Specification<MudeTFascicolo>() {
            @Override
            public Predicate toPredicate(Root<MudeTFascicolo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                
        		predicates.add(builder.or(
								builder.isNull(root.get("dataFine")),
								builder.greaterThan(root.get("dataFine"), Date.from(Instant.now()))
						));

                query.distinct(true);
                if (mudeTUser != null && !"backoffice".equalsIgnoreCase(scope)) {
                    // also include "fascicoli" where the mudeTUser is referenced
            		Subquery<Long> fascicoloUtenteIdFascicolo = query.subquery(Long.class);     
            		Root<MudeRFascicoloUtente> fascicoloUtenteSubRoot = fascicoloUtenteIdFascicolo.from(MudeRFascicoloUtente.class);  
            		
                    fascicoloUtenteIdFascicolo.select(fascicoloUtenteSubRoot.get("fascicolo").get("id")).where(
                		builder.and(
    						builder.isNull(fascicoloUtenteSubRoot.get("dataFine")),
        				   	builder.equal(fascicoloUtenteSubRoot.get("utente").get("idUser"), mudeTUser.getIdUser())
                    	));
                    // also includes "fascicoli" for which the user as istanzeUtente relations 
            		Subquery<Long> istanzaUtenteIdFascicolo = query.subquery(Long.class);
            		Root<MudeRIstanzaUtente> istanzaUtenteSubRoot = istanzaUtenteIdFascicolo.from(MudeRIstanzaUtente.class);
                    istanzaUtenteIdFascicolo.select(istanzaUtenteSubRoot.get("istanza").get("mudeTFascicolo").get("id")).where(
        				builder.and(
    						builder.isNull(istanzaUtenteSubRoot.get("dataFine")),
        				   	builder.equal(istanzaUtenteSubRoot.get("utente").get("idUser"), mudeTUser.getIdUser())
        				)
                	);
                	
            		predicates.add(builder.or(
                			builder.equal(root.get("mudeTUser").get("idUser"), mudeTUser.getIdUser()),
        					builder.in(root.get("id")).value(fascicoloUtenteIdFascicolo),
        					builder.in(root.get("id")).value(istanzaUtenteIdFascicolo),
	                        builder.in(root.get("id")).value(fascicoli != null && !fascicoli.isEmpty() ? fascicoli :null)
                        )
                    );
                }

				if(StringUtils.isNotBlank(idIntestatarioPf)) {
	                Root<MudeRFascicoloIntestatario> subRootIntestatario = query.from(MudeRFascicoloIntestatario.class);
					predicates.add(builder.and(
							builder.equal(root.get("id"), subRootIntestatario.get("fascicolo").get("id")),
							builder.or(
									builder.like(
											builder.function("concat", String.class, builder.upper(subRootIntestatario.get("intestatario").get("nome")), builder.literal(" "), builder.upper(subRootIntestatario.get("intestatario").get("cognome"))), 
											"%"+idIntestatarioPf.toUpperCase().replaceAll("[ ]+", " ")+"%"),
									builder.like(
											builder.function("concat", String.class, builder.upper(subRootIntestatario.get("intestatario").get("cognome")), builder.literal(" "), builder.upper(subRootIntestatario.get("intestatario").get("nome"))), 
											"%"+idIntestatarioPf.replaceAll("[ ]+", " ")+"%"),
									builder.like(subRootIntestatario.get("intestatario").get("cf"), "%"+idIntestatarioPf+"%")
							)
						)
					);
				}

				if(StringUtils.isNotBlank(idIntestatarioPg)) {
	                Root<MudeRFascicoloIntestatario> subRootIntestatario = query.from(MudeRFascicoloIntestatario.class);
					predicates.add(builder.and(
							builder.equal(root.get("id"), subRootIntestatario.get("fascicolo").get("id")),
							builder.or(
									builder.like(builder.upper(subRootIntestatario.get("intestatario").get("ragioneSociale")), "%"+idIntestatarioPg.toUpperCase().replaceAll("[ ]+", " ")+"%"),
									builder.like(subRootIntestatario.get("intestatario").get("piva"), "%"+idIntestatarioPg.replaceAll("[ ]+", " ")+"%"),
									builder.like(subRootIntestatario.get("intestatario").get("pivaComunitaria"), "%"+idIntestatarioPg+"%")
							)
						)
					);
				}
				
                if ((mudeDComuneList != null && !mudeDComuneList.isEmpty()) || null != idDug || StringUtils.isNotBlank(duf)) {
                    List<Predicate> subPredicates = new ArrayList<>();
                    Subquery<Long> sub = query.subquery(Long.class);
                    Root<MudeRFascicoloIndirizzo> subRoot = sub.from(MudeRFascicoloIndirizzo.class);
                    subPredicates.add(builder.equal(root.get("id"), subRoot.get("mudeTFascicolo").get("id")));
                    subPredicates.add(builder.isNull(subRoot.get("dataFine")));
                    if(null != idDug){
                        subPredicates.add(builder.equal(subRoot.get("indirizzo").get("idDug"), idDug));
                    }

                    if(StringUtils.isNotBlank(duf)){
                        subPredicates.add(builder.like(builder.upper(subRoot.get("indirizzo").get("indirizzo")), "%" + duf.toUpperCase() + "%"));
                    }

                    if(mudeDComuneList != null && !mudeDComuneList.isEmpty()){
                        subPredicates.add(builder.in(subRoot.get("indirizzo").get("mudeDComune").get("idComune")).value(mudeDComuneList));
                    }

                    sub.select(subRoot.get("mudeTFascicolo").get("id")).where(subPredicates.toArray(new Predicate[0]));
                    predicates.add(builder.in(root.get("id")).value(sub));
                }

                if (mudeDTipoIntervento != null) {
                    final Join<MudeTFascicolo, MudeDTipoIntervento> mudeDTipoInterventoJoin = root.join("tipoIntervento");
                    predicates.add(builder.equal(mudeDTipoInterventoJoin.get("codice"), mudeDTipoIntervento));
                }

                if (dataCreazioneDa != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("dataCreazione"), Timestamp.valueOf(dataCreazioneDa.atStartOfDay())));
                }

                if (dataCreazioneA != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("dataCreazione"), Timestamp.valueOf(dataCreazioneA.atTime(23, 59))));
                }

                if (codiceFascicolo != null) {
                    final String codiceFascicoloLike = "%" + codiceFascicolo.toUpperCase() + "%";
                    predicates.add(builder.like(builder.upper(root.get("codiceFascicolo")), codiceFascicoloLike));
                }

                if (statoFascicolo != null) {
                    Subquery<Long> sub = query.subquery(Long.class);
                    Root<MudeRFascicoloStato> subRoot = sub.from(MudeRFascicoloStato.class);
                    Predicate p = builder.and(builder.and(builder.equal(root.get("id"), subRoot.get("fascicolo").get("id")), builder.isNull(subRoot.get("dataFine"))),builder.equal(subRoot.get("statoFascicolo").get("codice"), statoFascicolo.toUpperCase()));
                    sub.select(subRoot.get("fascicolo").get("id")).where(p);
                    predicates.add(builder.in(root.get("id")).value(sub));
                }

                if ("backoffice".equalsIgnoreCase(scope)) {
                	Subquery<Long> sub = query.subquery(Long.class);
                    if (StringUtils.isNotBlank(idIntestatarioPf)) {
                    	final String idIntestatarioPfLike = "%" + idIntestatarioPf.toUpperCase() + "%";
                        Root<MudeRFascicoloIntestatario> subIntestatario = sub.from(MudeRFascicoloIntestatario.class);
                        final Join<MudeRFascicoloIntestatario, MudeTContatto> mudeTContattoJoin = subIntestatario.join("intestatario");
                        Predicate pIntestatario = builder.and(
                        		builder.and(
                        			builder.equal(root.get("id"), subIntestatario.get("fascicolo").get("id")),
                        			//builder.isNull(subIntestatario.get("dataFine")),
                        			builder.or( //
                                            builder.like(builder.upper(mudeTContattoJoin.get("nome")), idIntestatarioPfLike), //
                                            builder.like(builder.upper(mudeTContattoJoin.get("cognome")), idIntestatarioPfLike), //
                                            builder.like(builder.upper(mudeTContattoJoin.get("cf")), idIntestatarioPfLike) //
                                    )
                        		)
                        );
                        sub.select(subIntestatario.get("fascicolo").get("id")).where(pIntestatario);
                        predicates.add(builder.in(root.get("id")).value(sub));
                    	 
                    } else if (StringUtils.isNotBlank(idIntestatarioPg)) {
                    	final String idIntestatarioPgLike = "%" + idIntestatarioPg.toUpperCase() + "%";
                        Root<MudeRFascicoloIntestatario> subIntestatario = sub.from(MudeRFascicoloIntestatario.class);
                        final Join<MudeRFascicoloIntestatario, MudeTContatto> mudeTContattoJoin = subIntestatario.join("intestatario");
                        Predicate pIntestatario = builder.and(
                        		builder.and(
                        				builder.equal(root.get("id"), subIntestatario.get("fascicolo").get("id")),
                        				//builder.isNull(subIntestatario.get("dataFine")),
                        			builder.or( //
                                            builder.like(builder.upper(mudeTContattoJoin.get("ragioneSociale")), idIntestatarioPgLike), //
                                            builder.like(builder.upper(mudeTContattoJoin.get("piva")), idIntestatarioPgLike), //
                                            builder.like(builder.upper(mudeTContattoJoin.get("pivaComunitaria")), idIntestatarioPgLike) //
                                    )
                        		)
                        );
                        sub.select(subIntestatario.get("fascicolo").get("id")).where(pIntestatario);
                        predicates.add(builder.in(root.get("id")).value(sub));
                    	 
                    }

                    if (StringUtils.isNotBlank(idPm)) {
                    	final String idPmLike = "%" + idPm.toUpperCase() + "%";
                    	Subquery<Long> subPm = query.subquery(Long.class);
                    	Root<MudeTIstanza> subRootIstanza = subPm.from(MudeTIstanza.class);
                    	Root<MudeRIstanzaUtente> subRootPm = subPm.from(MudeRIstanzaUtente.class);
                        final Join<MudeRIstanzaUtente,MudeDAbilitazione> mudeDAbilitazioneJoin = subRootPm.join("abilitazione");
                        final Join<MudeRIstanzaUtente, MudeTUser> mudeTUserJoin = subRootPm.join("utente");
                        List<String> codAbilitazione = new ArrayList<String>();
                        codAbilitazione.add("PM_RUP_PM_OBB");
                        codAbilitazione.add("PM_RUP_PM_OPZ");
                        Predicate pPm = builder.and(
                        		builder.and(
                        			builder.equal(root.get("id"), subRootIstanza.get("mudeTFascicolo").get("id")),
                        			builder.equal(subRootPm.get("istanza").get("id"), subRootIstanza.get("id")),
                        			builder.in(mudeDAbilitazioneJoin.get("codice")).value(codAbilitazione),
                        			builder.isNull(mudeDAbilitazioneJoin.get("dataFine")),
                        			builder.or( //
                                            builder.like(builder.upper(mudeTUserJoin.get("nome")), idPmLike), //
                                            builder.like(builder.upper(mudeTUserJoin.get("cognome")), idPmLike), //
                                            builder.like(builder.upper(mudeTUserJoin.get("cf")), idPmLike) //
                                    )
                        		)
                        		
                        );
                        subPm.select(subRootIstanza.get("mudeTFascicolo").get("id")).where(pPm);
                        predicates.add(builder.in(root.get("id")).value(subPm));
                    }
                    	
                	// search only mudeopen_t_istanza.id externally linked to istanzaUtente or those linked (via fascicolo) to the current user 
                    return builder.and(builder.and(predicates.toArray(new Predicate[0])));
                }
        		
                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    // Fix RUOLI Utente BO
    public static Specification<MudeTFascicolo> findByRole(MudeTUser mudeTUser, List<Long> mudeTFascicoloList) {
    	
    	return new Specification<MudeTFascicolo>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTFascicolo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	return builder.in(root.get("id")).value(mudeTFascicoloList);
            }
        };
    }

    public static Specification<MudeTFascicolo> findById(final Map map) {
        return new Specification<MudeTFascicolo>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTFascicolo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateLong(builder, root.get("id"), map);
            }
        };
    }
}