/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.JsonUtils;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.SoggettoIstanzaVO;

@Service
public class IstanzaServiceUtil {
	protected static final Logger logger = Logger.getLogger(IstanzaServiceImpl.class.getCanonicalName());
	
    @Autowired
    protected IstanzaStatoService istanzaStatoService;

    @Autowired
    protected ContattoEntityMapper contattoEntityMapper;

    @Autowired
    protected MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    protected IndexManager indexManager;

    protected void checkStatoPerModifiche(MudeTIstanza istanza) {
        IstanzaStatoSlimVO vo = istanzaStatoService.findStatoByIstanzaSlim(istanza.getId());
        if (null != vo) {
            DizionarioVO statoIstanza = vo.getStatoIstanza();
            String codiceStato = statoIstanza.getCodice();
            if (codiceStato.equalsIgnoreCase(StatoIstanza.DA_FIRMARE.getValue()) || codiceStato.equalsIgnoreCase(StatoIstanza.FIRMATA.getValue())) {
                // DISMISSED DUE TO MANUAL HANDLING: istanzaStatoService.saveIstanzaStato(istanza.getId(), StatoIstanza.BOZZA.getValue(), null, httpHeaders);
            } else if (codiceStato.equalsIgnoreCase(StatoIstanza.DEPOSITATA.getValue())) {
                throw new BusinessException("Modifica non consentita su istanza inoltrata [numero_istanza: " + istanza.getCodiceIstanza() + "]");
            }
        }
    }

    protected void aggiornaJsonDataPF(SoggettoIstanzaVO soggetto, MudeTContatto mudeTContatto, MudeTIstanza mudeTIstanza, MudeRIstanzaSoggetto mudeRIstanzaSoggetto, List<String> ruoli) throws JSONException {
        JSONObject jsonDataIstanza = new JSONObject(mudeTIstanza.getJsonData());
        String jsonStringContact = JsonUtils.toJsonString(contattoEntityMapper.mapEntityToVO(mudeRIstanzaSoggetto.getMudeTContatto(), null));
        String cf = mudeTContatto.getCf();
        if (jsonDataIstanza.has("QDR_SOGGETTO_COINV")) {
            JSONObject jsonDataCoinv = (JSONObject) jsonDataIstanza.get("QDR_SOGGETTO_COINV");
            if (jsonDataCoinv.has("subjectList")) {
                JSONObject subjectList = (JSONObject) jsonDataCoinv.get("subjectList");
                if (subjectList.has(mudeTContatto.getCf())) {
                    JSONObject jsonDataCoinvCF = (JSONObject) subjectList.get(cf);
                    jsonDataCoinvCF.put("contact", new JSONObject(jsonStringContact));
                    jsonDataCoinvCF.put("idSoggetto", mudeRIstanzaSoggetto.getMudeTContatto().getId());
                }
            }
        }

        if (jsonDataIstanza.has("QDR_SOGGETTO_ABILIT")) {
            JSONObject jsonDataAbilit = (JSONObject) jsonDataIstanza.get("QDR_SOGGETTO_ABILIT");
            if (jsonDataAbilit.has("delegatiList")) {
                JSONObject delegatiList = (JSONObject) jsonDataAbilit.get("delegatiList");
                if (delegatiList.has(mudeTContatto.getCf())) {
                    JSONObject jsonDataAbilitCF = (JSONObject) delegatiList.get(cf);
                    jsonDataAbilitCF.put("contact", new JSONObject(jsonStringContact));
                    jsonDataAbilitCF.put("idSoggetto", mudeRIstanzaSoggetto.getMudeTContatto().getId());
                    //todo inserice codifica ruolo delegante
                } else if (ruoli.contains("DE")) {
                    if (StringUtils.isNotBlank(soggetto.getRuoliPossibili())) {
                        JSONArray arr = new JSONArray(soggetto.getRuoliPossibili());
                        JSONObject j = new JSONObject();
                        j.put("roles", arr);
                        j.put("contact", new JSONObject(jsonStringContact));
                        j.put("idSoggetto", mudeRIstanzaSoggetto.getMudeTContatto().getId());
                        delegatiList.put(cf, j);
                    }
                }
            }
        }
        mudeTIstanza.setJsonData(jsonDataIstanza.toString());

        checkStatoPerModifiche(mudeTIstanza);
    }

