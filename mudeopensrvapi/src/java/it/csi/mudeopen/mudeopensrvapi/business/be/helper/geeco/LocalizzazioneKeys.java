/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.helper.geeco;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
{
	"submit": true,
	"isValid": true,
	"_URL_BACKEND": "https:\/\/tst-mudeopen-fo-rp.nivolapiemonte.it\/mudeopen\/rest",
	"dataGrid": [{
		"sub": "",
		"text_fieldsezione": "",
		"foglioN": "2",
		"censito_al_catasto": "catasto_terreni",
		"map": "4"
	},
	{
		"sub": "2",
		"text_fieldsezione": "",
		"foglioN": "2",
		"censito_al_catasto": "catasto_terreni",
		"map": "4"
	}]
}
 * 
 * ---------------------------------
{
	"submit": true,
	"isValid": true,
	"_URL_BACKEND": "https:\/\/tst-mudeopen-fo-rp.nivolapiemonte.it\/mudeopen\/rest",
	"relativo_all_immobile": "ACQUI TERME",
	"dataGrid": [{
		"secondario": "",
		"scala": "",
		"interno2": "",
		"bis": "",
		"n": "",
		"int": "",
		"piano": "",
		"cap": "",
		"sedime": "ARCO",
		"bisInterno": "",
		"apriMappaConGeeco": false,
		"denominazione": "eee3",
		"selezionare_se_si_tratta_di_indirizzo_principale": true
	}]
 * ----------------------------------------
 * SIGMATER
 "id": "24646567",
 "id_geo_particella": "4455184",
 "id_geo_foglio": "5383",
 "cod_com_belfiore": "A518",
 "cod_com_istat": "001013",
 "comune": "Avigliana",
 "sezione": "_",
 "foglio": "20",
 "particella": "199",
 "allegato": "0",
 "sviluppo": "0",
 "aggiornato_al": "2021-12-31",
 "istat_prov": "001",
 "sigla_prov": "TO",
 "ol_uid": "936",
 "check": "<span class=\"gridCheck far\"></span>",
 "$$hashKey": "object:597" * 
 * 
 * @author guideluc
 *
 */
public class LocalizzazioneKeys {
	
	//Sono le informazioni presenti nel Tab Catastale di Mude
	List datiCatastaliKeys;
	//Sono le informazioni presenti nel Tab Ubicazione di Mude
	List datiUbicazioneKeys;
	//Sono le informazioni presenti nel json tornato da Geeco..Per Extra Torino	
	List datiGeecoKeysExtraCoto;
	//Sono le informazioni presenti nel json tornato da Geeco..Per Torino	
	List datiGeecoKeysCoto;	
	Map mapSigmaterMude;
	
	public LocalizzazioneKeys() {
		//-----------------------------------------
		this.datiCatastaliKeys=new ArrayList<String>();
		this.datiCatastaliKeys.add("text_fieldsezione");
		this.datiCatastaliKeys.add("foglioN");
		this.datiCatastaliKeys.add("sub");
		this.datiCatastaliKeys.add("censito_al_catasto");
		this.datiCatastaliKeys.add("map");
		//-----------------------------------------
		this.datiUbicazioneKeys=new ArrayList<String>();
		this.datiUbicazioneKeys.add("secondario");
		this.datiUbicazioneKeys.add("scala");
		this.datiUbicazioneKeys.add("interno2");
		this.datiUbicazioneKeys.add("bis");
		this.datiUbicazioneKeys.add("n");
		this.datiUbicazioneKeys.add("int");
		this.datiUbicazioneKeys.add("piano");
		this.datiUbicazioneKeys.add("cap");
		this.datiUbicazioneKeys.add("sedime");
		this.datiUbicazioneKeys.add("bisInterno");
		this.datiUbicazioneKeys.add("denominazione");
		//-----------------------------------------
/*
 "id": "24646567",
 "id_geo_particella": "4455184",
 "id_geo_foglio": "5383",
 "cod_com_belfiore": "A518",
 "cod_com_istat": "001013",
 "comune": "Avigliana",
 "sezione": "_",
 "foglio": "20",
 "particella": "199",
 "allegato": "0",
 "sviluppo": "0",
 "aggiornato_al": "2021-12-31",
 "istat_prov": "001",
 "sigla_prov": "TO",
 "ol_uid": "936",
 "check": "<span class=\"gridCheck far\"></span>",
 "$$hashKey": "object:597" * 
 * */		
		this.datiGeecoKeysExtraCoto=new ArrayList<String>();		
		this.datiGeecoKeysExtraCoto.add("id");
		this.datiGeecoKeysExtraCoto.add("id_geo_particella");
		this.datiGeecoKeysExtraCoto.add("id_geo_foglio");
		this.datiGeecoKeysExtraCoto.add("cod_com_belfiore");
		this.datiGeecoKeysExtraCoto.add("cod_com_istat");
		this.datiGeecoKeysExtraCoto.add("comune");
		this.datiGeecoKeysExtraCoto.add("sezione");
		this.datiGeecoKeysExtraCoto.add("foglio");
		this.datiGeecoKeysExtraCoto.add("particella");
		this.datiGeecoKeysExtraCoto.add("allegato");
		this.datiGeecoKeysExtraCoto.add("sviluppo");
		this.datiGeecoKeysExtraCoto.add("aggiornato_al");
		this.datiGeecoKeysExtraCoto.add("istat_prov");
		this.datiGeecoKeysExtraCoto.add("sigla_prov");
		this.datiGeecoKeysExtraCoto.add("ol_uid");	
		
		this.datiGeecoKeysCoto=new ArrayList<String>();		
		this.datiGeecoKeysCoto.add("cit_part");
		this.datiGeecoKeysCoto.add("foglio");
		this.datiGeecoKeysCoto.add("n_part");
		
		
		//---------------------------------------------------------
		mapSigmaterMude=new HashMap<String, String>();
		mapSigmaterMude.put("sezione", "text_fieldsezione");
		mapSigmaterMude.put("foglio", "foglioN");
		mapSigmaterMude.put("particella", "particella");
		//mapSigmaterMude.put("id_geo_particella", "");
		//mapSigmaterMude.put("id_geo_foglio", "");
	}

	//---------------------------------------------------------------------------------
	public List getDatiCatastaliKeys() {
		return datiCatastaliKeys;
	}

	public void setDatiCatastaliKeys(List datiCatastaliKeys) {
		this.datiCatastaliKeys = datiCatastaliKeys;
	}

	public List getDatiUbicazioneKeys() {
		return datiUbicazioneKeys;
	}

	public void setDatiUbicazioneKeys(List datiUbicazioneKeys) {
		this.datiUbicazioneKeys = datiUbicazioneKeys;
	}

	public List getDatiSigmaterKeys() {
		return datiGeecoKeysExtraCoto;
	}

	public void setDatiSigmaterKeys(List datiSigmaterKeys) {
		this.datiGeecoKeysExtraCoto = datiSigmaterKeys;
	}
	//---------------------------------------------------------------------------------	

	public List getDatiGeecoKeysExtraCoto() {
		return datiGeecoKeysExtraCoto;
	}

	public void setDatiGeecoKeysExtraCoto(List datiGeecoKeysExtraCoto) {
		this.datiGeecoKeysExtraCoto = datiGeecoKeysExtraCoto;
	}

	public List getDatiGeecoKeysCoto() {
		return datiGeecoKeysCoto;
	}

	public void setDatiGeecoKeysCoto(List datiGeecoKeysCoto) {
		this.datiGeecoKeysCoto = datiGeecoKeysCoto;
	}

	public Map getMapSigmaterMude() {
		return mapSigmaterMude;
	}

	public void setMapSigmaterMude(Map mapSigmaterMude) {
		this.mapSigmaterMude = mapSigmaterMude;
	}
	
	
}
