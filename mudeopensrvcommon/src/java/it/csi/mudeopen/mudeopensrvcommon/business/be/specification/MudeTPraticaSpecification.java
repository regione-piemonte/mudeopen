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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRComuneFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeREnteFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
public class MudeTPraticaSpecification extends BaseSpecification {
    public static Specification<MudeTDocumento> findDocumentiByPratica(Long idPratica) {
        return new Specification<MudeTDocumento>() {
            @Override
            public Predicate toPredicate(Root<MudeTDocumento> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                query.distinct(true);
                //Filtro per id_pratica
                if(idPratica != null) {
	            	Subquery<Long> praticaUtenteIdPratica = query.subquery(Long.class);     
	            	Root<MudeTPratica> praticaUtenteSubRoot = praticaUtenteIdPratica.from(MudeTPratica.class);  
	            		
	                praticaUtenteIdPratica.select(root.get("id")).where(
	            		builder.and(
							builder.equal(root.get("pratica").get("id"), praticaUtenteSubRoot.get("id")),
							builder.equal(praticaUtenteSubRoot.get("id"), idPratica),
							builder.isNull(root.get("dataAnnullamento"))
	                	));
	
	        		predicates.add(builder.and(
	    					builder.in(root.get("id")).value(praticaUtenteIdPratica)
	                    )
	                );
                }

                return builder.and(builder.and(predicates.toArray(new Predicate[0])));
            }
        };
    }

