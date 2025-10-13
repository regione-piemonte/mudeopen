/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.csi.mudeopen.mudeopensrvapi.business.be.helper.ElementoCatasto;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.LocationTorinoFabbricatiHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.LoocsiHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.helper.SigmaterFactoryServiceHelper;
import it.csi.mudeopen.mudeopensrvapi.vo.geeco.yaml.EditedFeature;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoSelectedCllbk;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoSession;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.GeecoServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoSelectedCllbkRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoSessionCllbkRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTGeecoSessionRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;

@Component
public class GeecoCallbakHelper {

	private static Logger logger = Logger.getLogger(GeecoCallbakHelper.class.getCanonicalName());

	public static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	MudeTGeecoSelectedCllbkRepository selectedRepositoryCllbk;

	@Autowired
	MudeTGeecoSessionCllbkRepository mudeTGeecoSessionCllbkRepository;

	@Autowired
	MudeTIstanzaRepository mudeTIstanzaRepository;

	@Autowired
	LoocsiHelper loocsiHelper;

	@Autowired
	LocationTorinoFabbricatiHelper locationTorinoFabbricatiHelper;

	@Autowired
	SigmaterFactoryServiceHelper sigmaterFactoryServiceHeper;
	
	@Autowired
	private MudeTGeecoSessionRepository mudeTGeecoSessionRepository;

	@Value("${mockgeecosigmater}")
	public boolean mockgeecosigmater;

	@Autowired
	MudeTContattoRepository contattoRepository;

    @Autowired
    private GeecoServiceHelper geecoServiceHelper;
    
    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;
	
	private String getCf(MudeTIstanza istanza) {
		MudeTContatto contatto = contattoRepository.findProfessionistaByIdIstanza(istanza.getId());
		if(contatto!=null && StringUtils.isNotBlank(contatto.getCf()))
			return contatto.getCf();
			
		return istanza.getMudeTUser().getCf();
	}

	public Long handleFeatureSelected(String geecoSessionId, EditedFeature feature)
			throws Exception {
		Long idIstanza = mudeTGeecoSessionCllbkRepository.findIdIstanzaByGeecoSessionId(geecoSessionId);
		MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
		
		String cf = getCf(istanza);
		logger.info("IdIstanza : "+idIstanza+" -> CodiceFiscale : "+cf);
		
		GeecoParticella geecoParticella = GeecoParticella.fromEditedFeature(feature, istanza.getComune().getCodBelfioreComune());
		if(geecoParticella == null)
			return null;
			
		geecoParticella.setUid(UUID.randomUUID().toString());
		
		MudeTGeecoSelectedCllbk geecoSelected = null;
		List<MudeTGeecoSelectedCllbk> callbackList = selectedRepositoryCllbk.findBySessioneGeeco(geecoSessionId);
		if(callbackList == null || callbackList.isEmpty()){
			geecoSelected =new MudeTGeecoSelectedCllbk();
			geecoSelected.setIdSession(geecoSessionId);
			geecoSelected.setSelectedData(mapper.writeValueAsString(feature));
			geecoSelected.setSelectedPosition(mapper.writeValueAsString(feature));
			geecoSelected.setDataInizio(new Date());
			geecoSelected.setDataModifica(new Date());
		}
		else {
			geecoSelected = callbackList.get(0);
		}


		boolean isComuneConvenzionatoSigmater = sigmaterFactoryServiceHeper.isAbilitatoSigmater(geecoParticella, geecoSessionId, cf);
		logger.info("populateteMetadataFromFeatures:: comune "+(isComuneConvenzionatoSigmater?"":"NON ")+"convenzionato con Sigmater ");
		
		List<ElementoCatasto> elementiCatasto = null;
		ArrayNode datiUbicazioneArray = null;
		
		//"3003:4326" torino_catasto_terreni/torino_catasto ---- "32632:4326" extra_torino/ds_torino/ds_extra_torino
		String postGisConvert = mudeCProprietaRepository.getValueByName("GEECO_POSTGIS_CONVERT_" + geecoParticella.getOrigin().toUpperCase(), "");
		if("".equals(postGisConvert))
			postGisConvert = geecoParticella.getOrigin().indexOf("torino_")>-1 ? "3003:4326" : "32632:4326"; 
		
		geecoParticella.setFirstPointConverted(geecoServiceHelper.convertPointFormat(geecoParticella.getFirstPoint(), postGisConvert));

		if(geecoParticella instanceof GeecoParticellaExtraTorino) {
			if("extra_torino".equals(geecoParticella.getOrigin())) 
					elementiCatasto = sigmaterFactoryServiceHeper.getAllElementiCatastoFromParticellaExtraTorino((GeecoParticellaExtraTorino)geecoParticella, cf);

			datiUbicazioneArray = getDatiUbicazioneFromLoccsi(geecoParticella, /* isTorino */ false);
		}
		else if(geecoParticella instanceof GeecoParticellaTorinoCatastoTerreni){
			elementiCatasto = sigmaterFactoryServiceHeper.getAllElementiCatastoFromParticellaTorinoCatastoTerreni((GeecoParticellaTorinoCatastoTerreni) geecoParticella, cf);
			
			datiUbicazioneArray = getDatiUbicazioneFromLoccsi(geecoParticella, /* isTorino */ true);
		}
		else if(geecoParticella instanceof GeecoParticellaTorinoCatastoFabbricati){
			elementiCatasto = sigmaterFactoryServiceHeper.getAllElementiCatastoFromGeecoParticellaTorinoCatastoFabbricati((GeecoParticellaTorinoCatastoFabbricati) geecoParticella, cf);
			
			geecoParticella.setFirstPoint(geecoServiceHelper.convertPointFormat(geecoParticella.getFirstPoint(), geecoParticella.getBelfiore()));
			datiUbicazioneArray = locationTorinoFabbricatiHelper.locationsFromIdFabbricato((GeecoParticellaTorinoCatastoFabbricati) geecoParticella);
		}

		ObjectNode resultToMerge = toJson(geecoParticella, elementiCatasto, datiUbicazioneArray);

		String currentJsonString = geecoSelected.getCallbackResult();
		ObjectNode actual = null;
		if(currentJsonString==null){
			actual = mapper.createObjectNode();
		}
		else{
			actual = (ObjectNode) mapper.readTree(currentJsonString);
		}

		ObjectNode merged = merge(geecoParticella, actual, resultToMerge);
		geecoSelected.setCallbackResult(mapper.writeValueAsString(merged));
		Long idSelectedData=null;
		MudeTGeecoSelectedCllbk saved = selectedRepositoryCllbk.saveDAO(geecoSelected);
		idSelectedData = saved.getId();

		return idSelectedData;
	}

