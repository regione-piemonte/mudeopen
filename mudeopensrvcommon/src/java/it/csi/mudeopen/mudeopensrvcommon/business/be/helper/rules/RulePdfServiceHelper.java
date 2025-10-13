/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.rules;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class RulePdfServiceHelper {

	public HashMap  getMapCopertina(String json) {
		boolean isValid=false;
		HashMap copertina=new HashMap();
		
		RuleSampleCustom qdr=null;
		RuleServiceFactory factory = new RuleServiceFactory();
		//-----------------------------------------------------------
		String val1="Deroga_1";
		qdr=new RuleSampleCustom("TAB_ASS_Q","deroga_richiesta",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val1, isValid);			
		//-----------------------------------------------------------
		String val2="Deroga_Vigili_del_Fuoco_2";
		qdr=new RuleSampleCustom("TAB_ASS_R","contenuta_documentazione",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val2, isValid);
		//-----------------------------------------------------------
		String val4="Parere_Igienico_4";
		qdr=new RuleSampleCustom("TAB_AUTOC_1","opere_parti_comuni",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val4, isValid);
		//-----------------------------------------------------------
		String val18="Parere_Igienico_18";
		qdr=new RuleSampleCustom("TAB_AUTOC_1","non_e_conforme",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val18, isValid);		
		//-----------------------------------------------------------
		String val19="Autorizzazione_Sismica_19";
		qdr=new RuleSampleCustom("TAB_ASS_M","allega_autorizzazione_sismica",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val19, isValid);			
		//-----------------------------------------------------------
		//SCIA
		//-----------------------------------------------------------
		String val3="Valutazione_Progetto_Vigili_del_Fuoco_3";
		qdr=new RuleSampleCustom("TAB_ASS_R","contenuta_documentazione_necessaria",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val3, isValid);			
		//-----------------------------------------------------------
		String val20="Denuncia_Sismica_allegata_20";
		qdr=new RuleSampleCustom("TAB_ASS_T","allega_dichiarazione_sostitutiva",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val20, isValid);			
		//-----------------------------------------------------------
		String val21="Impatto_Clima_Acustico_21";
		qdr=new RuleSampleCustom("TAB_ASS_T","allega_documentazione",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val21, isValid);					
		//-----------------------------------------------------------
		String val22="Amianto_22";
		qdr=new RuleSampleCustom("TAB_ASS_F","allegato",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val22, isValid);		
		
		//-----------------------------------------------------------		
		//TUTELA STORICO-AMBIENTALE
		//-----------------------------------------------------------
		//Autorizzazione Paesaggistica Semplificata (5)
		String val5="Autorizzazione_Paesaggistica_Semplificata_5";
		qdr=new RuleSampleCustom("TAB_VINC_1","intervento_e_assoggettato_al_procedimento_semplificato",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val5, isValid);		
		//-----------------------------------------------------------
		//Autorizzazione Paesaggistica Ordinaria (6)
		String val6="Autorizzazione_Paesaggistica_Ordinaria_6";
		qdr=new RuleSampleCustom("TAB_VINC_1","intervento_e_assoggettato_al_procedimento_ordinario",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val6, isValid);		
		//-----------------------------------------------------------
		//Autorizzazione Paesaggistica Ordinaria ai sensi dell’Accordo tra Regione Piemonte e Ministero per i Beni ed Attività Culturali (25)
		String val25="Autorizzazione_Paesaggistica_25";
		qdr=new RuleSampleCustom("TAB_VINC_1","intervento_e_assoggettato_al_procedimento",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val25, isValid);				
		//-----------------------------------------------------------
		//Accertamento Compatibilità Paesaggistica ottenuto (24)		
		String val24="Accertamento_24";
		qdr=new RuleSampleCustom("TAB_VINC_1","autorizzazione_rilasciata",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val24, isValid);		
		//-----------------------------------------------------------
		//Parere Commissione Locale Paesaggio (17)
		String val17="Parere_17";
		qdr=new RuleSampleCustom("TAB_VINC_1","progetto_storico_artistico_paesaggistico",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val17, isValid);		
		//-----------------------------------------------------------
		//Parere Soprintendenza ai Beni Ambientali e  Architettonici (7)		
		String val7="Parere_7";
		qdr=new RuleSampleCustom("TAB_VINC_1","presente_istanza_e_contenuta_la_documentazione_necessaria",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val7, isValid);		
		//-----------------------------------------------------------
		//Parere Soprintendenza Archeologica (8)
		String val8="Parere_8";
		qdr=new RuleSampleCustom("TAB_VINC_1","nellaPresenteIstanzaEContenutaLaDocumentazioneNecessariaAiFiniDelRilascioDelParereNullaOsta",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val8, isValid);		
		//-----------------------------------------------------------
		//Parere per Bene in Area Protetta (9)
		String val9="Parere_9";
		qdr=new RuleSampleCustom("TAB_VINC_1","nella_presente_istanza_e_contenuta_la_documentazione_necessaria_ai_fini_del_rilascio_del_parere_nulla_osta",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val9, isValid);	
		//-----------------------------------------------------------
		//Piano del Colore, Arredo Urbano (16)
		String val16="Parere_16";
		qdr=new RuleSampleCustom("TAB_ASS_V","richiesta_verbale",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val16, isValid);			

		//-----------------------------------------------------------
		// TUTELA ECOLOGICA E FUNZIONALE
		//-----------------------------------------------------------
		// Autorizzazione per Vincolo Idrogeologico(10)
		String val10="Autorizzazione_10";
		qdr=new RuleSampleCustom("TAB_VINC_2","dichiarapere_non_rientrano_fra_specifica_autorizzazione",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val10, isValid);			
		//-----------------------------------------------------------
		// Autorizzazione per Zona “Natura 2000”(11)
		String val11="Autorizzazione_11";
		qdr=new RuleSampleCustom("TAB_VINC_2","presente_istanza_contenuta_la_documentazione",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val11, isValid);					
		//-----------------------------------------------------------
		// Parere per Tutela Ecologica(13)
		String val13="Parere_13";
		qdr=new RuleSampleCustom("TAB_VINC_2","istanza_e_contenuta_la_documentazione_necessaria",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val13, isValid);					
		//-----------------------------------------------------------
		// Parere per  Tutela Funzionale(14)
		String val14="Parere_14";
		qdr=new RuleSampleCustom("TAB_VINC_2","vinvolo_documentazione",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val14, isValid);					
		//-----------------------------------------------------------
		// Denuncia Sismica posticipata a inizio lavori strutturali(26)
		String val26="Denuncia_26";
		qdr=new RuleSampleCustom("TAB_VINC_2","documentazione_ad_inizio_lavori",json);		
		isValid=factory.validateRule(qdr);		
		copertina.put(val26, isValid);					
		//-----------------------------------------------------------
		//
		//-----------------------------------------------------------		
		return copertina;
	}

}
