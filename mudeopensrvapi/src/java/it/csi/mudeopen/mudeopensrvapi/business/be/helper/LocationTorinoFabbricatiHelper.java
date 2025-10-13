/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco.GeecoParticellaTorinoCatastoFabbricati;
import it.csi.mudeopen.mudeopensrvapi.vo.toponomastica_torino.CivicoVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenFabbricatiparticelle;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenFpCiviciFull;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenMwPreTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRCiviciParticelleurbane;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRFabbrCivici;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenTopCivici;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenFabbricatiparticelleRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenFpCiviciFullRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenMwPreTIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRCiviciParticelleurbaneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRFabbrCiviciRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenTopCiviciRepository;

@Component
public class LocationTorinoFabbricatiHelper {

    private static Logger logger = Logger.getLogger(LocationTorinoFabbricatiHelper.class.getCanonicalName());

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MudeopenTopCiviciRepository mudeopenTopCiviciRepository;

    @Autowired
    MudeopenRFabbrCiviciRepository mudeopenRFabbrCiviciRepository;

    @Autowired
    MudeopenMwPreTIndirizzoRepository mudeopenMwPreTIndirizzoRepository;

    @Autowired
    MudeopenFabbricatiparticelleRepository mudeopenFabbricatiparticelleRepository;

    @Autowired
    MudeopenRCiviciParticelleurbaneRepository mudeopenRCiviciParticelleurbaneRepository;

    @Autowired
    MudeopenFpCiviciFullRepository mudeopenFpCiviciFullRepository;

    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;
    
    @Autowired
    private ToponomasticaHelper toponomasticaHelper;
    
