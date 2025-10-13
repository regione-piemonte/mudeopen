/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDNazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDDugRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIndirizzoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONArray;

@Component
public class JsonDataHelper {

    @Autowired
    private MudeDDugRepository mudeDDugRepository;

    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;

    @Autowired
    private MudeTIndirizzoRepository mudeTIndirizzoRepository;

    public String getCodiceSpecieFromJson(JSONObject objSpeciePratica) throws Exception {
        JSONObject tabQualif1 = null;

        if(objSpeciePratica.has("TAB_QUALIF_1"))
        	tabQualif1 = (JSONObject) objSpeciePratica.get("TAB_QUALIF_1");
        if(objSpeciePratica.has("TAB_FIL_COMUNIC"))
        	tabQualif1 = (JSONObject) objSpeciePratica.get("TAB_FIL_COMUNIC");
        if(objSpeciePratica.has("QDR_PROROGA"))
        	tabQualif1 = (JSONObject) objSpeciePratica.get("QDR_PROROGA");
        if(objSpeciePratica.has("TAB_CILAS_QUALIF_9"))
        	tabQualif1 = (JSONObject) objSpeciePratica.get("TAB_CILAS_QUALIF_9");
        if(objSpeciePratica.has("TAB_IND_PROC"))
        	tabQualif1 = (JSONObject) objSpeciePratica.get("TAB_IND_PROC");

        if(tabQualif1 != null)
            return  null != tabQualif1.get("chk_comunica") ? (String) tabQualif1.get("chk_comunica") : null;

        return null;
    }

    public MudeTIndirizzo getIndirizzoFromJson(MudeTIstanza istanza, JSONObject objIndirizzo) throws Exception {
        JSONObject tabLocal1 = (JSONObject) objIndirizzo.get("TAB_LOCAL_1");

        JSONArray dataGrid = (JSONArray) tabLocal1.get("dataGrid");
        for (int i=0; i<dataGrid.length(); i++) {
            JSONObject item = (JSONObject) dataGrid.getJSONObject(i);
            Boolean isIndirizzoPrincipale = item.has("selezionare_se_si_tratta_di_indirizzo_principale") ? (Boolean) item.get("selezionare_se_si_tratta_di_indirizzo_principale") : Boolean.FALSE;
            if (isIndirizzoPrincipale) {
                MudeTIndirizzo indirizzo = null != istanza.getIndirizzo() ? istanza.getIndirizzo() : new MudeTIndirizzo();
                String sedime = item.has("sedime") ? item.get("sedime").toString() : null;
                MudeDDug dug = mudeDDugRepository.findByDenominazioneAndDataFineIsNull(sedime.toUpperCase());
                if (null != dug) {
                    indirizzo.setIdDug(dug.getIdDug());
                }
                String denominazione = item.has("denominazione") ? item.get("denominazione").toString() : null;
                indirizzo.setIndirizzo(denominazione);
                String num = item.has("n") ? item.get("n").toString() : null;
                indirizzo.setNumeroCivico(num);

                MudeDComune comune = istanza.getComune();
                if (null != comune) {
                    indirizzo.setMudeDComune(comune);
                    MudeDProvincia provincia = comune.getMudeDProvincia();
                    MudeDRegione regione = null != provincia ? provincia.getMudeDRegione() : null;
                    MudeDNazione nazione = null != regione ? regione.getMudeDNazione() : null;
                    if (null != nazione) {
                        indirizzo.setMudeDNazione(nazione);
                    }
                }

                mudeTIndirizzoRepository.saveAndFlushDAO(indirizzo);
                return indirizzo;
            }
        }
        return null;
    }
}