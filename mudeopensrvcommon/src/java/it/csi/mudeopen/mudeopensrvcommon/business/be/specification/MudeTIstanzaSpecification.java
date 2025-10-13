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
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRAbilitazioneFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggettoRuoli;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteEnteComuneRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaCosmo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCatasto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocUbicazione;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.DateSearchBackofficeEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.QueryOpEnum;
public class MudeTIstanzaSpecification extends BaseSpecification {
    private static final String BACKOFFICE = "backoffice";

	public static Specification<MudeTIstanza> findBy(MudeTUser mudeTUser, List<Long> mudeDComuneList, Long idDug, String duf,
    		String idIntestatarioPf, String idIntestatarioPg, String idPm, MudeTFascicolo mudeTFascicolo, List<Long> praticaIDs, MudeDTipoIstanza mudeDTipoIstanza, 
    		LocalDate dataCreazioneDa, LocalDate dataCreazioneA, String codiceIstanza,
                                                     String scope, 
                                                     boolean excludeFoAbilitazioni, 
                                                     boolean isDSRegional,
												     boolean skipStateCheck,
												     List<String> soggettiRole) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                query.distinct(true);
                
        		predicates.add(builder.or(
								builder.isNull(root.get("dataFine")),
								builder.greaterThan(root.get("dataFine"), Date.from(Instant.now()))
						));
                
                if (mudeTFascicolo != null) {
                    final Join<MudeTIstanza, MudeTFascicolo> mudeTFascicoloJoin = root.join("mudeTFascicolo");
                    predicates.add(builder.equal(mudeTFascicoloJoin.get("id"), mudeTFascicolo.getId()));
                }

                //Filtro per id_pratica
                if(!praticaIDs.isEmpty()) {
	            	Subquery<Long> praticaUtenteIdPratica = query.subquery(Long.class);     
	            	Root<MudeRIstanzaPratica> praticaUtenteSubRoot = praticaUtenteIdPratica.from(MudeRIstanzaPratica.class);  
	            		
	                praticaUtenteIdPratica.select(root.get("id")).where(
	            		builder.and(
							builder.equal(root.get("id"), praticaUtenteSubRoot.get("istanza").get("id")),
							builder.in(praticaUtenteSubRoot.get("pratica").get("id")).value(praticaIDs)
	                	));
	
	        		predicates.add(builder.and(
	    					builder.in(root.get("id")).value(praticaUtenteIdPratica)
	                    )
	                );
                }

                if(mudeDComuneList != null && idDug == null && duf == null) {
                	if(mudeDComuneList.size() == 1)
                		predicates.add(builder.equal(root.get("comune").get("idComune"), mudeDComuneList.get(0)));
                	else if(mudeDComuneList.size() >= 1)
                		predicates.add(builder.in(root.get("comune").get("idComune")).value(mudeDComuneList));
                }
                else {
	                if (mudeDComuneList != null && !mudeDComuneList.isEmpty()) {
	                    predicates.add(builder.in(root.get("indirizzo").get("mudeDComune").get("idComune")).value(mudeDComuneList));
	                }
	
	                if (idDug != null) {
	                    predicates.add(builder.equal(root.get("indirizzo").get("idDug"), idDug));
	                }
	
	                if (duf != null) {
	                    predicates.add(builder.like(builder.upper(root.get("indirizzo").get("indirizzo")), "%" + duf.toUpperCase() + "%"));
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
                    			builder.in(subRootIntestatarioRuoli.get("ruoli")).value(soggettiRole)
                    		)
                    );
                    subIntestatario.select(subRootIntestatario.get("mudeTIstanza").get("id")).where(pIntestatario);
                    predicates.add(builder.in(root.get("id")).value(subIntestatario));
                	 
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
                    			builder.in(subRootIntestatarioRuoli.get("ruoli")).value(soggettiRole)
                    		)
                    );
                    subIntestatario.select(subRootIntestatario.get("mudeTIstanza").get("id")).where(pIntestatario);
                    predicates.add(builder.in(root.get("id")).value(subIntestatario));
                	 
                }

                if (mudeDTipoIstanza != null) {
                    final Join<MudeTIstanza, MudeDTipoIstanza> mudeDTipoIstanzaJoin = root.join("tipoIstanza");
                    predicates.add(builder.equal(mudeDTipoIstanzaJoin.get("codice"), mudeDTipoIstanza.getCodice()));
                }

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
	                			builder.equal(root.get("id"), subRoot.get("istanza").get("id")), 
								builder.isNull(subRoot.get("dataFine")),
	                    		builder.not(subRoot.get("statoIstanza").get("codice").in((Object[])overrideStates))
	                    ));
	                    
	                    predicates.add(builder.in(root.get("id")).value(sub));
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
                        			//builder.equal(root.get("id"), subRootPm.get("istanza").get("id")),
                        			
                        			builder.in(mudeDAbilitazioneJoin.get("codice")).value(codAbilitazione),
                        			builder.or( //
                                            builder.like(builder.upper(mudeTUserJoin.get("nome")), idPmLike), //
                                            builder.like(builder.upper(mudeTUserJoin.get("cognome")), idPmLike), //
                                            builder.like(builder.upper(mudeTUserJoin.get("cf")), idPmLike) //
                                    )
                        		)
                        		
                        );
                        subPm.select(subRootPm.get("istanza").get("id")).where(pPm);
                        predicates.add(builder.in(root.get("id")).value(subPm));
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
	                					builder.in(root.get("id")).value(istanzaUtenteIdIstanza),
	                					builder.equal(root.get("mudeTFascicolo").get("mudeTUser").get("idUser"), mudeTUser.getIdUser())
	                			)
                			);
	                
                }

                else // search without current user 
	                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static Specification<MudeTIstanza> findPraticheBy(MudeTUser mudeTUser, String codiceIstanza, /*List<Long> mudeTContattoList*/String idIntestatarioPf,
                                                             String idIntestatarioPg, String idPm, Long idIstanzaRiferimento, MudeTFascicolo mudeTFascicolo, MudeDTipoIstanza mudeDTipoIstanza,
                                                             String numeroProtocollo, LocalDate dataProtocolloDa, LocalDate dataProtocolloA, Long anno, MudeTPratica pratica, StatoIstanza stato, 
                                                             LocalDate dataRegistrazionePraticaDa, LocalDate dataRegistrazionePraticaA, String scope) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                query.distinct(true);
                if (mudeTUser != null && !BACKOFFICE.equalsIgnoreCase(scope)) {
                    final Join<MudeTIstanza, MudeTFascicolo> mudeTFascicoloJoin = root.join("mudeTFascicolo");
                    predicates.add(builder.equal(mudeTFascicoloJoin.get("mudeTUser").get("idUser"), mudeTUser.getIdUser()));
                }

                if (mudeTFascicolo != null) {
                    final Join<MudeTIstanza, MudeTFascicolo> mudeTFascicoloJoin = root.join("mudeTFascicolo");
                    predicates.add(builder.equal(mudeTFascicoloJoin.get("id"), mudeTFascicolo.getId()));
                }