    public ArrayNode locationsFromIdFabbricato(GeecoParticellaTorinoCatastoFabbricati geecoParticella) throws Exception {
        String idFabbricato = geecoParticella.getIdFabbricato();

        boolean toponomasticaFirst = "enabled".equals(mudeCProprietaRepository.getValueByName("TOPONOMASTICA_REVOLVE_FROM_CTSERV_FIRST", "disabled"));
        
        ArrayNode datiUbicazioneArray = mapper.createArrayNode();

        List<MudeopenRFabbrCivici> fabbCivici = mudeopenRFabbrCiviciRepository.findByIdFabbricato(Integer.parseInt(idFabbricato));

        Map<Integer, MudeopenMwPreTIndirizzo> indirizziByFkCivico = new HashMap<>();
        Map<Integer, MudeopenTopCivici> civiciByFkCivico = new HashMap<>();

        fabbCivici.forEach(x->{
            Integer currentFkCivico = x.getFkCivici();

            // DATI DA MudeopenMwPreTIndirizzo
            List<MudeopenMwPreTIndirizzo> indirizzi = mudeopenMwPreTIndirizzoRepository.findByIdCivicoTopon(x.getFkCivici());
            List<MudeopenTopCivici> civici = mudeopenTopCiviciRepository.findByFkCivici(x.getFkCivici());
            MudeopenMwPreTIndirizzo indirizzo = indirizzi!=null && !indirizzi.isEmpty() ? indirizzi.get(0) : null;
            MudeopenTopCivici civico = civici!=null && !civici.isEmpty() ? civici.get(0) : null;

        	if(toponomasticaFirst || indirizzo == null)
	        	try {
	        		CivicoVO civicoFromToponom = toponomasticaHelper.cercaCivicoPerId(x.getFkCivici());
	        		if(civicoFromToponom != null) {
	        			// override from API
		        		indirizzo = new MudeopenMwPreTIndirizzo() {{
							setSedime(civicoFromToponom.getVia().getSedime().getDescrizione());
							setDescVia(civicoFromToponom.getVia().getDenominazioneCorrente());
							setNumCivico(""+civicoFromToponom.getNumeroRadice());
							setIdCivicoTopon(x.getFkCivici());
							setIdViaTopon(civicoFromToponom.getVia().getIdVia().longValue());
	        			}};
	        		}
	        		
	        		logger.info("[LocationTorinoFabbricatiHelper::locationsFromIdFabbricato] CALL cercaCivicoPerId["+x.getFkCivici()+"]: " + civicoFromToponom);
	        	} catch (Exception e) { 
	        		logger.error("[LocationTorinoFabbricatiHelper::locationsFromIdFabbricato] ERROR cercaCivicoPerId["+x.getFkCivici()+"]", e);
	        	}
            
            if(indirizzo!=null)
                indirizziByFkCivico.put(currentFkCivico, indirizzo);
            if(civico!=null)
                civiciByFkCivico.put(currentFkCivico, civico);
        });

        fabbCivici.forEach(x->{
            Integer currentFkCivico = x.getFkCivici();

            MudeopenMwPreTIndirizzo indirizzo = indirizziByFkCivico.get(currentFkCivico);
            MudeopenTopCivici civico = civiciByFkCivico.get(currentFkCivico);

            if(indirizzo==null && civico!=null)
                indirizzo = findBestCandidate(civico, indirizziByFkCivico.values());

            // DATI DA MudeopenFpCiviciFull
            MudeopenFabbricatiparticelle fabbricatiparticelle = mudeopenFabbricatiparticelleRepository.findByIdFabbricato(idFabbricato).get(0);
            // id_civico di CiviciParticelleUrbane coincide con fk_civico di FabbrCivici
            MudeopenRCiviciParticelleurbane particelleurbane = mudeopenRCiviciParticelleurbaneRepository.findByFkCellula(fabbricatiparticelle.getFkCellula()).stream().filter( cpu -> cpu.getIdCivico().intValue()==currentFkCivico.intValue()).findFirst().get();

            MudeopenFpCiviciFull civiciFull = mudeopenFpCiviciFullRepository.findByIdCivico(particelleurbane.getIdCivico()).get(0);

            if(civico!=null && civiciFull!=null) {
                ObjectNode datiUbicazione = mapper.createObjectNode();

                String descrStrada = civico.getDescrStrada();
                if(StringUtils.isBlank(descrStrada) && indirizzo!=null)
                    descrStrada = indirizzo.getSedime() +" "+ indirizzo.getDescVia();
                datiUbicazione.put("descr_strada", descrStrada);//MudeopenMwPreTIndirizzo
                
                String descrCivico =  civico.getDescrizioneCivico();
                if(StringUtils.isBlank(descrCivico) && indirizzo!=null)
                    descrCivico = indirizzo.getNumCivico();
                datiUbicazione.put("descr_civico", descrCivico);//MudeopenMwPreTIndirizzo

                String descrIndirizzo = "";
                if((StringUtils.isBlank(civico.getDescrStrada()) || StringUtils.isBlank(civico.getDescrizioneCivico())) && indirizzo!=null && indirizzo.getIndirizzoCompleto() != null)
                    descrIndirizzo = indirizzo.getIndirizzoCompleto();
                else 
                    descrIndirizzo = descrStrada +" "+ descrCivico;
                datiUbicazione.put("descr_indirizzo", descrIndirizzo);//MudeopenMwPreTIndirizzo

                datiUbicazione.put("uid", geecoParticella.getUid());
                datiUbicazione.put("sedime", indirizzo != null ? indirizzo.getSedime() : ""); //MudeopenMwPreTIndirizzo
                datiUbicazione.put("denominazione", indirizzo != null ? indirizzo.getDescVia():"");//MudeopenMwPreTIndirizzo
                datiUbicazione.put("n", civico.getNumero());//MudeopenMwPreTIndirizzo
                datiUbicazione.put("bis", civiciFull.getBisTerDecoded()); //MudeopenFpCiviciFull
                datiUbicazione.put("bisInterno", civiciFull.getBisInterno1Decoded()); //MudeopenFpCiviciFull
                datiUbicazione.put("int", civiciFull.getInterno1());//MudeopenFpCiviciFull
                datiUbicazione.put("interno2", civiciFull.getInterno2());//MudeopenFpCiviciFull
                
                datiUbicazione.put("cap", civiciFull.getCap());//MudeopenFpCiviciFull
                datiUbicazione.put("id_fabbricato", geecoParticella.getIdFabbricato());
                datiUbicazione.put("scala", civiciFull.getScala());//MudeopenFpCiviciFull
                datiUbicazione.put("secondario", civiciFull.getSecondario());//MudeopenFpCiviciFull
                datiUbicazione.put("origin", "aci");
                
            	datiUbicazione.put("fk_civico", ""+currentFkCivico);
                if(indirizzo != null && indirizzo.getIdViaTopon() != null)
                	datiUbicazione.put("IdViaToponom", ""+indirizzo.getIdViaTopon());

    			try {
    				double[] point = geecoParticella.getFirstPointConverted();

    				datiUbicazione.put("WGS_LON", point[0]);
    				datiUbicazione.put("WGS_LAT", point[1]);
    			} catch (Exception skip) { }
    			
                datiUbicazioneArray.add(datiUbicazione);
            }

        });
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(datiUbicazioneArray));
        return datiUbicazioneArray;

    }

private MudeopenMwPreTIndirizzo findBestCandidate(MudeopenTopCivici civico, Collection<MudeopenMwPreTIndirizzo> indirizzi) {
    logger.info("Indirizzo not found  looking for best candidate");

    MudeopenMwPreTIndirizzo candidate = null;
    for(MudeopenMwPreTIndirizzo i : indirizzi){
        String descStradaCurrentIndirizzo = i.getSedime() +" "+ i.getDescVia();
        if(civico.getDescrStrada().trim().toLowerCase().equals(descStradaCurrentIndirizzo.trim().toLowerCase())){
            candidate = i;
            logger.info("Found candidate sedime="+candidate.getSedime()+" via="+candidate.getDescVia());
            break;
        }
    };
    if(candidate==null){
        try {
            logger.info("No candidate found... Building indirizzo from Top Civici");
            String parts[] = civico.getDescrStrada().split(" ", 2);
            String sedime = parts[0].toUpperCase();
            String descVia = parts[1].toUpperCase();
            logger.info(String.format("FAKE INDIRIZZO -> sedime: %s, via: %s", sedime, descVia));
            candidate = new MudeopenMwPreTIndirizzo();
            candidate.setSedime(sedime);
            candidate.setDescVia(descVia);
            candidate.setNumCivico(civico.getDescrizioneCivico());
        } catch (Exception e){
            logger.error("ERROR BUILDING INDIRIZZO FROM TOP CIVICI", e);
            candidate=null;
        }

    }
    return candidate;
}
}