	private ObjectNode merge(GeecoParticella particella, ObjectNode actual, ObjectNode resultToMerge) {
		ObjectNode result = mapper.createObjectNode();
		ArrayNode datiCatastaliArray = mapper.createArrayNode();
		ArrayNode datiUbicazioneArray = mapper.createArrayNode();

		ArrayNode actualDatiCatastaliArray = (ArrayNode) actual.get("datiCatastali");
		ArrayNode actualDatiUbicazioneArray = (ArrayNode) actual.get("datiUbicazione");

		if(actualDatiCatastaliArray!=null) {
			actualDatiCatastaliArray.forEach(n -> {
				if (!n.get("uid").asText().equalsIgnoreCase(particella.getUid())) {
					datiCatastaliArray.add(n);
				}
			});
		}
		if(actualDatiUbicazioneArray!=null) {
			actualDatiUbicazioneArray.forEach(n -> {
				if (!n.get("uid").asText().equalsIgnoreCase(particella.getUid())) {
					datiUbicazioneArray.add(n);
				}
			});
		}

		ArrayNode toMergeDatiCatastaliArray = (ArrayNode) resultToMerge.get("datiCatastali");
		ArrayNode toMergeDatiUbicazioneArray = (ArrayNode) resultToMerge.get("datiUbicazione");

		if(toMergeDatiCatastaliArray != null)
			toMergeDatiCatastaliArray.forEach(n->{
				datiCatastaliArray.add(n);
			});

		toMergeDatiUbicazioneArray.forEach(n->{
			datiUbicazioneArray.add(n);
		});

		result.set("datiCatastali", datiCatastaliArray);
		result.set("datiUbicazione", datiUbicazioneArray);

		return result;
	}

