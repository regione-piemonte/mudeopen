/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.*;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.GeecoServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GeoriferimentoUpdateTableService {

    @Autowired
    MudeDComuneRepository mudeDComuneRepository;
    
    @Autowired
    MudeTIstanzaRepository mudeTIstanzaRepository;
    
    @Autowired
    private MudeDDugRepository mudeDDugRepository;

    @Autowired
    MudeopenRLocGeoriferimRepository mudeopenRLocGeoriferimRepository;

    @Autowired
    MudeopenFabbricatiparticelleRepository mudeopenFabbricatiparticelleRepository;

    @Autowired
    MudeopenRLocCellulaRepository mudeopenRLocCellulaRepository;

    @Autowired
    MudeopenRCiviciParticelleurbaneRepository mudeopenRCiviciParticelleurbaneRepository;

    @Autowired
    MudeopenFpCiviciFullRepository mudeopenFpCiviciFullRepository;
    @Autowired
    MudeopenRLocFabbricatoRepository mudeopenRLocFabbricatoRepository;

    @Autowired
    MudeopenRLocDatocarotaRepository mudeopenRLocDatocarotaRepository;

    @Autowired
    MudeopenRLocUbicazioneRepository mudeopenRLocUbicazioneRepository;

    @Autowired
    MudeopenMwPreTIndirizzoRepository mudeopenMwPreTIndirizzoRepository;

    @Autowired
    MudeopenRLocCatastoRepository mudeopenRLocCatastoRepository;

    @Autowired
    MudeopenRLocTitolareRepository mudeopenRLocTitolareRepository;
    
    @Autowired
    MudeTGeecoSessionRepository mudeTGeecoSessionRepository;
    
    @Autowired
    MudeTGeecoSelectedCllbkRepository mudeTGeecoSelectedCllbkRepository;

    @Autowired
	private GeecoServiceHelper geecoServiceHelper;

    private static Logger logger = Logger.getLogger(GeoriferimentoUpdateTableService.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public void test(Long idIstanza){
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>\n"+istanza.getJsonData());

    }

    public void updateGeoriferimentoTables(Long idIstanza) throws Exception {
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        if(istanza==null)
            return;
        
        String jsonDataString = istanza.getJsonData();
        ObjectNode jsonData =  (ObjectNode) mapper.readTree(jsonDataString);

        updateGeoriferimentoTables(istanza, jsonData, null, null);
    }
    
    public void updateGeoriferimentoTables(MudeTIstanza istanza, ObjectNode jsonData, ArrayNode datiUbicazione, ArrayNode datiCatastali) throws Exception {
        Map<String, Long> idGeoriferimentoByUid = new HashMap<>();
        MudeDComune comune = istanza.getComune();

        if(datiUbicazione == null) {
	        try {
	            datiUbicazione = (ArrayNode) jsonData.get("TAB_LOCAL_1").get("dataGrid");
	            cloneGeecoFromParentSelection(istanza);
	        } catch (Exception e) { }

	        try {
	            datiCatastali = (ArrayNode) jsonData.get("TAB_LOCAL_2").get("dataGrid");
	        } catch (Exception e) { }
        }
        
        MudeopenRLocFabbricato fabbricato = new MudeopenRLocFabbricato();
        MudeopenRLocCellula cellula = new MudeopenRLocCellula();
        
        deleteOld(istanza.getId());
        if(datiCatastali!=null ) {
            for(JsonNode jsonNode : datiCatastali) {

                String origin = jsonNode.has("origin") ? jsonNode.get("origin").asText() : "";

                String sezione = jsonNode.has("text_fieldsezione") ? jsonNode.get("text_fieldsezione").asText() : "";
                String foglio = jsonNode.has("foglioN") ? jsonNode.get("foglioN").asText() : "";
                String particella = jsonNode.has("map") ? jsonNode.get("map").asText() : "";
                String sezioneUrbana = jsonNode.has("sezioneUrbana") ? jsonNode.get("sezioneUrbana").asText() : "";
                String subalterno = jsonNode.has("sub") ? jsonNode.get("sub").asText() : "";
                String tipo = jsonNode.has("censito_al_catasto") ? jsonNode.get("censito_al_catasto").asText() : "";

                // j929
                tipo = "catasto_terreni".equals(tipo)? "T" : "catasto_fabbricati".equals(tipo) ? "F" : tipo;

                String idFabbricato = "0";
                try {
                    logger.info("Original Selection from GEECO  \n"+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode.get("original")));
                    idFabbricato = jsonNode.get("original").get("idFabbricato").asText();
                } catch (Exception e) {
                	// logger.error("", e);
                }
                
                String personalizzatoCatasto = jsonNode.has("geolocalizzazione") && jsonNode.get("geolocalizzazione").asText().equals("geolocalizzazione") ? "N" : "S"; // verify

                Long idCellula = null;
                String idGeoDatocarota = null;
                //DATO PROVENIENTE DA GEORIFERIMENTO
                if (jsonNode.has("uid")) {
                    String uid = jsonNode.get("uid").asText();
                    Long idGeoriferimento = null;
                    if (idGeoriferimentoByUid.containsKey(uid)) {
                        idGeoriferimento = idGeoriferimentoByUid.get(uid);
                    } else {
                        MudeopenRLocGeoriferim locGeoriferim = new MudeopenRLocGeoriferim();
                        locGeoriferim.setIdIstanza(istanza.getId());
                        locGeoriferim.setIdLivelloPoligono(0L);
                        locGeoriferim.setDescLivelloPoligono(origin);
                        locGeoriferim.setDataGeoriferimento(new Date());
                        locGeoriferim.setIstatComune(comune.getIstatComune());
                        locGeoriferim.setGeometriaSdo(geecoServiceHelper.getFirsGeometryPoint(istanza.getId(), "001272".equals(comune.getIstatComune()) ? "3003:4326" : "32632:4326"));
                        locGeoriferim = mudeopenRLocGeoriferimRepository.saveDAO(locGeoriferim);
                        idGeoriferimento = locGeoriferim.getIdGeoriferimento();
                        idGeoriferimentoByUid.put(uid, locGeoriferim.getIdGeoriferimento());
                        
                        //<<<<<<<<<<<<<<<<<<<<<TORINO ACI>>>>>>>>>>>>>>>>>>>>>
                        if ("torino_catasto".equalsIgnoreCase(origin)) {
                            MudeopenFabbricatiparticelle fabbricatiparticelle = mudeopenFabbricatiparticelleRepository.findByIdFabbricato(idFabbricato).get(0);

                            cellula = new MudeopenRLocCellula();
                            cellula.setIdGeoriferimento(idGeoriferimento);
                            cellula.setChiaveCarotaggio("ACI; 1");
                            cellula.setCodCellula(fabbricatiparticelle.getFkCellula());
                            cellula = mudeopenRLocCellulaRepository.saveDAO(cellula);
                            idCellula = cellula.getIdCellula();

                            fabbricato = new MudeopenRLocFabbricato();
                            fabbricato.setIdCellula(cellula.getIdCellula());
                            fabbricato.setChiaveCarotaggio("ACI; 2");
                            fabbricato.setCodFabbricato(idFabbricato);
                            fabbricato = mudeopenRLocFabbricatoRepository.saveDAO(fabbricato);

                            // MudeopenRLocDatocarota
                            MudeopenRLocDatocarota datocarota = new MudeopenRLocDatocarota();
                            datocarota.setIdGeoriferimento(idGeoriferimento);
                            datocarota = mudeopenRLocDatocarotaRepository.saveDAO(datocarota);
                            idGeoDatocarota = datocarota.getIdGeoDatocarota() + "";
                        }                        
                    }

                    // MudeopenRLocCatasto
                    MudeopenRLocCatasto catasto = new MudeopenRLocCatasto();
                    catasto.setIdGeoriferimento(idGeoriferimento);
					catasto.setIdFabbricato(fabbricato.getIdFabbricato()); 	// SILVANA JIRA 695
					catasto.setIdCellula(fabbricato.getIdCellula());						// SILVANA JIRA 695
                    catasto.setIdIstanza(istanza.getId());
                    catasto.setSezione(sezione);
                    catasto.setSezioneUrbana(sezioneUrbana);
                    catasto.setFoglio(foglio);
                    catasto.setParticella(particella);
                    catasto.setSubalterno(subalterno);
                    catasto.setF1TipoCatasto(tipo);
                    catasto.setF1Personalizzato(personalizzatoCatasto);
                    catasto.setF1PersonalizzatoDettaglio("");
                    catasto.setChiaveCarotaggio(idGeoDatocarota);

                    catasto = mudeopenRLocCatastoRepository.saveDAO(catasto);

                    // MudeMudeopenRLocTitolare
                    MudeopenRLocTitolare titolare = new MudeopenRLocTitolare();
                    titolare.setIdCatasto(catasto.getIdCatasto());
                    mudeopenRLocTitolareRepository.saveDAO(titolare);

                }
                // DATO NON PROVENIENTE DA GEORIFERIMENTO
                else {
                    MudeopenRLocGeoriferim locGeoriferim = new MudeopenRLocGeoriferim();
                    locGeoriferim.setIdIstanza(istanza.getId());
                    //locGeoriferim.setIdLivelloPoligono(0L);
                    locGeoriferim.setDescLivelloPoligono(origin);
                    locGeoriferim.setDataGeoriferimento(new Date());
                    locGeoriferim.setIstatComune(comune.getIstatComune());
                    locGeoriferim.setGeometriaSdo(geecoServiceHelper.getFirsGeometryPoint(istanza.getId(), "001272".equals(comune.getIstatComune()) ? "3003:4326" : "32632:4326"));
                    locGeoriferim = mudeopenRLocGeoriferimRepository.saveDAO(locGeoriferim);
                    Long idGeoriferimento = locGeoriferim.getIdGeoriferimento();

                    MudeopenRLocCatasto catasto = new MudeopenRLocCatasto();
                    catasto.setIdGeoriferimento(idGeoriferimento);
                    // catasto.setIdFabbricato(Long.parseLong(idFabbricato)); SILVANA JIRA 695
                    catasto.setIdIstanza(istanza.getId());
                    catasto.setSezione(sezione);
                    catasto.setSezioneUrbana(sezioneUrbana);
                    catasto.setFoglio(foglio);
                    catasto.setParticella(particella);
                    catasto.setSubalterno(subalterno);
                    catasto.setF1TipoCatasto(tipo);
                    catasto.setF1Personalizzato("S");
                    catasto.setF1PersonalizzatoDettaglio("");
                    catasto = mudeopenRLocCatastoRepository.saveDAO(catasto);

                    // MudeMudeopenRLocTitolare
                    MudeopenRLocTitolare titolare = new MudeopenRLocTitolare();
                    titolare.setIdCatasto(catasto.getIdCatasto());
                    mudeopenRLocTitolareRepository.saveDAO(titolare);
                }
            }
        }

        if(datiUbicazione!=null ) {
            for (JsonNode jsonNode : datiUbicazione) {
                String personalizzato =  jsonNode.has("geolocalizzazione") && jsonNode.get("geolocalizzazione").asText().equals("geolocalizzazione")? "N" : "S"; // verify
                
                //DATO PROVENIENTE DA GEORIFERIMENTO
                if (jsonNode.has("uid") && jsonNode.has("origin")) {
                    String uid = jsonNode.get("uid").asText();
                    String origin = jsonNode.get("origin").asText();
                    Long idGeoriferimento = null;
                    if (idGeoriferimentoByUid.containsKey(uid)) {
                        idGeoriferimento = idGeoriferimentoByUid.get(uid);
                    } else {
                        MudeopenRLocGeoriferim locGeoriferim = new MudeopenRLocGeoriferim();
                        locGeoriferim.setIdIstanza(istanza.getId());
                        locGeoriferim.setDataGeoriferimento(new Date());
                        locGeoriferim.setIstatComune(comune.getIstatComune());
                        locGeoriferim.setGeometriaSdo(geecoServiceHelper.getFirsGeometryPoint(istanza.getId(), "001272".equals(comune.getIstatComune()) ? "3003:4326" : "32632:4326"));
                        locGeoriferim = mudeopenRLocGeoriferimRepository.saveDAO(locGeoriferim);
                        idGeoriferimento = locGeoriferim.getIdGeoriferimento();
                        idGeoriferimentoByUid.put(uid, locGeoriferim.getIdGeoriferimento());
                    }

                    if("aci".equals(origin)){
                    	
                        String IdCivicoToponom = null;
                        if(jsonNode.has("fk_civico"))
                        	IdCivicoToponom = jsonNode.get("fk_civico").asText();

                        String IdViaToponom = null;
                        if(jsonNode.has("IdViaToponom"))
                        	IdViaToponom = jsonNode.get("IdViaToponom").asText();
                        
                        /*
                        MudeopenFabbricatiparticelle fabbricatiparticelle = mudeopenFabbricatiparticelleRepository.findByIdFabbricato(idFabbricato).get(0);
                        MudeopenRCiviciParticelleurbane particelleurbane = mudeopenRCiviciParticelleurbaneRepository.findByFkCellula(fabbricatiparticelle.getFkCellula()).get(0);
                        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>" + particelleurbane.getIdCivico());
                        List<MudeopenFpCiviciFull> byIdCivico = mudeopenFpCiviciFullRepository.findByIdCivico(particelleurbane.getIdCivico());
                        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< "+byIdCivico.size());

                        MudeopenFpCiviciFull civiciFull = byIdCivico.get(0);
                        */
                        MudeopenRLocUbicazione ubicazione = new MudeopenRLocUbicazione();
                        ubicazione.setIdGeoriferimento(idGeoriferimento);
                        ubicazione.setIdFabbricato(fabbricato.getIdFabbricato()); 	// SILVANA JIRA 695
                        ubicazione.setIdCellula(fabbricato.getIdCellula());						// SILVANA JIRA 695
                        ubicazione.setIdIstanza(istanza.getId());

                        /* OLD LOGIC
                        ubicazione.setSedime(indirizzo.getSedime());
                        ubicazione.setDescVia(indirizzo.getDescVia());
                        ubicazione.setNumCivico(""+indirizzo.getNumCivico());
                        ubicazione.setBis(civiciFull.getBisTerDecoded()); // FIX DECODE
                        ubicazione.setBisinterno(civiciFull.getBisInterno1Decoded()); // FIX DECODE
                        ubicazione.setInterno(civiciFull.getInterno1());
                        ubicazione.setInterno2(civiciFull.getInterno2());
                        ubicazione.setScala(civiciFull.getScala());
                        ubicazione.setSecondario(civiciFull.getSecondario());
                        ubicazione.setCap(civiciFull.getCap()+"");

                        ubicazione.setF1Personalizzato(personalizzato);
                        ubicazione.setF1PersonalizzatoDettaglio("");
                        ubicazione.setPiano(null);
                        */

                        // NEW LOGIC

                        String sedime = jsonNode.has("sedime") ? jsonNode.get("sedime").asText() : null;
                        String denominazione = jsonNode.has("denominazione") ? jsonNode.get("denominazione").asText() : null;
//                        int civico = jsonNode.get("n").intValue();
                        String civico =jsonNode.has("n") ? jsonNode.get("n").asText() : null;
                        String scala = jsonNode.has("scala") ? jsonNode.get("scala").asText() : null;
                        String piano = jsonNode.has("piano") ?jsonNode.get("piano").asText(): null;
                        String int_ = jsonNode.has("int") ?jsonNode.get("int").asText(): null;
                        String bisInterno = jsonNode.has("bisInterno") ?jsonNode.get("bisInterno").asText(): null;
                        String interno2 = jsonNode.has("interno2") ?jsonNode.get("interno2").asText(): null;
                        String secondario = jsonNode.has("secondario") ?jsonNode.get("secondario").asText(): null;
                        String cap = jsonNode.has("cap") ? jsonNode.get("cap").asText(): null;
                        String bis = jsonNode.has("bis") ? jsonNode.get("bis").asText(): null;
                        boolean principale = jsonNode.has("selezionare_se_si_tratta_di_indirizzo_principale") ?
                                jsonNode.get("selezionare_se_si_tratta_di_indirizzo_principale").asBoolean() :false;
                        ubicazione.setSedime(sedime);
                        ubicazione.setDescVia(denominazione);
//                        ubicazione.setNumCivico(""+civico);
                        ubicazione.setNumCivico(civico);
                        ubicazione.setBis(bis); // FIX DECODE
                        ubicazione.setBisinterno(bisInterno); // FIX DECODE
                        ubicazione.setInterno(int_);
                        ubicazione.setInterno2(interno2);
                        ubicazione.setScala(scala);
                        ubicazione.setSecondario(secondario);
                        ubicazione.setCap(cap);
                        ubicazione.setF1Personalizzato(personalizzato);
                        ubicazione.setF1PersonalizzatoDettaglio(principale ? "principale" : "");
                        ubicazione.setPiano(piano);
                        
                        ubicazione.setIdCivicoToponom(IdCivicoToponom);
                        ubicazione.setIdViaToponom(IdViaToponom);

                        mudeopenRLocUbicazioneRepository.saveDAO(ubicazione);

                    }
                    else{

                        String sedime = jsonNode.has("sedime") ? jsonNode.get("sedime").asText() : null;
                        String denominazione = jsonNode.has("denominazione") ? jsonNode.get("denominazione").asText() : null;
//                        int civico = jsonNode.get("n").intValue();
                        String civico =jsonNode.has("n") ? jsonNode.get("n").asText() : null;
                        String scala = jsonNode.has("scala") ? jsonNode.get("scala").asText() : null;
                        String piano = jsonNode.has("piano") ?jsonNode.get("piano").asText(): null;
                        String int_ = jsonNode.has("int") ?jsonNode.get("int").asText(): null;
                        String bisInterno = jsonNode.has("bisInterno") ?jsonNode.get("bisInterno").asText(): null;
                        String interno2 = jsonNode.has("interno2") ?jsonNode.get("interno2").asText(): null;
                        String secondario = jsonNode.has("secondario") ?jsonNode.get("secondario").asText(): null;
                        String cap = jsonNode.has("cap") ? jsonNode.get("cap").asText(): null;
                        String bis = jsonNode.has("bis") ? jsonNode.get("bis").asText(): null;
                        boolean principale = jsonNode.has("selezionare_se_si_tratta_di_indirizzo_principale") ?
                                jsonNode.get("selezionare_se_si_tratta_di_indirizzo_principale").asBoolean() :false;
                        MudeopenRLocUbicazione ubicazione = new MudeopenRLocUbicazione();
                        ubicazione.setIdGeoriferimento(idGeoriferimento);

                        // maybe?
//                        if(fabbricato != null) {
//	                        ubicazione.setIdFabbricato(fabbricato.getIdFabbricato());
//	                        ubicazione.setIdCellula(fabbricato.getIdCellula());
//                        }
                        
                        ubicazione.setIdIstanza(istanza.getId());
                        ubicazione.setSedime(sedime);
                        ubicazione.setDescVia(denominazione);
//                        ubicazione.setNumCivico(""+civico);
                        ubicazione.setNumCivico(civico);
                        ubicazione.setBis(bis); // FIX DECODE
                        ubicazione.setBisinterno(bisInterno); // FIX DECODE
                        ubicazione.setInterno(int_);
                        ubicazione.setInterno2(interno2);
                        ubicazione.setScala(scala);
                        ubicazione.setSecondario(secondario);
                        ubicazione.setCap(cap);
                        ubicazione.setF1Personalizzato(personalizzato);
                        ubicazione.setF1PersonalizzatoDettaglio(principale ? "principale" : "");
                        ubicazione.setPiano(piano);
                        mudeopenRLocUbicazioneRepository.saveDAO(ubicazione);
                    }

                }
                else{ // address from formio 
                    MudeopenRLocGeoriferim locGeoriferim = new MudeopenRLocGeoriferim();
                    locGeoriferim.setIdIstanza(istanza.getId());
                    locGeoriferim.setDataGeoriferimento(new Date());
                    locGeoriferim.setIstatComune(comune.getIstatComune());
                    locGeoriferim.setGeometriaSdo(geecoServiceHelper.getFirsGeometryPoint(istanza.getId(), "001272".equals(comune.getIstatComune()) ? "3003:4326" : "32632:4326"));
                    locGeoriferim = mudeopenRLocGeoriferimRepository.saveDAO(locGeoriferim);
                    
                    Long idGeoriferimento = locGeoriferim.getIdGeoriferimento();
                    String sedime = jsonNode.has("sedime") ? jsonNode.get("sedime").asText() : null;
                    String denominazione = jsonNode.has("denominazione") ? jsonNode.get("denominazione").asText() : null;
//                        int civico = jsonNode.get("n").intValue();
                    String civico =jsonNode.has("n") ? jsonNode.get("n").asText() : null;
                    String scala = jsonNode.has("scala") ? jsonNode.get("scala").asText() : null;
                    String piano = jsonNode.has("piano") ?jsonNode.get("piano").asText(): null;
                    String int_ = jsonNode.has("int") ?jsonNode.get("int").asText(): null;
                    String bisInterno = jsonNode.has("bisInterno") ?jsonNode.get("bisInterno").asText(): null;
                    String interno2 = jsonNode.has("interno2") ?jsonNode.get("interno2").asText(): null;
                    String secondario = jsonNode.has("secondario") ?jsonNode.get("secondario").asText(): null;
                    String cap = jsonNode.has("cap") ? jsonNode.get("cap").asText(): null;
                    String bis = jsonNode.has("bis") ? jsonNode.get("bis").asText(): null;
                    boolean principale = jsonNode.has("selezionare_se_si_tratta_di_indirizzo_principale") ?
                            jsonNode.get("selezionare_se_si_tratta_di_indirizzo_principale").asBoolean() :false;
                    MudeopenRLocUbicazione ubicazione = new MudeopenRLocUbicazione();
                    //ubicazione.setIdGeoriferimento(idGeoriferimento); SILVANA JIRA 694
                    ubicazione.setIdIstanza(istanza.getId());
                    ubicazione.setSedime(sedime);
                    ubicazione.setDescVia(denominazione);
//                    ubicazione.setNumCivico(""+civico);
                    ubicazione.setNumCivico(civico);
                    ubicazione.setBis(bis); // FIX DECODE
                    ubicazione.setBisinterno(bisInterno); // FIX DECODE
                    ubicazione.setInterno(int_);
                    ubicazione.setInterno2(interno2);
                    ubicazione.setScala(scala);
                    ubicazione.setSecondario(secondario);
                    ubicazione.setCap(cap);
                    ubicazione.setF1Personalizzato("S");
                    ubicazione.setPiano(piano);
                    ubicazione.setF1PersonalizzatoDettaglio(principale ? "principale" : "");
                    mudeopenRLocUbicazioneRepository.saveDAO(ubicazione);

                }

            }
            
        }
    }

    public void deleteOld(Long idIstanza){
        try {
            List<MudeopenRLocGeoriferim> oldGeoriferimenti = mudeopenRLocGeoriferimRepository.findByIdIstanza(idIstanza);
            mudeopenRLocGeoriferimRepository.deleteByIdIstanza(idIstanza);
            mudeopenRLocCatastoRepository.deleteByIdIstanza(idIstanza);
            mudeopenRLocUbicazioneRepository.deleteByIdIstanza(idIstanza);
            if (!oldGeoriferimenti.isEmpty()) {
                List<Long> georiferimentiIdsOld = oldGeoriferimenti.stream().map(x -> x.getIdGeoriferimento()).collect(Collectors.toList());
                mudeopenRLocCellulaRepository.deleteByIdGeoriferimentoList(georiferimentiIdsOld);
                mudeopenRLocDatocarotaRepository.deleteByIdGeoriferimentoList(georiferimentiIdsOld);
                //mudeopenRLocFabbricatoRepository
                //mudeopenRLocTitolareRepository

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
     * j932: called from DPS change state
     */
	public void cloneGeecoFromParentSelection(MudeTIstanza istanza) {
        Long idIstRif = getIdIstanzaIndirizzoRif(istanza);
		
    	// copy TAB_LOCAL_1/2 from parent
		try {
			ObjectNode datiUbicazione = null;
			try {
				datiUbicazione = (ObjectNode)mapper.readTree(istanza.getJsonData()).get("TAB_LOCAL_1");
			} catch (Exception e) { }

			// NO ADDR, REFERENCE
			if(datiUbicazione == null && idIstRif != null) {
				String jdataParent = mudeTIstanzaRepository.getJDataByIdIstanza(idIstRif);

		        ObjectNode jsonDataParent =  (ObjectNode) mapper.readTree(jdataParent == null? "{}" : jdataParent);
		        if(jsonDataParent.has("TAB_LOCAL_1")) {
		        	datiUbicazione = (ObjectNode)jsonDataParent.get("TAB_LOCAL_1");
			        updateGeoriferimentoTables(istanza, jsonDataParent, (ArrayNode)datiUbicazione.get("dataGrid"), jsonDataParent.has("TAB_LOCAL_2")? (ArrayNode)jsonDataParent.get("TAB_LOCAL_2").get("dataGrid") : null);
		        }
		        else {
		        	// from "istanza nativa"
		        	jdataParent = reverseJsonAddressFromLocTables(idIstRif, jdataParent);
		        	
		        	jsonDataParent =  (ObjectNode) mapper.readTree(jdataParent == null? "{}" : jdataParent);
		        	if(jsonDataParent.has("TAB_LOCAL_1")) {
		        		datiUbicazione = (ObjectNode)jsonDataParent.get("TAB_LOCAL_1");
				        updateGeoriferimentoTables(istanza, jsonDataParent, (ArrayNode)datiUbicazione.get("dataGrid"), jsonDataParent.has("TAB_LOCAL_2")? (ArrayNode)jsonDataParent.get("TAB_LOCAL_2").get("dataGrid") : null);
		        	}
		        }
			}
		} catch (Exception e) {
			logger.error("[GeoriferimentoUpdateTableService::cloneGeecoParentSelection] EXCEPTION", e);
		}
		
    	// copy last geeco session records
		try {
        	_cloneGeecoFromParentSelection(istanza, idIstRif);
		} catch (Exception e) {
			logger.error("[GeoriferimentoUpdateTableService::cloneGeecoParentSelection] EXCEPTION", e);
		}
	}

	// finds if there is geeco selection to be copied from the instance parent
	private void _cloneGeecoFromParentSelection(MudeTIstanza istanza, Long idIstRif) {
		if(idIstRif == null) return;
			
		MudeTGeecoSelectedCllbk isThereGecoSelectedCllbk = mudeTGeecoSelectedCllbkRepository.getLatestSessionPosition(istanza.getId());
        if(isThereGecoSelectedCllbk != null) return;
        
		MudeTGeecoSelectedCllbk mudeTGeecoSelectedCllbkFromParent = mudeTGeecoSelectedCllbkRepository.getLatestSessionPosition(idIstRif);
		if(mudeTGeecoSelectedCllbkFromParent == null) return; // nothing to copy from!
        
		MudeTGeecoSession mudeTGeecoSession = mudeTGeecoSessionRepository.findBySessioneGeeco(mudeTGeecoSelectedCllbkFromParent.getIdSession());
		
		String newidSessione = UUID.randomUUID().toString();
		MudeTGeecoSession newMudeTGeecoSession = new MudeTGeecoSession();
		newMudeTGeecoSession.setIdIstanza(istanza.getId());
		newMudeTGeecoSession.setUrl_Geeco(mudeTGeecoSession.getUrl_Geeco());
		newMudeTGeecoSession.setMudeTUser(mudeTGeecoSession.getMudeTUser());
		newMudeTGeecoSession.setDataInizio(mudeTGeecoSession.getDataInizio());
		newMudeTGeecoSession.setSessioneGeeco(newidSessione);
		mudeTGeecoSessionRepository.saveDAO(newMudeTGeecoSession);
		
		MudeTGeecoSelectedCllbk newMudeTGeecoSelectedCllbk = new MudeTGeecoSelectedCllbk();
		newMudeTGeecoSelectedCllbk.setSelectedPosition(mudeTGeecoSelectedCllbkFromParent.getSelectedPosition());
		newMudeTGeecoSelectedCllbk.setSelectedData(mudeTGeecoSelectedCllbkFromParent.getSelectedData());
		newMudeTGeecoSelectedCllbk.setCallbackResult(mudeTGeecoSelectedCllbkFromParent.getCallbackResult());
		newMudeTGeecoSelectedCllbk.setIdSession(newidSessione);
		newMudeTGeecoSelectedCllbk.setDataInizio(newMudeTGeecoSelectedCllbk.getDataInizio());
		mudeTGeecoSelectedCllbkRepository.saveDAO(newMudeTGeecoSelectedCllbk);
		
	}

	private Long getIdIstanzaIndirizzoRif(MudeTIstanza istanza) {
		Long idIstRif = istanza.getIdIstanzaRiferimento();
        if(idIstRif == null) {
        	// it could be taken from id_istanza_rid in r_fascicolo_indirizzo... 
        	if((idIstRif = mudeTIstanzaRepository.getIdIstanzaFromFascicoloIndirizzo(istanza.getMudeTFascicolo().getId())) == null)
        		return null;
        }
		return idIstRif;
	}
	
	public String reverseJsonAddressFromLocTables(Long idIstanza, String json) {
		try {
			ObjectNode jsonData = json == null? mapper.createObjectNode() : (ObjectNode)mapper.readTree(json);

			// if TAB_LOCAL_1/2 already initialized, then copy 'em and get out
			if(jsonData.get("TAB_LOCAL_1") != null || jsonData.get("TAB_LOCAL_2") != null) {
				ObjectNode jsonDataSimplified = mapper.createObjectNode();
				jsonDataSimplified.set("TAB_LOCAL_1", jsonData.get("TAB_LOCAL_1"));
				jsonDataSimplified.set("TAB_LOCAL_2", jsonData.get("TAB_LOCAL_2"));
				
				return jsonDataSimplified.toString();
			}

			ArrayNode datiUbicazioneArray = mapper.createArrayNode();
		    for(MudeopenRLocUbicazione mudeRLocUbicazione : mudeopenRLocUbicazioneRepository.getByIdIstanza(idIstanza)) {
				ObjectNode datiUbicazione = mapper.createObjectNode();

				datiUbicazione.put("uid", "" + UUID.randomUUID()); // generate a random UUID in order to create the georiferimento record
				datiUbicazione.put("sedime", mudeRLocUbicazione.getSedime()); 
				datiUbicazione.put("denominazione", mudeRLocUbicazione.getDescVia());
				datiUbicazione.put("n", mudeRLocUbicazione.getNumCivico());
				datiUbicazione.put("cap", mudeRLocUbicazione.getCap());
				datiUbicazione.put("selezionare_se_si_tratta_di_indirizzo_principale", "principale".equalsIgnoreCase(mudeRLocUbicazione.getF1PersonalizzatoDettaglio()));

                if(mudeRLocUbicazione.getIdCivicoToponom() == null 
                		&& mudeRLocUbicazione.getIdViaToponom() == null 
                		&& !"ACICOTO".equals(mudeRLocUbicazione.getDescFonteUbicazione()))
                	datiUbicazione.put("origin", "loccsi"); // this should never be the case! 
                else
                	datiUbicazione.put("origin", "aci");

                // ACI
				datiUbicazione.put("bis", mudeRLocUbicazione.getBis()); 
				datiUbicazione.put("bisInterno", mudeRLocUbicazione.getBisinterno()); 
				datiUbicazione.put("int", mudeRLocUbicazione.getInterno());
				datiUbicazione.put("interno2", mudeRLocUbicazione.getInterno2());
				// ?? datiUbicazione.put("descr_strada", mudeRLocUbicazione.get());
				// ?? datiUbicazione.put("descr_civico", mudeRLocUbicazione.get());
				// ?? datiUbicazione.put("descr_indirizzo", mudeRLocUbicazione.get());
				datiUbicazione.put("scala", mudeRLocUbicazione.getScala());
				datiUbicazione.put("secondario", mudeRLocUbicazione.getSecondario());
				datiUbicazione.put("geolocalizzazione", "S".equals(mudeRLocUbicazione.getF1Personalizzato()) ? "manuale": "geolocalizzazione");
				
				datiUbicazione.put("IdCivicoToponom", mudeRLocUbicazione.getIdCivicoToponom());
				datiUbicazione.put("IdViaToponom", mudeRLocUbicazione.getIdViaToponom());
				
				datiUbicazioneArray.add(datiUbicazione);
		    }

			ArrayNode datiCatastaliArray = mapper.createArrayNode();
		    for(MudeopenRLocCatasto mudeRLocCatasto : mudeopenRLocCatastoRepository.getByIdIstanza(idIstanza)) {
				ObjectNode datiCatastali = mapper.createObjectNode();
				
				if(mudeRLocCatasto.getIdFabbricato() != null)
					datiCatastali.put("uid", "" + UUID.randomUUID()); // generate a random UUID in order to create the georiferimento record
				
		    	datiCatastali.put("censito_al_catasto", "F".equals(mudeRLocCatasto.getF1TipoCatasto())? "catasto_fabbricati" : "catasto_terreni");
		    	datiCatastali.put("foglioN", mudeRLocCatasto.getFoglio());
		    	datiCatastali.put("map", mudeRLocCatasto.getParticella());
		    	datiCatastali.put("text_fieldsezione", "_".equals(mudeRLocCatasto.getSezione()) ? "" : mudeRLocCatasto.getSezione());
		    	datiCatastali.put("sub", mudeRLocCatasto.getSubalterno());
		    	
		    	String idFabbricato = mudeopenRLocCellulaRepository.getCodFabbricatoByIdGeoriferimento(mudeRLocCatasto.getIdGeoriferimento());
		    	if(idFabbricato != null) {
		    		ObjectNode codFabbricato = mapper.createObjectNode();
		    		codFabbricato.put("idFabbricato", idFabbricato);
		    		datiCatastali.set("original", codFabbricato);
		    	}

		    	datiCatastali.put("origin", mudeopenRLocCellulaRepository.findByIdGeoriferimento(mudeRLocCatasto.getIdGeoriferimento()).size() > 0? "torino_catasto" : "");
		    	
		    	datiCatastaliArray.add(datiCatastali);
		    }
			
			ObjectNode dataGridUbicazione = mapper.createObjectNode();
			
			if(datiUbicazioneArray.size() == 0) { // stil no data from _loc_ubicazione? 
	        	MudeTIstanza istanzaIndirizzoRif = mudeTIstanzaRepository.findOne(idIstanza);
	        	
	        	// fallback to t_indirizzo
				if(istanzaIndirizzoRif != null && istanzaIndirizzoRif.getIndirizzo() != null) {
					MudeTIndirizzo mudeTIndirizzo = istanzaIndirizzoRif.getIndirizzo();
					
					ObjectNode datiUbicazione = mapper.createObjectNode();
	
					if(mudeTIndirizzo.getIdDug() != null)
						datiUbicazione.put("sedime", mudeDDugRepository.getDenominazione(mudeTIndirizzo.getIdDug())); 
					datiUbicazione.put("denominazione", mudeTIndirizzo.getIndirizzo());
					
					datiUbicazione.put("n", mudeTIndirizzo.getNumeroCivico());
					datiUbicazione.put("cap", mudeTIndirizzo.getCap());
	
					datiUbicazioneArray.add(datiUbicazione);
				}
			}
			
			dataGridUbicazione.set("dataGrid", datiUbicazioneArray);
			jsonData.set("TAB_LOCAL_1", dataGridUbicazione);

			ObjectNode dataGridCatasto = mapper.createObjectNode();
			dataGridCatasto.set("dataGrid", datiCatastaliArray);
			jsonData.set("TAB_LOCAL_2", dataGridCatasto);
			
			return jsonData.toString();
		}
		catch(Exception ex) {
			logger.error("[GeoriferimentoUpdateTableService::reverseJsonAddressFromLocTables] exception", ex);
		}
		
		return json;		
	}

}