 // Fix RUOLI Utente BO
    public static Specification<MudeTPratica> findByRole(MudeTUser mudeTUser, List<Long> mudeTPraticaList) {
    	
    	return new Specification<MudeTPratica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	return builder.in(root.get("id")).value(mudeTPraticaList);
            }
        };
    }

    public static Specification<MudeTPratica> findByFilters(MudeTUser mudeTUser,Long idPratica, Long anno, String numPratica, Long idComune,
			String scope) {
        return new Specification<MudeTPratica>() {
            @Override
            public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                // for IDF records
            	predicates.add(builder.isNotNull(root.get("annoPratica")));
                
                query.distinct(true);
                //Filtro per id_pratica
                if (idPratica != null) {
                	predicates.add(builder.equal(root.get("id"), idPratica));
                }

                //Condizione in like
                if (numPratica != null) {
                	final String numPraticaLike = "%" + numPratica.toUpperCase() + "%";
                    predicates.add(builder.like(builder.upper(root.get("numeroPratica")), numPraticaLike));
//                	predicates.add(builder.equal(root.get("numeroPratica"), numPratica));
                }

                if (anno != null) {
                	predicates.add(builder.equal(root.get("annoPratica"), anno));
                }

                if (idComune != null) {
                	Subquery<Long> sub = query.subquery(Long.class);
                    Root<MudeRIstanzaPratica> subRoot = sub.from(MudeRIstanzaPratica.class);
                    final Join<MudeTIstanza,MudeRIstanzaPratica> mudeTIstanzaJoin = subRoot.join("istanza");
                    Predicate p = builder.and(
                			builder.equal(root.get("id"), subRoot.get("pratica").get("id")), 
                    		builder.equal(mudeTIstanzaJoin.get("comune").get("idComune"),idComune),
							builder.or(
									builder.isNull(subRoot.get("dataFine")),
									builder.greaterThan(subRoot.get("dataFine"), Date.from(Instant.now()))
							)
                    		
                    );
                    sub.select(subRoot.get("pratica").get("id")).where(p);
                    predicates.add(builder.in(root.get("id")).value(sub));
                }

                return builder.and(builder.and(predicates.toArray(new Predicate[0])));
            }
        };
    }

    public static Specification<MudeTPratica> findByIstatComune(String istatComune) {
    	 return new Specification<MudeTPratica>() {
             @Override
             public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        	 	List<Predicate> predicates = new ArrayList<>();
        	 	
				// for IDF records
				predicates.add(builder.isNull(root.get("dataFine")));
                 
                query.distinct(true);
                if (StringUtils.isNotBlank(istatComune)) {
                	Subquery<Long> sub = query.subquery(Long.class);
                    Root<MudeRIstanzaPratica> subRoot = sub.from(MudeRIstanzaPratica.class);
                    final Join<MudeTIstanza,MudeRIstanzaPratica> mudeTIstanzaJoin = subRoot.join("istanza");
                    Instant now = Instant.now();
					Predicate p = builder.and(
                    		builder.and(
                    			builder.equal(root.get("id"), subRoot.get("pratica").get("id")) 
                    		),
                    		builder.equal(mudeTIstanzaJoin.get("comune").get("istatComune"),istatComune),
                    		builder.or(
                    				builder.isNull(mudeTIstanzaJoin.get("comune").get("fineValidita")),
                    				builder.greaterThan(mudeTIstanzaJoin.get("comune").get("fineValidita"), Date.from(now))
                    				),
                    		builder.or(
                    				builder.isNull(mudeTIstanzaJoin.get("comune").get("inizioValidita")),
                    				builder.lessThanOrEqualTo(mudeTIstanzaJoin.get("comune").get("inizioValidita"), Date.from(now))
                    				)
                    );
                    sub.select(subRoot.get("pratica").get("id")).where(p);
                    predicates.add(builder.in(root.get("id")).value(sub));
                }

                return builder.and(builder.and(predicates.toArray(new Predicate[0])));
             }
         };
    }

    public static Specification<MudeTPratica> findByAnnoPratica(String annoPratica) {
   	 return new Specification<MudeTPratica>() {
            @Override
            public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                query.distinct(true);
                if (StringUtils.isNotBlank(annoPratica)) {
                	predicates.add(builder.equal(root.get("annoPratica"), annoPratica));
                }

                return builder.and(builder.and(predicates.toArray(new Predicate[0])));
            }
        };
   }

    public static Specification<MudeTPratica> findByNumeroPratica(String numeroPratica) {
      	 return new Specification<MudeTPratica>() {
               @Override
               public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                   List<Predicate> predicates = new ArrayList<>();
                   query.distinct(true);
                   if (StringUtils.isNotBlank(numeroPratica)) {
                   	predicates.add(builder.equal(root.get("numeroPratica"), numeroPratica));
                   }

                   return builder.and(builder.and(predicates.toArray(new Predicate[0])));
               }
           };
    }

    public static Specification<MudeTPratica> findByFruitore(String codiceFruitore) {
    	 return new Specification<MudeTPratica>() {
             @Override
             public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				query.distinct(true);
				
				Subquery<Long> selectIdComune = query.subquery(Long.class);
				Root<MudeRComuneFruitore> subRootComuneFruitore = selectIdComune.from(MudeRComuneFruitore.class);
				Predicate pComuneFruitore = builder.and(
						 builder.equal(subRootComuneFruitore.get("fruitore").get("codiceFruitore"), codiceFruitore),
						 builder.isNull(subRootComuneFruitore.get("dataFine"))
						 );
				selectIdComune.select(subRootComuneFruitore.get("comune").get("idComune")).where(pComuneFruitore);
				 
				Subquery<Long> selectIdIstanza = query.subquery(Long.class);
				Root<MudeTIstanza> subRootIstanza = selectIdIstanza.from(MudeTIstanza.class);
				Predicate pIstanzaComune = builder.and(
						 	subRootIstanza.get("comune").get("idComune").in(selectIdComune)
						 );
				selectIdIstanza.select(subRootIstanza.get("id")).where(pIstanzaComune);
                Subquery<Long> selectIdPratica = query.subquery(Long.class);
                Root<MudeRIstanzaPratica> subRootIstanzaPratica = selectIdPratica.from(MudeRIstanzaPratica.class);
                Predicate pIstanzaPratica = builder.and(
                		 	subRootIstanzaPratica.get("istanza").get("id").in(selectIdIstanza)
                		 );
                selectIdPratica.select(subRootIstanzaPratica.get("pratica").get("id")).where(pIstanzaPratica);
                return builder.and(root.get("id").in(selectIdPratica));
             }
         };
    }

    public static Specification<MudeTPratica> findByIstanzaConStati(List<String> statiIstanza){
    	if (statiIstanza == null)
    		return null;
    	return new Specification<MudeTPratica>() {
            @Override
            public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                query.distinct(true);
                Subquery<Long> idIstanza = query.subquery(Long.class);
                Root<MudeRIstanzaStato> subRootIStanzaStato = idIstanza.from(MudeRIstanzaStato.class);
                idIstanza.select(subRootIStanzaStato.get("istanza").get("id")).where(
                				builder.and(builder.isNull(subRootIStanzaStato.get("dataFine")),subRootIStanzaStato.get("statoIstanza").get("codice").in(statiIstanza))
                		);
                Subquery<Long> sub = query.subquery(Long.class);
                Root<MudeRIstanzaPratica> subRoot = sub.from(MudeRIstanzaPratica.class);
                final Join<MudeTIstanza,MudeRIstanzaPratica> mudeTIstanzaJoin = subRoot.join("istanza");
                Predicate pIstanzaStato = builder.and(
                		mudeTIstanzaJoin.get("id").in(idIstanza)
                		);
                sub.select(subRoot.get("pratica").get("id")).where(pIstanzaStato);
                return builder.and(root.get("id").in(sub));
            }
        };
    	
    }

    public static Specification<MudeTPratica> addIstanzaSpcs(final Specifications<MudeTIstanza> specs) {
        return new Specification<MudeTPratica>() {
            @Override
            public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Subquery<Long> selectIdIstanza = query.subquery(Long.class);
				Root<MudeTIstanza> istanzaRoot = selectIdIstanza.from(MudeTIstanza.class);
				selectIdIstanza.select(istanzaRoot.get("id")).where( /*builder.and(istanzaRoot.get("id").in(1L) ) */ specs.toPredicate(istanzaRoot, query, builder) );
				
				Subquery<Long> selectIdIstanzaPratica = query.subquery(Long.class);
				Root<MudeRIstanzaPratica> istanzaPraticaRoot = selectIdIstanzaPratica.from(MudeRIstanzaPratica.class);
				selectIdIstanzaPratica.select(istanzaPraticaRoot.get("pratica").get("id")).where(builder.and(
						istanzaPraticaRoot.get("istanza").get("id").in(selectIdIstanza),
						builder.or(
								builder.isNull(istanzaPraticaRoot.get("dataFine")),
								builder.greaterThan(istanzaPraticaRoot.get("dataFine"), Date.from(Instant.now()))
						)
					));
				 
				return builder.and(root.get("id").in(selectIdIstanzaPratica));
            }
        };
    }

    public static Specification<MudeTPratica> findByPraticaFrom(final LocalDate dataRegistrazionePraticaDa) {
    	if(dataRegistrazionePraticaDa == null) return null;
        return new Specification<MudeTPratica>() {
            @Override
            public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.greaterThanOrEqualTo(root.get("dataInizio"), Timestamp.valueOf(dataRegistrazionePraticaDa.atStartOfDay()));
            }
        };
    }

    public static Specification<MudeTPratica> findByPraticaTo(final LocalDate dataRegistrazionePraticaA) {
    	if(dataRegistrazionePraticaA == null) return null;
        return new Specification<MudeTPratica>() {
            @Override
            public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.lessThanOrEqualTo(root.get("dataInizio"), Timestamp.valueOf(dataRegistrazionePraticaA.atTime(23, 59)));
            }
        };
    }

    public static Specification<MudeTPratica> findByEnte(final Map map) {
     	 return new Specification<MudeTPratica>() {
              @Override
              public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	  Predicate codice = getPredicateString(builder, root.get("ente").get("codice"), map);
            	  Predicate descrizione = getPredicateString(builder, root.get("ente").get("descrizione"), map);
            	  Predicate descrizioneEstesa = getPredicateString(builder, root.get("ente").get("descrizioneEstesa"), map);
                  return builder.or(codice, descrizione, descrizioneEstesa);
              }
          };
   }

    public static Specification<MudeTPratica> addFruitoreEnte(String codiceFruitore) {
        return new Specification<MudeTPratica>() {
            @Override
            public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Subquery<Long> selectIdEnte = query.subquery(Long.class);
				Root<MudeREnteFruitore> istanzaPraticaRoot = selectIdEnte.from(MudeREnteFruitore.class);
				selectIdEnte.select(istanzaPraticaRoot.get("ente").get("id")).where(builder.equal(istanzaPraticaRoot.get("fruitore").get("codiceFruitore"), codiceFruitore));
				 
				return builder.and(root.get("ente").get("id").in(selectIdEnte));
            }
        };
    }

    public static Specification<MudeTPratica> findById(final Map map) {
        return new Specification<MudeTPratica>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<MudeTPratica> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateLong(builder, root.get("id"), map);
            }
        };
    }
}