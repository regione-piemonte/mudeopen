/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDSpeciePratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaQuadroUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.GeoriferimentoUpdateTableService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaTemplateResponseEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TemplateQuadroEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDSpeciePraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaQuadroUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaTemplateQuadroService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.ManagerAbilitazioni;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.IstanzaTemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.IstanzaTemplateResponse;

@Service
public class IstanzaTemplateQuadroServiceImpl implements IstanzaTemplateQuadroService {

    private static Logger logger = Logger.getLogger(IstanzaTemplateQuadroServiceImpl.class.getCanonicalName());
    private final String CLS_NAME = this.getClass().getSimpleName();

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private IstanzaTemplateResponseEntityMapper istanzaTemplateResponseEntityMapper;

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;

    @Autowired
    private TemplateQuadroEntityMapper templateQuadroEntityMapper;

    @Autowired
    private MudeRTemplateQuadroRepository mudeRTemplateQuadroRepository;

    @Autowired
    private MudeRIstanzaQuadroUtenteRepository mudeRIstanzaQuadroUtenteRepository;

    @Autowired
    private ManagerAbilitazioni managerAbilitazioni;

    @Autowired
    private IstanzaStatoService istanzaStatoService;

    @Autowired
    private JsonDataHelper jsonDataHelper;

    @Autowired
    FascicoliService fascicoliService;

    @Autowired
    private MudeDSpeciePraticaRepository mudeDSpeciePraticaRepository;

    @Autowired
    private MudeDQuadroRepository mudeDQuadroRepository;

    @Autowired
    private GeoriferimentoUpdateTableService georiferimentoUpdateTableService;

    @Override
    public List<IstanzaTemplateResponse> loadIstanzeTemplateQuadroByIdIstanza(Long idIstanza) {
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        List<MudeRTemplateQuadro> entities = new ArrayList<>();
        if (null != istanza) {
            entities = mudeRTemplateQuadroRepository.findByTemplate(istanza.getTemplate().getIdTemplate());

        }
        return istanzaTemplateResponseEntityMapper.mapListEntityToListVO(istanza, entities);
    }

