/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.specification;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRComuneFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeREnteFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggettoRuoli;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRRuoloFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.BaseSpecification;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoRuoloUtente;
public class MudeTIstanzaSlimSpecificationWS extends BaseSpecification {
    public static Specification<MudeTIstanzaSLIM> hasStato(final Map map) {
    	if(map == null) return null;
        return new Specification<MudeTIstanzaSLIM>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSLIM> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			    Subquery<Long> sub = query.subquery(Long.class);
			    Root<MudeRIstanzaStato> subRoot = sub.from(MudeRIstanzaStato.class);
			    Predicate p = builder.and(
			    		builder.and(
			    			builder.equal(root.get("id"), subRoot.get("istanza").get("id")), 
							builder.isNull(subRoot.get("dataFine"))
			    		),
			    		getPredicateString(builder, subRoot.get("statoIstanza").get("codice"), map)
			    );
			    sub.select(subRoot.get("istanza").get("id")).where(p);
			    
			    return builder.in(root.get("id")).value(sub);
            }
        };
    }

    public static Specification<MudeTIstanzaSLIM> hasStatoIn(final List list) {
    	if (list == null)
    		return null;
        return new Specification<MudeTIstanzaSLIM>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSLIM> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			    Subquery<Long> sub = query.subquery(Long.class);
			    Root<MudeRIstanzaStato> subRoot = sub.from(MudeRIstanzaStato.class);
			    Predicate p = builder.and(
			    		builder.and(
			    			builder.equal(root.get("id"), subRoot.get("istanza").get("id")), 
							builder.isNull(subRoot.get("dataFine"))
			    		),
			    		getPredicateListString(builder, subRoot.get("statoIstanza").get("codice"), list)
			    );
			    sub.select(subRoot.get("istanza").get("id")).where(p);
			    return builder.in(root.get("id")).value(sub);
            }
        };
    }

    public static Specification<MudeTIstanzaSLIM> hasTipoIstanzaIn(final List list) {
    	if (list == null)
    		return null;
        return new Specification<MudeTIstanzaSLIM>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSLIM> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	return getPredicateListString(builder, root.get("idTipoIstanza"), list);
            }
        };
    }

	// USED FOR: everything but ricercaPaginata03, modificaStatoIstanza, inserisciNotifica
	public static Specification<MudeTIstanzaSLIM> findByWS(List<Long> mudeDComuneList,
													   String numeroPratica,
													   Long annoPratica,
													   Long annoIstanza,
													   String tipoIntestatario,
													   String intestatario,        // Cognome o Ragione sociale
													   String intestatarioNome,
													   String indirizzo,
													   Integer annoIntervento) {
		return new Specification<MudeTIstanzaSLIM>() {
			@Override
			public Predicate toPredicate(Root<MudeTIstanzaSLIM> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();
				query.distinct(true);
				//Filtro per id_pratica
				if(StringUtils.isNotBlank(numeroPratica) || annoPratica != null) {
					Subquery<Long> praticaUtenteIdPratica = query.subquery(Long.class);
					Root<MudeRIstanzaPratica> praticaUtenteSubRoot = praticaUtenteIdPratica.from(MudeRIstanzaPratica.class);
					praticaUtenteIdPratica.select(root.get("id")).where(
							builder.and(
									builder.equal(root.get("id"), praticaUtenteSubRoot.get("istanza").get("id")),
									StringUtils.isNotBlank(numeroPratica) ? builder.equal(praticaUtenteSubRoot.get("pratica").get("numeroPratica"), numeroPratica.trim()) : builder.isTrue(builder.literal(true)),
									annoPratica != null ? builder.equal(praticaUtenteSubRoot.get("pratica").get("annoPratica").as(String.class), annoPratica) : builder.isTrue(builder.literal(true))
							));
					predicates.add(builder.and(
							builder.in(root.get("id")).value(praticaUtenteIdPratica)
					));
				}

				String[] statoFilter = new String[] {"BZZ", "FRM", "DFR", "RPA"};
				Subquery<Long> sub = query.subquery(Long.class);
				Root<MudeRIstanzaStato> subRoot = sub.from(MudeRIstanzaStato.class);
				Predicate p = builder.and(
						builder.and(
								builder.equal(root.get("id"), subRoot.get("istanza").get("id")),
								builder.isNull(subRoot.get("dataFine"))
						),
						builder.not(subRoot.get("statoIstanza").get("codice").in((Object[])statoFilter))
				);
				sub.select(subRoot.get("istanza").get("id")).where(p);
				predicates.add(builder.in(root.get("id")).value(sub));
				if(annoIstanza != null)
					predicates.add(builder.equal(builder.function("YEAR", Integer.class, root.get("dataDps") ), annoIstanza));
				if(mudeDComuneList != null && !mudeDComuneList.isEmpty())
					predicates.add(builder.in(root.get("indirizzo").get("mudeDComune").get("idComune")).value(mudeDComuneList));
				if(annoIntervento != null)
					predicates.add(builder.like(root.get("annoIntervento"), "%"+annoIntervento+"%"));
				// Aggiunta filtro per tipo intestatario
				if (StringUtils.isNotBlank(tipoIntestatario)) {
					Subquery<Long> subIntestatario = null;
					subIntestatario = query.subquery(Long.class);
					Root<MudeRIstanzaSoggetto> subRootIntestatario = subIntestatario.from(MudeRIstanzaSoggetto.class);
					Root<MudeRIstanzaSoggettoRuoli> subRootIntestatarioRuoli = subIntestatario.from(MudeRIstanzaSoggettoRuoli.class);
					final Join<MudeRIstanzaSoggetto, MudeTContatto> mudeTContattoJoin = subRootIntestatario.join("mudeTContatto");
					final String intestatarioLike = intestatario == null ? "%" : "%"+intestatario+"%";
					final String intestatarioNomeLike = intestatarioNome == null ? "%" : "%"+intestatarioNome+"%";
					Predicate pIntestatario = builder.and(
							builder.and(
									builder.equal(subRootIntestatario.get("idIstanzaSoggetto"), subRootIntestatarioRuoli.get("idIstanzaSoggetto")),
									builder.and(builder.equal(builder.upper(mudeTContattoJoin.get("tipoContatto")), tipoIntestatario.equalsIgnoreCase("F") ? TipoContatto.PF : TipoContatto.PG)),
									builder.and(builder.like(builder.upper(mudeTContattoJoin.get(tipoIntestatario.equalsIgnoreCase("F")?"cognome":"ragioneSociale")), intestatarioLike)),
									builder.and(builder.like(builder.upper(builder.coalesce(mudeTContattoJoin.get(tipoIntestatario.equalsIgnoreCase("F")?"nome":"ragioneSociale"),".")), intestatarioNomeLike)),
									builder.equal(subRootIntestatarioRuoli.get("ruoli"),"IN")
							)
					);
					subIntestatario.select(subRootIntestatario.get("mudeTIstanza").get("id")).where(pIntestatario);
					predicates.add(builder.in(root.get("id")).value(subIntestatario));
				}

				if (StringUtils.isNotBlank(indirizzo)) {
					predicates.add(builder.like(builder.upper(root.get("indirizzo").get("indirizzo")), "%"+indirizzo.toUpperCase()+"%"));
				}

				return builder.and(builder.and(predicates.toArray(new Predicate[0])));
			}
		};
	}

	// USED JUST FOR: ricercaPaginata03
    public static Specification<MudeTIstanzaSLIM> findByWS(String[] specieDSRegional,
    													Long idComune, 
											    		String numeroPratica,
											    		Long annoPratica,
											    		Long annoIstanza,
											    		String tipoIntestatario,
											    		String intestatario,		// Cognome o Ragione sociale
											    		String intestatarioNome,
											    		String indirizzo,
											    		Integer annoIntervento) {
        return new Specification<MudeTIstanzaSLIM>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanzaSLIM> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                query.distinct(true);
                predicates.add(builder.equal(root.get("idComune"), idComune));
                //Filtro per id_pratica
                if(StringUtils.isNotBlank(numeroPratica) || annoPratica != null) {
	            	Subquery<Long> praticaUtenteIdPratica = query.subquery(Long.class);     
	            	Root<MudeRIstanzaPratica> praticaUtenteSubRoot = praticaUtenteIdPratica.from(MudeRIstanzaPratica.class);  
	            		
	                praticaUtenteIdPratica.select(root.get("id")).where(
	            		builder.and(
							builder.equal(root.get("id"), praticaUtenteSubRoot.get("istanza").get("id")),
							StringUtils.isNotBlank(numeroPratica) ? builder.equal(praticaUtenteSubRoot.get("pratica").get("numeroPratica"), numeroPratica.trim()) : builder.isTrue(builder.literal(true)),
							annoPratica != null ? builder.equal(praticaUtenteSubRoot.get("pratica").get("annoPratica").as(String.class), ""+annoPratica) : builder.isTrue(builder.literal(true))
	                	));
	
	        		predicates.add(builder.and(
	        				builder.in(root.get("id")).value(praticaUtenteIdPratica)
	        		));
                }

                // adds filter states to be excluded from query
                String[] statoFilter = new String[] {"BZZ", "FRM", "DFR"};
				Root<MudeRIstanzaStato> subRootStato = query.from(MudeRIstanzaStato.class);
				Predicate p = builder.and(
							builder.equal(subRootStato.get("istanza").get("id"), root.get("id")),
							builder.isNull(subRootStato.get("dataFine")),
							builder.not(subRootStato.get("statoIstanza").get("codice").in((Object[])statoFilter))
				);
				predicates.add(p);
                if(annoIstanza != null) 
                    predicates.add(builder.equal(builder.function("YEAR", Integer.class, root.get("dataDps") ), annoIstanza));
                if(annoIntervento != null)
                    predicates.add(builder.like(root.get("annoIntervento"), "%"+annoIntervento+"%"));
                // Aggiunta filtro per tipo intestatario
                if (StringUtils.isNotBlank(tipoIntestatario)) {
                    Subquery<Long> subIntestatario = query.subquery(Long.class);
                    Root<MudeRIstanzaSoggetto> subRootIntestatario = subIntestatario.from(MudeRIstanzaSoggetto.class);
                    Root<MudeRIstanzaSoggettoRuoli> subRootIntestatarioRuoli = subIntestatario.from(MudeRIstanzaSoggettoRuoli.class);
                    final Join<MudeRIstanzaSoggetto, MudeTContatto> mudeTContattoJoin = subRootIntestatario.join("mudeTContatto");
                    final String intestatarioLike = intestatario == null ? "%" : "%"+intestatario+"%";
                    final String intestatarioNomeLike = intestatarioNome == null ? "%" : "%"+intestatarioNome+"%";
                    Predicate pIntestatario = builder.and(
                    		builder.and(
                    			builder.equal(subRootIntestatario.get("idIstanzaSoggetto"), subRootIntestatarioRuoli.get("idIstanzaSoggetto")),
                    			builder.and(builder.equal(builder.upper(mudeTContattoJoin.get("tipoContatto")), tipoIntestatario.equalsIgnoreCase("F") ? TipoContatto.PF : TipoContatto.PG)),
                    			builder.and(builder.like(builder.upper(mudeTContattoJoin.get(tipoIntestatario.equalsIgnoreCase("F")?"cognome":"ragioneSociale")), intestatarioLike)),
                    			builder.and(builder.like(builder.upper(builder.coalesce(mudeTContattoJoin.get(tipoIntestatario.equalsIgnoreCase("F")?"nome":"ragioneSociale"),".")), intestatarioNomeLike)),
                    			builder.equal(subRootIntestatarioRuoli.get("ruoli"),"IN")
                    		)
                    );
                    subIntestatario.select(subRootIntestatario.get("mudeTIstanza").get("id")).where(pIntestatario);
                    predicates.add(builder.in(root.get("id")).value(subIntestatario));
                }

                if (StringUtils.isNotBlank(indirizzo)) {
                    Root<MudeTIndirizzo> subRootINdirizzo = query.from(MudeTIndirizzo.class);
    				predicates.add(builder.and(
    						builder.equal(root.get("idIndirizzo"), subRootINdirizzo.get("id")),
    						builder.like(builder.function("concat", String.class, 
    	    						subRootINdirizzo.get("indirizzo"), 
    		        	            builder.literal(" "), 
    		        	            subRootINdirizzo.get("numeroCivico")), "%"+indirizzo+"%")
    						
    						)
    					);
                }

                predicates.add(
                		builder.or(
	                		builder.isNull(root.get("idSpeciePratica")),
	                		builder.not(root.get("idSpeciePratica").in(specieDSRegional))
                		));
                    
                return builder.and(builder.and(predicates.toArray(new Predicate[0])));
            }
        };
    }

    public static Specification<MudeTIstanzaSLIM> hasCodiceFascicolo(final String token) {
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTIstanzaSLIM>() {
            public Predicate toPredicate(Root<MudeTIstanzaSLIM> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	
				Subquery<Long> selectIdFascicolo = query.subquery(Long.class);
				Root<MudeTFascicolo> fascicoloRoot = selectIdFascicolo.from(MudeTFascicolo.class);
				Predicate pFascicolo = builder.like(builder.function("replace", 
					        	            String.class, fascicoloRoot.get("codiceFascicolo"), builder.literal("-"), 
					        	            builder.literal("")), 
							"%"+token.replaceAll("-", "")+"%");
				selectIdFascicolo.select(fascicoloRoot.get("id")).where(pFascicolo);
            	
                return builder.equal(root.get("idFascicolo"), selectIdFascicolo);
            }
        };
    }

    public static Specification<MudeTIstanzaSLIM> hasCodiceIstanza(final String token) {
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTIstanzaSLIM>() {
            public Predicate toPredicate(Root<MudeTIstanzaSLIM> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(builder.function("replace", 
			                	            String.class, root.get("codiceIstanza"), builder.literal("-"), 
			                	            builder.literal("")), 
                			"%"+token.replaceAll("-", "")+"%"
            		);
            }
        };
    }

    public static Specification<MudeTIstanzaSLIM> filterByFruitore(Long id_fruitore){
    	return  (root, query, builder) -> {
    		 Subquery<Long> selectIdEnte = query.subquery(Long.class);
    		 Root<MudeREnteFruitore> enteFruitoreRoot = selectIdEnte.from(MudeREnteFruitore.class);
    		 Predicate pEnteFruitore = builder.and(
    				 	builder.equal(enteFruitoreRoot.get("fruitore").get("idFruitore"), id_fruitore),
    				 	builder.isNull(enteFruitoreRoot.get("dataFine"))
    				 );
    		 selectIdEnte.select(enteFruitoreRoot.get("ente").get("id")).where(pEnteFruitore);
    		 
    		 Subquery<Long> selectComuneFruitore = query.subquery(Long.class);
    		 Root<MudeRComuneFruitore> comuneFruitoreRoot = selectComuneFruitore.from(MudeRComuneFruitore.class);
    		 Predicate pComuneFruitore = builder.and(
 				 	builder.equal(comuneFruitoreRoot.get("fruitore").get("idFruitore"), id_fruitore),
 				 	builder.isNull(comuneFruitoreRoot.get("dataFine"))
 				 );
    		 selectComuneFruitore.select(comuneFruitoreRoot.get("comune").get("idComune")).where(pComuneFruitore);
    		 
    		 Subquery<String> selectCodiceRuoloUtente = query.subquery(String.class);
    		 Root<MudeRRuoloFruitore> ruoloFruitoreRoot = selectCodiceRuoloUtente.from(MudeRRuoloFruitore.class);
    		 Predicate pRuoloFruitore = builder.and(
 				 	builder.equal(ruoloFruitoreRoot.get("mudeDFruitore").get("idFruitore"), id_fruitore),
 				 	builder.isNull(ruoloFruitoreRoot.get("idTipoIstanza")),
 				 	builder.isNull(ruoloFruitoreRoot.get("dataFine"))
 				 );
    		 selectCodiceRuoloUtente.select(ruoloFruitoreRoot.get("mudeDRuoloUtente").get("codice")).where(pRuoloFruitore);
    		 Subquery<String> selectCodiceTipoIstanzaUtente = query.subquery(String.class);
    		 Root<MudeRTipoIstanzaFruitore> tipoIstanzaFruitoreRoot = selectCodiceTipoIstanzaUtente.from(MudeRTipoIstanzaFruitore.class);
    		 Predicate pTipoIstanzaFruitore = builder.and(
 				 	builder.equal(tipoIstanzaFruitoreRoot.get("mudeDFruitore").get("idFruitore"), id_fruitore),
 				 	builder.isNull(tipoIstanzaFruitoreRoot.get("dataFine"))
 				 );
    		 selectCodiceTipoIstanzaUtente.select(tipoIstanzaFruitoreRoot.get("mudeDTipoIstanza").get("codice")).where(pTipoIstanzaFruitore);
    		 Subquery<MudeRIstanzaEnte> selectIstanzaEnte = query.subquery(MudeRIstanzaEnte.class);
    		 Root<MudeRIstanzaEnte> istanzaEnteRoot = selectIstanzaEnte.from(MudeRIstanzaEnte.class);
    		 Predicate pIstanzaEnte = 
							builder.and(
									builder.and(istanzaEnteRoot.get("ente").get("id").in(selectIdEnte)),
									builder.and(
											builder.equal(
												root.get("id"),
												istanzaEnteRoot.get("istanza").get("id"))
									),
									builder.isNull(istanzaEnteRoot.get("dataFine")),
									selectCodiceRuoloUtente.getSelection().in((Object[])(new String[] {TipoRuoloUtente.UTENTE_GESTORE_OPERAZIONI.getValue(), TipoRuoloUtente.UTENTE_LETTORE_OPERAZIONI.getValue()}) )
				);
    				 
    		 selectIstanzaEnte.select(istanzaEnteRoot.get("istanza").get("id")).where(pIstanzaEnte);
    		 return builder.and(
 				 	builder.or(
 				 			builder.equal(root.get("idFonte"), "WS"),
							builder.in(root.get("id")).value(selectIstanzaEnte) // link through mude_r_istanza_ente
 					),
 				 
 				 	builder.and(root.get("idTipoIstanza").in(selectCodiceTipoIstanzaUtente)),
 				 	builder.and(root.get("idComune").in(selectComuneFruitore))
				);
    	};
    }
}