	private ObjectNode toJson(GeecoParticella particella, List<ElementoCatasto> elementiCatasto, ArrayNode datiUbicazioneArray){
		ObjectNode result = mapper.createObjectNode();
		ArrayNode datiCatastaliArray = mapper.createArrayNode();
		if(elementiCatasto != null) {
			for(ElementoCatasto elemento : elementiCatasto){
				ObjectNode datiCatastali = null;
	
	
				datiCatastali = mapper.createObjectNode();
				datiCatastali.put("uid", particella.getUid());
				datiCatastali.put("censito_al_catasto", elemento.getCensito_al_catasto());
				datiCatastali.put("foglioN", elemento.getFoglio());
				datiCatastali.put("map", elemento.getMappale());
				datiCatastali.put("text_fieldsezione", elemento.getSezione());
				datiCatastali.put("sub", elemento.getSubalterno());
				datiCatastali.set("original", particella.toJson());
				datiCatastali.put("origin", particella.getOrigin());
	
				/*
				if(elemento.getTitolarita()!=null && !elemento.getTitolarita().isEmpty()){
	
					ArrayNode titolari = mapper.createArrayNode();
					for(Titolarita tit : elemento.getTitolarita()){
						ObjectNode titolare = mapper.createObjectNode();
						if(tit.getSoggettoFisico()!=null){
							titolare.put("nome",tit.getSoggettoFisico().getNome());
							titolare.put("cognome",tit.getSoggettoFisico().getCognome());
							titolare.put("cf",tit.getSoggettoFisico().getCodFiscale());
						}
						if(tit.getSoggettoGiuridico()!=null){
							titolare.put("denominazione",tit.getSoggettoGiuridico().getDenominazione());
							titolare.put("partitaIva",tit.getSoggettoGiuridico().getPartitaIva());
						}
						titolari.add(titolare);
					}
					datiCatastali.set("titolari", titolari);
				}
				 */
				datiCatastaliArray.add(datiCatastali);
	
				/*ObjectNode datiUbicazione = null;
				if(elemento.getIndirizzi()!=null && !elemento.getIndirizzi().isEmpty()) {
					for (Indirizzo indirizzo : elemento.getIndirizzi()) {
						datiUbicazione = mapper.createObjectNode();
						datiUbicazione.put("sedime", indirizzo.getToponimo());
						datiUbicazione.put("denominazione", indirizzo.getIndirizzo());
						datiUbicazione.put("n", indirizzo.getNumeroCivico());
						if (elemento.getDatiFabbricato() != null) {
							datiUbicazione.put("scala", elemento.getDatiFabbricato().getScala());
							datiUbicazione.put("piano", elemento.getDatiFabbricato().getPiano1());
							datiUbicazione.put("interno", elemento.getDatiFabbricato().getInterno1());
							datiUbicazione.put("interno2", elemento.getDatiFabbricato().getInterno2());
						}
						datiUbicazioneArray.add(datiUbicazione);
					}
				}
	
				 */
	
	
	
			}
			result.set("datiCatastali", datiCatastaliArray);
		}
		result.set("datiUbicazione", datiUbicazioneArray);

		return result;

	}

	private ArrayNode getDatiUbicazioneFromLoccsi(GeecoParticella geecoParticella, boolean isTorino) throws Exception {
		JsonNode loccsiData = loocsiHelper.locationsFromCoordinates(geecoParticella.getFirstPoint(), isTorino);
		
		ArrayNode datiUbicazioneArray = mapper.createArrayNode();
		loccsiData = ((ArrayNode) loccsiData).get(0);
		ArrayNode features = (ArrayNode) loccsiData.get("featureCollection").get("features");
		
		features.forEach(f->{
			ObjectNode datiUbicazione = mapper.createObjectNode();

			String sedime = f.get("properties").get("tipo_via").asText("");
			String nome_via = f.get("properties").get("nome_via").asText("");
			String civico_num = f.get("properties").get("civico_num").asText("");

			datiUbicazione.put("uid", geecoParticella.getUid());
			datiUbicazione.put("sedime", sedime);
			datiUbicazione.put("denominazione", nome_via);
			datiUbicazione.put("n", civico_num);
			try {
				datiUbicazione.put("cap", f.get("properties").get("cap").asText(""));
			} catch (Exception e) {}

			datiUbicazione.put("descr_strada", sedime +" "+nome_via);
			datiUbicazione.put("descr_civico", civico_num);
			datiUbicazione.put("descr_indirizzo", sedime +" "+nome_via+ " "+civico_num);
			datiUbicazione.put("origin", "loccsi");
			
			try {
				double[] point = geecoParticella.getFirstPointConverted();
				
				datiUbicazione.put("WGS_LON", point[0]);
				datiUbicazione.put("WGS_LAT", point[1]);
			} catch (Exception skip) { }

			datiUbicazioneArray.add(datiUbicazione);
		});
		return datiUbicazioneArray;
	}
	
	public boolean isGeecoEnabled(String geecoSessionId) {
		MudeTGeecoSession mudeTGeecoSession = mudeTGeecoSessionRepository.findBySessioneGeeco(geecoSessionId);
		return mudeTGeecoSession != null;
	}
	

}
