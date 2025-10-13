/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDAbilitazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggettoRuoli;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteEnteComuneRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzaSlim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaCosmo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCatasto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocUbicazione;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.DateSearchBackofficeEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.QueryOpEnum;


public class MudeTIstanzaSlimSpecification extends BaseSpecification {
    private static final String BACKOFFICE = "backoffice";

	public static Specification<MudeTIstanzaSlim> findBy(MudeTUser mudeTUser, List<Long> mudeDComuneList, Long idDug, String duf,
    		String idIntestatarioPf, String idIntestatarioPg, String idPm, MudeTFascicolo mudeTFascicolo, List<Long> praticaIDs, String codiceTipoIStanza, 
    		LocalDate dataCreazioneDa, LocalDate dataCreazioneA, String codiceIstanza,
                                                     String scope, 
                                                     boolean excludeFoAbilitazioni, 
                                                     boolean isDSRegional,
												     boolean skipStateCheck) {
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                query.distinct(true);
                
        		predicates.add(builder.or(
								builder.isNull(root.get("dataFine")),
								builder.greaterThan(root.get("dataFine"), Date.from(Instant.now()))
						));
                
                if (mudeTFascicolo != null) {
//                    final Join<MudeTIstanzaSlim, MudeTFascicolo> mudeTFascicoloJoin = root.join("mudeTFascicolo");
//                    predicates.add(builder.equal(mudeTFascicoloJoin.get("id"), mudeTFascicolo.getId()));
                }

                //Filtro per id_pratica
                if(!praticaIDs.isEmpty()) {
	            	Subquery<Long> praticaUtenteIdPratica = query.subquery(Long.class);     
	            	Root<MudeRIstanzaPratica> praticaUtenteSubRoot = praticaUtenteIdPratica.from(MudeRIstanzaPratica.class);  
	            		
	                praticaUtenteIdPratica.select(root.get("idIstanza")).where(
	            		builder.and(
							builder.equal(root.get("idIstanza"), praticaUtenteSubRoot.get("istanza").get("id")),
							builder.in(praticaUtenteSubRoot.get("pratica").get("id")).value(praticaIDs)
	                	));
	
	        		predicates.add(builder.and(
	    					builder.in(root.get("idIstanza")).value(praticaUtenteIdPratica)
	                    )
	                );
                }

                if(mudeDComuneList != null && idDug == null && duf == null) {
                	if(mudeDComuneList.size() == 1)
                		predicates.add(builder.equal(root.get("idComune"), mudeDComuneList.get(0)));
                	else if(mudeDComuneList.size() >= 1)
                		predicates.add(builder.in(root.get("idComune")).value(mudeDComuneList));
                }
                else {
	                if (mudeDComuneList != null && !mudeDComuneList.isEmpty()) {
//	                    predicates.add(builder.in(root.get("indirizzo").get("mudeDComune").get("idComune")).value(mudeDComuneList));
	                }
	
	                if (idDug != null) {
//	                    predicates.add(builder.equal(root.get("indirizzo").get("idDug"), idDug));
	                }
	
	                if (duf != null) {
//	                    predicates.add(builder.like(builder.upper(root.get("indirizzo").get("indirizzo")), "%" + duf.toUpperCase() + "%"));
	                }
                }
                
                Subquery<Long> subIntestatario=null;
                if (StringUtils.isNotBlank(idIntestatarioPf)) {
                	final String idIntestatarioPfLike = "%" + idIntestatarioPf.toUpperCase() + "%";
                	subIntestatario = query.subquery(Long.class);
                    Root<MudeRIstanzaSoggetto> subRootIntestatario = subIntestatario.from(MudeRIstanzaSoggetto.class);
                    Root<MudeRIstanzaSoggettoRuoli> subRootIntestatarioRuoli = subIntestatario.from(MudeRIstanzaSoggettoRuoli.class);
                    final Join<MudeRIstanzaSoggetto, MudeTContatto> mudeTContattoJoin = subRootIntestatario.join("mudeTContatto");
                    Predicate pIntestatario = builder.and(
                    		builder.and(
                    			builder.equal(subRootIntestatario.get("idIstanzaSoggetto"), subRootIntestatarioRuoli.get("idIstanzaSoggetto")),
                    			builder.or( //
    									builder.like(
												builder.function("concat", String.class, builder.upper(mudeTContattoJoin.get("nome")), builder.literal(" "), builder.upper(mudeTContattoJoin.get("cognome"))), "%"+idIntestatarioPf.toUpperCase().replaceAll("[ ]+", " ")+"%"
											),
    									builder.like(
												builder.function("concat", String.class, builder.upper(mudeTContattoJoin.get("cognome")), builder.literal(" "), builder.upper(mudeTContattoJoin.get("nome"))), "%"+idIntestatarioPf.toUpperCase().replaceAll("[ ]+", " ")+"%"
											),
    									builder.like(mudeTContattoJoin.get("cf"), "%"+idIntestatarioPf+"%")
                                ),
                    			builder.equal(subRootIntestatarioRuoli.get("ruoli"),"IN")
                    		)
                    );
                    subIntestatario.select(subRootIntestatario.get("mudeTIstanza").get("id")).where(pIntestatario);
                    predicates.add(builder.in(root.get("idIstanza")).value(subIntestatario));
                	 
                } else if (StringUtils.isNotBlank(idIntestatarioPg)) {
                	final String idIntestatarioPgLike = "%" + idIntestatarioPg.toUpperCase() + "%";
                	subIntestatario = query.subquery(Long.class);
                    Root<MudeRIstanzaSoggetto> subRootIntestatario = subIntestatario.from(MudeRIstanzaSoggetto.class);
                    Root<MudeRIstanzaSoggettoRuoli> subRootIntestatarioRuoli = subIntestatario.from(MudeRIstanzaSoggettoRuoli.class);
                    final Join<MudeRIstanzaSoggetto, MudeTContatto> mudeTContattoJoin = subRootIntestatario.join("mudeTContatto");
                    Predicate pIntestatario = builder.and(
                    		builder.and(
                    			builder.equal(subRootIntestatario.get("idIstanzaSoggetto"), subRootIntestatarioRuoli.get("idIstanzaSoggetto")),
                    			builder.or( //
                                        builder.like(builder.upper(mudeTContattoJoin.get("ragioneSociale")), idIntestatarioPgLike), //
                                        builder.like(builder.upper(mudeTContattoJoin.get("piva")), idIntestatarioPgLike), //
                                        builder.like(builder.upper(mudeTContattoJoin.get("pivaComunitaria")), idIntestatarioPgLike) //
                                ),
                    			builder.equal(subRootIntestatarioRuoli.get("ruoli"),"IN")
                    		)
                    );
                    subIntestatario.select(subRootIntestatario.get("mudeTIstanza").get("id")).where(pIntestatario);
                    predicates.add(builder.in(root.get("idIstanza")).value(subIntestatario));
                	 
                }

                if (codiceTipoIStanza != null)
                    predicates.add(builder.equal(root.get("idTipoIstanza"), codiceTipoIStanza));

                if (dataCreazioneDa != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("dataCreazione"), Timestamp.valueOf(dataCreazioneDa.atStartOfDay())));
                }

                if (dataCreazioneA != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("dataCreazione"), Timestamp.valueOf(dataCreazioneA.atTime(23, 59))));
                }

                if (codiceIstanza != null) {
                    final String codiceIstanzaLike = "%" + codiceIstanza.toUpperCase() + "%";
                    predicates.add(builder.like(builder.upper(root.get("codiceIstanza")), codiceIstanzaLike));
                }

                if (BACKOFFICE.equalsIgnoreCase(scope)) {
                	if(!skipStateCheck) {
	                    String[] overrideStates = isDSRegional? new String[] {"BZZ", "DFR", "FRM", "DPS", "PRC"} 
									: new String[] {"BZZ", "DFR", "FRM"};
	
	                    Subquery<Long> sub = query.subquery(Long.class);
	                    Root<MudeRIstanzaStato> subRoot = sub.from(MudeRIstanzaStato.class);
	                    
	                    sub.select(subRoot.get("istanza").get("id")).where(builder.and(
	                			builder.equal(root.get("idIstanza"), subRoot.get("istanza").get("id")), 
								builder.isNull(subRoot.get("dataFine")),
	                    		builder.not(subRoot.get("statoIstanza").get("codice").in((Object[])overrideStates))
	                    ));
	                    
	                    predicates.add(builder.in(root.get("idIstanza")).value(sub));
                	}
                	
                    if (StringUtils.isNotBlank(idPm)) {
                    	final String idPmLike = "%" + idPm.toUpperCase() + "%";
                    	Subquery<Long> subPm = query.subquery(Long.class);
                        Root<MudeRIstanzaUtente> subRootPm = subPm.from(MudeRIstanzaUtente.class);
                        final Join<MudeRIstanzaUtente,MudeDAbilitazione> mudeDAbilitazioneJoin = subRootPm.join("abilitazione", JoinType.INNER);
                        final Join<MudeRIstanzaUtente, MudeTUser> mudeTUserJoin = subRootPm.join("utente", JoinType.INNER);
                        List<String> codAbilitazione = new ArrayList<String>();
                        codAbilitazione.add("PM_RUP_PM_OBB");
                        codAbilitazione.add("PM_RUP_PM_OPZ");
                        Predicate pPm = builder.and(
                        		builder.and(
                        			//builder.equal(root.get("idIstanza"), subRootPm.get("istanza").get("id")),
                        			
                        			builder.in(mudeDAbilitazioneJoin.get("codice")).value(codAbilitazione),
                        			builder.or( //
                                            builder.like(builder.upper(mudeTUserJoin.get("nome")), idPmLike), //
                                            builder.like(builder.upper(mudeTUserJoin.get("cognome")), idPmLike), //
                                            builder.like(builder.upper(mudeTUserJoin.get("cf")), idPmLike) //
                                    )
                        		)
                        		
                        );
                        subPm.select(subRootPm.get("istanza").get("id")).where(pPm);
                        predicates.add(builder.in(root.get("idIstanza")).value(subPm));
                    }
                    	
                	// search only mudeopen_t_istanza.id externally linked to istanzaUtente or those linked (via fascicolo) to the current user 
                    return builder.and(predicates.toArray(new Predicate[0]));
                }

                else if(mudeTFascicolo == null || !excludeFoAbilitazioni) {
	                // include "istanze" where the mudeTUser is referenced
	        		Subquery<Long> istanzaUtenteIdIstanza = query.subquery(Long.class);     
	        		Root<MudeRIstanzaUtente> istanzaUtenteSubRoot = istanzaUtenteIdIstanza.from(MudeRIstanzaUtente.class);    
	                istanzaUtenteIdIstanza.select(istanzaUtenteSubRoot.get("istanza").get("id")).where(
	            		builder.and(
							builder.isNull(istanzaUtenteSubRoot.get("dataFine")),
	    				   	builder.equal(istanzaUtenteSubRoot.get("utente").get("idUser"), mudeTUser.getIdUser())
	                	));
	                
	            	// search only mudeopen_t_istanza.id externally linked to istanzaUtente or those linked (via fascicolo) to the current user 
	                return builder.and(builder.and(predicates.toArray(new Predicate[0])),
	                			builder.or(
	                					builder.in(root.get("idIstanza")).value(istanzaUtenteIdIstanza),
	                					builder.equal(root.get("idUser"), mudeTUser.getIdUser())
	                			)
                			);
	                
                }

                else // search without current user 
	                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> findByNumeroPratica(String numeroPratica) {
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            		Subquery<Long> praticaUtenteIdPratica = query.subquery(Long.class);     
            		Root<MudeRIstanzaPratica> praticaUtenteSubRoot = praticaUtenteIdPratica.from(MudeRIstanzaPratica.class);  
            		
                    praticaUtenteIdPratica.select(praticaUtenteSubRoot.get("istanza").get("id")).where(
    						builder.equal(praticaUtenteSubRoot.get("pratica").get("numeroPratica"), numeroPratica)
    					);
                    
			    return builder.in(root.get("idIstanza")).value(praticaUtenteIdPratica);
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> findById(final Map map) {
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateLong(builder, root.get("idIstanza"), map);
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> findByKeywords(final Map map) {
    	if(map == null 
    			|| map.get(QueryOpEnum.LIKE.getValue()) == null 
    			|| "".equals(((String)map.get(QueryOpEnum.LIKE.getValue())).trim()))
    		return null;
    	
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                for(String tag : ((String)map.get(QueryOpEnum.LIKE.getValue())).trim().replaceAll("[ ]+", " ").split(" "))
                	predicates.add(builder.like(builder.upper(root.get("keywords")), "%" + tag.toUpperCase() + "%"));
            	
                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> hasTipoIstanza(final Map map) {
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("idTipoIstanza"), map);
            }
        };
    }

    // is it used?
    public static Specification<MudeTIstanzaSlim> findByNumeroProtocolloIstanza(final Map map) {
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("numeroProtocollo"), map);
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> hasStato(final Map map) {
    	if(map == null) return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			    Subquery<Long> sub = query.subquery(Long.class);
			    Root<MudeRIstanzaStato> subRoot = sub.from(MudeRIstanzaStato.class);
			    Predicate p = builder.and(
			    		builder.and(
			    			builder.equal(root.get("idIstanza"), subRoot.get("istanza").get("id")), 
							builder.isNull(subRoot.get("dataFine"))
			    		),
			    		getPredicateString(builder, subRoot.get("statoIstanza").get("codice"), map)
			    );
			    sub.select(subRoot.get("istanza").get("id")).where(p);
			    
			    return builder.in(root.get("idIstanza")).value(sub);
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> hasStatoIn(final List list) {
    	if (list == null)
    		return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			    Subquery<Long> sub = query.subquery(Long.class);
			    Root<MudeRIstanzaStato> subRoot = sub.from(MudeRIstanzaStato.class);
			    Predicate p = builder.and(
			    		builder.and(
			    			builder.equal(root.get("idIstanza"), subRoot.get("istanza").get("id")), 
							builder.isNull(subRoot.get("dataFine"))
			    		),
			    		getPredicateListString(builder, subRoot.get("statoIstanza").get("codice"), list)
			    );
			    sub.select(subRoot.get("istanza").get("id")).where(p);
			    return builder.in(root.get("idIstanza")).value(sub);
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> hasTipoIstanzaIn(final List list) {
    	if (list == null)
    		return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			    //return builder.in(root.get("tipoIstanza").get("codice")).value(map);
            	return getPredicateListString(builder, root.get("idTipoIstanza"), list);
            }
        };
    }