/*                if (mudeTContattoList != null) {
                    final Join<MudeTIstanza, MudeTContatto> mudeTContattoJoin = root.join("intestatario");
                    final Path<MudeTContatto> group = mudeTContattoJoin.<MudeTContatto>get("id");
                    predicates.add(group.in(mudeTContattoList));
                }*/
                if (StringUtils.isNotBlank(idIntestatarioPf) && !BACKOFFICE.equalsIgnoreCase(scope)) {
                    final String idIntestatarioPfLike = "%" + idIntestatarioPf.toUpperCase() + "%";
                    predicates.add( //
                            builder.or( //
                                    builder.like(builder.upper(root.get("mudeTFascicolo").get("intestatario").get("nome")), idIntestatarioPfLike), //
                                    builder.like(builder.upper(root.get("mudeTFascicolo").get("intestatario").get("cognome")), idIntestatarioPfLike), //
                                    builder.like(builder.upper(root.get("mudeTFascicolo").get("intestatario").get("cf")), idIntestatarioPfLike) //
                            ));
                } else if (StringUtils.isNotBlank(idIntestatarioPg) && !BACKOFFICE.equalsIgnoreCase(scope)) {
                    final String idIntestatarioPgLike = "%" + idIntestatarioPg.toUpperCase() + "%";
                    predicates.add( //
                            builder.or( //
                                    builder.like(builder.upper(root.get("mudeTFascicolo").get("intestatario").get("ragioneSociale")), idIntestatarioPgLike), //
                                    builder.like(builder.upper(root.get("mudeTFascicolo").get("intestatario").get("piva")), idIntestatarioPgLike), //
                                    builder.like(builder.upper(root.get("mudeTFascicolo").get("intestatario").get("pivaComunitaria")), idIntestatarioPgLike) //
                            ));
                }

                if (mudeDTipoIstanza != null) {
                    final Join<MudeTIstanza, MudeDTipoIstanza> mudeDTipoIstanzaJoin = root.join("tipoIstanza");
                    predicates.add(builder.equal(mudeDTipoIstanzaJoin.get("codice"), mudeDTipoIstanza.getCodice()));
                }