    @Override
    public IstanzaTemplateQuadroVO loadIstanzaTemplateQuadroByPK(Long idIstanza, Long idTemplateQuadro, MudeTUser mudeTUser, HttpHeaders httpHeaders) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(idIstanza);
        IstanzaTemplateQuadroVO vo = null;
        if (null != istanza) {
            Optional<MudeRTemplateQuadro> templateQuadro = mudeRTemplateQuadroRepository.findByIdTemplateQuadro(idTemplateQuadro);
            if (templateQuadro.isPresent()) {
                IstanzaVO istanzaVO = istanzaEntityMapper.mapEntityToVO(istanza, mudeTUser, "frontoffice-slim1");
                TemplateQuadroVO templateQuadroVO = templateQuadroEntityMapper.mapEntityToSlimVO(templateQuadro.get(), null);
                vo = new IstanzaTemplateQuadroVO();
                vo.setIstanza(istanzaVO);
                vo.setTemplateQuadro(templateQuadroVO);

                // used to store subquadro properties
                vo.setJsonDataSubquadro(templateQuadro.get().getProprieta());

                if(mudeTUser != null)
                	vo.setQuadroModificabile(managerAbilitazioni.canUserChangeQuadro(false, idIstanza, mudeTUser, 
                			httpHeaders, 
                			templateQuadro.get().getMudeDTemplate().getObbligatoriaNominaPM(), 
                			templateQuadro.get().getMudeDQuadro().getIdQuadro(), templateQuadro.get().getMudeDQuadro().getMudeDTipoQuadro().getCodTipoQuadro()));
            }
        }
        return vo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public IstanzaTemplateQuadroVO saveIstanzaTemplateQuadro(IstanzaTemplateQuadroVO istanzaTemplateQuadro, 
    				boolean isMainJSONModified,
    				boolean isMainQuadroValidated, 
    				MudeTUser mudeTUser, 
    				HttpHeaders httpHeaders) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "] Parametro in input IstanzaTemplateQuadroVO [" + istanzaTemplateQuadro + "]");

        MudeTIstanza istanza = mudeTIstanzaRepository.findOneAndLockRow(istanzaTemplateQuadro.getIstanza().getIdIstanza());
        if (null != istanza) {
            MudeDQuadro mainMudeDQuadro = null;
            Optional<MudeRTemplateQuadro> templateQuadroOPT = null;

            if(istanzaTemplateQuadro.getTemplateQuadro().getIdTemplateQuadro() != null) // main quadro details infos indirectly retrieved from idTemplateQuadro
            	templateQuadroOPT  = mudeRTemplateQuadroRepository.findByIdTemplateQuadro(istanzaTemplateQuadro.getTemplateQuadro().getIdTemplateQuadro());
            else  // or main quadro details infos directly retrieved from idQuadro (implies SubQuadro details are provided too) 
                mainMudeDQuadro = mudeDQuadroRepository.findOne(istanzaTemplateQuadro.getTemplateQuadro().getQuadro().getIdQuadro());

            if((templateQuadroOPT != null && templateQuadroOPT.isPresent()) 
            		|| mainMudeDQuadro != null) {
                MudeRTemplateQuadro tq = templateQuadroOPT == null? null : templateQuadroOPT.get();

        		Boolean isObbligatoriaNominaPM = tq == null ? false : tq.getMudeDTemplate().getObbligatoriaNominaPM(); 
        		Long idQuadro = istanzaTemplateQuadro.getIdSubQuadro();
        		String codTipoQuadro = istanzaTemplateQuadro.getCodeSubQuadro();
        		
        		if(idQuadro == null)
        			idQuadro = tq.getMudeDQuadro().getIdQuadro(); // just in case of MudeRTemplateQuadro != null
        		if(codTipoQuadro == null)
        			codTipoQuadro = tq.getMudeDQuadro().getMudeDTipoQuadro().getCodTipoQuadro(); // just in case of MudeRTemplateQuadro != null

                if(istanzaTemplateQuadro.getJsonDataQuadro() != null || istanzaTemplateQuadro.getJsonDataSubquadro() != null)
            		// if data validation is needed to be store, then check the if the user har proper rights 
	                if(!managerAbilitazioni.canUserChangeQuadro(true, istanza.getId(), mudeTUser, httpHeaders, isObbligatoriaNominaPM, idQuadro, codTipoQuadro))
	                    throw new BusinessException("Non sei abilitato ad eseguire questa operazione");

                String tipoQuadroMain = (tq == null? mainMudeDQuadro : tq.getMudeDQuadro()).getTopMostCodiceTipoQuadro();
                this.updateJsonDataIstanza(istanza, 
                			tipoQuadroMain, 
                			istanzaTemplateQuadro,
                			isMainJSONModified,
                			isMainQuadroValidated, 
                			mudeTUser);

                //tabella in cui si registrano solo le variazioni del jsondata
                // ... è in pratica un log
                MudeRIstanzaQuadroUtente mudeRIstanzaQuadroUtente = new MudeRIstanzaQuadroUtente();
                mudeRIstanzaQuadroUtente.setIdIstanza(istanza.getId());
                mudeRIstanzaQuadroUtente.setIdUtente(mudeTUser.getIdUser());
                mudeRIstanzaQuadroUtente.setIdQuadro(idQuadro); // idSubQuadro || idQuadro
                mudeRIstanzaQuadroUtente.setDataModifica(Calendar.getInstance().getTime());
                mudeRIstanzaQuadroUtenteRepository.saveAndFlushDAO(mudeRIstanzaQuadroUtente);

                // unused by now
                //istanzaTemplateQuadro.setIstanza(istanzaEntityMapper.mapEntityToVO(istanza, mudeTUser));
                //istanzaTemplateQuadro.setTemplateQuadro(templateQuadroEntityMapper.mapEntityToVO(tq, null));
            }
        }

        return istanzaTemplateQuadro;
    }

	@Override
	public IstanzaTemplateQuadroVO updateIstanzaTemplateQuadro(IstanzaTemplateQuadroVO istanzaTemplateQuadro) {
		return null; // DISMISSED  
	}

    private void updateJsonDataIstanza(MudeTIstanza istanza, 
    									String tipoQuadroMain,
    									IstanzaTemplateQuadroVO istanzaTemplateQuadro,
    				    				boolean isMainJSONModified,
    				    				boolean isMainQuadroValidated, 
    									MudeTUser mudeTUser) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "]");

		String codiceTipoSubQuadro = istanzaTemplateQuadro.getCodeSubQuadro(); 
		String strJSONDataQuadro = istanzaTemplateQuadro.getJsonDataQuadro(); 
		String strJSONDataSubquadro = istanzaTemplateQuadro.getJsonDataSubquadro();

		// if completed and no JSON passed, then get exiting in order to update the validation time
		boolean isJustQuadroValidationConfirm = strJSONDataQuadro == null && strJSONDataSubquadro == null;
				
		MudeTIndirizzo indirizzo = null;
        if (null != istanza) {
            try {
                JSONObject jsonDataIstanza;
                if(istanza.getJsonData() == null)
                	jsonDataIstanza = new JSONObject();
                else
                    jsonDataIstanza = new JSONObject(istanza.getJsonData());

                // set jdata for main quadro
        		JSONObject mainQuadroJDATA = null;
        		if(isJustQuadroValidationConfirm) {
        			if(jsonDataIstanza.has(tipoQuadroMain))
        				mainQuadroJDATA = jsonDataIstanza.getJSONObject(tipoQuadroMain);
        			else
        				mainQuadroJDATA = new JSONObject();
        		}
        		else {
            		if(strJSONDataQuadro == null)
            			mainQuadroJDATA = jsonDataIstanza.getJSONObject(tipoQuadroMain);
            		else
            			mainQuadroJDATA = new JSONObject(strJSONDataQuadro);
	
	                jsonDataIstanza.put(tipoQuadroMain, mainQuadroJDATA);
	                
	                if(codiceTipoSubQuadro != null)
		                jsonDataIstanza.put(codiceTipoSubQuadro, new JSONObject(strJSONDataSubquadro));
	                
	                // updates jDataInfo params
	                managerAbilitazioni.preventChangesFromOtherUsers(jsonDataIstanza, tipoQuadroMain, mudeTUser);
	                
	                checkStatoPerModifiche(istanza, tipoQuadroMain);
        		}

        		if(isMainJSONModified) {
        			// set validation time in case all the data 
        			mainQuadroJDATA.put("_VALIDATION_TIME", isMainQuadroValidated ? System.currentTimeMillis() : null);
        			// removes template updated flag, this make disappear the user banner message  
                	jsonDataIstanza.remove(IstanzaVO._NEW_TEMPLATE);

                	// set quadro as compiled
            		if(isMainQuadroValidated)
            			mainQuadroJDATA.put("_COMPILED", "true");
        		}

        		// removes all subordinated JData keys
        		String keys2remove = istanzaTemplateQuadro.getjDataKeysToDelete();
        		if(StringUtils.isNotBlank(keys2remove))
        			for(String key2remove : keys2remove.split(","))
        				if(jsonDataIstanza.has(key2remove)) {
        					jsonDataIstanza.remove(key2remove);
                			mainQuadroJDATA.put("_VALIDATION_TIME", (Long)null);
                			mainQuadroJDATA.put("_COMPILED", "false");
        				}

                if(isMainJSONModified && "QDR_LOCALIZZAZIONE".equals(tipoQuadroMain)) {
                	try {
		                indirizzo = jsonDataHelper.getIndirizzoFromJson(istanza, jsonDataIstanza);
		                if (null != indirizzo)
		                    istanza.setIndirizzo(indirizzo);
					} catch (Exception skip) {
		                LoggerUtil.error(logger, "[" + CLS_NAME + "::" + methodName  + " - updateFolderAddressFromIstanza] ERROR : " + skip, skip);
					}
        		}

                if(isMainJSONModified 
                		&& ("TAB_QUALIF_1".equals(codiceTipoSubQuadro) 
                				|| "TAB_CILAS_QUALIF_9".equals(codiceTipoSubQuadro) 
                				|| "TAB_IND_PROC".equals(codiceTipoSubQuadro) 
                				|| "TAB_FIL_COMUNIC".equals(codiceTipoSubQuadro) 
                				|| "QDR_PROROGA".equals(codiceTipoSubQuadro))
                		/*"QDR_QUALIFIC".equals(tipoQuadroMain)*/) {
                	try {
		                String codiceSpeciePratica = jsonDataHelper.getCodiceSpecieFromJson(jsonDataIstanza);
		                if (null != codiceSpeciePratica) {
		                    MudeDSpeciePratica speciePratica = mudeDSpeciePraticaRepository.findOne(codiceSpeciePratica);
		                    if(null != speciePratica) {
		                        istanza.setSpeciePratica(speciePratica);
		                        
	        					jsonDataIstanza.put("sanatoria", !speciePratica.getFlagSanatoria()? "no" : "si");
	        					jsonDataIstanza.put("variante", !speciePratica.getFlagVariante()? "no" : "si");
	        					if(strJSONDataSubquadro != null) 
	        						jsonDataIstanza.put("_tipo_istanza", strJSONDataSubquadro.indexOf("titoloAbilitativoCartaceo")>-1? "#TITOLO_CATACEO#" : "#TITOLO_DIGITALE#");
		                    }
		                }
					} catch (Exception skip) {
		                LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "] ERROR : " + skip);
					}
        		}

            	// updates entire JData
                istanza.setJsonData(jsonDataIstanza.toString());
                mudeTIstanzaRepository.saveDAO(istanza);

                if(isMainJSONModified && "QDR_LOCALIZZAZIONE".equals(tipoQuadroMain)) {
                	try {
		                if (null != indirizzo) {
		        			fascicoliService.updateFolderAddressFromIstanza(istanza, false);
		                }
					} catch (Exception skip) {
		                LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName  + " - updateFolderAddressFromIstanza] ERROR : " + skip);
					}
		                
                	try {
	        	        georiferimentoUpdateTableService.updateGeoriferimentoTables(istanza.getId());
					} catch (Exception skip) {
		                LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName  + " - updateGeoriferimentoTables] ERROR : " + skip);
					}
        		}

                istanzaTemplateQuadro.setJsonDataQuadro(null);
            	// set mainQuadro JsonData to be returned to the client (TODO: set to null when the client uses just istanza.json_data property)
                istanzaTemplateQuadro.setJsonDataQuadro(mainQuadroJDATA.toString());
            	// set all JsonData to be returned to the client
                IstanzaVO istanza2 = new IstanzaVO();
                
                String jsonString = jsonDataIstanza.toString().replaceAll("[\\\\]u00[0-1][A-Z0-9]{1,1}", ""); // replaces control chars below \u0020
                istanza2.setJsonData(jsonString);                	
				istanzaTemplateQuadro.setIstanza(istanza2);

            } catch (JSONException e) {
                LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "] ERROR : " + e);
            }
        }
    }

    private void checkStatoPerModifiche(MudeTIstanza istanza, String tipoQuadroMain) {
        //fixme mettere lo stato bozza
        IstanzaStatoSlimVO vo = istanzaStatoService.findStatoByIstanzaSlim(istanza.getId());
        if (null != vo) {
            DizionarioVO statoIstanza = vo.getStatoIstanza();
            String codiceStato = statoIstanza.getCodice();
            if(!(codiceStato.equalsIgnoreCase(StatoIstanza.BOZZA.getValue())
            		|| codiceStato.equalsIgnoreCase(StatoIstanza.DA_FIRMARE.getValue())
            		|| codiceStato.equalsIgnoreCase(StatoIstanza.RESTITUITA_PER_VERIFICHE.getValue())
            		|| codiceStato.equalsIgnoreCase(StatoIstanza.FIRMATA.getValue())))
                throw new BusinessException("Modifica non consentita su istanza inoltrata [numero_istanza: " + istanza.getCodiceIstanza() + "]");
            else if(!"QDR_PRESENTA".equals(tipoQuadroMain)) {
            	// DISMISSED DUE TO MANUAL HANDLING: istanzaStatoService.saveIstanzaStato(istanza.getId(), StatoIstanza.BOZZA.getValue(), null, httpHeaders);
            }
        }
    }

}