/*
    public static Specification<MudeTIstanzaSlim> hasCodiceFascicolo(final String token) {
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTIstanzaSlim>() {
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(builder.function("replace", 
			                	            String.class, root.get("mudeTFascicolo").get("codiceFascicolo"), builder.literal("-"), 
			                	            builder.literal("")), 
                			"%"+token.replaceAll("-", "")+"%"
            		);
            }
        };
    }
*/

    public static Specification<MudeTIstanzaSlim> hasCodiceIstanza(final String token) {
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTIstanzaSlim>() {
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(builder.function("replace", 
			                	            String.class, root.get("codiceIstanza"), builder.literal("-"), 
			                	            builder.literal("")), 
                			"%"+token.replaceAll("-", "")+"%"
            		);
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> hasFonte(final Map map) {
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("idFonte"), map);
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> findByRegistrazioneFrom(final LocalDate dataRegistrazioneDa) {
    	if(dataRegistrazioneDa == null) return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.greaterThanOrEqualTo(root.get("dataDps"), Timestamp.valueOf(dataRegistrazioneDa.atStartOfDay()));
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> findByRegistrazioneTo(final LocalDate dataRegistrazioneA) {
    	if(dataRegistrazioneA == null) return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.lessThanOrEqualTo(root.get("dataDps"), Timestamp.valueOf(dataRegistrazioneA.atTime(23, 59)));
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> findByProtocolloFrom(final LocalDate dataRegistrazioneProtocolloDa) {
    	if(dataRegistrazioneProtocolloDa == null) return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.greaterThanOrEqualTo(root.get("dataProtocollo"), Timestamp.valueOf(dataRegistrazioneProtocolloDa.atStartOfDay()));
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> findByProtocolloTo(final LocalDate dataRegistrazioneProtocolloA) {
    	if(dataRegistrazioneProtocolloA == null) return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.lessThanOrEqualTo(root.get("dataProtocollo"), Timestamp.valueOf(dataRegistrazioneProtocolloA.atTime(23, 59)));
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> findByDataDpsFrom(final String dataDpsDa) {
    	if(dataDpsDa == null) return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            	LocalDate date = LocalDate.parse(dataDpsDa, formatter);
                return builder.greaterThanOrEqualTo(root.get("dataDps"), Timestamp.valueOf(date.atStartOfDay()));
            }
        };
    }

    public static Specification<MudeTIstanzaSlim> findByDataDpsTo(final String dataDpsA) {
    	if(dataDpsA == null) return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            	LocalDate date = LocalDate.parse(dataDpsA, formatter);
                return builder.lessThanOrEqualTo(root.get("dataDps"), Timestamp.valueOf(date.atTime(23, 59)));
            }
        };
    }

    // BO
    public static Specification<MudeTIstanzaSlim> filterByComuneAndEnte(Long idUser) {
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Subquery<Long> subIstanzaEnte = query.subquery(Long.class);
                Root<MudeRIstanzaEnte> subIstanzaEnteRoot = subIstanzaEnte.from(MudeRIstanzaEnte.class);
                Root<MudeRUtenteEnteComuneRuolo> utenteEnteComuneRuolo = subIstanzaEnte.from(MudeRUtenteEnteComuneRuolo.class);
                Predicate p = builder.and(
            		builder.equal(root.get("idIstanza"), subIstanzaEnteRoot.get("istanza").get("id")),
            		builder.isNull(subIstanzaEnteRoot.get("dataFine")),
            		
            		builder.equal(utenteEnteComuneRuolo.get("idComune"), root.get("idComune")),
            		builder.equal(utenteEnteComuneRuolo.get("idUtente"), idUser),
            		builder.isNull(utenteEnteComuneRuolo.get("dataFine")),
            		builder.equal(subIstanzaEnteRoot.get("ente").get("id"), utenteEnteComuneRuolo.get("idEnte"))
                );
                subIstanzaEnte.select(subIstanzaEnteRoot.get("istanza").get("id")).where(p);
			    return builder.in(root.get("idIstanza")).value(subIstanzaEnte);
           }
        };
    }
    
    public static Specification<MudeTIstanzaSlim> findByControlloCampione(final int year, final int month) {
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
            	
			    Subquery<Long> subCosmo = query.subquery(Long.class);
			    Root<MudeTPraticaCosmo> subRootCosmo = subCosmo.from(MudeTPraticaCosmo.class);
			    subCosmo.select(subRootCosmo.get("idIstanza")).where(builder.or(
			    		builder.and(
			    			builder.equal(root.get("idIstanza"), subRootCosmo.get("idIstanza")), 
							builder.isNull(subRootCosmo.get("dataFine")),
				    		builder.equal(subRootCosmo.get("controlloCampione"), true)
				    ),
		    		builder.equal(subRootCosmo.get("ccSelezionato"), true)
			    ));
			    
			    predicates.add(builder.in(root.get("idIstanza")).value(subCosmo));

			    int _year = year, _month = month;
			    if(month == -1 || year == -1) {
	                Calendar cal = Calendar.getInstance();
	                cal.add(Calendar.MONTH, -1);
	                _month = cal.get(Calendar.MONTH) + 1;
	                _year = cal.get(Calendar.YEAR);
			    }
			    
                YearMonth yearMonth = YearMonth.of(_year, _month);
                LocalDate firstOfPrevMonth = yearMonth.atDay(1);
                LocalDate lastOfPrevMonth = yearMonth.atEndOfMonth();

			    Subquery<Long> subStatus = query.subquery(Long.class);
			    Root<MudeRIstanzaStato> subRootStatus = subStatus.from(MudeRIstanzaStato.class);
			    subStatus.select(subRootStatus.get("istanza").get("id")).where(builder.and(
		    			builder.equal(root.get("idIstanza"), subRootStatus.get("istanza").get("id")), 
						//builder.isNull(subRootStatus.get("dataFine")),
			    		//builder.greaterThanOrEqualTo(subRootStatus.get("statoIstanza").get("livello"), 201),
		    			builder.equal(subRootStatus.get("statoIstanza").get("codice"), "ACC"),
			    		builder.between(subRootStatus.get("dataInizio"), 
		                		Timestamp.valueOf(firstOfPrevMonth.atStartOfDay()), Timestamp.valueOf(lastOfPrevMonth.atTime(23, 59, 59)))			    		
			    ));
			    
			    predicates.add(builder.in(root.get("idIstanza")).value(subStatus));
			    
                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    
    
    /*
     * Istanze DS
     */

	public static Specification<MudeTIstanzaSlim> findByTipoPratica(final Map tipoPratica) {
		if(tipoPratica == null) return null;
		return new Specification<MudeTIstanzaSlim>() {
			@Override
			public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("idSpeciePratica"), tipoPratica);
			}
		};
	}

	public static Specification<MudeTIstanzaSlim> findByDates(final Map tipoData, final Map dataFrom, final Map dataTo) {
		if(tipoData == null || (dataFrom == null && dataTo == null)) return null;
		return new Specification<MudeTIstanzaSlim>() {
			@Override
			public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                
                Subquery<Long> sub = query.subquery(Long.class);
                Root<MudeRIstanzaStato> statoIStanzaRoot = sub.from(MudeRIstanzaStato.class);
                
                String tipoDataStr = (String)tipoData.get("eq");
                if(tipoDataStr != null) {
                	DateSearchBackofficeEnum typeDateSearch = DateSearchBackofficeEnum.findByValue(Integer.parseInt(tipoDataStr));
                	
	                if(dataFrom != null) {
	                	String dateStr = (String)dataFrom.get("eq");
		            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		            	LocalDate date = LocalDate.parse(dateStr, formatter);
		            	predicates.add(builder.greaterThanOrEqualTo(statoIStanzaRoot.get("dataInizio"), Timestamp.valueOf(date.atStartOfDay())));
	                }
	
	                if(dataTo != null) {
	                	String dateStr = (String)dataTo.get("eq");
		            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		            	LocalDate date = LocalDate.parse(dateStr, formatter);
		            	predicates.add(builder.lessThanOrEqualTo(statoIStanzaRoot.get("dataInizio"), Timestamp.valueOf(date.atTime(23, 59, 59))));
	                }
	                
	                predicates.add(builder.equal(root.get("idIstanza"), statoIStanzaRoot.get("istanza").get("id")));
	                predicates.add(builder.equal(statoIStanzaRoot.get("statoIstanza").get("codice"), typeDateSearch == DateSearchBackofficeEnum.DATA_PROTOCOLLO? "APA" : "ACC"));
	                
	                sub.select(statoIStanzaRoot.get("istanza").get("id")).where(builder.and(predicates.toArray(new Predicate[0])));
                }
				
                
                return builder.in(root.get("idIstanza")).value(sub);
			}
		};
	}

	public static Specification<MudeTIstanzaSlim> findByIndirizzo(final Long idComune
													, final Map numeroCivico
													, final Map interno
													, final Map scala
													, final Map piano) {
		if(idComune == null || (numeroCivico == null && interno == null && scala == null && piano == null)) return null;
		return new Specification<MudeTIstanzaSlim>() {
			@Override
			public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Subquery<Long> subIstanzaUbicazione = query.subquery(Long.class);
                Root<MudeopenRLocUbicazione> subIstanzaUbicazioneRoot = subIstanzaUbicazione.from(MudeopenRLocUbicazione.class);
                Predicate p = builder.and(
            		builder.equal(root.get("idIstanza"), subIstanzaUbicazioneRoot.get("idIstanza")),
            		builder.isNull(subIstanzaUbicazioneRoot.get("dataFine"))
                );
                
                List<Predicate> predicates = new ArrayList<>();
				if(numeroCivico != null)
					predicates.add(getPredicateString(builder, subIstanzaUbicazioneRoot.get("numCivico"), numeroCivico));
				if(interno != null)
					predicates.add(getPredicateString(builder, subIstanzaUbicazioneRoot.get("interno"), interno));
				if(scala != null)
					predicates.add(getPredicateString(builder, subIstanzaUbicazioneRoot.get("scala"), scala));
				if(piano != null)
					predicates.add(getPredicateString(builder, subIstanzaUbicazioneRoot.get("piano"), piano));

                predicates.add(p);
                subIstanzaUbicazione.select(subIstanzaUbicazioneRoot.get("idIstanza")).where(builder.and(predicates.toArray(new Predicate[0])));
				
			    return builder.in(root.get("idIstanza")).value(subIstanzaUbicazione);
			}
		};
	}

	public static Specification<MudeTIstanzaSlim> findByTipoCatasto(final Long idComune
													, final Map tipoCatasto
													, final Map foglio
													, final Map numero
													, final Map subalterno) {
		if(idComune == null || (tipoCatasto == null && foglio == null && numero == null && subalterno == null)) return null;
		
		return new Specification<MudeTIstanzaSlim>() {
			@Override
			public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Subquery<Long> subIstanzaCatasto = query.subquery(Long.class);
                Root<MudeopenRLocCatasto> subIstanzaCatastoRoot = subIstanzaCatasto.from(MudeopenRLocCatasto.class);
                Predicate p = builder.and(
            		builder.equal(root.get("idIstanza"), subIstanzaCatastoRoot.get("idIstanza")),
            		builder.isNull(subIstanzaCatastoRoot.get("dataFine"))
                );
                
                List<Predicate> predicates = new ArrayList<>();
				if(tipoCatasto != null)
					predicates.add(getPredicateString(builder, subIstanzaCatastoRoot.get("f1TipoCatasto"), tipoCatasto));
				if(foglio != null)
					predicates.add(getPredicateString(builder, subIstanzaCatastoRoot.get("foglio"), foglio));
				if(numero != null)
					predicates.add(getPredicateString(builder, subIstanzaCatastoRoot.get("particella"), numero));
				if(subalterno != null)
					predicates.add(getPredicateString(builder, subIstanzaCatastoRoot.get("subalterno"), subalterno));

                predicates.add(p);
                subIstanzaCatasto.select(subIstanzaCatastoRoot.get("idIstanza")).where(builder.and(predicates.toArray(new Predicate[0])));
                
			    return builder.in(root.get("idIstanza")).value(subIstanzaCatasto);
			}
		};
	}

    public static Specification<MudeTIstanzaSlim> hasLivello(final Map map) {
    	if(map == null) return null;
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			    Subquery<Long> sub = query.subquery(Long.class);
			    Root<MudeRIstanzaStato> subRoot = sub.from(MudeRIstanzaStato.class);
			    Predicate p = builder.and(
			    		builder.and(
			    			builder.equal(root.get("idIstanza"), subRoot.get("istanza").get("id")), 
							builder.isNull(subRoot.get("dataFine"))
			    		),
			    		getPredicateString(builder, subRoot.get("statoIstanza").get("livello"), map)
			    );
			    sub.select(subRoot.get("istanza").get("id")).where(p);
			    
			    return builder.in(root.get("idIstanza")).value(sub);
            }
        };
    }

    
    public static Specification<MudeTIstanzaSlim> filterOnlyRootDS(Specifications<MudeTIstanzaSlim> allSpecs) {
        return new Specification<MudeTIstanzaSlim>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSlim> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
            	
			    Subquery<Long> sub = query.subquery(Long.class);
			    Root<MudeTIstanzaSlim> subRoot = sub.from(MudeTIstanzaSlim.class);
			    
                Root<MudeRIstanzaPratica> subRootRPrat = sub.from(MudeRIstanzaPratica.class);
                //final Join<MudeTIstanzaSlim, MudeRIstanzaPratica> mudeRIstanzaPraticaJoin = subRootRPrat.join("istanza", JoinType.INNER);

                predicates.add(
                	builder.equal(subRoot.get("idIstanza"), subRootRPrat.get("istanza").get("id"))
                );
                		
                predicates.add(allSpecs.toPredicate(subRoot, query, builder));
                
			    sub.select(builder.min(subRoot.get("idIstanza")))
			    	.where(builder.and(predicates.toArray(new Predicate[0])))
			    	.groupBy(subRootRPrat.get("pratica").get("id"));
			    
			    return builder.in(root.get("idIstanza")).value(sub);
			    
            }
        };
    }

    
}