//                if (dataProtocollo != null){
//                    //todo il campo al momento non esiste
////                    predicates.add(builder.greaterThanOrEqualTo(root.get("dataProtocollo"), Timestamp.valueOf(dataProtocollo.atTime(23, 59))));
//
//                }
                if (dataProtocolloDa != null) {
                    //todo la colonna al momento non esiste sul db
//                    predicates.add(builder.greaterThanOrEqualTo(root.get("dataProtocollo"), Timestamp.valueOf(dataProtocolloDa.atStartOfDay())));
                }

                if (dataProtocolloA != null) {
                    //todo la colonna al momento non esiste sul db
//                    predicates.add(builder.lessThanOrEqualTo(root.get("dataProtocollo"), Timestamp.valueOf(dataProtocolloA.atTime(23, 59))));
                }

                if (dataRegistrazionePraticaDa != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("dataRegistrazionePratica"), Timestamp.valueOf(dataRegistrazionePraticaDa.atStartOfDay())));
                }

                if (dataRegistrazionePraticaA != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("dataRegistrazionePratica"), Timestamp.valueOf(dataRegistrazionePraticaA.atTime(23, 59))));
                }

                if (codiceIstanza != null) {
                    final String codiceIstanzaLike = "%" + codiceIstanza + "%";
                    predicates.add(builder.like(root.get("codiceIstanza"), codiceIstanzaLike));
                }

                /*Implementato nella pratica
                 * if (anno != null) {
                    predicates.add(builder.equal(root.get("anno"), anno));
                }*/
                if (numeroProtocollo != null) {
                    predicates.add(builder.equal(root.get("numeroProtocollo"), numeroProtocollo));
                }

                /*if (pratica != null) {
                	Subquery<Long> sub = query.subquery(Long.class);
                    Root<MudeRIstanzaPratica> subRoot = sub.from(MudeRIstanzaPratica.class);
                    Predicate p = builder.and(
                    		builder.and(
                    			builder.equal(root.get("id"), subRoot.get("istanza").get("id")),
                    			builder.equal(subRoot.get("pratica").get("id"),pratica.getId())
                    		)
                    		
                    );
                    sub.select(subRoot.get("istanza").get("id")).where(p);
                    predicates.add(builder.in(root.get("id")).value(sub));
                	
                }*/
                if (pratica != null || anno != null) {
	            	Subquery<Long> praticaUtenteIdPratica = query.subquery(Long.class);     
	            	Root<MudeRIstanzaPratica> praticaUtenteSubRoot = praticaUtenteIdPratica.from(MudeRIstanzaPratica.class);  
	            	
	            	if(anno != null) {
		                praticaUtenteIdPratica.select(root.get("id")).where(
		            		builder.and(
								builder.equal(root.get("id"), praticaUtenteSubRoot.get("istanza").get("id")),
								builder.equal(praticaUtenteSubRoot.get("pratica").get("annoPratica").as(String.class),anno.toString())
		                	));
	            	}

	            	if(pratica != null) {
		                praticaUtenteIdPratica.select(root.get("id")).where(
		            		builder.and(
								//builder.equal(abilitazioneFunzioni_Join.get("id"), praticaUtenteSubRoot.get("abilitazione").get("id")),
								//builder.equal(afRoot.get("funzione").get("codice"), FunzioniAbilitazioniEnum.CONS_IST_ALL_FASCIC.getDescription()),
								builder.equal(root.get("id"), praticaUtenteSubRoot.get("istanza").get("id")),
								builder.equal(praticaUtenteSubRoot.get("pratica").get("id"),pratica.getId())
								//builder.isNull(praticaUtenteSubRoot.get("dataFine")),
		    				   	//builder.equal(praticaUtenteSubRoot.get("utente").get("idUser"), idUser)
		                	));
	            	}
		
	        		predicates.add(builder.and(
	    					builder.in(root.get("id")).value(praticaUtenteIdPratica)
	                    )
	                );
                }

                if (idIstanzaRiferimento != null) {
                    predicates.add(builder.equal(root.get("idIstanzaRiferimento"), idIstanzaRiferimento));
                }

                /*if (stato != null) {
                	final Join<MudeTIstanza, MudeRIstanzaStato> mudeRIstanzaStatoJoin = root.join("mudeRIstanzaStato");
                    predicates.add(builder.equal(mudeRIstanzaStatoJoin.get("statoIstanza").get("codice"), stato.getValue()));
                }*/
                if (BACKOFFICE.equalsIgnoreCase(scope)) {
                	String[] statoFilter = new String[] {"BZZ", "RPA", "DFR", "FRM"};
                    Subquery<Long> sub = query.subquery(Long.class);
                    Root<MudeRIstanzaStato> subRoot = sub.from(MudeRIstanzaStato.class);
                    if (stato == null) {
	                    Predicate p = builder.and(
	                    		builder.and(
	                    			builder.equal(root.get("id"), subRoot.get("istanza").get("id")), 
									builder.isNull(subRoot.get("dataFine"))
	                    		),
	                    		builder.not(subRoot.get("statoIstanza").get("codice").in((Object[])statoFilter))
	                    );
	                    sub.select(subRoot.get("istanza").get("id")).where(p);
	                    predicates.add(builder.in(root.get("id")).value(sub));
                    }else {
                    	String[] statoFilterImpostato = new String[] {stato.getValue()};
                    	Predicate p = builder.and(
	                    		builder.and(
	                    			builder.equal(root.get("id"), subRoot.get("istanza").get("id")), 
									builder.isNull(subRoot.get("dataFine"))
	                    		),
	                    		builder.and(subRoot.get("statoIstanza").get("codice").in((Object[])statoFilterImpostato))
	                    );
	                    sub.select(subRoot.get("istanza").get("id")).where(p);
	                    predicates.add(builder.in(root.get("id")).value(sub));
                    }

                    Subquery<Long> subIntestatario=null;
                    if (StringUtils.isNotBlank(idIntestatarioPf)) {
                    	final String idIntestatarioPfLike = "%" + idIntestatarioPf.toUpperCase() + "%";
                    	subIntestatario = query.subquery(Long.class);
                    	Root<MudeTPratica> subRootPratica = subIntestatario.from(MudeTPratica.class);
                    	Root<MudeRIstanzaPratica> subRootIstanzaPratica = subIntestatario.from(MudeRIstanzaPratica.class);
                        Root<MudeRIstanzaSoggetto> subRootIntestatario = subIntestatario.from(MudeRIstanzaSoggetto.class);
                        Root<MudeRIstanzaSoggettoRuoli> subRootIntestatarioRuoli = subIntestatario.from(MudeRIstanzaSoggettoRuoli.class);
                        final Join<MudeRIstanzaSoggetto, MudeTContatto> mudeTContattoJoin = subRootIntestatario.join("mudeTContatto");
                        //final Join<MudeRIstanzaSoggettoRuoli,MudeRIstanzaSoggetto> mudeTIstanzaSoggettoRuoliJoin = subRootIntestatario.join("id_istanza_soggetto");
                        Predicate pIntestatario = builder.and(
                        		builder.and(
                        			builder.isNotNull(subRootPratica.get("annoPratica")),
                        			builder.equal(subRootPratica.get("id"), subRootIstanzaPratica.get("pratica").get("id")),
                        			builder.equal(subRootIntestatario.get("idIstanzaSoggetto"), subRootIntestatarioRuoli.get("idIstanzaSoggetto")),
                        			builder.or( //
                                            builder.like(builder.upper(mudeTContattoJoin.get("nome")), idIntestatarioPfLike), //
                                            builder.like(builder.upper(mudeTContattoJoin.get("cognome")), idIntestatarioPfLike), //
                                            builder.like(builder.upper(mudeTContattoJoin.get("cf")), idIntestatarioPfLike) //
                                    ),
                        			builder.equal(subRootIntestatarioRuoli.get("ruoli"),"IN")
                        		)
                        );
                        subIntestatario.select(subRootIntestatario.get("mudeTIstanza").get("id")).where(pIntestatario);
                        predicates.add(builder.in(root.get("id")).value(subIntestatario));
                    	 
                    } else if (StringUtils.isNotBlank(idIntestatarioPg)) {
                    	final String idIntestatarioPgLike = "%" + idIntestatarioPg.toUpperCase() + "%";
                    	subIntestatario = query.subquery(Long.class);
                    	Root<MudeTPratica> subRootPratica = subIntestatario.from(MudeTPratica.class);
                    	Root<MudeRIstanzaPratica> subRootIstanzaPratica = subIntestatario.from(MudeRIstanzaPratica.class);
                        Root<MudeRIstanzaSoggetto> subRootIntestatario = subIntestatario.from(MudeRIstanzaSoggetto.class);
                        Root<MudeRIstanzaSoggettoRuoli> subRootIntestatarioRuoli = subIntestatario.from(MudeRIstanzaSoggettoRuoli.class);
                        final Join<MudeRIstanzaSoggetto, MudeTContatto> mudeTContattoJoin = subRootIntestatario.join("mudeTContatto");
                        //final Join<MudeRIstanzaSoggettoRuoli,MudeRIstanzaSoggetto> mudeTIstanzaSoggettoRuoliJoin = subRootIntestatario.join("id_istanza_soggetto");
                        Predicate pIntestatario = builder.and(
                        		builder.and(
                        			builder.isNotNull(subRootPratica.get("annoPratica")),
                        			builder.equal(subRootPratica.get("id"), subRootIstanzaPratica.get("pratica").get("id")),
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
                        predicates.add(builder.in(root.get("id")).value(subIntestatario));
                    	 
                    }

                    if (StringUtils.isNotBlank(idPm)) {
                    	final String idPmLike = "%" + idPm.toUpperCase() + "%";
                    	Subquery<Long> subPm = query.subquery(Long.class);
                    	
                    	Root<MudeRIstanzaUtente> subRootPm = subPm.from(MudeRIstanzaUtente.class);
                        final Join<MudeRIstanzaUtente,MudeDAbilitazione> mudeDAbilitazioneJoin = subRootPm.join("abilitazione");
                        final Join<MudeRIstanzaUtente, MudeTUser> mudeTUserJoin = subRootPm.join("utente");
                        List<String> codAbilitazione = new ArrayList<String>();
                        codAbilitazione.add("PM_RUP_PM_OBB");
                        codAbilitazione.add("PM_RUP_PM_OPZ");
                        Predicate pPm = builder.and(
                        		builder.and(
                        			builder.equal(root.get("id"), subRootPm.get("istanza").get("id")),
                        			builder.in(mudeDAbilitazioneJoin.get("codice")).value(codAbilitazione),
                        			builder.or( //
                                            builder.like(builder.upper(mudeTUserJoin.get("nome")), idPmLike), //
                                            builder.like(builder.upper(mudeTUserJoin.get("cognome")), idPmLike), //
                                            builder.like(builder.upper(mudeTUserJoin.get("cf")), idPmLike) //
                                    )
                        		)
                        		
                        );
                        subPm.select(subRootPm.get("istanza").get("id")).where(pPm);
                        predicates.add(builder.in(root.get("id")).value(subPm));
                    }
                    	
                	// search only mudeopen_t_istanza.id externally linked to istanzaUtente or those linked (via fascicolo) to the current user 
                    return builder.and(predicates.toArray(new Predicate[0]));
                }

                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static Specification<MudeTIstanza> findByFascicolo(MudeTFascicolo mudeTFascicolo, Long idUser) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                if (mudeTFascicolo != null) {
                    final Join<MudeTIstanza, MudeTFascicolo> mudeTFascicoloJoin = root.join("mudeTFascicolo");
                    predicates.add(builder.equal(mudeTFascicoloJoin.get("id"), mudeTFascicolo.getId()));
                }

                if(idUser != null) {
                    // also include "istanze" where the mudeTUser is referenced
            		Subquery<Long> fascicoloUtenteIdFascicolo = query.subquery(Long.class);     
            		Root<MudeRFascicoloUtente> fascicoloUtenteSubRoot = fascicoloUtenteIdFascicolo.from(MudeRFascicoloUtente.class);  
            		
            		Root<MudeTIstanza> iRoot = fascicoloUtenteIdFascicolo.from(MudeTIstanza.class);
            		Join<MudeTIstanza, MudeTFascicolo> tFascicolo_Join = iRoot.join("mudeTFascicolo", JoinType.INNER);
            		Root<MudeRAbilitazioneFunzione> afRoot = fascicoloUtenteIdFascicolo.from(MudeRAbilitazioneFunzione.class);
            		Join<MudeRAbilitazioneFunzione, MudeDAbilitazione> abilitazioneFunzioni_Join = afRoot.join("abilitazione", JoinType.INNER);
            		
                    fascicoloUtenteIdFascicolo.select(iRoot.get("id")).where(
                		builder.and(
    						builder.equal(abilitazioneFunzioni_Join.get("id"), fascicoloUtenteSubRoot.get("abilitazione").get("id")),
    						builder.equal(afRoot.get("funzione").get("codice"), FunzioniAbilitazioniEnum.ELENCO_IST_FASCIC.getDescription()),
    						builder.equal(tFascicolo_Join.get("id"), fascicoloUtenteSubRoot.get("fascicolo").get("id")),
    						builder.isNull(fascicoloUtenteSubRoot.get("dataFine")),
        				   	builder.equal(fascicoloUtenteSubRoot.get("utente").get("idUser"), idUser)
                    	));
	                // includes only "istanze" for which the user as istanzeUtente relations 
	        		Subquery<Long> istanzaUtenteId = query.subquery(Long.class);               
	        		Root<MudeRIstanzaUtente> istanzaUtenteSubRoot = istanzaUtenteId.from(MudeRIstanzaUtente.class);
	                
	        		istanzaUtenteId.select(istanzaUtenteSubRoot.get("istanza").get("id")).where(
	    				builder.and(
							builder.isNull(istanzaUtenteSubRoot.get("dataFine")),
	    				   	builder.equal(istanzaUtenteSubRoot.get("utente").get("idUser"), idUser)
	    				)
	            	);
	
            		predicates.add(builder.or(
        					builder.in(root.get("id")).value(fascicoloUtenteIdFascicolo),
        					builder.in(root.get("id")).value(istanzaUtenteId)
                        )
                    );
	        		
                }

                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static Specification<MudeTIstanza> findByPratica(MudeTPratica mudeTPratica, Long idUser) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                /*if (mudeTPratica != null) {
                    final Join<MudeTIstanza, MudeTPratica> mudeTPraticaJoin = root.join("mudeTPratica");
                    predicates.add(builder.equal(mudeTPraticaJoin.get("id"), mudeTPratica.getId()));
                }*/
                if(idUser != null) {
                    // also include "istanze" where the mudeTUser is referenced
            		Subquery<Long> praticaUtenteIdPratica = query.subquery(Long.class);     
            		Root<MudeRIstanzaPratica> praticaUtenteSubRoot = praticaUtenteIdPratica.from(MudeRIstanzaPratica.class);  
            		
            		//Root<MudeTIstanza> iRoot = praticaUtenteIdPratica.from(MudeTIstanza.class);
            		//Join<MudeTIstanza, MudeTPratica> tPratica_Join = iRoot.join("mudeTPratica", JoinType.INNER);
            		//Root<MudeRAbilitazioneFunzione> afRoot = praticaUtenteIdPratica.from(MudeRAbilitazioneFunzione.class);
            		//Join<MudeRAbilitazioneFunzione, MudeDAbilitazione> abilitazioneFunzioni_Join = afRoot.join("abilitazione", JoinType.INNER);
            		
                    praticaUtenteIdPratica.select(root.get("id")).where(
                		builder.and(
    						//builder.equal(abilitazioneFunzioni_Join.get("id"), praticaUtenteSubRoot.get("abilitazione").get("id")),
    						//builder.equal(afRoot.get("funzione").get("codice"), FunzioniAbilitazioniEnum.CONS_IST_ALL_FASCIC.getDescription()),
    						builder.equal(root.get("id"), praticaUtenteSubRoot.get("istanza").get("id")),
    						builder.equal(praticaUtenteSubRoot.get("pratica").get("id"),mudeTPratica.getId())
    						//builder.isNull(praticaUtenteSubRoot.get("dataFine")),
        				   	//builder.equal(praticaUtenteSubRoot.get("utente").get("idUser"), idUser)
                    	));
	                // includes only "istanze" for which the user as istanzeUtente relations 
	        		Subquery<Long> istanzaUtenteId = query.subquery(Long.class);               
	        		Root<MudeRIstanzaUtente> istanzaUtenteSubRoot = istanzaUtenteId.from(MudeRIstanzaUtente.class);
	                
	        		istanzaUtenteId.select(istanzaUtenteSubRoot.get("istanza").get("id")).where(
	    				builder.and(
							builder.isNull(istanzaUtenteSubRoot.get("dataFine")),
	    				   	builder.equal(istanzaUtenteSubRoot.get("utente").get("idUser"), idUser)
	    				)
	            	);
	
            		predicates.add(builder.or(
        					builder.in(root.get("id")).value(praticaUtenteIdPratica),
        					builder.in(root.get("id")).value(istanzaUtenteId)
                        )
                    );
	        		
                }

                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static Specification<MudeTIstanza> findByNumeroPratica(String numeroPratica) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            		Subquery<Long> praticaUtenteIdPratica = query.subquery(Long.class);     
            		Root<MudeRIstanzaPratica> praticaUtenteSubRoot = praticaUtenteIdPratica.from(MudeRIstanzaPratica.class);  
            		
                    praticaUtenteIdPratica.select(praticaUtenteSubRoot.get("istanza").get("id")).where(
    						builder.equal(praticaUtenteSubRoot.get("pratica").get("numeroPratica"), numeroPratica)
    					);
                    
			    return builder.in(root.get("id")).value(praticaUtenteIdPratica);
            }
        };
    }

    public static Specification<MudeTIstanza> findById(final Map map) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateLong(builder, root.get("id"), map);
            }
        };
    }

    public static Specification<MudeTIstanza> findByKeywords(final Map map) {
    	if(map == null 
    			|| map.get(QueryOpEnum.LIKE.getValue()) == null 
    			|| "".equals(((String)map.get(QueryOpEnum.LIKE.getValue())).trim()))
    		return null;
    	
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                for(String tag : ((String)map.get(QueryOpEnum.LIKE.getValue())).trim().replaceAll("[ ]+", " ").split(" "))
                	predicates.add(builder.like(builder.upper(root.get("keywords")), "%" + tag.toUpperCase() + "%"));
            	
                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    public static Specification<MudeTIstanza> hasTipoIstanza(final Map map) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("tipoIstanza").get("codice"), map);
            }
        };
    }

    // is it used?
    public static Specification<MudeTIstanza> findByNumeroProtocolloIstanza(final Map map) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("numeroProtocollo"), map);
            }
        };
    }

    public static Specification<MudeTIstanza> hasStato(final Map map) {
    	if(map == null) return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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

    public static Specification<MudeTIstanza> hasStatoIn(final List list) {
    	if (list == null)
    		return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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

    public static Specification<MudeTIstanza> hasTipoIstanzaIn(final List list) {
    	if (list == null)
    		return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			    //return builder.in(root.get("tipoIstanza").get("codice")).value(map);
            	return getPredicateListString(builder, root.get("tipoIstanza").get("codice"), list);
            }
        };
    }

    /*
    public static Specification<MudeTIstanza> hasCodiceFascicolo(final String token) {
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTIstanza>() {
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(builder.function("replace", 
			                	            String.class, root.get("mudeTFascicolo").get("codiceFascicolo"), builder.literal("-"), 
			                	            builder.literal("")), 
                			"%"+token.replaceAll("-", "")+"%"
            		);
            }
        };
    }
    */

    public static Specification<MudeTIstanza> hasCodiceIstanza(final String token) {
        if(StringUtils.isBlank(token)) return null;
        return new Specification<MudeTIstanza>() {
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(builder.function("replace", 
			                	            String.class, root.get("codiceIstanza"), builder.literal("-"), 
			                	            builder.literal("")), 
                			"%"+token.replaceAll("-", "")+"%"
            		);
            }
        };
    }

    public static Specification<MudeTIstanza> hasFonte(final Map map) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("idFonte"), map);
            }
        };
    }

    public static Specification<MudeTIstanza> findByRegistrazioneFrom(final LocalDate dataRegistrazioneDa) {
    	if(dataRegistrazioneDa == null) return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.greaterThanOrEqualTo(root.get("dataDps"), Timestamp.valueOf(dataRegistrazioneDa.atStartOfDay()));
            }
        };
    }

    public static Specification<MudeTIstanza> findByRegistrazioneTo(final LocalDate dataRegistrazioneA) {
    	if(dataRegistrazioneA == null) return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.lessThanOrEqualTo(root.get("dataDps"), Timestamp.valueOf(dataRegistrazioneA.atTime(23, 59)));
            }
        };
    }

    public static Specification<MudeTIstanza> findByProtocolloFrom(final LocalDate dataRegistrazioneProtocolloDa) {
    	if(dataRegistrazioneProtocolloDa == null) return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.greaterThanOrEqualTo(root.get("dataProtocollo"), Timestamp.valueOf(dataRegistrazioneProtocolloDa.atStartOfDay()));
            }
        };
    }

    public static Specification<MudeTIstanza> findByProtocolloTo(final LocalDate dataRegistrazioneProtocolloA) {
    	if(dataRegistrazioneProtocolloA == null) return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.lessThanOrEqualTo(root.get("dataProtocollo"), Timestamp.valueOf(dataRegistrazioneProtocolloA.atTime(23, 59)));
            }
        };
    }

    public static Specification<MudeTIstanza> findByDataDpsFrom(final String dataDpsDa) {
    	if(dataDpsDa == null) return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            	LocalDate date = LocalDate.parse(dataDpsDa, formatter);
                return builder.greaterThanOrEqualTo(root.get("dataDps"), Timestamp.valueOf(date.atStartOfDay()));
            }
        };
    }

    public static Specification<MudeTIstanza> findByDataDpsTo(final String dataDpsA) {
    	if(dataDpsA == null) return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            	LocalDate date = LocalDate.parse(dataDpsA, formatter);
                return builder.lessThanOrEqualTo(root.get("dataDps"), Timestamp.valueOf(date.atTime(23, 59)));
            }
        };
    }

    // BO
    public static Specification<MudeTIstanza> filterByComuneAndEnte(Long idUser) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Subquery<Long> subIstanzaEnte = query.subquery(Long.class);
                Root<MudeRIstanzaEnte> subIstanzaEnteRoot = subIstanzaEnte.from(MudeRIstanzaEnte.class);
                Root<MudeRUtenteEnteComuneRuolo> utenteEnteComuneRuolo = subIstanzaEnte.from(MudeRUtenteEnteComuneRuolo.class);
                Predicate p = builder.and(
            		builder.equal(root.get("id"), subIstanzaEnteRoot.get("istanza").get("id")),
            		builder.isNull(subIstanzaEnteRoot.get("dataFine")),
            		
            		builder.equal(utenteEnteComuneRuolo.get("idComune"), root.get("comune").get("idComune")),
            		builder.equal(utenteEnteComuneRuolo.get("idUtente"), idUser),
            		builder.isNull(utenteEnteComuneRuolo.get("dataFine")),
            		builder.equal(subIstanzaEnteRoot.get("ente").get("id"), utenteEnteComuneRuolo.get("idEnte"))
                );
                subIstanzaEnte.select(subIstanzaEnteRoot.get("istanza").get("id")).where(p);
			    return builder.in(root.get("id")).value(subIstanzaEnte);
           }
        };
    }
    
    public static Specification<MudeTIstanza> findByControlloCampione(final int year, final int month) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
            	
			    Subquery<Long> subCosmo = query.subquery(Long.class);
			    Root<MudeTPraticaCosmo> subRootCosmo = subCosmo.from(MudeTPraticaCosmo.class);
			    subCosmo.select(subRootCosmo.get("idIstanza")).where(builder.or(
			    		builder.and(
			    			builder.equal(root.get("id"), subRootCosmo.get("idIstanza")), 
							builder.isNull(subRootCosmo.get("dataFine")),
				    		builder.equal(subRootCosmo.get("controlloCampione"), true)
				    ),
		    		builder.equal(subRootCosmo.get("ccSelezionato"), true)
			    ));
			    
			    predicates.add(builder.in(root.get("id")).value(subCosmo));

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
		    			builder.equal(root.get("id"), subRootStatus.get("istanza").get("id")), 
						//builder.isNull(subRootStatus.get("dataFine")),
			    		//builder.greaterThanOrEqualTo(subRootStatus.get("statoIstanza").get("livello"), 201),
		    			builder.equal(subRootStatus.get("statoIstanza").get("codice"), "ACC"),
			    		builder.between(subRootStatus.get("dataInizio"), 
		                		Timestamp.valueOf(firstOfPrevMonth.atStartOfDay()), Timestamp.valueOf(lastOfPrevMonth.atTime(23, 59, 59)))			    		
			    ));
			    
			    predicates.add(builder.in(root.get("id")).value(subStatus));
			    
                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    
    
    /*
     * Istanze DS
     */

	public static Specification<MudeTIstanza> findByTipoPratica(final Map tipoPratica) {
		if(tipoPratica == null) return null;
		return new Specification<MudeTIstanza>() {
			@Override
			public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return getPredicateString(builder, root.get("speciePratica").get("codice"), tipoPratica);
			}
		};
	}

	public static Specification<MudeTIstanza> findByDates(final Map tipoData, final Map dataFrom, final Map dataTo) {
		if(tipoData == null || (dataFrom == null && dataTo == null)) return null;
		return new Specification<MudeTIstanza>() {
			@Override
			public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
	                
	                predicates.add(builder.equal(root.get("id"), statoIStanzaRoot.get("istanza").get("id")));
	                predicates.add(builder.equal(statoIStanzaRoot.get("statoIstanza").get("codice"), typeDateSearch == DateSearchBackofficeEnum.DATA_PROTOCOLLO? "APA" : "ACC"));
	                
	                sub.select(statoIStanzaRoot.get("istanza").get("id")).where(builder.and(predicates.toArray(new Predicate[0])));
                }
				
                
                return builder.in(root.get("id")).value(sub);
			}
		};
	}

	public static Specification<MudeTIstanza> findByIndirizzo(final Long idComune
													, final Map numeroCivico
													, final Map interno
													, final Map scala
													, final Map piano) {
		if(idComune == null || (numeroCivico == null && interno == null && scala == null && piano == null)) return null;
		return new Specification<MudeTIstanza>() {
			@Override
			public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Subquery<Long> subIstanzaUbicazione = query.subquery(Long.class);
                Root<MudeopenRLocUbicazione> subIstanzaUbicazioneRoot = subIstanzaUbicazione.from(MudeopenRLocUbicazione.class);
                Predicate p = builder.and(
            		builder.equal(root.get("id"), subIstanzaUbicazioneRoot.get("idIstanza")),
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
				
			    return builder.in(root.get("id")).value(subIstanzaUbicazione);
			}
		};
	}

	public static Specification<MudeTIstanza> findByTipoCatasto(final Long idComune
													, final Map tipoCatasto
													, final Map foglio
													, final Map numero
													, final Map subalterno) {
		if(idComune == null || (tipoCatasto == null && foglio == null && numero == null && subalterno == null)) return null;
		
		return new Specification<MudeTIstanza>() {
			@Override
			public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Subquery<Long> subIstanzaCatasto = query.subquery(Long.class);
                Root<MudeopenRLocCatasto> subIstanzaCatastoRoot = subIstanzaCatasto.from(MudeopenRLocCatasto.class);
                Predicate p = builder.and(
            		builder.equal(root.get("id"), subIstanzaCatastoRoot.get("idIstanza")),
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
                
			    return builder.in(root.get("id")).value(subIstanzaCatasto);
			}
		};
	}

    public static Specification<MudeTIstanza> hasLivello(final Map map) {
    	if(map == null) return null;
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
			    Subquery<Long> sub = query.subquery(Long.class);
			    Root<MudeRIstanzaStato> subRoot = sub.from(MudeRIstanzaStato.class);
			    Predicate p = builder.and(
			    		builder.and(
			    			builder.equal(root.get("id"), subRoot.get("istanza").get("id")), 
							builder.isNull(subRoot.get("dataFine"))
			    		),
			    		getPredicateString(builder, subRoot.get("statoIstanza").get("livello"), map)
			    );
			    sub.select(subRoot.get("istanza").get("id")).where(p);
			    
			    return builder.in(root.get("id")).value(sub);
            }
        };
    }

    
    public static Specification<MudeTIstanza> filterOnlyRootDS(Specifications<MudeTIstanza> allSpecs) {
        return new Specification<MudeTIstanza>() {
            @Override
            public Predicate toPredicate(Root<MudeTIstanza> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
            	
			    Subquery<Long> sub = query.subquery(Long.class);
			    Root<MudeTIstanza> subRoot = sub.from(MudeTIstanza.class);
			    
                Root<MudeRIstanzaPratica> subRootRPrat = sub.from(MudeRIstanzaPratica.class);
                //final Join<MudeTIstanza, MudeRIstanzaPratica> mudeRIstanzaPraticaJoin = subRootRPrat.join("istanza", JoinType.INNER);

                predicates.add(
                	builder.equal(subRoot.get("id"), subRootRPrat.get("istanza").get("id"))
                );
                		
                predicates.add(allSpecs.toPredicate(subRoot, query, builder));
                
			    sub.select(builder.min(subRoot.get("id")))
			    	.where(builder.and(predicates.toArray(new Predicate[0])))
			    	.groupBy(subRootRPrat.get("pratica").get("id"));
			    
			    return builder.in(root.get("id")).value(sub);
			    
            }
        };
    }

    
}