    protected void aggiornaJsonDataPG(MudeTContatto mudeTContatto, MudeTIstanza mudeTIstanza, MudeRIstanzaSoggetto mudeRIstanzaSoggetto, List<String> ruoli) throws JSONException {
        JSONObject jsonDataIstanza = new JSONObject(mudeTIstanza.getJsonData());
        String jsonStringContact = JsonUtils.toJsonString(contattoEntityMapper.mapEntityToVO(mudeRIstanzaSoggetto.getMudeTContatto(), null));
        String piva = null != mudeTContatto.getPiva() ? mudeTContatto.getPiva() : mudeTContatto.getPivaComunitaria();
        if (jsonDataIstanza.has("QDR_SOGGETTO_COINV")) {
            JSONObject jsonDataCoinv = (JSONObject) jsonDataIstanza.get("QDR_SOGGETTO_COINV");
            if (jsonDataCoinv.has("subjectList")) {
                JSONObject subjectList = (JSONObject) jsonDataCoinv.get("subjectList");
                if (subjectList.has(piva)) {
                    JSONObject jsonDataCoinvPIVA = (JSONObject) subjectList.get(piva);
                    jsonDataCoinvPIVA.put("contact", new JSONObject(jsonStringContact));
                    jsonDataCoinvPIVA.put("idSoggetto", mudeRIstanzaSoggetto.getMudeTContatto().getId());
                }
            }
        }
        if (jsonDataIstanza.has("QDR_SOGGETTO_ABILIT")) {
            JSONObject jsonDataAbilit = (JSONObject) jsonDataIstanza.get("QDR_SOGGETTO_ABILIT");
            if (jsonDataAbilit.has("delegatiList")) {
                JSONObject delegatiList = (JSONObject) jsonDataAbilit.get("delegatiList");
                if (delegatiList.has(piva)) {
                    JSONObject jsonDataCoinvPIVA = (JSONObject) delegatiList.get(piva);
                    jsonDataCoinvPIVA.put("contact", new JSONObject(jsonStringContact));
                    jsonDataCoinvPIVA.put("idSoggetto", mudeRIstanzaSoggetto.getMudeTContatto().getId());
                }
            }
        }
        mudeTIstanza.setJsonData(jsonDataIstanza.toString());
        checkStatoPerModifiche(mudeTIstanza);
    }

    protected void removeSubjectFromJData(MudeTIstanza mudeTIstanza, MudeTContatto mudeTContatto, boolean isDelegante) {
        if(!StringUtils.isNotBlank(mudeTIstanza.getJsonData()))
        	return;

        try {
            JSONObject jsonDataIstanza = new JSONObject(mudeTIstanza.getJsonData());
            if (mudeTContatto.getTipoContatto().name().equalsIgnoreCase(TipoContatto.PF.name())) {
                if (jsonDataIstanza.has("QDR_SOGGETTO_COINV")) {
                    JSONObject jsonDataCoinv = (JSONObject) jsonDataIstanza.get("QDR_SOGGETTO_COINV");
                    if (jsonDataCoinv.has("subjectList")) {
                        JSONObject subjectList = (JSONObject) jsonDataCoinv.get("subjectList");
                        if (subjectList.has(mudeTContatto.getCf())) {
                            subjectList.remove(mudeTContatto.getCf());
                        }
                    }
                }
                if (isDelegante) {
                    if (jsonDataIstanza.has("QDR_SOGGETTO_ABILIT")) {
                        JSONObject jsonDataAbilit = (JSONObject) jsonDataIstanza.get("QDR_SOGGETTO_ABILIT");
                        if (jsonDataAbilit.has("delegatiList")) {
                            JSONObject delegatiList = (JSONObject) jsonDataAbilit.get("delegatiList");
                            if (delegatiList.has(mudeTContatto.getCf())) {
                                delegatiList.remove(mudeTContatto.getCf());
                            }
                        }
                    }
                }
                mudeTIstanza.setJsonData(jsonDataIstanza.toString());

            } else {
                String piva = null != mudeTContatto.getPiva() ? mudeTContatto.getPiva() : mudeTContatto.getPivaComunitaria();
                if (jsonDataIstanza.has("QDR_SOGGETTO_COINV")) {
                    JSONObject jsonDataCoinv = (JSONObject) jsonDataIstanza.get("QDR_SOGGETTO_COINV");
                    if (jsonDataCoinv.has("subjectList")) {
                        JSONObject subjectList = (JSONObject) jsonDataCoinv.get("subjectList");
                        if (subjectList.has(piva)) {
                            subjectList.remove(piva);
                        }
                    }
                }
                if (isDelegante) {
                    if (jsonDataIstanza.has("QDR_SOGGETTO_ABILIT")) {
                        JSONObject jsonDataAbilit = (JSONObject) jsonDataIstanza.get("QDR_SOGGETTO_ABILIT");
                        if (jsonDataAbilit.has("delegatiList")) {
                            JSONObject delegatiList = (JSONObject) jsonDataAbilit.get("delegatiList");
                            if (delegatiList.has(piva)) {
                                delegatiList.remove(piva);
                            }
                        }
                    }
                }

                mudeTIstanza.setJsonData(jsonDataIstanza.toString());
            }
            mudeTIstanzaRepository.saveDAO(mudeTIstanza);
            //fixme aggiungere rimozione logica da tabella muderistanzautente
        } catch (JSONException e) {
            LoggerUtil.debug(logger, "[IstanzaApiServiceImpl::eliminaSoggettoCoinvolto] ERROR updating jsondata " + e.getMessage());
        }
    }

}